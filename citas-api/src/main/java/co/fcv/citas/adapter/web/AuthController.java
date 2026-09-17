package co.fcv.citas.adapter.web;

import co.fcv.citas.application.AuthService;
import co.fcv.citas.domain.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.Set;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final AuthFacade facade;
    public AuthController(AuthFacade facade) { this.facade = facade; }
    public record RegisterRequest(
            @NotBlank @Size(max = 100) String firstName,
            @NotBlank @Size(max = 100) String lastName,
            @NotBlank @Pattern(regexp = "CC|CE|TI|PA|PPT") String documentType,
            @NotBlank @Pattern(regexp = "[A-Za-z0-9-]{3,30}") String documentNumber,
            @NotBlank @Email @Size(max = 254) String email,
            @NotBlank @Pattern(regexp = "[+0-9 ()-]{7,25}") String phone,
            @NotBlank @Size(min = 8, max = 72) String password) {}
    public record LoginRequest(@NotBlank @Email @Size(max = 254) String email,
                               @NotBlank @Size(max = 72) String password) {}
    public record RefreshRequest(@NotBlank @Size(max = 4096) String refreshToken) {}
    public record UserResponse(Long id, String firstName, String lastName, String email, Set<String> roles) {
        static UserResponse from(User u) { return new UserResponse(u.id(), u.firstName(), u.lastName(), u.email(), u.roles()); }
    }
    public record SessionResponse(String accessToken, String refreshToken, String tokenType, long expiresIn, UserResponse user) {
        static SessionResponse from(AuthService.Login l) {
            return new SessionResponse(l.tokens().accessToken(), l.tokens().refreshToken(), l.tokens().tokenType(),
                    l.tokens().expiresIn(), UserResponse.from(l.user()));
        }
    }
    @PostMapping("/register") public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest r) {
        User user = facade.register(new AuthService.Registration(r.firstName(), r.lastName(), r.documentType(),
                r.documentNumber(), r.email(), r.phone(), r.password()));
        return ResponseEntity.status(201).cacheControl(CacheControl.noStore()).body(UserResponse.from(user));
    }
    @PostMapping("/login") public ResponseEntity<SessionResponse> login(@Valid @RequestBody LoginRequest r) {
        return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(SessionResponse.from(facade.login(r.email(), r.password())));
    }
    @PostMapping("/refresh") public ResponseEntity<SessionResponse> refresh(@Valid @RequestBody RefreshRequest r) {
        return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(SessionResponse.from(facade.refresh(r.refreshToken())));
    }
    @GetMapping("/me") public ResponseEntity<UserResponse> me(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(UserResponse.from(facade.identity(Long.valueOf(jwt.getSubject()), jwt.getClaimAsString("sid"))));
    }
    @PostMapping("/logout") public ResponseEntity<Void> logout(@AuthenticationPrincipal Jwt jwt) {
        facade.logout(Long.valueOf(jwt.getSubject()), jwt.getClaimAsString("sid"));
        return ResponseEntity.noContent().cacheControl(CacheControl.noStore()).build();
    }
}
