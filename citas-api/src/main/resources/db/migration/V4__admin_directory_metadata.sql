ALTER TABLE app_users ADD COLUMN created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
CREATE INDEX ix_users_active_name ON app_users(active, last_name, first_name);
CREATE INDEX ix_professionals_active ON professionals(active, professional_code);
