# Agente de citas-api

## Stack y capas
Java 21, Spring Boot 3.5.16, Maven, JPA, MySQL 8.4 y Flyway.
- `domain/`: registros de usuario y sesión, sin frameworks.
- `application/`: casos de uso y puertos, sin dependencias Spring/JPA.
- `adapter/persistence/`: entidades y repositorios JPA.
- `adapter/security/`: JWT, BCrypt, autorización y CORS.
- `adapter/web/`: DTO validados, controladores, errores y fachada transaccional.

No exponer entidades o hashes como respuestas HTTP. Registro público solo USER. Tokens access/refresh con secretos distintos; comprobar propósito, expiración y sesión activa. Renovación mediante actualización condicional atómica. Mantener logout revocable por sesión.

## Comandos
Con Java 21/Maven: `mvn -B -ntp verify`.
Desde workspace Docker: `docker compose exec -T citas-api-dev mvn -B -ntp verify`.
Prueba HTTP contra MySQL: `node scripts/smoke-auth.mjs`, con API en localhost:8080 o `API_BASE_URL` configurada.

## Datos y documentación
- Cambios de esquema mediante nuevas migraciones; no editar migraciones ya aplicadas.
- No registrar secretos ni credenciales; no abrir `.env`.
- Tests de integración usan H2 aislado; la evidencia MySQL es separada.
- Contrato: `docs/wiki/llm-wiki/wiki/contrato-auth.md`.
- Plan S2: `docs/wiki/scrum/README.md`; no declarar aprobación del usuario por inferencia.
- Wiki global: `docs/wiki/llm-wiki/wiki/index.md`; log cronológico append-only.
- Desarrollo en `develop`; nunca reescribir historial para simular progreso.
