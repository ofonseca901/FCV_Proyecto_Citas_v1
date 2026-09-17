---
id: HU-030
tipo: historia-de-usuario
titulo: Mantener contrato REST
estado: Pendiente de aprobación
epica: "[[EP-008-auditoria-y-contrato]]"
esfuerzo: Alto
sprint_sugerido: S4
dependencias: ["[[HU-003-interfaz-de-acceso]]", "[[HU-029-consultar-historial-de-estados]]"]
relacionadas: ["[[HU-017-buscar-disponibilidad]]", "[[HU-019-consultar-mis-citas]]"]
---

# HU-030 — Mantener contrato REST

## Historia de usuario
**COMO** equipo web y API **QUIERO** mantener documentado y verificable el contrato REST directo **PARA** que las capacidades funcionen sin BFF.

## Contexto y reglas
PRD RF-20 y restricciones. Cubre recursos, autenticación, roles, errores, validaciones y cambios de contrato usados por UI. No define endpoints inventados ni expone entidades, hashes, tokens o secretos.

## Dependencias y relaciones
- Épica: [[EP-008-auditoria-y-contrato]]. Depende de acceso y auditoría; acompaña todas las HU cross-repo.

## Esfuerzo
**Nivel:** Alto. Evolución coordinada entre dos repositorios y pruebas de contrato.

## Tareas de desarrollo
- [ ] **T-01 — Especificación REST.** Documentar operaciones reales, DTO, auth, errores y ownership.
- [ ] **T-02 — Consumidor directo.** Alinear URL configurable y manejo de errores del frontend.
- [ ] **T-03 — Verificación cruzada.** Mantener pruebas backend y build/typecheck/pruebas web aplicables por cambio.

## Criterios de aceptación
- **CA-01:** Dada capacidad aprobada con UI, cuando se integra, entonces frontend consume directamente API REST sin Express/BFF.
- **CA-02:** Dado contrato publicado, cuando API devuelve éxito o error previsto, entonces consumidor interpreta campos/estados de forma coherente.
- **CA-03:** Dado cambio de contrato, cuando se acepta, entonces se actualizan documento, pruebas backend y consumidor frontend en la misma HU.
- **CA-04:** Dado dato sensible o entidad interna, cuando se revisa contrato, entonces no se expone.

## Definition of Done
- [ ] CA-01 a CA-04 tienen evidencia en ambos repositorios para cada capacidad aplicable.
- [ ] Autenticación, CORS, validación y ownership están documentados y probados.
- [ ] No existen cambios de contrato sin enlace a HU y evidencia cruzada.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-20; restricciones backend/frontend, seguridad y pruebas S3+. Verificado: 2026-09-17.
