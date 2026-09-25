CREATE TABLE eps (
  id BIGINT AUTO_INCREMENT PRIMARY KEY, code VARCHAR(40) NOT NULL UNIQUE,
  name VARCHAR(150) NOT NULL UNIQUE, active BOOLEAN NOT NULL DEFAULT TRUE
);
CREATE TABLE insurance_plans (
  id BIGINT AUTO_INCREMENT PRIMARY KEY, eps_id BIGINT NOT NULL, code VARCHAR(40) NOT NULL,
  name VARCHAR(150) NOT NULL, active BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT uk_plan_eps_code UNIQUE (eps_id, code),
  CONSTRAINT fk_plan_eps FOREIGN KEY (eps_id) REFERENCES eps(id)
);
CREATE TABLE user_affiliations (
  user_id BIGINT PRIMARY KEY, plan_id BIGINT NOT NULL, created_at TIMESTAMP NOT NULL,
  CONSTRAINT fk_affiliation_user FOREIGN KEY (user_id) REFERENCES app_users(id),
  CONSTRAINT fk_affiliation_plan FOREIGN KEY (plan_id) REFERENCES insurance_plans(id)
);
CREATE TABLE password_reset_tokens (
  id BIGINT AUTO_INCREMENT PRIMARY KEY, user_id BIGINT NOT NULL, token_hash VARCHAR(100) NOT NULL UNIQUE,
  expires_at TIMESTAMP NOT NULL, consumed_at TIMESTAMP NULL, created_at TIMESTAMP NOT NULL,
  CONSTRAINT fk_reset_user FOREIGN KEY (user_id) REFERENCES app_users(id)
);
CREATE INDEX ix_reset_active ON password_reset_tokens(token_hash, expires_at);
CREATE TABLE appointment_reschedules (
  id BIGINT AUTO_INCREMENT PRIMARY KEY, appointment_id BIGINT NOT NULL, requested_by BIGINT NOT NULL,
  new_start_at TIMESTAMP NOT NULL, new_end_at TIMESTAMP NOT NULL, status VARCHAR(20) NOT NULL,
  decision_reason VARCHAR(500), created_at TIMESTAMP NOT NULL, decided_at TIMESTAMP NULL,
  CONSTRAINT fk_reschedule_appointment FOREIGN KEY (appointment_id) REFERENCES appointments(id),
  CONSTRAINT fk_reschedule_user FOREIGN KEY (requested_by) REFERENCES app_users(id)
);
CREATE TABLE appointment_slot_holds (
  professional_id BIGINT NOT NULL, start_at TIMESTAMP NOT NULL, reschedule_id BIGINT NOT NULL,
  PRIMARY KEY (professional_id, start_at),
  CONSTRAINT fk_hold_professional FOREIGN KEY (professional_id) REFERENCES professionals(id),
  CONSTRAINT fk_hold_reschedule FOREIGN KEY (reschedule_id) REFERENCES appointment_reschedules(id)
);
CREATE INDEX ix_appointment_patient ON appointments(patient_user_id, scheduled_start_at);
CREATE INDEX ix_history_appointment ON appointment_status_history(appointment_id, changed_at);
INSERT INTO eps(code,name,active) VALUES ('PARTICULAR','Particular',TRUE);
INSERT INTO insurance_plans(eps_id,code,name,active) VALUES (1,'PARTICULAR','Plan Particular',TRUE);
