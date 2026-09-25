package co.fcv.citas.adapter.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Reads only public bibliographic metadata. Articles are never persisted with patient or account data.
 */
@RestController
@RequestMapping("/api/v1/professional")
@PreAuthorize("hasRole('PROFESSIONAL')")
public class ProfessionalSpecialtyController {
  private static final Duration CACHE_TTL = Duration.ofMinutes(30);
  private final JdbcTemplate jdbc;
  private final ObjectMapper json;
  private final HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(4)).build();
  private final Map<String, CachedArticle> cache = new ConcurrentHashMap<>();

  ProfessionalSpecialtyController(JdbcTemplate jdbc, ObjectMapper json) {
    this.jdbc = jdbc;
    this.json = json;
  }

  @GetMapping("/specialties")
  public List<SpecialtyInsight> specialties(@AuthenticationPrincipal Jwt jwt) {
    long userId = Long.parseLong(jwt.getSubject());
    List<Map<String, Object>> specialties = jdbc.queryForList(
        "select s.id,s.name,s.code,ps.primary_specialty as primarySpecialty from professionals p "
            + "join professional_specialties ps on ps.professional_id=p.id "
            + "join specialties s on s.id=ps.specialty_id and s.active=true where p.user_id=? and p.active=true "
            + "order by ps.primary_specialty desc,s.name", userId);
    List<SpecialtyInsight> result = new ArrayList<>();
    for (Map<String, Object> specialty : specialties) {
      String name = (String) specialty.get("name");
      result.add(new SpecialtyInsight(((Number) specialty.get("id")).longValue(), name,
          (String) specialty.get("code"), Boolean.TRUE.equals(specialty.get("primarySpecialty")), latest(name)));
    }
    return result;
  }

  private Article latest(String specialty) {
    CachedArticle cached = cache.get(specialty);
    if (cached != null && cached.expiresAt().isAfter(Instant.now())) return cached.article();
    Article article = lookup(specialty);
    cache.put(specialty, new CachedArticle(article, Instant.now().plus(CACHE_TTL)));
    return article;
  }

  private Article lookup(String specialty) {
    try {
      String query = URLEncoder.encode("\"" + specialty + "\"[Title/Abstract]", StandardCharsets.UTF_8);
      JsonNode search = get("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&retmode=json&retmax=1&sort=pub+date&term=" + query);
      JsonNode ids = search.path("esearchresult").path("idlist");
      if (!ids.isArray() || ids.isEmpty()) return Article.unavailable();
      String id = ids.get(0).asText();
      JsonNode summary = get("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esummary.fcgi?db=pubmed&retmode=json&id=" + id).path("result").path(id);
      if (summary.isMissingNode()) return Article.unavailable();
      String author = summary.path("authors").isArray() && !summary.path("authors").isEmpty()
          ? summary.path("authors").get(0).path("name").asText("") : "";
      return new Article(summary.path("title").asText("Publicación reciente"), summary.path("fulljournalname").asText("PubMed"),
          summary.path("pubdate").asText(""), author, "https://pubmed.ncbi.nlm.nih.gov/" + id + "/", true);
    } catch (Exception ignored) {
      return Article.unavailable();
    }
  }

  private JsonNode get(String url) throws Exception {
    HttpRequest request = HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(7))
        .header("Accept", "application/json").GET().build();
    HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
    if (response.statusCode() != 200) throw new IllegalStateException("Public source unavailable");
    return json.readTree(response.body());
  }

  public record SpecialtyInsight(long id, String name, String code, boolean primarySpecialty, Article latestArticle) {}
  public record Article(String title, String journal, String publishedAt, String firstAuthor, String url, boolean available) {
    static Article unavailable() { return new Article("Sin publicación disponible en este momento", "PubMed", "", "", "", false); }
  }
  private record CachedArticle(Article article, Instant expiresAt) {}
}
