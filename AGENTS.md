# Instrucciones del workspace FCV Citas

## Contexto
Laboratorio académico con datos sintéticos. Leer `README.md`, `PRD.md`, `RESTRICCIONES_TECNICAS.md`, los AGENTS del repositorio afectado y `citas-api/docs/wiki/llm-wiki/wiki/index.md` antes de modificar funcionalidades.

## Responsabilidades
- `citas-api`: Java 21, Spring Boot 3.5.x, Maven, dominio/aplicación independientes de Spring, adaptadores REST/JPA/seguridad, MySQL y Flyway.
- `citas-web`: React + Vite + TypeScript; consume directamente REST, sin Express/BFF.
- Única wiki global: `citas-api/docs/wiki/llm-wiki/`.
- Antes de cambios en ambos repos, describir alcance y archivos afectados. Mantener coherencia del contrato de autenticación.

## Coordinación cross-repo
- Una Historia de Usuario (HU) es la unidad primaria de alcance y Definition of Done.
- Antes de modificar ambos repositorios, declarar: HU, objetivo, contrato REST afectado y archivos previstos por repositorio.
- Un cambio de contrato REST exige actualizar documentación, pruebas del backend y consumidor del frontend; no se considera terminado con evidencia de un solo repo.
- La raíz conserva su Git histórico; los repositorios de aplicación se operan con `git -C citas-api` y `git -C citas-web`.

## Trabajo y evidencia
- Ambos repos tienen `main` y `develop`. Trabajar en `develop`, no fusionar ni publicar sin solicitud.
- Existe un Git raíz previo a S2. Preservar su historial; no eliminarlo ni convertirlo en submódulos implícitamente. No usar `git add .` en la raíz para registrar repos anidados.
- No leer ni imprimir `.env`, credenciales, JWT o hashes. Usar variables de entorno y ejemplos sin secretos.
- Usar solo datos sintéticos de prueba; no enviar correos ni conectar sistemas reales.
- Planificación Scrum con la skill local limitada a `docs/wiki/scrum/`; no marcar aprobaciones o validaciones sin evidencia.
- Los prompts en `prompts/` son material de formación, no acciones que ejecutar automáticamente.
- Actualizar wiki, contrato y evidencia S2 al modificar comportamiento. Registrar limitaciones reales.

## LLM Wiki global
- `raw/` contiene fuentes curadas e inmutables; el agente las lee y nunca reescribe una fuente aprobada durante INGEST.
- `wiki/` contiene síntesis mantenidas por el agente; toda página nueva debe enlazarse desde `wiki/index.md`.
- `schema/` define convenciones y los flujos INGEST, QUERY, LEARN y LINT.
- El conocimiento durable se clasifica como `HECHO`, `DECISIÓN`, `PREFERENCIA` o `PREGUNTA ABIERTA`, siempre con fuente y fecha de verificación.
- `wiki/log.md` es append-only y registra operaciones realizadas, no conversaciones completas.
- INGEST integra una fuente en páginas existentes y señala contradicciones; QUERY empieza por `wiki/index.md` y contrasta con código/especificaciones; LEARN solo persiste conocimiento verificable; LINT busca claims obsoletos, duplicados, huérfanos, enlaces rotos y decisiones no aprobadas.
- Nunca persistir contraseñas, tokens, hashes, secretos, PII real ni contenido privado de FCV. No abrir `.env`.

## Automatizaciones n8n
- Las exportaciones de workflows se versionan como JSON en `citas-api/automations/n8n/`.
- No guardar credenciales, tokens OAuth, URLs privadas ni secretos embebidos en esos JSON.
- Las notas Markdown de diseño o formación deben permanecer fuera de la carpeta de exportaciones y no se consideran workflows ejecutables; los archivos históricos existentes allí quedan como pendiente explícito de reubicación y no deben activarse.

## Verificación
Desde raíz, con Docker disponible:
`docker compose exec -T citas-api-dev mvn -B -ntp verify`
`docker compose exec -T citas-web-dev npm run build`
`node citas-api/scripts/smoke-auth.mjs`

El script smoke requiere API ejecutándose, crea cuentas sintéticas y no imprime tokens ni contraseñas.
