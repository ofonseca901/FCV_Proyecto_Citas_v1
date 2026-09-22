CREATE TABLE locations (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(30) NOT NULL UNIQUE, name VARCHAR(180) NOT NULL, active BOOLEAN NOT NULL
);
CREATE TABLE specialties (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(50) NOT NULL UNIQUE, name VARCHAR(150) NOT NULL UNIQUE,
  duration_minutes INTEGER NOT NULL, general BOOLEAN NOT NULL, requires_approval BOOLEAN NOT NULL, active BOOLEAN NOT NULL,
  CONSTRAINT ck_specialty_duration CHECK (duration_minutes IN (30, 60))
);
CREATE TABLE professionals (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL UNIQUE, professional_code VARCHAR(40) NOT NULL UNIQUE,
  license_number VARCHAR(80) NOT NULL UNIQUE, active BOOLEAN NOT NULL,
  CONSTRAINT fk_professional_user FOREIGN KEY (user_id) REFERENCES app_users(id)
);
CREATE TABLE professional_specialties (
  professional_id BIGINT NOT NULL, specialty_id BIGINT NOT NULL, primary_specialty BOOLEAN NOT NULL,
  PRIMARY KEY (professional_id, specialty_id),
  FOREIGN KEY (professional_id) REFERENCES professionals(id), FOREIGN KEY (specialty_id) REFERENCES specialties(id)
);
CREATE TABLE professional_locations (
  professional_id BIGINT NOT NULL, location_id BIGINT NOT NULL,
  PRIMARY KEY (professional_id, location_id),
  FOREIGN KEY (professional_id) REFERENCES professionals(id), FOREIGN KEY (location_id) REFERENCES locations(id)
);
CREATE TABLE availability_blocks (
  id BIGINT AUTO_INCREMENT PRIMARY KEY, professional_id BIGINT NOT NULL, location_id BIGINT NOT NULL,
  available_date DATE NOT NULL, start_time TIME NOT NULL, end_time TIME NOT NULL, active BOOLEAN NOT NULL,
  CONSTRAINT ck_block_time CHECK (end_time > start_time),
  FOREIGN KEY (professional_id) REFERENCES professionals(id), FOREIGN KEY (location_id) REFERENCES locations(id)
);
CREATE INDEX ix_blocks_lookup ON availability_blocks(location_id, available_date);
CREATE TABLE appointments (
  id BIGINT AUTO_INCREMENT PRIMARY KEY, patient_user_id BIGINT NOT NULL, professional_id BIGINT NOT NULL,
  location_id BIGINT NOT NULL, specialty_id BIGINT NOT NULL, status VARCHAR(20) NOT NULL,
  scheduled_start_at TIMESTAMP NOT NULL, scheduled_end_at TIMESTAMP NOT NULL, rejection_reason VARCHAR(500), created_at TIMESTAMP NOT NULL,
  CONSTRAINT ck_appointment_time CHECK (scheduled_end_at > scheduled_start_at),
  FOREIGN KEY (patient_user_id) REFERENCES app_users(id), FOREIGN KEY (professional_id) REFERENCES professionals(id),
  FOREIGN KEY (location_id) REFERENCES locations(id), FOREIGN KEY (specialty_id) REFERENCES specialties(id)
);
CREATE INDEX ix_appointments_professional ON appointments(professional_id, scheduled_start_at);
CREATE TABLE appointment_slots (
  professional_id BIGINT NOT NULL, start_at TIMESTAMP NOT NULL, appointment_id BIGINT NOT NULL,
  PRIMARY KEY (professional_id, start_at), FOREIGN KEY (professional_id) REFERENCES professionals(id),
  FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);
CREATE TABLE appointment_status_history (
  id BIGINT AUTO_INCREMENT PRIMARY KEY, appointment_id BIGINT NOT NULL, status VARCHAR(20) NOT NULL,
  actor_user_id BIGINT, source VARCHAR(20) NOT NULL, reason VARCHAR(500), changed_at TIMESTAMP NOT NULL,
  FOREIGN KEY (appointment_id) REFERENCES appointments(id), FOREIGN KEY (actor_user_id) REFERENCES app_users(id)
);

INSERT INTO locations(code, name, active) VALUES
  ('HIC', 'Hospital Internacional de Colombia (HIC)', TRUE),
  ('ICV', 'Instituto Cardiovascular (ICV)', TRUE);
INSERT INTO specialties(code, name, duration_minutes, general, requires_approval, active) VALUES
  ('MEDICINA_GENERAL', 'Medicina General', 30, TRUE, FALSE, TRUE),
  ('CARDIOLOGIA', 'Cardiología', 30, FALSE, TRUE, TRUE),
  ('NEUROLOGIA', 'Neurología', 60, FALSE, TRUE, TRUE);
