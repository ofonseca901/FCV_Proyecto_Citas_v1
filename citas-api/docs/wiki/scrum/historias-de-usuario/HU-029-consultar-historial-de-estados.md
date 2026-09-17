---
id: HU-029
tipo: historia-de-usuario
titulo: Consultar historial de estados
estado: Pendiente de aprobación
epica: "[[EP-008-auditoria-y-contrato]]"
esfuerzo: Alto
sprint_sugerido: S4
dependencias: ["[[HU-020-cancelar-cita]]", "[[HU-023-decidir-cita-especializada]]", "[[HU-026-decidir-reprogramacion]]", "[[HU-028-cerrar-atencion]]"]
relacionadas: []
---

# HU-029 — Consultar historial de estados

## Historia de usuario
**COMO** actor autorizado **QUIERO** consultar el historial de estados de una cita accesible **PARA** verificar sus cambios.

## Contexto y reglas
PRD RF-19/RN-11/RN-12. Cada cambio conserva cita, estado nuevo, actor cuando existe, fuente SYSTEM/USER/ADMIN, fecha/hora y motivo opcional. Historial no se edita como CRUD; alcance exacto por rol debe respetar ownership.

## Dependencias y relaciones
- Épica: [[EP-008-auditoria-y-contrato]]. Consolida transiciones de [[HU-020-cancelar-cita]], [[HU-023-decidir-cita-especializada]], [[HU-026-decidir-reprogramacion]] y [[HU-028-cerrar-atencion]].

## Esfuerzo
**Nivel:** Alto. Auditoría inmutable, múltiples fuentes y autorización contextual.

## Tareas de desarrollo
- [ ] **T-01 — Modelo de historial.** Diseñar append-only, FKs, fuente y motivo bajo 3FN/Flyway.
- [ ] **T-02 — Registro transversal.** Registrar cada transición dentro de su operación atómica.
- [ ] **T-03 — Consulta autorizada/pruebas.** Exponer orden temporal, ownership e inmutabilidad.

## Criterios de aceptación
- **CA-01:** Dado cambio de estado válido, cuando termina, entonces historial guarda los campos obligatorios y motivo opcional.
- **CA-02:** Dada cita accesible, cuando actor autorizado consulta, entonces ve eventos ordenados temporalmente.
- **CA-03:** Dado actor no autorizado, cuando consulta historial ajeno, entonces se deniega.
- **CA-04:** Dado intento de editar/eliminar evento, cuando se realiza por CRUD normal, entonces se rechaza.

## Definition of Done
- [ ] CA-01 a CA-04 tienen pruebas de cada tipo de transición y autorización.
- [ ] Historial es append-only, 3FN y queda dentro de la misma transacción del cambio.
- [ ] Contrato y trazabilidad de todas las HU de estados están actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-19, RN-11/RN-12; requisitos 3FN. Verificado: 2026-09-17.
