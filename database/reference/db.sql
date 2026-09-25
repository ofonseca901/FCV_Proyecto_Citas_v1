-- ============================================================
-- FCV TRAINING LAB · SISTEMA DE AGENDAMIENTO DE CITAS
-- MySQL 8.4 LTS · Modelo normalizado hasta 3FN
-- Fecha de diseño: 2026-09-10
--
-- IMPORTANTE
-- 1) Este es un laboratorio académico. No representa el modelo
--    de datos real ni procesos internos de FCV.
-- 2) Las dos sedes y los nombres de especialidades se basan en
--    información pública de FCV.
-- 3) Profesionales, usuarios, EPS, planes, disponibilidades y
--    citas de ejemplo son datos SINTÉTICOS.
-- 4) Las duraciones de 30/60 minutos son supuestos pedagógicos,
--    no tiempos oficiales de atención de FCV.
-- ============================================================

CREATE DATABASE IF NOT EXISTS citas_fcv_training
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE citas_fcv_training;

SET NAMES utf8mb4;
SET time_zone = '-05:00';

-- ============================================================
-- 1. SEGURIDAD Y USUARIOS
-- ============================================================

CREATE TABLE IF NOT EXISTS roles (
    id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(30) NOT NULL UNIQUE,
    name VARCHAR(80) NOT NULL,
    description VARCHAR(255) NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(80) NOT NULL,
    document_type VARCHAR(20) NOT NULL,
    document_number VARCHAR(40) NOT NULL,
    email VARCHAR(160) NOT NULL,
    phone VARCHAR(30) NULL,
    password_hash VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_users_document UNIQUE (document_type, document_number),
    CONSTRAINT uq_users_email UNIQUE (email)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT UNSIGNED NOT NULL,
    role_id SMALLINT UNSIGNED NOT NULL,
    assigned_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role
        FOREIGN KEY (role_id) REFERENCES roles(id)
        ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS refresh_tokens (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    token_hash VARCHAR(255) NOT NULL,
    expires_at DATETIME NOT NULL,
    revoked_at DATETIME NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    device_info VARCHAR(255) NULL,
    CONSTRAINT uq_refresh_tokens_hash UNIQUE (token_hash),
    CONSTRAINT fk_refresh_tokens_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE,
    INDEX ix_refresh_tokens_user (user_id),
    INDEX ix_refresh_tokens_expiry (expires_at)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS password_reset_tokens (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    token_hash VARCHAR(255) NOT NULL,
    expires_at DATETIME NOT NULL,
    used_at DATETIME NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_password_reset_hash UNIQUE (token_hash),
    CONSTRAINT fk_password_reset_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE,
    INDEX ix_password_reset_user (user_id),
    INDEX ix_password_reset_expiry (expires_at)
) ENGINE=InnoDB;

-- ============================================================
-- 2. ASEGURAMIENTO / EPS
-- ============================================================

CREATE TABLE IF NOT EXISTS insurance_regimes (
    id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(30) NOT NULL UNIQUE,
    name VARCHAR(80) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS eps (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(30) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS eps_plans (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    eps_id BIGINT UNSIGNED NOT NULL,
    regime_id SMALLINT UNSIGNED NOT NULL,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(150) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uq_eps_plan_code UNIQUE (eps_id, code),
    CONSTRAINT fk_eps_plans_eps
        FOREIGN KEY (eps_id) REFERENCES eps(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_eps_plans_regime
        FOREIGN KEY (regime_id) REFERENCES insurance_regimes(id)
        ON DELETE RESTRICT,
    INDEX ix_eps_plans_regime (regime_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user_insurance_affiliations (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    plan_id BIGINT UNSIGNED NOT NULL,
    membership_number VARCHAR(80) NOT NULL,
    is_current BOOLEAN NOT NULL DEFAULT TRUE,
    valid_from DATE NULL,
    valid_to DATE NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_user_membership UNIQUE (user_id, plan_id, membership_number),
    CONSTRAINT fk_user_insurance_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_user_insurance_plan
        FOREIGN KEY (plan_id) REFERENCES eps_plans(id)
        ON DELETE RESTRICT,
    INDEX ix_user_insurance_current (user_id, is_current)
) ENGINE=InnoDB;

-- ============================================================
-- 3. CATÁLOGOS DE SERVICIO
-- ============================================================

CREATE TABLE IF NOT EXISTS locations (
    id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(30) NOT NULL UNIQUE,
    name VARCHAR(180) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    department VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS specialties (
    id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL UNIQUE,
    appointment_duration_minutes SMALLINT UNSIGNED NOT NULL,
    is_general BOOLEAN NOT NULL DEFAULT FALSE,
    requires_admin_approval BOOLEAN NOT NULL DEFAULT TRUE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT ck_specialty_duration
        CHECK (appointment_duration_minutes IN (30, 60))
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS professionals (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL UNIQUE,
    professional_code VARCHAR(40) NOT NULL UNIQUE,
    license_number VARCHAR(80) NOT NULL UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_professionals_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS professional_specialties (
    professional_id BIGINT UNSIGNED NOT NULL,
    specialty_id SMALLINT UNSIGNED NOT NULL,
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (professional_id, specialty_id),
    CONSTRAINT fk_prof_specialty_professional
        FOREIGN KEY (professional_id) REFERENCES professionals(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_prof_specialty_specialty
        FOREIGN KEY (specialty_id) REFERENCES specialties(id)
        ON DELETE RESTRICT
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS professional_locations (
    professional_id BIGINT UNSIGNED NOT NULL,
    location_id SMALLINT UNSIGNED NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (professional_id, location_id),
    CONSTRAINT fk_prof_location_professional
        FOREIGN KEY (professional_id) REFERENCES professionals(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_prof_location_location
        FOREIGN KEY (location_id) REFERENCES locations(id)
        ON DELETE RESTRICT
) ENGINE=InnoDB;

-- ============================================================
-- 4. CITAS Y ESTADOS
-- ============================================================

CREATE TABLE IF NOT EXISTS appointment_statuses (
    id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(40) NOT NULL UNIQUE,
    name VARCHAR(80) NOT NULL,
    is_terminal BOOLEAN NOT NULL DEFAULT FALSE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS appointments (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    patient_user_id BIGINT UNSIGNED NOT NULL,
    professional_id BIGINT UNSIGNED NOT NULL,
    location_id SMALLINT UNSIGNED NOT NULL,
    specialty_id SMALLINT UNSIGNED NOT NULL,
    insurance_affiliation_id BIGINT UNSIGNED NULL,
    status_id SMALLINT UNSIGNED NOT NULL,
    reason VARCHAR(500) NULL,
    scheduled_start_at DATETIME NOT NULL,
    scheduled_end_at DATETIME NOT NULL,
    created_by_user_id BIGINT UNSIGNED NOT NULL,
    approved_by_user_id BIGINT UNSIGNED NULL,
    approved_at DATETIME NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT ck_appointment_time
        CHECK (scheduled_end_at > scheduled_start_at),
    CONSTRAINT fk_appointments_patient
        FOREIGN KEY (patient_user_id) REFERENCES users(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_appointments_professional
        FOREIGN KEY (professional_id) REFERENCES professionals(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_appointments_location
        FOREIGN KEY (location_id) REFERENCES locations(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_appointments_specialty
        FOREIGN KEY (specialty_id) REFERENCES specialties(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_appointments_insurance
        FOREIGN KEY (insurance_affiliation_id) REFERENCES user_insurance_affiliations(id)
        ON DELETE SET NULL,
    CONSTRAINT fk_appointments_status
        FOREIGN KEY (status_id) REFERENCES appointment_statuses(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_appointments_created_by
        FOREIGN KEY (created_by_user_id) REFERENCES users(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_appointments_approved_by
        FOREIGN KEY (approved_by_user_id) REFERENCES users(id)
        ON DELETE RESTRICT,
    INDEX ix_appointments_patient (patient_user_id, scheduled_start_at),
    INDEX ix_appointments_professional (professional_id, scheduled_start_at),
    INDEX ix_appointments_status (status_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS availability_blocks (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    professional_id BIGINT UNSIGNED NOT NULL,
    location_id SMALLINT UNSIGNED NOT NULL,
    available_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT ck_availability_block_time
        CHECK (end_time > start_time),
    CONSTRAINT fk_availability_professional
        FOREIGN KEY (professional_id) REFERENCES professionals(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_availability_location
        FOREIGN KEY (location_id) REFERENCES locations(id)
        ON DELETE RESTRICT,
    INDEX ix_availability_prof_date
        (professional_id, available_date, start_time),
    INDEX ix_availability_location_date
        (location_id, available_date)
) ENGINE=InnoDB;

-- Slots atómicos de 30 minutos.
-- Una cita de 30 min reserva 1 slot; una de 60 min reserva 2 consecutivos.
CREATE TABLE IF NOT EXISTS professional_slots (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    availability_block_id BIGINT UNSIGNED NOT NULL,
    start_at DATETIME NOT NULL,
    end_at DATETIME NOT NULL,
    appointment_id BIGINT UNSIGNED NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_professional_slot_time
        CHECK (end_at > start_at),
    CONSTRAINT uq_block_slot UNIQUE (availability_block_id, start_at),
    CONSTRAINT fk_slots_availability_block
        FOREIGN KEY (availability_block_id) REFERENCES availability_blocks(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_slots_appointment
        FOREIGN KEY (appointment_id) REFERENCES appointments(id)
        ON DELETE SET NULL,
    INDEX ix_slots_start (start_at),
    INDEX ix_slots_appointment (appointment_id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS appointment_status_history (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    appointment_id BIGINT UNSIGNED NOT NULL,
    status_id SMALLINT UNSIGNED NOT NULL,
    changed_by_user_id BIGINT UNSIGNED NULL,
    change_source VARCHAR(20) NOT NULL DEFAULT 'USER',
    reason VARCHAR(500) NULL,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_status_history_source
        CHECK (change_source IN ('SYSTEM', 'USER', 'ADMIN')),
    CONSTRAINT fk_status_history_appointment
        FOREIGN KEY (appointment_id) REFERENCES appointments(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_status_history_status
        FOREIGN KEY (status_id) REFERENCES appointment_statuses(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_status_history_user
        FOREIGN KEY (changed_by_user_id) REFERENCES users(id)
        ON DELETE SET NULL,
    INDEX ix_status_history_appointment (appointment_id, changed_at)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS reschedule_request_statuses (
    id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(40) NOT NULL UNIQUE,
    name VARCHAR(80) NOT NULL,
    is_terminal BOOLEAN NOT NULL DEFAULT FALSE
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS reschedule_requests (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    appointment_id BIGINT UNSIGNED NOT NULL,
    requested_by_user_id BIGINT UNSIGNED NOT NULL,
    requested_location_id SMALLINT UNSIGNED NOT NULL,
    status_id SMALLINT UNSIGNED NOT NULL,
    previous_start_at DATETIME NOT NULL,
    previous_end_at DATETIME NOT NULL,
    requested_start_at DATETIME NOT NULL,
    requested_end_at DATETIME NOT NULL,
    decision_reason VARCHAR(500) NULL,
    decided_by_user_id BIGINT UNSIGNED NULL,
    decided_at DATETIME NULL,
    patient_action_after_rejection VARCHAR(30) NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ck_reschedule_time
        CHECK (requested_end_at > requested_start_at),
    CONSTRAINT ck_reschedule_patient_action
        CHECK (
            patient_action_after_rejection IS NULL
            OR patient_action_after_rejection IN ('KEEP_APPOINTMENT', 'CANCEL_APPOINTMENT')
        ),
    CONSTRAINT fk_reschedule_appointment
        FOREIGN KEY (appointment_id) REFERENCES appointments(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_reschedule_requested_by
        FOREIGN KEY (requested_by_user_id) REFERENCES users(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_reschedule_location
        FOREIGN KEY (requested_location_id) REFERENCES locations(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_reschedule_status
        FOREIGN KEY (status_id) REFERENCES reschedule_request_statuses(id)
        ON DELETE RESTRICT,
    CONSTRAINT fk_reschedule_decided_by
        FOREIGN KEY (decided_by_user_id) REFERENCES users(id)
        ON DELETE RESTRICT,
    INDEX ix_reschedule_appointment (appointment_id),
    INDEX ix_reschedule_status (status_id)
) ENGINE=InnoDB;

-- ============================================================
-- 5. SEEDS FIJOS
-- ============================================================

INSERT INTO roles (id, code, name, description) VALUES
(1, 'USER', 'Usuario', 'Paciente/usuario que solicita y gestiona sus citas'),
(2, 'PROFESSIONAL', 'Profesional', 'Profesional que administra su disponibilidad'),
(3, 'ADMIN', 'Administrador', 'Administra catálogos, profesionales y aprobaciones')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO insurance_regimes (id, code, name) VALUES
(1, 'CONTRIBUTIVO', 'Contributivo'),
(2, 'SUBSIDIADO', 'Subsidiado'),
(3, 'ESPECIAL', 'Especial'),
(4, 'EXCEPCION', 'Excepción'),
(5, 'PARTICULAR', 'Particular')
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO appointment_statuses (id, code, name, is_terminal) VALUES
(1, 'REQUESTED', 'Solicitada / pendiente de aprobación', FALSE),
(2, 'APPROVED', 'Aprobada', FALSE),
(3, 'REJECTED', 'Rechazada', TRUE),
(4, 'CANCELLED', 'Cancelada', TRUE),
(5, 'COMPLETED', 'Atendida / completada', TRUE),
(6, 'NO_SHOW', 'No asistió', TRUE)
ON DUPLICATE KEY UPDATE name = VALUES(name), is_terminal = VALUES(is_terminal);

INSERT INTO reschedule_request_statuses (id, code, name, is_terminal) VALUES
(1, 'PENDING', 'Pendiente', FALSE),
(2, 'APPROVED', 'Aprobada', TRUE),
(3, 'REJECTED', 'Rechazada', TRUE),
(4, 'CANCELLED', 'Cancelada por el usuario', TRUE)
ON DUPLICATE KEY UPDATE name = VALUES(name), is_terminal = VALUES(is_terminal);

-- ============================================================
-- 6. SEEDS PÚBLICOS FCV
-- ============================================================
-- Sedes: información pública institucional.
INSERT INTO locations (id, code, name, address, city, department, active) VALUES
(
  1,
  'HIC',
  'Hospital Internacional de Colombia (HIC)',
  'Km 7 Autopista Bucaramanga - Piedecuesta, Valle de Menzulí',
  'Piedecuesta',
  'Santander',
  TRUE
),
(
  2,
  'ICV',
  'Fundación Cardiovascular de Colombia - Instituto Cardiovascular (ICV)',
  'Calle 155A No. 23-58, Urbanización El Bosque',
  'Floridablanca',
  'Santander',
  TRUE
)
ON DUPLICATE KEY UPDATE
  name = VALUES(name),
  address = VALUES(address),
  city = VALUES(city),
  department = VALUES(department),
  active = VALUES(active);

-- Especialidades/servicios clínicos tomados de la oferta pública de FCV.
-- Las duraciones 30/60 min son supuestos del laboratorio.
INSERT INTO specialties
(id, code, name, appointment_duration_minutes, is_general, requires_admin_approval, active)
VALUES
(1, 'MEDICINA_GENERAL', 'Medicina General', 30, TRUE, FALSE, TRUE),
(2, 'CARDIOLOGIA_ADULTO', 'Cardiología Adulto', 30, FALSE, TRUE, TRUE),
(3, 'CARDIOLOGIA_PEDIATRICA', 'Cardiología Pediátrica', 30, FALSE, TRUE, TRUE),
(4, 'MEDICINA_INTERNA', 'Medicina Interna', 30, FALSE, TRUE, TRUE),
(5, 'PEDIATRIA', 'Pediatría', 30, FALSE, TRUE, TRUE),
(6, 'NEFROLOGIA', 'Nefrología', 30, FALSE, TRUE, TRUE),
(7, 'UROLOGIA', 'Urología', 30, FALSE, TRUE, TRUE),
(8, 'GASTROENTEROLOGIA', 'Gastroenterología', 30, FALSE, TRUE, TRUE),
(9, 'NEUMOLOGIA_ADULTO', 'Neumología Adulto', 30, FALSE, TRUE, TRUE),
(10, 'ENDOCRINOLOGIA', 'Endocrinología', 30, FALSE, TRUE, TRUE),
(11, 'ORTOPEDIA_TRAUMATOLOGIA', 'Ortopedia y Traumatología', 60, FALSE, TRUE, TRUE),
(12, 'NEUROLOGIA', 'Neurología', 60, FALSE, TRUE, TRUE)
ON DUPLICATE KEY UPDATE
  name = VALUES(name),
  appointment_duration_minutes = VALUES(appointment_duration_minutes),
  is_general = VALUES(is_general),
  requires_admin_approval = VALUES(requires_admin_approval),
  active = VALUES(active);

-- ============================================================
-- 7. SEEDS SINTÉTICOS DE ENTRENAMIENTO
-- ============================================================

INSERT INTO eps (id, code, name, active) VALUES
(1, 'EPS_DEMO_A', 'EPS Demo Salud', TRUE),
(2, 'EPS_DEMO_B', 'EPS Demo Familiar', TRUE),
(3, 'PARTICULAR_DEMO', 'Atención Particular Demo', TRUE)
ON DUPLICATE KEY UPDATE name = VALUES(name), active = VALUES(active);

INSERT INTO eps_plans (id, eps_id, regime_id, code, name, active) VALUES
(1, 1, 1, 'A-CONTRIB', 'Plan Contributivo Demo', TRUE),
(2, 1, 2, 'A-SUBS', 'Plan Subsidiado Demo', TRUE),
(3, 2, 1, 'B-CONTRIB', 'Plan Contributivo Familiar Demo', TRUE),
(4, 2, 3, 'B-ESPECIAL', 'Plan Especial Demo', TRUE),
(5, 3, 5, 'PARTICULAR', 'Particular / pago directo', TRUE)
ON DUPLICATE KEY UPDATE name = VALUES(name), active = VALUES(active);

-- Password de laboratorio para TODOS estos usuarios: Demo1234*
-- Hash BCrypt generado únicamente para el entorno de entrenamiento.
INSERT INTO users
(id, first_name, last_name, document_type, document_number, email, phone, password_hash, active, email_verified)
VALUES
(1, 'Admin', 'Laboratorio', 'CC', '900000001', 'admin@demo.invalid', '3000000001', '$2y$10$QAPT/bPvvEILB0ovqykfTuwSBznwY2p0rJhZJguneKjk2dr7VQFeG', TRUE, TRUE),

(10, 'Andrea', 'Ruiz', 'CC', '910000010', 'andrea.ruiz@demo.invalid', '3100000010', '$2y$10$QAPT/bPvvEILB0ovqykfTuwSBznwY2p0rJhZJguneKjk2dr7VQFeG', TRUE, TRUE),
(11, 'Carlos', 'Mejía', 'CC', '910000011', 'carlos.mejia@demo.invalid', '3100000011', '$2y$10$QAPT/bPvvEILB0ovqykfTuwSBznwY2p0rJhZJguneKjk2dr7VQFeG', TRUE, TRUE),
(12, 'Diana', 'Torres', 'CC', '910000012', 'diana.torres@demo.invalid', '3100000012', '$2y$10$QAPT/bPvvEILB0ovqykfTuwSBznwY2p0rJhZJguneKjk2dr7VQFeG', TRUE, TRUE),
(13, 'Felipe', 'Rojas', 'CC', '910000013', 'felipe.rojas@demo.invalid', '3100000013', '$2y$10$QAPT/bPvvEILB0ovqykfTuwSBznwY2p0rJhZJguneKjk2dr7VQFeG', TRUE, TRUE),
(14, 'Laura', 'Mendoza', 'CC', '910000014', 'laura.mendoza@demo.invalid', '3100000014', '$2y$10$QAPT/bPvvEILB0ovqykfTuwSBznwY2p0rJhZJguneKjk2dr7VQFeG', TRUE, TRUE),
(15, 'Mateo', 'García', 'CC', '910000015', 'mateo.garcia@demo.invalid', '3100000015', '$2y$10$QAPT/bPvvEILB0ovqykfTuwSBznwY2p0rJhZJguneKjk2dr7VQFeG', TRUE, TRUE),
(16, 'Natalia', 'Vargas', 'CC', '910000016', 'natalia.vargas@demo.invalid', '3100000016', '$2y$10$QAPT/bPvvEILB0ovqykfTuwSBznwY2p0rJhZJguneKjk2dr7VQFeG', TRUE, TRUE),
(17, 'Sergio', 'Castro', 'CC', '910000017', 'sergio.castro@demo.invalid', '3100000017', '$2y$10$QAPT/bPvvEILB0ovqykfTuwSBznwY2p0rJhZJguneKjk2dr7VQFeG', TRUE, TRUE),

(100, 'Paciente', 'Uno', 'CC', '920000100', 'paciente1@demo.invalid', '3200000100', '$2y$10$QAPT/bPvvEILB0ovqykfTuwSBznwY2p0rJhZJguneKjk2dr7VQFeG', TRUE, TRUE),
(101, 'Paciente', 'Dos', 'CC', '920000101', 'paciente2@demo.invalid', '3200000101', '$2y$10$QAPT/bPvvEILB0ovqykfTuwSBznwY2p0rJhZJguneKjk2dr7VQFeG', TRUE, TRUE),
(102, 'Paciente', 'Tres', 'CC', '920000102', 'paciente3@demo.invalid', '3200000102', '$2y$10$QAPT/bPvvEILB0ovqykfTuwSBznwY2p0rJhZJguneKjk2dr7VQFeG', TRUE, TRUE),
(103, 'Paciente', 'Cuatro', 'CC', '920000103', 'paciente4@demo.invalid', '3200000103', '$2y$10$QAPT/bPvvEILB0ovqykfTuwSBznwY2p0rJhZJguneKjk2dr7VQFeG', TRUE, TRUE),
(104, 'Paciente', 'Cinco', 'CC', '920000104', 'paciente5@demo.invalid', '3200000104', '$2y$10$QAPT/bPvvEILB0ovqykfTuwSBznwY2p0rJhZJguneKjk2dr7VQFeG', TRUE, TRUE),
(105, 'Paciente', 'Seis', 'CC', '920000105', 'paciente6@demo.invalid', '3200000105', '$2y$10$QAPT/bPvvEILB0ovqykfTuwSBznwY2p0rJhZJguneKjk2dr7VQFeG', TRUE, TRUE)
ON DUPLICATE KEY UPDATE
  first_name = VALUES(first_name),
  last_name = VALUES(last_name),
  phone = VALUES(phone),
  active = VALUES(active);

INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(1, 3),
(10, 2), (11, 2), (12, 2), (13, 2),
(14, 2), (15, 2), (16, 2), (17, 2),
(100, 1), (101, 1), (102, 1), (103, 1), (104, 1), (105, 1);

INSERT INTO professionals
(id, user_id, professional_code, license_number, active)
VALUES
(1, 10, 'PROF-001', 'RM-DEMO-0001', TRUE),
(2, 11, 'PROF-002', 'RM-DEMO-0002', TRUE),
(3, 12, 'PROF-003', 'RM-DEMO-0003', TRUE),
(4, 13, 'PROF-004', 'RM-DEMO-0004', TRUE),
(5, 14, 'PROF-005', 'RM-DEMO-0005', TRUE),
(6, 15, 'PROF-006', 'RM-DEMO-0006', TRUE),
(7, 16, 'PROF-007', 'RM-DEMO-0007', TRUE),
(8, 17, 'PROF-008', 'RM-DEMO-0008', TRUE)
ON DUPLICATE KEY UPDATE active = VALUES(active);

-- Profesionales totalmente ficticios; las asignaciones sirven solo al laboratorio.
INSERT IGNORE INTO professional_specialties
(professional_id, specialty_id, is_primary, active)
VALUES
(1, 1, TRUE, TRUE),
(2, 1, TRUE, TRUE),
(3, 2, TRUE, TRUE),
(3, 4, FALSE, TRUE),
(4, 3, TRUE, TRUE),
(4, 5, FALSE, TRUE),
(5, 6, TRUE, TRUE),
(5, 7, FALSE, TRUE),
(6, 8, TRUE, TRUE),
(6, 9, FALSE, TRUE),
(7, 11, TRUE, TRUE),
(8, 12, TRUE, TRUE),
(8, 10, FALSE, TRUE);

INSERT IGNORE INTO professional_locations
(professional_id, location_id, active)
VALUES
(1,1,TRUE),(1,2,TRUE),
(2,1,TRUE),(2,2,TRUE),
(3,1,TRUE),(3,2,TRUE),
(4,1,TRUE),(4,2,TRUE),
(5,1,TRUE),(5,2,TRUE),
(6,1,TRUE),(6,2,TRUE),
(7,1,TRUE),(7,2,TRUE),
(8,1,TRUE),(8,2,TRUE);

INSERT INTO user_insurance_affiliations
(id, user_id, plan_id, membership_number, is_current, valid_from)
VALUES
(1, 100, 1, 'AF-DEMO-100', TRUE, CURDATE()),
(2, 101, 2, 'AF-DEMO-101', TRUE, CURDATE()),
(3, 102, 3, 'AF-DEMO-102', TRUE, CURDATE()),
(4, 103, 4, 'AF-DEMO-103', TRUE, CURDATE()),
(5, 104, 5, 'AF-DEMO-104', TRUE, CURDATE()),
(6, 105, 1, 'AF-DEMO-105', TRUE, CURDATE())
ON DUPLICATE KEY UPDATE
  is_current = VALUES(is_current),
  valid_from = VALUES(valid_from);

-- Disponibilidad dinámica: fechas relativas a la fecha de carga del seed.
-- Cada profesional recibe varios bloques en ambas sedes.
INSERT INTO availability_blocks
(id, professional_id, location_id, available_date, start_time, end_time, active)
VALUES
(1,1,1,DATE_ADD(CURDATE(),INTERVAL 1 DAY),'08:00:00','12:00:00',TRUE),
(2,1,1,DATE_ADD(CURDATE(),INTERVAL 1 DAY),'14:00:00','17:00:00',TRUE),
(3,1,2,DATE_ADD(CURDATE(),INTERVAL 3 DAY),'08:00:00','12:00:00',TRUE),
(4,1,2,DATE_ADD(CURDATE(),INTERVAL 3 DAY),'14:00:00','17:00:00',TRUE),

(5,2,2,DATE_ADD(CURDATE(),INTERVAL 1 DAY),'08:00:00','12:00:00',TRUE),
(6,2,2,DATE_ADD(CURDATE(),INTERVAL 1 DAY),'14:00:00','17:00:00',TRUE),
(7,2,1,DATE_ADD(CURDATE(),INTERVAL 4 DAY),'08:00:00','12:00:00',TRUE),
(8,2,1,DATE_ADD(CURDATE(),INTERVAL 4 DAY),'14:00:00','17:00:00',TRUE),

(9,3,1,DATE_ADD(CURDATE(),INTERVAL 2 DAY),'08:00:00','12:00:00',TRUE),
(10,3,1,DATE_ADD(CURDATE(),INTERVAL 2 DAY),'14:00:00','17:00:00',TRUE),
(11,3,2,DATE_ADD(CURDATE(),INTERVAL 5 DAY),'08:00:00','12:00:00',TRUE),
(12,3,2,DATE_ADD(CURDATE(),INTERVAL 5 DAY),'14:00:00','17:00:00',TRUE),

(13,4,2,DATE_ADD(CURDATE(),INTERVAL 2 DAY),'08:00:00','12:00:00',TRUE),
(14,4,2,DATE_ADD(CURDATE(),INTERVAL 2 DAY),'14:00:00','17:00:00',TRUE),
(15,4,1,DATE_ADD(CURDATE(),INTERVAL 6 DAY),'08:00:00','12:00:00',TRUE),
(16,4,1,DATE_ADD(CURDATE(),INTERVAL 6 DAY),'14:00:00','17:00:00',TRUE),

(17,5,1,DATE_ADD(CURDATE(),INTERVAL 3 DAY),'08:00:00','12:00:00',TRUE),
(18,5,1,DATE_ADD(CURDATE(),INTERVAL 3 DAY),'14:00:00','17:00:00',TRUE),
(19,5,2,DATE_ADD(CURDATE(),INTERVAL 7 DAY),'08:00:00','12:00:00',TRUE),
(20,5,2,DATE_ADD(CURDATE(),INTERVAL 7 DAY),'14:00:00','17:00:00',TRUE),

(21,6,2,DATE_ADD(CURDATE(),INTERVAL 3 DAY),'08:00:00','12:00:00',TRUE),
(22,6,2,DATE_ADD(CURDATE(),INTERVAL 3 DAY),'14:00:00','17:00:00',TRUE),
(23,6,1,DATE_ADD(CURDATE(),INTERVAL 8 DAY),'08:00:00','12:00:00',TRUE),
(24,6,1,DATE_ADD(CURDATE(),INTERVAL 8 DAY),'14:00:00','17:00:00',TRUE),

(25,7,1,DATE_ADD(CURDATE(),INTERVAL 4 DAY),'08:00:00','12:00:00',TRUE),
(26,7,1,DATE_ADD(CURDATE(),INTERVAL 4 DAY),'14:00:00','17:00:00',TRUE),
(27,7,2,DATE_ADD(CURDATE(),INTERVAL 9 DAY),'08:00:00','12:00:00',TRUE),
(28,7,2,DATE_ADD(CURDATE(),INTERVAL 9 DAY),'14:00:00','17:00:00',TRUE),

(29,8,2,DATE_ADD(CURDATE(),INTERVAL 4 DAY),'08:00:00','12:00:00',TRUE),
(30,8,2,DATE_ADD(CURDATE(),INTERVAL 4 DAY),'14:00:00','17:00:00',TRUE),
(31,8,1,DATE_ADD(CURDATE(),INTERVAL 10 DAY),'08:00:00','12:00:00',TRUE),
(32,8,1,DATE_ADD(CURDATE(),INTERVAL 10 DAY),'14:00:00','17:00:00',TRUE)
ON DUPLICATE KEY UPDATE active = VALUES(active);

-- Generar slots atómicos de 30 min a partir de los bloques.
INSERT IGNORE INTO professional_slots
(availability_block_id, start_at, end_at)
SELECT
    ab.id,
    TIMESTAMP(
        ab.available_date,
        ADDTIME(ab.start_time, SEC_TO_TIME(seq.n * 1800))
    ) AS start_at,
    TIMESTAMP(
        ab.available_date,
        ADDTIME(ab.start_time, SEC_TO_TIME((seq.n + 1) * 1800))
    ) AS end_at
FROM availability_blocks ab
JOIN (
    SELECT 0 n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3
    UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7
    UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 10 UNION ALL SELECT 11
    UNION ALL SELECT 12 UNION ALL SELECT 13 UNION ALL SELECT 14 UNION ALL SELECT 15
    UNION ALL SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL SELECT 19
) seq
WHERE ab.active = TRUE
  AND ADDTIME(ab.start_time, SEC_TO_TIME((seq.n + 1) * 1800)) <= ab.end_time;

-- Citas sintéticas de ejemplo.
-- Cita general: APPROVED automáticamente.
INSERT INTO appointments
(id, patient_user_id, professional_id, location_id, specialty_id,
 insurance_affiliation_id, status_id, reason,
 scheduled_start_at, scheduled_end_at, created_by_user_id,
 approved_by_user_id, approved_at)
VALUES
(
  1, 100, 1, 1, 1, 1, 2,
  'Consulta general de demostración',
  TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL 1 DAY),'08:00:00'),
  TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL 1 DAY),'08:30:00'),
  100, NULL, NOW()
),
(
  2, 101, 3, 1, 2, 2, 1,
  'Valoración especializada de demostración',
  TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL 2 DAY),'09:00:00'),
  TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL 2 DAY),'09:30:00'),
  101, NULL, NULL
),
(
  3, 102, 7, 1, 11, 3, 2,
  'Consulta de ortopedia de demostración',
  TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL 4 DAY),'10:00:00'),
  TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL 4 DAY),'11:00:00'),
  102, 1, NOW()
)
ON DUPLICATE KEY UPDATE reason = VALUES(reason);

-- Reservar los slots asociados a esas citas.
UPDATE professional_slots ps
JOIN availability_blocks ab ON ab.id = ps.availability_block_id
SET ps.appointment_id = 1
WHERE ab.professional_id = 1
  AND ab.location_id = 1
  AND ps.start_at >= TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL 1 DAY),'08:00:00')
  AND ps.end_at <= TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL 1 DAY),'08:30:00');

UPDATE professional_slots ps
JOIN availability_blocks ab ON ab.id = ps.availability_block_id
SET ps.appointment_id = 2
WHERE ab.professional_id = 3
  AND ab.location_id = 1
  AND ps.start_at >= TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL 2 DAY),'09:00:00')
  AND ps.end_at <= TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL 2 DAY),'09:30:00');

UPDATE professional_slots ps
JOIN availability_blocks ab ON ab.id = ps.availability_block_id
SET ps.appointment_id = 3
WHERE ab.professional_id = 7
  AND ab.location_id = 1
  AND ps.start_at >= TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL 4 DAY),'10:00:00')
  AND ps.end_at <= TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL 4 DAY),'11:00:00');

INSERT INTO appointment_status_history
(appointment_id, status_id, changed_by_user_id, change_source, reason)
VALUES
(1, 2, NULL, 'SYSTEM', 'Aprobación automática por tratarse de cita de Medicina General'),
(2, 1, 101, 'USER', 'Solicitud especializada pendiente de aprobación administrativa'),
(3, 1, 102, 'USER', 'Solicitud especializada creada'),
(3, 2, 1, 'ADMIN', 'Aprobación administrativa de laboratorio');

INSERT INTO reschedule_requests
(id, appointment_id, requested_by_user_id, requested_location_id, status_id,
 previous_start_at, previous_end_at, requested_start_at, requested_end_at)
VALUES
(
  1, 1, 100, 2, 1,
  TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL 1 DAY),'08:00:00'),
  TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL 1 DAY),'08:30:00'),
  TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL 3 DAY),'09:00:00'),
  TIMESTAMP(DATE_ADD(CURDATE(),INTERVAL 3 DAY),'09:30:00')
)
ON DUPLICATE KEY UPDATE status_id = VALUES(status_id);

-- ============================================================
-- 8. CONSULTAS DE VERIFICACIÓN
-- ============================================================

-- Resumen de tablas
SELECT 'roles' table_name, COUNT(*) rows_count FROM roles
UNION ALL SELECT 'users', COUNT(*) FROM users
UNION ALL SELECT 'locations', COUNT(*) FROM locations
UNION ALL SELECT 'specialties', COUNT(*) FROM specialties
UNION ALL SELECT 'professionals', COUNT(*) FROM professionals
UNION ALL SELECT 'availability_blocks', COUNT(*) FROM availability_blocks
UNION ALL SELECT 'professional_slots', COUNT(*) FROM professional_slots
UNION ALL SELECT 'appointments', COUNT(*) FROM appointments;

-- Disponibilidad libre con profesional, sede y fecha.
SELECT
    p.professional_code,
    CONCAT(u.first_name, ' ', u.last_name) AS professional_name,
    l.code AS location_code,
    l.name AS location_name,
    ps.start_at,
    ps.end_at
FROM professional_slots ps
JOIN availability_blocks ab ON ab.id = ps.availability_block_id
JOIN professionals p ON p.id = ab.professional_id
JOIN users u ON u.id = p.user_id
JOIN locations l ON l.id = ab.location_id
WHERE ps.appointment_id IS NULL
  AND ab.active = TRUE
ORDER BY ps.start_at, professional_name
LIMIT 50;
