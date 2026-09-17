---
id: HU-021
tipo: historia-de-usuario
titulo: Solicitar cita especializada
estado: Pendiente de aprobación
epica: "[[EP-005-citas-especializadas]]"
esfuerzo: Alto
sprint_sugerido: S4
dependencias: ["[[HU-017-buscar-disponibilidad]]"]
relacionadas: ["[[HU-022-consultar-bandeja-citas-especializadas]]", "[[HU-023-decidir-cita-especializada]]"]
---

# HU-021 — Solicitar cita especializada

## Historia de usuario
**COMO** USER **QUIERO** solicitar una cita especializada con profesional y franja **PARA** que ADMIN decida la atención.

## Contexto, alcance y reglas
PRD RF-12/RN-01/RN-03. La solicitud nace `REQUESTED` y retiene slots; especialidad, sede y profesional deben ser válidos. Excluye la decisión administrativa.

## Dependencias y relaciones
- Épica: [[EP-005-citas-especializadas]]. Depende de [[HU-017-buscar-disponibilidad]].

## Esfuerzo
**Nivel:** Alto. Retención concurrente, estado inicial, contrato y auditoría.

## Tareas de desarrollo
- [ ] **T-01 — Modelo de solicitud/retención.** Diseñar relación con cita/slots, estado e índices en 3FN.
- [ ] **T-02 — Solicitud atómica.** Validar oferta y retener sin doble reserva.
- [ ] **T-03 — UI/pruebas.** Integrar confirmación y carreras de solicitudes.

## Criterios de aceptación
- **CA-01:** Dada franja especializada elegible, cuando USER solicita, entonces crea cita `REQUESTED` y retiene todos sus slots.
- **CA-02:** Dada franja tomada o retenida concurrentemente, cuando solicita, entonces falla sin duplicar reserva.
- **CA-03:** Dada especialidad/profesional/sede inactiva o no asociada, cuando solicita, entonces se rechaza.
- **CA-04:** Dada solicitud creada, cuando ADMIN consulta bandeja, entonces aparece con los datos necesarios para decidir.

## Definition of Done
- [ ] CA-01 a CA-04 tienen evidencia de integración y concurrencia.
- [ ] Retención, FKs, índice y Flyway justifican 3FN/RN-01.
- [ ] Contrato backend/web, estado inicial y auditoría están alineados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-12, RN-01/RN-03/RN-08/RN-11. Verificado: 2026-09-17.
