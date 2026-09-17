---
id: HU-023
tipo: historia-de-usuario
titulo: Decidir cita especializada
estado: Pendiente de aprobación
epica: "[[EP-005-citas-especializadas]]"
esfuerzo: Alto
sprint_sugerido: S4
dependencias: ["[[HU-022-consultar-bandeja-citas-especializadas]]"]
relacionadas: ["[[HU-029-consultar-historial-de-estados]]"]
---

# HU-023 — Decidir cita especializada

## Historia de usuario
**COMO** ADMIN **QUIERO** aprobar o rechazar una solicitud especializada **PARA** controlar la atención solicitada.

## Contexto y reglas
PRD RF-12/RN-03/RN-04/RN-09. Solo `REQUESTED` pasa a `APPROVED` o `REJECTED`; rechazo exige motivo y libera slots. La decisión es exclusiva de ADMIN y queda auditada.

## Dependencias y relaciones
- Épica: [[EP-005-citas-especializadas]]. Depende de [[HU-022-consultar-bandeja-citas-especializadas]].

## Esfuerzo
**Nivel:** Alto. Transición concurrente, liberación de retención, motivo y auditoría.

## Tareas de desarrollo
- [ ] **T-01 — Transición atómica.** Validar estado vigente, decisión y liberación solo al rechazar.
- [ ] **T-02 — Acción ADMIN.** Exponer aprobación/rechazo con motivo obligatorio.
- [ ] **T-03 — Pruebas y UI.** Cubrir carreras, roles, motivo e historial.

## Criterios de aceptación
- **CA-01:** Dada solicitud `REQUESTED`, cuando ADMIN aprueba, entonces queda `APPROVED` y conserva su franja.
- **CA-02:** Dada solicitud `REQUESTED`, cuando ADMIN rechaza con motivo, entonces queda `REJECTED`, guarda motivo y libera slots.
- **CA-03:** Dado rechazo sin motivo, estado distinto o no ADMIN, cuando decide, entonces se rechaza sin transición.
- **CA-04:** Dada decisión válida, cuando se consulta historial/bandeja, entonces registra actor/fuente/fecha y deja de estar pendiente.

## Definition of Done
- [ ] CA-01 a CA-04 tienen pruebas de integración y concurrencia aplicable.
- [ ] Transición, slots y auditoría son atómicos y no exponen datos innecesarios.
- [ ] Contrato web/API y trazabilidad Scrum quedan actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-12/RF-18, RN-03/RN-04/RN-09/RN-11/RN-12. Verificado: 2026-09-17.
