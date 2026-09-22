package co.fcv.citas.adapter.web;

import co.fcv.citas.adapter.persistence.SchedulingRepository;
import java.sql.Time;
import java.time.*;
import java.util.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SchedulingFacade {
  private final SchedulingRepository data;
  public SchedulingFacade(SchedulingRepository data){this.data=data;}
  public List<Map<String,Object>> locations(){return data.locations();} public List<Map<String,Object>> specialties(){return data.specialties();}
  @Transactional public long specialty(String code,String name,int duration,boolean general){ if(duration!=30&&duration!=60) throw bad("La duración debe ser 30 o 60 minutos."); return data.insertSpecialty(code.strip().toUpperCase(Locale.ROOT),name.strip(),duration,general); }
  @Transactional public long professional(long userId,String code,String license){ return data.insertProfessional(userId,code.strip(),license.strip()); }
  @Transactional public void specialty(long professional,long specialty,boolean primary){ data.assignSpecialty(professional,specialty,primary); }
  @Transactional public void location(long professional,long location){ if(!data.locationExists(location)) throw bad("Sede inválida."); data.assignLocation(professional,location); }
  @Transactional public long block(long user,long professional,long location,LocalDate date,LocalTime start,LocalTime end){ if(!data.professionalFor(professional,user)) throw forbidden("Solo puedes gestionar tu propia agenda."); if(!date.isAfter(LocalDate.now(Clock.systemUTC()))) throw bad("El bloque debe ser futuro."); if(!start.isBefore(end)||start.getMinute()%30!=0||end.getMinute()%30!=0) throw bad("Los bloques usan franjas de 30 minutos."); if(!data.worksAt(professional, location)) throw bad("El profesional no está habilitado en esa sede."); if(data.overlaps(professional,date,start,end)) throw bad("El bloque se solapa con otro bloque."); return data.insertBlock(professional,location,date,start,end); }
  public List<Map<String,Object>> blocks(long user,long professional,LocalDate date){ if(!data.professionalFor(professional,user)) throw forbidden("Agenda ajena."); return data.blocks(professional,date); }
  public List<Map<String,Object>> availability(long location,long specialty,LocalDate date){ var s=data.specialty(specialty).orElseThrow(()->bad("Especialidad inválida.")); int duration=((Number)s.get("duration_minutes")).intValue(); List<Map<String,Object>> result=new ArrayList<>(); for(var b:data.candidateBlocks(location,specialty,date)){ LocalTime from=((Time)b.get("startTime")).toLocalTime(), to=((Time)b.get("endTime")).toLocalTime(); long pro=((Number)b.get("professionalId")).longValue(); for(LocalTime t=from; !t.plusMinutes(duration).isAfter(to); t=t.plusMinutes(30)){ LocalDateTime at=LocalDateTime.of(date,t); boolean free=true; for(int i=0;i<duration/30;i++) free &= !data.slotTaken(pro,at.plusMinutes(30L*i)); if(free){ var row=new LinkedHashMap<>(b); row.put("startAt",at.toString()); row.put("durationMinutes",duration); result.add(row); } } } return result; }
  @Transactional public long book(long patient,long professional,long location,long specialty,LocalDateTime start){ if(start.isBefore(LocalDateTime.now(Clock.systemUTC()))) throw bad("No puedes reservar en el pasado."); var s=data.specialty(specialty).orElseThrow(()->bad("Especialidad inválida.")); if(!data.serves(professional,specialty,location)) throw bad("El profesional no atiende esa especialidad o sede."); int minutes=((Number)s.get("duration_minutes")).intValue(); String status=(Boolean)s.get("requires_approval")?"REQUESTED":"APPROVED"; LocalDateTime end=start.plusMinutes(minutes); try { long id=data.insertAppointment(patient,professional,location,specialty,status,start,end); for(int i=0;i<minutes/30;i++) data.reserve(professional,id,start.plusMinutes(30L*i)); data.history(id,status,patient,"USER",null); return id; } catch(DataIntegrityViolationException e){ throw bad("El horario acaba de ser reservado. Elige otro."); } }
  @Transactional public void decide(long admin,long id,boolean approve,String reason){ var a=data.appointment(id).orElseThrow(()->bad("Cita inexistente.")); if(!"REQUESTED".equals(a.get("status"))) throw bad("La cita ya fue decidida."); if(!approve&&(reason==null||reason.isBlank())) throw bad("El rechazo requiere un motivo."); data.decision(id,approve?"APPROVED":"REJECTED",approve?null:reason.strip(),admin); }
  public List<Map<String,Object>> requested(){return data.requested();}
  private ApiErrors.RequestFailure bad(String m){return new ApiErrors.RequestFailure(400,"INVALID",m);} private ApiErrors.RequestFailure forbidden(String m){return new ApiErrors.RequestFailure(403,"FORBIDDEN",m);}
}
