package co.fcv.citas.adapter.persistence;

import java.sql.*;
import java.time.*;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

@Repository
public class SchedulingRepository {
  private final JdbcTemplate jdbc;
  public SchedulingRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }
  public record Row(Map<String,Object> values) { public Long id(){ return ((Number)values.get("id")).longValue(); } }
  public List<Map<String,Object>> locations(){ return jdbc.queryForList("select id,code,name from locations where active=true order by name"); }
  public List<Map<String,Object>> specialties(){ return jdbc.queryForList("select id,code,name,duration_minutes as durationMinutes,general,requires_approval as requiresApproval from specialties where active=true order by general desc,name"); }
  public Optional<Map<String,Object>> specialty(long id){ return jdbc.queryForList("select * from specialties where id=? and active=true",id).stream().findFirst(); }
  public long insertSpecialty(String code,String name,int duration,boolean general){ return insert("insert into specialties(code,name,duration_minutes,general,requires_approval,active) values(?,?,?,?,?,true)", code,name,duration,general,!general); }
  public long insertProfessional(long userId,String code,String license){ long id=insert("insert into professionals(user_id,professional_code,license_number,active) values(?,?,?,true)",userId,code,license); jdbc.update("insert into user_roles(user_id,role_code) values(?,?)",userId,"PROFESSIONAL"); return id; }
  public boolean professionalFor(long professionalId,long userId){ return jdbc.queryForObject("select count(*) from professionals where id=? and user_id=? and active=true",Integer.class,professionalId,userId)>0; }
  public void assignSpecialty(long professionalId,long specialtyId,boolean primary){ if(primary) jdbc.update("update professional_specialties set primary_specialty=false where professional_id=?",professionalId); jdbc.update("insert into professional_specialties(professional_id,specialty_id,primary_specialty) values(?,?,?) on duplicate key update primary_specialty=values(primary_specialty)",professionalId,specialtyId,primary); }
  public void assignLocation(long professionalId,long locationId){ jdbc.update("insert into professional_locations(professional_id,location_id) values(?,?) on duplicate key update location_id=values(location_id)",professionalId,locationId); }
  public boolean serves(long professionalId,long specialtyId,long locationId){ return jdbc.queryForObject("select count(*) from professionals p join professional_specialties ps on ps.professional_id=p.id join professional_locations pl on pl.professional_id=p.id where p.id=? and p.active=true and ps.specialty_id=? and pl.location_id=?",Integer.class,professionalId,specialtyId,locationId)>0; }
  public boolean worksAt(long professionalId,long locationId){ return jdbc.queryForObject("select count(*) from professional_locations where professional_id=? and location_id=?",Integer.class,professionalId,locationId)>0; }
  public boolean locationExists(long locationId){ return jdbc.queryForObject("select count(*) from locations where id=? and active=true",Integer.class,locationId)>0; }
  public List<Map<String,Object>> blocks(long professionalId,LocalDate date){ return jdbc.queryForList("select id,location_id as locationId,available_date as availableDate,start_time as startTime,end_time as endTime from availability_blocks where professional_id=? and available_date=? and active=true order by start_time",professionalId,date); }
  public boolean overlaps(long professionalId,LocalDate date,LocalTime start,LocalTime end){ return jdbc.queryForObject("select count(*) from availability_blocks where professional_id=? and available_date=? and active=true and start_time < ? and end_time > ?",Integer.class,professionalId,date,end,start)>0; }
  public long insertBlock(long professionalId,long locationId,LocalDate date,LocalTime start,LocalTime end){ return insert("insert into availability_blocks(professional_id,location_id,available_date,start_time,end_time,active) values(?,?,?,?,?,true)",professionalId,locationId,date,start,end); }
  public List<Map<String,Object>> candidateBlocks(long locationId,long specialtyId,LocalDate date){ return jdbc.queryForList("select b.professional_id as professionalId,b.location_id as locationId,b.start_time as startTime,b.end_time as endTime,p.professional_code as professionalCode,u.first_name as firstName,u.last_name as lastName from availability_blocks b join professionals p on p.id=b.professional_id and p.active=true join app_users u on u.id=p.user_id join professional_specialties ps on ps.professional_id=p.id and ps.specialty_id=? where b.location_id=? and b.available_date=? and b.active=true",specialtyId,locationId,date); }
  public boolean slotTaken(long professionalId,LocalDateTime start){ return jdbc.queryForObject("select count(*) from appointment_slots where professional_id=? and start_at=?",Integer.class,professionalId,Timestamp.valueOf(start))>0; }
  public long insertAppointment(long patient,long professional,long location,long specialty,String status,LocalDateTime start,LocalDateTime end){ return insert("insert into appointments(patient_user_id,professional_id,location_id,specialty_id,status,scheduled_start_at,scheduled_end_at,created_at) values(?,?,?,?,?,?,?,?)",patient,professional,location,specialty,status,Timestamp.valueOf(start),Timestamp.valueOf(end),Timestamp.from(Instant.now())); }
  public void reserve(long professional,long appointment,LocalDateTime start){ jdbc.update("insert into appointment_slots(professional_id,start_at,appointment_id) values(?,?,?)",professional,Timestamp.valueOf(start),appointment); }
  public Optional<Map<String,Object>> appointment(long id){ return jdbc.queryForList("select * from appointments where id=?",id).stream().findFirst(); }
  public List<Map<String,Object>> requested(){ return jdbc.queryForList("select a.id,a.scheduled_start_at as scheduledStartAt,a.scheduled_end_at as scheduledEndAt,u.first_name as patientFirstName,u.last_name as patientLastName,p.professional_code as professionalCode,s.name as specialty,l.name as location from appointments a join app_users u on u.id=a.patient_user_id join professionals p on p.id=a.professional_id join specialties s on s.id=a.specialty_id join locations l on l.id=a.location_id where a.status='REQUESTED' order by a.scheduled_start_at"); }
  public void decision(long appointmentId,String status,String reason,long actor){ jdbc.update("update appointments set status=?,rejection_reason=? where id=?",status,reason,appointmentId); if("REJECTED".equals(status)) jdbc.update("delete from appointment_slots where appointment_id=?",appointmentId); jdbc.update("insert into appointment_status_history(appointment_id,status,actor_user_id,source,reason,changed_at) values(?,?,?,?,?,?)",appointmentId,status,actor,"ADMIN",reason,Timestamp.from(Instant.now())); }
  public void history(long appointmentId,String status,long actor,String source,String reason){ jdbc.update("insert into appointment_status_history(appointment_id,status,actor_user_id,source,reason,changed_at) values(?,?,?,?,?,?)",appointmentId,status,actor,source,reason,Timestamp.from(Instant.now())); }
  private long insert(String sql,Object... args){ GeneratedKeyHolder keys=new GeneratedKeyHolder(); jdbc.update((PreparedStatementCreator)c->{ PreparedStatement ps=c.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS); for(int i=0;i<args.length;i++) ps.setObject(i+1,args[i]); return ps;},keys); return Objects.requireNonNull(keys.getKey()).longValue(); }
}
