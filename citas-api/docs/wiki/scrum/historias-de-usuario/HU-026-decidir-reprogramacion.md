---
id: HU-026
tipo: historia-de-usuario
titulo: Decidir reprogramación
estado: Pendiente de aprobación
epica: "[[EP-006-reprogramacion]]"
esfuerzo: Alto
sprint_sugerido: S4
dependencias: ["[[HU-025-consultar-bandeja-reprogramaciones]]"]
relacionadas: ["[[HU-029-consultar-historial-de-estados]]"]
---

# HU-026 — Decidir reprogramación

## Historia de usuario
**COMO** ADMIN **QUIERO** aprobar o rechazar una reprogramación pendiente **PARA** aplicar la franja correcta de forma segura.

## Contexto y reglas
PRD RF-15/RN-10. Al aprobar libera slots antiguos y asigna nuevos; al rechazar libera reserva provisional y mantiene original. Motivo cuando corresponda; historial obligatorio.

## Dependencias y relaciones
- Épica: [[EP-006-reprogramacion]]. Depende de [[HU-025-consultar-bandeja-reprogramaciones]].

## Esfuerzo
**Nivel:** Alto. Cambio atómico de dos franjas, estados y auditoría.

## Tareas de desarrollo
- [ ] **T-01 — Transición de decisión.** Diseñar reglas e idempotencia para `PENDING`.
- [ ] **T-02 — Operación ADMIN.** Aprobar/rechazar con causa aplicable y autorización.
- [ ] **T-03 — Pruebas/UI.** Verificar reserva antigua/nueva, rechazo y carreras.

## Criterios de aceptación
- **CA-01:** Dada reprogramación `PENDING`, cuando ADMIN aprueba, entonces cita usa nueva franja y la original se libera.
- **CA-02:** Dada reprogramación `PENDING`, cuando ADMIN rechaza, entonces nueva retención se libera y cita original permanece.
- **CA-03:** Dado estado no pendiente o no ADMIN, cuando decide, entonces no cambia franjas ni estado.
- **CA-04:** Dada decisión válida, cuando se consulta historial, entonces registra actor, fuente, fecha/hora y motivo aplicable.

## Definition of Done
- [ ] CA-01 a CA-04 tienen pruebas transaccionales/concurrentes relevantes.
- [ ] No se pierde ni duplica franja; transición y auditoría son atómicas.
- [ ] Contrato, web y trazabilidad están alineados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-15, RN-09/RN-10/RN-11/RN-12. Verificado: 2026-09-17.
