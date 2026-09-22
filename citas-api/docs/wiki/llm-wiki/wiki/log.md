# Registro cronológico

- 2026-09-16 · INGEST: requisitos S2, restricciones y normalización de identidad.
- 2026-09-16 · LEARN: repos de aplicación inicialmente vacíos; Git raíz previo preservado; Docker iniciado para trabajar con Java 21 y MySQL.
- 2026-09-16 · LEARN: base React creada localmente como propuesta sin procedencia Stitch/AI Studio. HU pendientes de aprobación explícita.
- 2026-09-16 · LEARN: corregida conexión mediante overlay al esquema existente vacío `citas_fcv_training`; conservado volumen MySQL. Claves JWT de desarrollo generadas en archivo ignorado sin alterar `.env`.
- 2026-09-16 · LINT/VERIFY: 6 pruebas de integración H2 y 18 comprobaciones HTTP MySQL correctas; build frontend correcto; flujo de navegador registro/login/error/logout comprobado. Aprobaciones y origen Stitch/AI Studio pendientes; ver s2-evidencia.md.
- 2026-09-17 · INGEST/SCHEMA: se incorporó el gobierno operativo de RAW/WIKI/SCHEMA, manifiesto de procedencia, plantilla de páginas y operaciones INGEST/QUERY/LEARN/LINT; se añadieron decisiones, preferencias y riesgos. No se modificaron fuentes originales ni funcionalidades.
# 2026-09-22 — LEARN

- Se registró el contrato y evidencia inicial de S3. Fuente: `SchedulingController`, migración V2 y verificaciones aisladas. No se cerraron HU: faltan pruebas específicas y prueba manual por roles.
