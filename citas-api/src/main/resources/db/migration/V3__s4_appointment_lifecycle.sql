CREATE TABLE appointment_reschedules (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  appointment_id BIGINT NOT NULL,
  requested_by BIGINT NOT NULL,
  new_start_at TIMESTAMP NOT NULL,
  new_end_at TIMESTAMP NOT NULL,
  status VARCHAR(20) NOT NULL,
  request_reason VARCHAR(500),
  decision_reason VARCHAR(500),
  created_at TIMESTAMP NOT NULL,
  decided_at TIMESTAMP NULL,
  CONSTRAINT fk_reschedule_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id),
  CONSTRAINT fk_reschedule_user FOREIGN KEY (requested_by) REFERENCES app_users(id),
  CONSTRAINT ck_reschedule_status CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED')),
  CONSTRAINT ck_reschedule_time CHECK (new_end_at > new_start_at)
);
CREATE INDEX ix_reschedules_status ON appointment_reschedules(status, created_at);
CREATE INDEX ix_reschedules_appointment ON appointment_reschedules(appointment_id, status);

