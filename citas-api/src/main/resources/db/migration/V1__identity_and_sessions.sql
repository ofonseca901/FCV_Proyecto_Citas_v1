CREATE TABLE app_users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    document_type VARCHAR(10) NOT NULL,
    document_number VARCHAR(30) NOT NULL,
    email VARCHAR(254) NOT NULL,
    phone VARCHAR(25) NOT NULL,
    password_hash VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT uk_users_document UNIQUE (document_type, document_number)
);

CREATE TABLE roles (
    code VARCHAR(20) NOT NULL PRIMARY KEY
);
INSERT INTO roles (code) VALUES ('USER'), ('PROFESSIONAL'), ('ADMIN');

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_code VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_id, role_code),
    FOREIGN KEY (user_id) REFERENCES app_users(id),
    FOREIGN KEY (role_code) REFERENCES roles(code)
);

CREATE TABLE auth_sessions (
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    refresh_id VARCHAR(36) NOT NULL,
    expires_at TIMESTAMP(6) NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES app_users(id)
);
CREATE INDEX ix_sessions_user ON auth_sessions(user_id);
CREATE INDEX ix_sessions_expiration ON auth_sessions(expires_at);
