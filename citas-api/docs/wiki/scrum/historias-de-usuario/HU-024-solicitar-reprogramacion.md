---
id: HU-024
tipo: historia-de-usuario
titulo: Solicitar reprogramación
estado: Pendiente de aprobación
epica: "[[EP-006-reprogramacion]]"
esfuerzo: Alto
sprint_sugerido: S4
dependencias: ["[[HU-019-consultar-mis-citas]]", "[[HU-017-buscar-disponibilidad]]"]
relacionadas: ["[[HU-026-decidir-reprogramacion]]"]
---

# HU-024 — Solicitar reprogramación

## Historia de usuario
**COMO** USER **QUIERO** solicitar una nueva franja para mi cita aprobada futura **PARA** ajustarla sin perder la cita original.

## Contexto y reglas
PRD RF-15/RN-10. Conserva profesional y especialidad; cambio de profesional es cita nueva. Retiene nueva franja mientras queda `PENDING`; original conserva la suya. Excluye decisión ADMIN.

## Dependencias y relaciones
- Épica: [[EP-006-reprogramacion]]. Depende de [[HU-019-consultar-mis-citas]] y [[HU-017-buscar-disponibilidad]].

## Esfuerzo
**Nivel:** Alto. Dos franjas relacionadas, retención y consistencia transaccional.

## Tareas de desarrollo
- [ ] **T-01 — Modelo de solicitud.** Relacionar cita original/nueva franja/estado con FKs, únicos e índices 3FN.
- [ ] **T-02 — Retención segura.** Validar cita aprobada/futura, ownership y disponibilidad.
- [ ] **T-03 — UI/pruebas.** Cubrir preservación original, carrera y cambio prohibido de profesional.

## Criterios de aceptación
- **CA-01:** Dada cita propia `APPROVED` futura, cuando USER elige nueva franja del mismo profesional/especialidad, entonces se crea solicitud `PENDING` y la nueva franja queda retenida.
- **CA-02:** Dada solicitud pendiente, cuando se consulta cita original, entonces conserva su franja y estado hasta decisión.
- **CA-03:** Dada cita no aprobada/pasada, profesional distinto o nueva franja tomada, cuando solicita, entonces se rechaza sin retención.
- **CA-04:** Dada solicitud creada, cuando ADMIN abre bandeja, entonces ve datos suficientes para decidir.

## Definition of Done
- [ ] CA-01 a CA-04 tienen evidencia de transacción y concurrencia.
- [ ] FKs/índices/Flyway justifican 3FN y no se pierde cita original.
- [ ] Contrato y trazabilidad con decisión están actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-15, RN-01/RN-10/RN-11; requisitos 3FN. Verificado: 2026-09-17.
