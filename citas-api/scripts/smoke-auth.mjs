import assert from 'node:assert/strict';
import { randomUUID } from 'node:crypto';

const base = process.env.API_BASE_URL || 'http://localhost:8080';
const id = randomUUID().replaceAll('-', '');
const data = { firstName: 'Paciente', lastName: 'Sintetico', documentType: 'CC', documentNumber: id.slice(0, 24),
  email: `s2-${id}@example.test`, phone: '3000000000', password: randomUUID() };
let checks = 0;
async function call(path, expected, body, token, method) {
  const result = await fetch(`${base}${path}`, {
    method: method || (body === undefined ? 'GET' : 'POST'),
    headers: { ...(body === undefined ? {} : { 'Content-Type': 'application/json' }), ...(token ? { Authorization: `Bearer ${token}` } : {}) },
    body: body === undefined ? undefined : JSON.stringify(body), signal: AbortSignal.timeout(15000),
  });
  assert.equal(result.status, expected, `${path}: status esperado ${expected}, recibido ${result.status}`);
  checks++;
  const text = await result.text();
  return text ? JSON.parse(text) : null;
}
try {
  await call('/actuator/health', 200);
  const user = await call('/api/auth/register', 201, data);
  assert.deepEqual(user.roles, ['USER']); assert.equal(user.passwordHash, undefined);
  await call('/api/auth/register', 409, { ...data, email: data.email.toUpperCase() });
  await call('/api/auth/register', 409, { ...data, email: `other-${data.email}` });
  await call('/api/auth/register', 400, { ...data, roles: ['ADMIN'] });
  await call('/api/auth/register', 400, { ...data, firstName: ' ' });
  await call('/api/auth/login', 401, { email: data.email, password: 'incorrecta' });
  await call('/api/auth/me', 401);
  const session = await call('/api/auth/login', 200, { email: data.email, password: data.password });
  await call('/api/auth/me', 200, undefined, session.accessToken);
  await call('/api/auth/me', 401, undefined, session.refreshToken);
  await call('/api/auth/me', 401, undefined, session.accessToken + 'broken');
  await call('/api/auth/refresh', 401, { refreshToken: session.accessToken });
  const next = await call('/api/auth/refresh', 200, { refreshToken: session.refreshToken });
  await call('/api/auth/refresh', 401, { refreshToken: session.refreshToken });
  await call('/api/auth/logout', 204, {}, next.accessToken);
  await call('/api/auth/me', 401, undefined, next.accessToken);
  await call('/api/auth/refresh', 401, { refreshToken: next.refreshToken });
  console.log(`PASS: ${checks} comprobaciones HTTP de autenticación. Cuenta sintética de prueba creada; no se imprimieron credenciales ni tokens.`);
} catch (error) {
  console.error(`FAIL: ${error.message}`); process.exitCode = 1;
}
