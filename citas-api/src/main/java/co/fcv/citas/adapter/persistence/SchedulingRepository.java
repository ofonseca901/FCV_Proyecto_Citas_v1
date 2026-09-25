package co.fcv.citas.adapter.persistence;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

@Repository
public class SchedulingRepository {
    private final JdbcTemplate jdbc;

    public SchedulingRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Map<String, Object>> locations() {
        return jdbc.queryForList("select id,code,name from locations where active=true order by name");
    }

    public List<Map<String, Object>> specialties() {
        return jdbc.queryForList("select id,code,name,duration_minutes as durationMinutes,general,requires_approval as requiresApproval "
                + "from specialties where active=true order by general desc,name");
    }

    public Optional<Map<String, Object>> specialty(long id) {
        return jdbc.queryForList("select * from specialties where id=? and active=true", id).stream().findFirst();
    }

    public long insertSpecialty(String code, String name, int duration, boolean general) {
        return insert("insert into specialties(code,name,duration_minutes,general,requires_approval,active) values(?,?,?,?,?,true)",
                code, name, duration, general, !general);
    }

    public boolean activeUser(long userId) {
        return count("select count(*) from app_users where id=? and active=true", userId) > 0;
    }

    public boolean professionalAlreadyRegistered(long userId) {
        return count("select count(*) from professionals where user_id=?", userId) > 0;
    }

    public long insertProfessional(long userId, String code, String license) {
        long id = insert("insert into professionals(user_id,professional_code,license_number,active) values(?,?,?,true)",
                userId, code, license);
        jdbc.update("insert into user_roles(user_id,role_code) values(?,?)", userId, "PROFESSIONAL");
        return id;
    }

    public boolean professionalFor(long professionalId, long userId) {
        return count("select count(*) from professionals where id=? and user_id=? and active=true", professionalId, userId) > 0;
    }

    public boolean professionalExists(long professionalId) {
        return count("select count(*) from professionals where id=? and active=true", professionalId) > 0;
    }

    public void assignSpecialty(long professionalId, long specialtyId, boolean primary) {
        if (primary) {
            jdbc.update("update professional_specialties set primary_specialty=false where professional_id=?", professionalId);
        }
        jdbc.update("insert into professional_specialties(professional_id,specialty_id,primary_specialty) values(?,?,?) "
                + "on duplicate key update primary_specialty=values(primary_specialty)", professionalId, specialtyId, primary);
    }

    public boolean activeSpecialty(long specialtyId) {
        return count("select count(*) from specialties where id=? and active=true", specialtyId) > 0;
    }

    public void assignLocation(long professionalId, long locationId) {
        jdbc.update("insert into professional_locations(professional_id,location_id) values(?,?) "
                + "on duplicate key update location_id=values(location_id)", professionalId, locationId);
    }

    public boolean serves(long professionalId, long specialtyId, long locationId) {
        return count("select count(*) from professionals p "
                + "join professional_specialties ps on ps.professional_id=p.id "
                + "join professional_locations pl on pl.professional_id=p.id "
                + "where p.id=? and p.active=true and ps.specialty_id=? and pl.location_id=?",
                professionalId, specialtyId, locationId) > 0;
    }

    public boolean worksAt(long professionalId, long locationId) {
        return count("select count(*) from professional_locations where professional_id=? and location_id=?",
                professionalId, locationId) > 0;
    }

    public boolean locationExists(long locationId) {
        return count("select count(*) from locations where id=? and active=true", locationId) > 0;
    }

    public List<Map<String, Object>> blocks(long professionalId, LocalDate date) {
        return jdbc.queryForList("select id,location_id as locationId,available_date as availableDate,start_time as startTime,end_time as endTime "
                + "from availability_blocks where professional_id=? and available_date=? and active=true order by start_time",
                professionalId, date);
    }

    public boolean overlaps(long professionalId, LocalDate date, LocalTime start, LocalTime end) {
        return count("select count(*) from availability_blocks where professional_id=? and available_date=? and active=true "
                + "and start_time < ? and end_time > ?", professionalId, date, end, start) > 0;
    }

    public boolean insideBlock(long professionalId, long locationId, LocalDate date, LocalTime start, LocalTime end) {
        return count("select count(*) from availability_blocks where professional_id=? and location_id=? and available_date=? "
                + "and active=true and start_time <= ? and end_time >= ?", professionalId, locationId, date, start, end) > 0;
    }

    public long insertBlock(long professionalId, long locationId, LocalDate date, LocalTime start, LocalTime end) {
        return insert("insert into availability_blocks(professional_id,location_id,available_date,start_time,end_time,active) values(?,?,?,?,?,true)",
                professionalId, locationId, date, start, end);
    }

    public List<Map<String, Object>> candidateBlocks(long locationId, long specialtyId, LocalDate date) {
        return jdbc.queryForList("select b.professional_id as professionalId,b.location_id as locationId,b.start_time as startTime,b.end_time as endTime,"
                + "p.professional_code as professionalCode,u.first_name as firstName,u.last_name as lastName "
                + "from availability_blocks b join professionals p on p.id=b.professional_id and p.active=true "
                + "join app_users u on u.id=p.user_id and u.active=true "
                + "join professional_specialties ps on ps.professional_id=p.id and ps.specialty_id=? "
                + "where b.location_id=? and b.available_date=? and b.active=true",
                specialtyId, locationId, date);
    }

    public boolean slotTaken(long professionalId, LocalDateTime start) {
        return count("select count(*) from appointment_slots where professional_id=? and start_at=?",
                professionalId, Timestamp.valueOf(start)) > 0;
    }

    public long insertAppointment(long patient, long professional, long location, long specialty, String status,
                                  LocalDateTime start, LocalDateTime end) {
        return insert("insert into appointments(patient_user_id,professional_id,location_id,specialty_id,status,scheduled_start_at,scheduled_end_at,created_at) "
                + "values(?,?,?,?,?,?,?,?)", patient, professional, location, specialty, status,
                Timestamp.valueOf(start), Timestamp.valueOf(end), Timestamp.from(Instant.now()));
    }

    public void reserve(long professional, long appointment, LocalDateTime start) {
        jdbc.update("insert into appointment_slots(professional_id,start_at,appointment_id) values(?,?,?)",
                professional, Timestamp.valueOf(start), appointment);
    }

    public Optional<Map<String, Object>> appointment(long id) {
        return jdbc.queryForList("select * from appointments where id=?", id).stream().findFirst();
    }

    public Optional<Map<String, Object>> appointmentForPatient(long id, long patient) {
        return jdbc.queryForList(appointmentDetailsSql() + " where a.id=? and a.patient_user_id=?", id, patient)
                .stream().findFirst();
    }

    public Optional<Map<String, Object>> appointmentForProfessional(long id, long professional) {
        return jdbc.queryForList(appointmentDetailsSql() + " where a.id=? and a.professional_id=?", id, professional)
                .stream().findFirst();
    }

    public List<Map<String, Object>> patientAppointments(long patient) {
        return jdbc.queryForList(appointmentDetailsSql() + " where a.patient_user_id=? order by a.scheduled_start_at", patient);
    }

    public List<Map<String, Object>> professionalAppointments(long professional, LocalDateTime from, LocalDateTime to,
                                                               Long locationId) {
        String sql = appointmentDetailsSql() + " where a.professional_id=? and a.status='APPROVED' "
                + "and a.scheduled_start_at>=? and a.scheduled_start_at<?";
        if (locationId != null) {
            sql += " and a.location_id=?";
            return jdbc.queryForList(sql + " order by a.scheduled_start_at", professional, Timestamp.valueOf(from),
                    Timestamp.valueOf(to), locationId);
        }
        return jdbc.queryForList(sql + " order by a.scheduled_start_at", professional, Timestamp.valueOf(from), Timestamp.valueOf(to));
    }

    public List<Map<String, Object>> requested() {
        return jdbc.queryForList(appointmentDetailsSql() + " where a.status='REQUESTED' order by a.scheduled_start_at");
    }

    public List<Map<String, Object>> appointmentHistory(long appointmentId) {
        return jdbc.queryForList("select id,appointment_id as appointmentId,status,actor_user_id as actorUserId,source,reason,changed_at as changedAt "
                + "from appointment_status_history where appointment_id=? order by changed_at,id", appointmentId);
    }

    public void decision(long appointmentId, String status, String reason, long actor) {
        jdbc.update("update appointments set status=?,rejection_reason=? where id=?", status, reason, appointmentId);
        if ("REJECTED".equals(status)) {
            jdbc.update("delete from appointment_slots where appointment_id=?", appointmentId);
        }
        history(appointmentId, status, actor, "ADMIN", reason);
    }

    public void updateAppointmentStatus(long appointmentId, String status, String reason) {
        jdbc.update("update appointments set status=?,rejection_reason=? where id=?", status, reason, appointmentId);
    }

    public void deleteSlots(long appointmentId, LocalDateTime start, LocalDateTime end) {
        jdbc.update("delete from appointment_slots where appointment_id=? and start_at>=? and start_at<?",
                appointmentId, Timestamp.valueOf(start), Timestamp.valueOf(end));
    }

    public void history(long appointmentId, String status, long actor, String source, String reason) {
        jdbc.update("insert into appointment_status_history(appointment_id,status,actor_user_id,source,reason,changed_at) "
                + "values(?,?,?,?,?,?)", appointmentId, status, actor, source, reason, Timestamp.from(Instant.now()));
    }

    public boolean pendingReschedule(long appointmentId) {
        return count("select count(*) from appointment_reschedules where appointment_id=? and status='PENDING'", appointmentId) > 0;
    }

    public long insertReschedule(long appointmentId, long userId, LocalDateTime start, LocalDateTime end, String reason) {
        return insert("insert into appointment_reschedules(appointment_id,requested_by,new_start_at,new_end_at,status,request_reason,created_at) "
                + "values(?,?,?,?, 'PENDING', ?, ?)", appointmentId, userId, Timestamp.valueOf(start), Timestamp.valueOf(end),
                reason, Timestamp.from(Instant.now()));
    }

    public Optional<Map<String, Object>> reschedule(long id) {
        return jdbc.queryForList("select * from appointment_reschedules where id=?", id).stream().findFirst();
    }

    public List<Map<String, Object>> pendingReschedules() {
        return jdbc.queryForList("select r.id,r.appointment_id as appointmentId,r.new_start_at as newStartAt,r.new_end_at as newEndAt,"
                + "r.request_reason as requestReason,r.created_at as createdAt,a.scheduled_start_at as currentStartAt,"
                + "a.scheduled_end_at as currentEndAt,u.first_name as patientFirstName,u.last_name as patientLastName,"
                + "p.professional_code as professionalCode,s.name as specialty,l.name as location "
                + "from appointment_reschedules r join appointments a on a.id=r.appointment_id "
                + "join app_users u on u.id=a.patient_user_id join professionals p on p.id=a.professional_id "
                + "join specialties s on s.id=a.specialty_id join locations l on l.id=a.location_id "
                + "where r.status='PENDING' order by r.created_at");
    }

    public void decideReschedule(long id, String status, String reason) {
        jdbc.update("update appointment_reschedules set status=?,decision_reason=?,decided_at=? where id=?",
                status, reason, Timestamp.from(Instant.now()), id);
    }

    public void updateAppointmentSchedule(long appointmentId, LocalDateTime start, LocalDateTime end) {
        jdbc.update("update appointments set scheduled_start_at=?,scheduled_end_at=?,rejection_reason=null where id=?",
                Timestamp.valueOf(start), Timestamp.valueOf(end), appointmentId);
    }

    private String appointmentDetailsSql() {
        return "select a.id,a.patient_user_id as patientUserId,a.professional_id as professionalId,a.location_id as locationId,"
                + "a.specialty_id as specialtyId,a.status,a.scheduled_start_at as scheduledStartAt,a.scheduled_end_at as scheduledEndAt,"
                + "a.rejection_reason as rejectionReason,a.created_at as createdAt,u.first_name as patientFirstName,u.last_name as patientLastName,"
                + "p.professional_code as professionalCode,p.license_number as licenseNumber,s.code as specialtyCode,s.name as specialty,"
                + "s.duration_minutes as durationMinutes,l.code as locationCode,l.name as location "
                + "from appointments a join app_users u on u.id=a.patient_user_id join professionals p on p.id=a.professional_id "
                + "join specialties s on s.id=a.specialty_id join locations l on l.id=a.location_id";
    }

    private int count(String sql, Object... args) {
        Integer value = jdbc.queryForObject(sql, Integer.class, args);
        return value == null ? 0 : value;
    }

    private long insert(String sql, Object... args) {
        GeneratedKeyHolder keys = new GeneratedKeyHolder();
        jdbc.update((PreparedStatementCreator) connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }
            return statement;
        }, keys);
        return Objects.requireNonNull(keys.getKey()).longValue();
    }
}
