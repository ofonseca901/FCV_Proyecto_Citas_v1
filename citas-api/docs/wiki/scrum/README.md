# Plan S2 — acceso de usuarios

Fuente: PRD RF-01 y RF-02, restricciones técnicas y entregable S2. Estas especificaciones están **pendientes de aprobación**; la autorización de trabajar no se registra como aprobación de historias aún no revisadas.

## Incremento propuesto

- Épica: [[EP-001-acceso-de-usuarios]].
- S2, orden secuencial: [[HU-001-registrar-usuario]], [[HU-002-gestionar-sesion]], [[HU-003-interfaz-de-acceso]].
- Stack backend requerido: Java 21, Spring Boot 3.5.x, Maven, MySQL 8.4, JPA, Flyway, JWT.
- Frontend pendiente de selección/exportación; React + Vite + TypeScript es la propuesta si no hay diseño existente.

## Límites y decisiones

S2 entrega acceso de pacientes y una base ejecutable. No entrega agenda, recuperación de contraseña, gestión administrativa ni citas. La conexión MySQL y los commits deben verificarse con resultados reales. No se considera un frontend local una exportación de Stitch/AI Studio.

Las fases siguientes conservan el alcance del PRD: agenda y reservas en S3/S4; automatizaciones n8n en S5/S6. Sus historias se detallarán antes de implementarlas.
