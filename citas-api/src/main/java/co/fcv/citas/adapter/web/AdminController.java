package co.fcv.citas.adapter.web;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import co.fcv.citas.application.AuthPorts;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
  private final JdbcTemplate jdbc; private final AuthPorts.Passwords passwords;
  public AdminController(JdbcTemplate jdbc, AuthPorts.Passwords passwords){this.jdbc=jdbc;this.passwords=passwords;}
  public record UserRequest(@NotBlank @Size(max=100) String firstName,@NotBlank @Size(max=100) String lastName,@NotBlank @Pattern(regexp="CC|CE|TI|PA|PPT") String documentType,@NotBlank @Size(max=30) String documentNumber,@NotBlank @Email String email,@NotBlank @Size(max=25) String phone,@NotBlank @Size(min=8,max=72) String password){}
  public record ActiveRequest(boolean active){}
  @GetMapping("/users") public Map<String,Object> users(@RequestParam(defaultValue="") String q,@RequestParam(defaultValue="0") @Min(0) int page,@RequestParam(defaultValue="20") @Min(1) @Max(100) int size){
    String like="%"+q.strip()+"%"; int offset=page*size;
    List<Map<String,Object>> items=jdbc.queryForList("select u.id,u.first_name as firstName,u.last_name as lastName,u.document_type as documentType,u.document_number as documentNumber,u.email,u.phone,u.active,u.created_at as createdAt, group_concat(r.role_code order by r.role_code) as roles from app_users u join user_roles r on r.user_id=u.id where (u.first_name like ? or u.last_name like ? or u.email like ? or u.document_number like ?) group by u.id order by u.last_name,u.first_name limit ? offset ?",like,like,like,like,size,offset);
    Integer total=jdbc.queryForObject("select count(*) from app_users where first_name like ? or last_name like ? or email like ? or document_number like ?",Integer.class,like,like,like,like);
    return Map.of("items",items,"total",Objects.requireNonNullElse(total,0),"page",page,"size",size);
  }
  @PostMapping("/users") @ResponseStatus(HttpStatus.CREATED) public Map<String,Long> create(@Valid @RequestBody UserRequest r){
    try { jdbc.update("insert into app_users(first_name,last_name,document_type,document_number,email,phone,password_hash,active,created_at) values(?,?,?,?,?,?,?,?,?)",r.firstName().strip(),r.lastName().strip(),r.documentType(),r.documentNumber().strip(),r.email().strip().toLowerCase(Locale.ROOT),r.phone().strip(),passwords.hash(r.password()),true,Timestamp.from(Instant.now())); Long id=jdbc.queryForObject("select id from app_users where email=?",Long.class,r.email().strip().toLowerCase(Locale.ROOT)); jdbc.update("insert into user_roles(user_id,role_code) values(?, 'USER')",id); return Map.of("id",id); }catch(Exception e){throw new ApiErrors.RequestFailure(409,"DUPLICATE","El correo o documento ya existe.");}
  }
  @PatchMapping("/users/{id}/active") @ResponseStatus(HttpStatus.NO_CONTENT) public void userActive(@PathVariable long id,@RequestBody ActiveRequest r){if(jdbc.update("update app_users set active=? where id=?",r.active(),id)==0)throw new ApiErrors.RequestFailure(404,"NOT_FOUND","Usuario inexistente.");}
  @GetMapping("/professionals") public List<Map<String,Object>> professionals(){return jdbc.queryForList("select p.id,p.professional_code as professionalCode,p.license_number as licenseNumber,p.active,u.id as userId,u.first_name as firstName,u.last_name as lastName,u.email,group_concat(distinct s.name order by s.name) as specialties,group_concat(distinct l.name order by l.name) as locations from professionals p join app_users u on u.id=p.user_id left join professional_specialties ps on ps.professional_id=p.id left join specialties s on s.id=ps.specialty_id left join professional_locations pl on pl.professional_id=p.id left join locations l on l.id=pl.location_id group by p.id order by u.last_name,u.first_name");}
  @PatchMapping("/professionals/{id}/active") @ResponseStatus(HttpStatus.NO_CONTENT) public void professionalActive(@PathVariable long id,@RequestBody ActiveRequest r){if(jdbc.update("update professionals set active=? where id=?",r.active(),id)==0)throw new ApiErrors.RequestFailure(404,"NOT_FOUND","Profesional inexistente.");}
  @GetMapping("/dashboard") public Map<String,Object> dashboard(){
    Integer users=jdbc.queryForObject("select count(*) from app_users where active=true and id in (select user_id from user_roles where role_code='USER')",Integer.class); Integer professionals=jdbc.queryForObject("select count(*) from professionals where active=true",Integer.class); Integer pending=jdbc.queryForObject("select count(*) from appointments where status='REQUESTED'",Integer.class);
    return Map.of("activeUsers",Objects.requireNonNullElse(users,0),"activeProfessionals",Objects.requireNonNullElse(professionals,0),"pendingRequests",Objects.requireNonNullElse(pending,0),"appointmentsByStatus",jdbc.queryForList("select status,count(*) as total from appointments group by status"),"occupationByLocation",jdbc.queryForList("select l.name,count(a.id) as total from locations l left join appointments a on a.location_id=l.id group by l.id,l.name order by l.name"));
  }
}
