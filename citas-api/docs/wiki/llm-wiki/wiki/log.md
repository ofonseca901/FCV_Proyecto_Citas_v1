# Registro cronológico

- 2026-09-16 · INGEST: requisitos S2, restricciones y normalización de identidad.
- 2026-09-16 · LEARN: repos de aplicación inicialmente vacíos; Git raíz previo preservado; Docker iniciado para trabajar con Java 21 y MySQL.
- 2026-09-16 · LEARN: base React creada localmente como propuesta sin procedencia Stitch/AI Studio. HU pendientes de aprobación explícita.
- 2026-09-16 · LEARN: corregida conexión mediante overlay al esquema existente vacío `citas_fcv_training`; conservado volumen MySQL. Claves JWT de desarrollo generadas en archivo ignorado sin alterar `.env`.
- 2026-09-16 · LINT/VERIFY: 6 pruebas de integración H2 y 18 comprobaciones HTTP MySQL correctas; build frontend correcto; flujo de navegador registro/login/error/logout comprobado. Aprobaciones y origen Stitch/AI Studio pendientes; ver s2-evidencia.md.
- 2026-09-17 · INGEST/SCHEMA: se incorporó el gobierno operativo de RAW/WIKI/SCHEMA, manifiesto de procedencia, plantilla de páginas y operaciones INGEST/QUERY/LEARN/LINT; se añadieron decisiones, preferencias y riesgos. No se modificaron fuentes originales ni funcionalidades.
# 2026-09-22 — LEARN

- Se registró el contrato y evidencia inicial de S3. Fuente: `SchedulingController`, migración V2 y verificaciones aisladas. No se cerraron HU: faltan pruebas específicas y prueba manual por roles.
# 2026-09-24 — S4

Se añadió el contrato REST S4, la migración compatible V3 y evidencia de validación disponible. Las automatizaciones n8n permanecen diferidas a S5/S6.

# 2026-09-25 — LEARN

- HECHO: se publicó `GET /api/v1/professional/specialties` para el profesional autenticado. Expone especialidades asignadas y metadatos públicos, no persistentes, del resultado más reciente de PubMed; la ausencia temporal de la fuente se expresa de forma segura.
- EVIDENCIA: imagen Docker de API y web construida; health de API y web respondió 200; prueba autenticada con datos sintéticos devolvió una especialidad y un artículo disponible.

# 2026-09-25 — LEARN

- HECHO: la sesión se invalida y revoca por 10 minutos sin actividad. La migración V5 añade `last_activity_at`; la API aplica la regla en identidad y refresh, y la web la refuerza sin persistir tokens.
- EVIDENCIA: `AuthIntegrationTest` ejecutó 7 pruebas sin fallas, incluida la revocación tras 11 minutos; `npm run typecheck` y `npm run lint` finalizaron correctamente.
