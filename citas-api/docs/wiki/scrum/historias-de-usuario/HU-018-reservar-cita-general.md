---
id: HU-018
tipo: historia-de-usuario
titulo: Reservar cita general
estado: Pendiente de aprobación
epica: "[[EP-004-citas-generales-y-seguimiento]]"
esfuerzo: Alto
sprint_sugerido: S4
dependencias: ["[[HU-017-buscar-disponibilidad]]"]
relacionadas: ["[[HU-019-consultar-mis-citas]]", "[[HU-029-consultar-historial-de-estados]]"]
---

# HU-018 — Reservar cita general

## Historia de usuario
**COMO** USER **QUIERO** confirmar una cita de Medicina General disponible **PARA** obtener atención aprobada de inmediato.

## Contexto, alcance y reglas
PRD RF-11/RN-01/RN-02. Selecciona Medicina General, profesional y franja disponible; confirma disponibilidad transaccional y crea `APPROVED`. No requiere ADMIN; excluye especializadas.

## Dependencias y relaciones
- Épica: [[EP-004-citas-generales-y-seguimiento]]. Depende de [[HU-017-buscar-disponibilidad]].

## Esfuerzo
**Nivel:** Alto. Reserva concurrente, estados, slots y contrato cruzado.

## Tareas de desarrollo
- [ ] **T-01 — Modelo de cita/reserva.** Diseñar FKs, slots, estado e índices con migración 3FN.
- [ ] **T-02 — Confirmación atómica.** Revalidar franja y impedir doble ocupación.
- [ ] **T-03 — UI/contrato/pruebas.** Integrar confirmación y carrera concurrente.

## Criterios de aceptación
- **CA-01:** Dada franja vigente de Medicina General, cuando USER confirma, entonces crea cita `APPROVED` con sede, profesional, fecha/hora y duración.
- **CA-02:** Dada franja tomada entre búsqueda y confirmación, cuando confirma, entonces falla sin crear cita ni doble reserva.
- **CA-03:** Dado profesional/especialidad/sede no elegible, cuando confirma, entonces se rechaza.
- **CA-04:** Dada cita creada, cuando se consulta historial, entonces existe transición inicial con fuente correspondiente.

## Definition of Done
- [ ] CA-01 a CA-04 tienen prueba de integración, incluida concurrencia.
- [ ] Flyway, FKs e índices justifican 3FN y RN-01; no hay doble reserva.
- [ ] Contrato backend/web y auditoría quedan coherentes.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-11, RN-01/RN-02/RN-11/RN-12; requisitos 3FN. Verificado: 2026-09-17.
