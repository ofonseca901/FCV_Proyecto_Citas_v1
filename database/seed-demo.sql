INSERT IGNORE INTO user_roles(user_id, role_code) SELECT id, 'ADMIN' FROM app_users WHERE email = 'admin@agenda.local';
INSERT IGNORE INTO user_roles(user_id, role_code) SELECT id, 'PROFESSIONAL' FROM app_users WHERE email LIKE 'medico%@agenda.local';
INSERT IGNORE INTO professionals(user_id, professional_code, license_number, active)
SELECT id, CONCAT('PROF-', LPAD(SUBSTRING(email,7,2),3,'0')), CONCAT('LIC-DEMO-',SUBSTRING(email,7,2)), TRUE FROM app_users WHERE email LIKE 'medico%@agenda.local';
INSERT IGNORE INTO professional_specialties(professional_id, specialty_id, primary_specialty)
SELECT p.id, s.id, TRUE FROM professionals p JOIN app_users u ON u.id=p.user_id JOIN specialties s ON s.code = CASE RIGHT(LEFT(u.email,8),1) WHEN '1' THEN 'MEDICINA_GENERAL' WHEN '2' THEN 'MEDICINA_GENERAL' WHEN '3' THEN 'CARDIOLOGIA' WHEN '4' THEN 'NEUROLOGIA' ELSE 'CARDIOLOGIA' END WHERE u.email LIKE 'medico%@agenda.local';
INSERT IGNORE INTO professional_locations(professional_id, location_id)
SELECT p.id, l.id FROM professionals p JOIN app_users u ON u.id=p.user_id JOIN locations l ON l.code = 'HIC' WHERE u.email LIKE 'medico%@agenda.local';
INSERT IGNORE INTO availability_blocks(professional_id, location_id, available_date, start_time, end_time, active)
SELECT p.id, l.id, DATE_ADD(CURDATE(), INTERVAL 1 DAY), '08:00:00', '12:00:00', TRUE FROM professionals p JOIN app_users u ON u.id=p.user_id JOIN locations l ON l.code = 'HIC' WHERE u.email LIKE 'medico%@agenda.local';
