# Agente de `citas-api`

## Alcance y estado verificado

Este repositorio contiene el backend del laboratorio en `citas-api/`. Trabajar aquí no autoriza cambios en `citas-web` ni en otros repositorios.

Estado comprobado en el árbol actual:

- Java 21 (`pom.xml`), Spring Boot 3.5.16, Maven y dependencias de Web, Validation, Data JPA, Security Resource Server, Actuator y Flyway.
- Persistencia configurada para MySQL y validada por JPA con `ddl-auto: validate`; las pruebas de integración usan H2 aislado.
- S2 implementa registro de `USER`, login JWT, refresh rotativo, logout revocable y `/api/auth/me`.
- La migración existente es `src/main/resources/db/migration/V1__identity_and_sessions.sql` y cubre usuarios, roles y sesiones; no existe todavía el modelo de citas/agendas del PRD.
- La prueba backend actual es `src/test/java/co/fcv/citas/AuthIntegrationTest.java`; cubre validación, duplicados, separación/rotación de tokens, concurrencia de refresh, expiración y CORS.
- Las HU-001, HU-002 y HU-003 están marcadas `Pendiente de aprobación`, con DoD sin completar. No tratar una propuesta Scrum como autorización implícita.

## Arquitectura obligatoria

- `domain/` contiene modelos puros (`User`, `AuthSession`); no depende de Spring, JPA ni HTTP.
- `application/` contiene casos de uso y puertos (`AuthService`, `AuthPorts`, `AuthFailure`); no importar frameworks de infraestructura aquí.
- `adapter/persistence/` implementa puertos mediante entidades y repositorios JPA.
- `adapter/security/` configura BCrypt, JWT, autorización, sesiones stateless y CORS.
- `adapter/web/` traduce HTTP/JSON, valida DTOs y delimita transacciones mediante la fachada; los controladores no concentran reglas de negocio.
- No exponer entidades, hashes ni detalles internos en respuestas. No acoplar el backend a React o Angular.

## Alcance funcional

Usar el PRD como fuente de requisitos, pero confirmar primero la HU y su DoD. Agenda, disponibilidad, citas, reprogramación, catálogos, administración y recuperación de contraseña no están implementados en este estado S2; no presentarlos como funcionales ni inventar endpoints para ellos.

## Seguridad y configuración

- Passwords: BCrypt con coste 12; la aplicación valida mínimo de 8 caracteres y máximo de 72 bytes UTF-8.
- JWT access y refresh usan secretos separados, HS256, propósito, issuer/audience y expiración; los secretos llegan por variables de entorno y deben tener al menos 32 bytes.
- La sesión se comprueba contra persistencia; refresh rota mediante actualización condicional y logout revoca por sesión.
- No registrar ni documentar passwords, tokens, hashes, credenciales o valores de `.env`. No abrir `.env`.
- Registro público solo crea rol `USER`; roles y ownership se derivan del servidor.
- Mantener CORS explícito (`FRONTEND_ORIGIN`) y validación server-side.

## Esquema y migraciones

- No editar migraciones ya aplicadas. Todo cambio de esquema requiere una nueva migración Flyway, revisión de dependencias funcionales/3FN y pruebas relevantes.
- Mantener `spring.jpa.hibernate.ddl-auto=validate`; Hibernate no crea ni modifica tablas.
- Usar únicamente datos sintéticos del laboratorio. No conectar sistemas clínicos ni servicios reales de FCV.

## Flujo de trabajo

1. Localizar la HU aprobada, sus criterios de aceptación y DoD en `docs/wiki/scrum/`. Si sigue pendiente, detener la implementación y solicitar aprobación.
2. Identificar reglas de negocio, rutas, DTOs, persistencia y consumidores afectados.
3. Antes de editar, declarar HU, objetivo, archivos previstos y si cambia el contrato REST.
4. Implementar el mínimo coherente dentro de las capas anteriores; no editar `citas-web`.
5. Añadir o actualizar pruebas de dominio, aplicación, REST y persistencia según el riesgo.
6. Verificar arquitectura, seguridad, contrato y DoD; distinguir evidencia ejecutada de lo no verificado.

## Verificación

Desde la raíz del workspace, con Docker disponible:

```powershell
docker compose exec -T citas-api-dev mvn -B -ntp verify
node citas-api/scripts/smoke-auth.mjs
```

`mvn verify` usa H2 aislado y no demuestra por sí solo integración MySQL. `smoke-auth.mjs` requiere la API activa y crea datos sintéticos; no imprimir tokens ni contraseñas en la salida. Para ejecución local, usar `mvn spring-boot:run` con variables de `.env.example` exportadas, nunca leyendo `.env`.

## Contratos y coordinación

- El contrato S2 está en `docs/wiki/llm-wiki/wiki/contrato-auth.md`; contrastarlo con controladores y pruebas antes de modificarlo.
- Un cambio REST exige evidencia backend y coordinación con el consumidor, pero este agente no modifica `citas-web`.
- La única LLM Wiki es global y la mantiene el agente orquestador. Este agente no crea ni mantiene una wiki propia: entrega al orquestador los hechos verificados, cambios de contrato, pruebas y limitaciones.
- Trabajar en `develop`; no fusionar, publicar ni reescribir historial sin solicitud.
