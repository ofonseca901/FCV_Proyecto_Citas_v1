---
id: HU-022
tipo: historia-de-usuario
titulo: Consultar bandeja de citas especializadas
estado: Pendiente de aprobación
epica: "[[EP-005-citas-especializadas]]"
esfuerzo: Medio
sprint_sugerido: S4
dependencias: ["[[HU-021-solicitar-cita-especializada]]"]
relacionadas: ["[[HU-023-decidir-cita-especializada]]"]
---

# HU-022 — Consultar bandeja de citas especializadas

## Historia de usuario
**COMO** ADMIN **QUIERO** consultar solicitudes especializadas `REQUESTED` con filtros **PARA** decidirlas oportunamente.

## Contexto, alcance y reglas
PRD RF-18. Filtra por sede, profesional, especialidad y fecha. Solo ADMIN accede; excluye aprobar/rechazar, cubierto por [[HU-023-decidir-cita-especializada]].

## Dependencias y relaciones
- Épica: [[EP-005-citas-especializadas]]. Depende de [[HU-021-solicitar-cita-especializada]].

## Esfuerzo
**Nivel:** Medio. Autorización, filtros e índices de bandeja.

## Tareas de desarrollo
- [ ] **T-01 — Consulta e índices.** Proyectar solicitante/oferta/fecha sin datos innecesarios.
- [ ] **T-02 — Vista ADMIN.** Aplicar filtros y estado pendiente.
- [ ] **T-03 — Pruebas.** Cubrir roles, combinaciones y exclusión de estados decididos.

## Criterios de aceptación
- **CA-01:** Dado ADMIN, cuando abre bandeja, entonces ve solo solicitudes `REQUESTED`.
- **CA-02:** Dado filtro por sede, profesional, especialidad o fecha, cuando lo aplica, entonces ve coincidencias solamente.
- **CA-03:** Dado no ADMIN, cuando intenta acceder, entonces se deniega.
- **CA-04:** Dada solicitud decidida, cuando consulta bandeja, entonces deja de aparecer como pendiente.

## Definition of Done
- [ ] CA-01 a CA-04 cuentan con evidencia de filtros, índices y autorización.
- [ ] Proyección evita exposición indebida y contrato web/API es coherente.
- [ ] Trazabilidad con decisión administrativa está actualizada.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-18/RF-12 y seguridad por rol. Verificado: 2026-09-17.
