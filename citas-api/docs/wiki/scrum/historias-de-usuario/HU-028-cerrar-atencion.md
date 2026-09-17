---
id: HU-028
tipo: historia-de-usuario
titulo: Cerrar atención
estado: Pendiente de aprobación
epica: "[[EP-007-operacion-profesional]]"
esfuerzo: Medio
sprint_sugerido: S4
dependencias: ["[[HU-027-consultar-agenda-profesional]]"]
relacionadas: ["[[HU-029-consultar-historial-de-estados]]"]
---

# HU-028 — Cerrar atención

## Historia de usuario
**COMO** PROFESSIONAL **QUIERO** marcar mi cita aplicable como completada o no asistida **PARA** cerrar su ciclo operativo.

## Contexto y reglas
PRD RF-17/RN-11/RN-12. Solo el profesional dueño cierra cita pasada/aplicable con `COMPLETED` o `NO_SHOW`; ambas transiciones quedan en historial. No cambia citas ajenas o futuras no aplicables.

## Dependencias y relaciones
- Épica: [[EP-007-operacion-profesional]]. Depende de [[HU-027-consultar-agenda-profesional]].

## Esfuerzo
**Nivel:** Medio. Regla temporal, ownership y estado terminal.

## Tareas de desarrollo
- [ ] **T-01 — Regla de cierre.** Precisar condición aplicable y transiciones permitidas.
- [ ] **T-02 — Acción propia.** Autorizar resultado y persistir auditoría inmutable.
- [ ] **T-03 — Pruebas/UI.** Cubrir ambos resultados, futuro/ajeno y visualización.

## Criterios de aceptación
- **CA-01:** Dada cita propia pasada/aplicable `APPROVED`, cuando PROFESSIONAL marca completada, entonces queda `COMPLETED`.
- **CA-02:** Dada la misma condición, cuando marca no asistida, entonces queda `NO_SHOW`.
- **CA-03:** Dada cita futura/no aplicable/ajena, cuando intenta cerrar, entonces se rechaza.
- **CA-04:** Dado cierre válido, cuando consulta historial, entonces registra actor, fuente y fecha/hora.

## Definition of Done
- [ ] CA-01 a CA-04 tienen evidencia de transición, temporalidad y ownership.
- [ ] Estados terminales no admiten cambios inválidos y la auditoría es inmutable.
- [ ] Contrato y trazabilidad están actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-17, RN-11/RN-12. Verificado: 2026-09-17.
