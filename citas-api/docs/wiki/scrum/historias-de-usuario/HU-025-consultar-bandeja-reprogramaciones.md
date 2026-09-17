---
id: HU-025
tipo: historia-de-usuario
titulo: Consultar bandeja de reprogramaciones
estado: Pendiente de aprobación
epica: "[[EP-006-reprogramacion]]"
esfuerzo: Medio
sprint_sugerido: S4
dependencias: ["[[HU-024-solicitar-reprogramacion]]"]
relacionadas: ["[[HU-026-decidir-reprogramacion]]"]
---

# HU-025 — Consultar bandeja de reprogramaciones

## Historia de usuario
**COMO** ADMIN **QUIERO** consultar reprogramaciones `PENDING` con filtros **PARA** resolverlas sin perder contexto.

## Contexto y reglas
PRD RF-18/RF-15. Muestra franja original y propuesta, sede, profesional, especialidad y fecha; filtra por sede/profesional/especialidad/fecha. Solo ADMIN; excluye decidir.

## Dependencias y relaciones
- Épica: [[EP-006-reprogramacion]]. Depende de [[HU-024-solicitar-reprogramacion]].

## Esfuerzo
**Nivel:** Medio. Consulta de dos franjas, filtros, ownership administrativo e índices.

## Tareas de desarrollo
- [ ] **T-01 — Proyección de bandeja.** Diseñar consulta indexada y mínima.
- [ ] **T-02 — Vista ADMIN.** Mostrar filtros y comparación de franja original/propuesta.
- [ ] **T-03 — Pruebas.** Cubrir rol, filtros, vacío y exclusión de solicitudes decididas.

## Criterios de aceptación
- **CA-01:** Dado ADMIN, cuando abre bandeja, entonces ve solo reprogramaciones `PENDING` con ambas franjas.
- **CA-02:** Dado filtro por sede, profesional, especialidad o fecha, cuando lo aplica, entonces ve coincidencias únicamente.
- **CA-03:** Dado no ADMIN, cuando intenta acceder, entonces se deniega.
- **CA-04:** Dada solicitud decidida, cuando consulta bandeja, entonces deja de aparecer pendiente.

## Definition of Done
- [ ] CA-01 a CA-04 tienen evidencia de rol, filtros e índices.
- [ ] Contrato web/API no revela datos no necesarios y está trazado a decisión.
- [ ] Trazabilidad Scrum actualizada.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-15/RF-18. Verificado: 2026-09-17.
