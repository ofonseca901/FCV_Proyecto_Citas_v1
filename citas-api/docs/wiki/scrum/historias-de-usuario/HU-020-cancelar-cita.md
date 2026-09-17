---
id: HU-020
tipo: historia-de-usuario
titulo: Cancelar cita
estado: Pendiente de aprobación
epica: "[[EP-004-citas-generales-y-seguimiento]]"
esfuerzo: Alto
sprint_sugerido: S4
dependencias: ["[[HU-019-consultar-mis-citas]]"]
relacionadas: ["[[HU-029-consultar-historial-de-estados]]"]
---

# HU-020 — Cancelar cita

## Historia de usuario
**COMO** USER **QUIERO** cancelar una cita futura no terminal **PARA** liberar la franja que no usaré.

## Contexto, alcance y reglas
PRD RF-14/RN-09/RN-11. Transición a `CANCELLED`, libera slots y registra historial. No reactiva cita cancelada ni permite cancelar ajena/pasada/terminal.

## Dependencias y relaciones
- Épica: [[EP-004-citas-generales-y-seguimiento]]. Depende de [[HU-019-consultar-mis-citas]].

## Esfuerzo
**Nivel:** Alto. Transición válida, liberación atómica y auditoría.

## Tareas de desarrollo
- [ ] **T-01 — Regla de transición.** Modelar estados permitidos y liberación en transacción.
- [ ] **T-02 — Acción propia.** Exponer cancelación con ownership y respuesta idempotente definida.
- [ ] **T-03 — Pruebas/UI.** Cubrir elegibilidad, slots liberados, historial y reintentos.

## Criterios de aceptación
- **CA-01:** Dada cita propia futura no terminal, cuando USER cancela, entonces queda `CANCELLED` y la franja se libera.
- **CA-02:** Dada cita pasada, terminal, ajena o ya cancelada, cuando cancela, entonces no se reactiva ni cambia indebidamente.
- **CA-03:** Dada cancelación válida, cuando consulta historial, entonces existe transición con actor/fuente y fecha/hora.
- **CA-04:** Dada franja liberada, cuando USER vuelve a buscar, entonces puede ser elegible según demás reglas.

## Definition of Done
- [ ] CA-01 a CA-04 tienen prueba de integración y transición concurrente aplicable.
- [ ] Estado, liberación y auditoría son atómicos y trazables.
- [ ] Contrato web/API y documentación Scrum quedan alineados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-14, RN-09/RN-11/RN-12. Verificado: 2026-09-17.
