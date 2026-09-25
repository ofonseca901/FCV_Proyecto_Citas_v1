ALTER TABLE auth_sessions ADD COLUMN last_activity_at TIMESTAMP(6) NULL;
UPDATE auth_sessions SET last_activity_at = CURRENT_TIMESTAMP(6) WHERE last_activity_at IS NULL;
ALTER TABLE auth_sessions MODIFY COLUMN last_activity_at TIMESTAMP(6) NOT NULL;
CREATE INDEX ix_sessions_last_activity ON auth_sessions(last_activity_at);
