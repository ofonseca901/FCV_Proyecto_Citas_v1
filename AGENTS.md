# Orquestación del workspace `citas`

## Alcance y repositorios

- Esta raíz orquesta exactamente dos repositorios Git independientes: `citas-api` y `citas-web`.
- No inicializar Git en la raíz ni mezclar responsabilidades entre repositorios.
- El frontend consume directamente `citas-api` por REST; no usar Express ni BFF.

## Inicio de cada tarea

1. Leer `README.md`, `PRD.md`, `RESTRICCIONES_TECNICAS.md` y `database/REQUISITOS_NORMALIZACION_3FN.md`.
2. Leer los `AGENTS.md` específicos cuando existan.
3. Para consultas de conocimiento, leer primero `citas-api/docs/FCV Dev/llm-wiki/wiki/index.md`.
4. Confirmar el alcance contra una HU y su DoD cuando estén disponibles.

## Límites de responsabilidad

- `citas-api`: dominio, aplicación, persistencia, seguridad, REST, pruebas, migraciones y n8n.
- `citas-web`: interfaz, navegación, estado visual, formularios, cliente REST y pruebas frontend.
- No inventar requisitos, endpoints, estados, catálogos ni políticas ausentes de las fuentes aprobadas.

## Cambios cross-repo

Antes de cambiar un contrato REST, producir un plan que enumere repositorios, archivos, compatibilidad, migración y pruebas. Validar y documentar evidencia en ambos repositorios.

## Git y seguridad

- Trabajar en `develop`; reservar `main` para incrementos estables.
- Preservar historial trazable; no reescribirlo para ocultar progreso.
- No leer, mostrar ni versionar secretos, tokens, credenciales o PII.
- Usar datos sintéticos; las referencias públicas FCV permitidas son únicamente las incluidas en los requisitos.
- Los JSON de n8n viven en `citas-api/automations/n8n/` y no contienen credenciales.

## Subagentes

- Catálogo canónico: `citas-api/docs/FCV Dev/subagents/README.md`.
- Elegir el subagente más específico y entregarle HU/CA/DoD, repositorio, archivos permitidos, contrato o diseño, autoridad de edición y evidencia esperada.
- Los especialistas pueden analizar o editar solo cuando el encargo lo autoriza; los verificadores son siempre independientes y de solo lectura.
- Ejecutar especialistas en paralelo únicamente cuando no compartan archivos de edición. Ejecutar verificadores después de la implementación.
- El orquestador consolida resultados, resuelve solapamientos y evita convertir recomendaciones en requisitos no aprobados.

## LLM Wiki

- Ubicación única: `citas-api/docs/FCV Dev/llm-wiki/`.
- `raw/` es inmutable; `wiki/` contiene síntesis verificadas; `schema/` gobierna formato y operación.
- Toda modificación estructural actualiza `wiki/index.md`; toda operación relevante agrega una entrada append-only en `wiki/log.md`.
- Persistir únicamente conocimiento durable clasificado como HECHO, DECISIÓN, PREFERENCIA o PREGUNTA ABIERTA.
- Separar evidencia de inferencia y ejecutar LINT ante cambios relevantes.

## Skills

- `scrum-spec-orchestrator` conserva por ahora una allowlist interna para la ruta histórica `citas-api/docs/wiki/scrum/`; no invocarla sobre la nueva ubicación hasta actualizar esa Skill de forma explícita.
- `stitch-design-to-frontend` gobierna Stitch → aprobación → AI Studio → reconciliación; no define backend.
