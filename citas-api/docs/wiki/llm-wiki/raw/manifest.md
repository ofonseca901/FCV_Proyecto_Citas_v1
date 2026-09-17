# Manifiesto de procedencia RAW

`raw/` es la colección de fuentes curadas. Una entrada identifica el origen, versión o commit, fecha de incorporación, clasificación y hash cuando se toma un snapshot. Las fuentes aprobadas no se editan durante INGEST; una revisión genera una nueva versión.

| ID | Fuente | Estado | Uso inicial |
|---|---|---|---|
| SRC-PRD-001 | `PRD.md`, versión 1.0 | normativa del workspace | alcance funcional y actores |
| SRC-TECH-001 | `RESTRICCIONES_TECNICAS.md` | normativa del workspace | stack, seguridad, Git y n8n |
| SRC-DB-001 | `database/REQUISITOS_NORMALIZACION_3FN.md` | normativa del workspace | modelo 3FN y decisiones exigidas |
| SRC-ROOT-001 | `README.md` y `AGENTS.md` | gobernanza | estructura, límites y operación |
| SRC-API-001 | `citas-api/README.md` y `citas-api/AGENTS.md` | evidencia operativa | backend S2 y verificaciones |
| SRC-WEB-001 | `citas-web/README.md` y `citas-web/AGENTS.md` | evidencia operativa | frontend S2 y restricciones |
| SRC-SCRUM-001 | `citas-api/docs/wiki/scrum/` | propuesta | HU y DoD, sin aprobación implícita |
| SRC-EVID-S2-001 | `citas-api/docs/wiki/llm-wiki/wiki/s2-evidencia.md` | evidencia fechada | resultados y limitaciones S2 |

Este manifiesto no autoriza a tratar una propuesta Scrum como decisión aprobada. `database/reference/` queda fuera del INGEST inicial hasta que se confirme su autorización como material de comparación.
