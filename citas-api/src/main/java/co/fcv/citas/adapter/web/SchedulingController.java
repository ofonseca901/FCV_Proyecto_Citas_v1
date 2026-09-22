package co.fcv.citas.adapter.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api")
public class SchedulingController {
  private final SchedulingFacade service;
  public SchedulingController(SchedulingFacade service){this.service=service;}
  public record SpecialtyRequest(@NotBlank @Pattern(regexp="[A-Z0-9_]{3,50}") String code,@NotBlank @Size(max=150) String name,@Min(30) @Max(60) int durationMinutes,boolean general){}
  public record ProfessionalRequest(@Positive long userId,@NotBlank @Size(max=40) String professionalCode,@NotBlank @Size(max=80) String licenseNumber){}
  public record AssignmentRequest(@Positive long specialtyId,boolean primarySpecialty){}
  public record LocationRequest(@Positive long locationId){}
  public record BlockRequest(@Positive long professionalId,@Positive long locationId,@NotNull LocalDate date,@NotNull LocalTime startTime,@NotNull LocalTime endTime){}
  public record BookingRequest(@Positive long professionalId,@Positive long locationId,@Positive long specialtyId,@NotNull LocalDateTime startAt){}
  public record DecisionRequest(boolean approve,@Size(max=500) String reason){}
  @GetMapping("/catalogs/locations") public List<Map<String,Object>> locations(){return service.locations();}
  @GetMapping("/catalogs/specialties") public List<Map<String,Object>> specialties(){return service.specialties();}
  @PostMapping("/admin/specialties") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('ADMIN')") public Map<String,Long> specialty(@Valid @RequestBody SpecialtyRequest r){return Map.of("id",service.specialty(r.code(),r.name(),r.durationMinutes(),r.general()));}
  @PostMapping("/admin/professionals") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('ADMIN')") public Map<String,Long> professional(@Valid @RequestBody ProfessionalRequest r){return Map.of("id",service.professional(r.userId(),r.professionalCode(),r.licenseNumber()));}
  @PostMapping("/admin/professionals/{id}/specialties") @PreAuthorize("hasRole('ADMIN')") public void assignSpecialty(@PathVariable long id,@Valid @RequestBody AssignmentRequest r){service.specialty(id,r.specialtyId(),r.primarySpecialty());}
  @PostMapping("/admin/professionals/{id}/locations") @PreAuthorize("hasRole('ADMIN')") public void assignLocation(@PathVariable long id,@Valid @RequestBody LocationRequest r){service.location(id,r.locationId());}
  @PostMapping("/professional/blocks") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('PROFESSIONAL')") public Map<String,Long> block(@AuthenticationPrincipal Jwt jwt,@Valid @RequestBody BlockRequest r){return Map.of("id",service.block(user(jwt),r.professionalId(),r.locationId(),r.date(),r.startTime(),r.endTime()));}
  @GetMapping("/professional/{id}/blocks") @PreAuthorize("hasRole('PROFESSIONAL')") public List<Map<String,Object>> blocks(@AuthenticationPrincipal Jwt jwt,@PathVariable long id,@RequestParam LocalDate date){return service.blocks(user(jwt),id,date);}
  @GetMapping("/availability") public List<Map<String,Object>> availability(@RequestParam long locationId,@RequestParam long specialtyId,@RequestParam LocalDate date){return service.availability(locationId,specialtyId,date);}
  @PostMapping("/appointments") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('USER')") public Map<String,Long> book(@AuthenticationPrincipal Jwt jwt,@Valid @RequestBody BookingRequest r){return Map.of("id",service.book(user(jwt),r.professionalId(),r.locationId(),r.specialtyId(),r.startAt()));}
  @GetMapping("/admin/appointments/requested") @PreAuthorize("hasRole('ADMIN')") public List<Map<String,Object>> requested(){return service.requested();}
  @PostMapping("/admin/appointments/{id}/decision") @PreAuthorize("hasRole('ADMIN')") public void decide(@AuthenticationPrincipal Jwt jwt,@PathVariable long id,@Valid @RequestBody DecisionRequest r){service.decide(user(jwt),id,r.approve(),r.reason());}
  private long user(Jwt jwt){return Long.parseLong(jwt.getSubject());}
}
