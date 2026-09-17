---
id: HU-032
tipo: historia-de-usuario
titulo: Notificar cambios de estado
estado: Pendiente de aprobación
epica: "[[EP-009-automatizaciones-posteriores]]"
esfuerzo: Alto
sprint_sugerido: S5/S6
dependencias: ["[[HU-029-consultar-historial-de-estados]]", "[[HU-030-mantener-contrato-rest]]"]
relacionadas: []
---

# HU-032 — Notificar cambios de estado

## Historia de usuario
**COMO** USER afectado por un cambio de cita **QUIERO** recibir una notificación sintética **PARA** conocer el nuevo estado.

## Contexto y reglas
PRD §10. Usa webhook + Gmail posteriores sobre transiciones auditadas. Notifica solo transiciones autorizadas y datos mínimos; el workflow no cambia estados ni conserva secretos.

## Dependencias y relaciones
- Épica: [[EP-009-automatizaciones-posteriores]]. Depende de [[HU-029-consultar-historial-de-estados]] y [[HU-030-mantener-contrato-rest]].

## Esfuerzo
**Nivel:** Alto. Evento fiable, idempotencia y límites de datos de notificación.

## Tareas de desarrollo
- [ ] **T-01 — Evento/contrato mínimo.** Definir payload sintético sin secretos ni PII real.
- [ ] **T-02 — Workflow n8n.** Recibir evento, deduplicar y notificar desde configuración externa.
- [ ] **T-03 — Evidencia.** Probar cambios aprobados/rechazados/cancelados con exportación sanitizada.

## Criterios de aceptación
- **CA-01:** Dado cambio de estado auditable elegible, cuando se emite evento, entonces workflow genera una notificación sintética correspondiente.
- **CA-02:** Dado reintento del mismo evento, cuando llega, entonces no produce notificación duplicada.
- **CA-03:** Dado evento o exportación, cuando se inspecciona, entonces contiene solo datos mínimos y ningún secreto/credencial.

## Definition of Done
- [ ] CA-01 a CA-03 tienen evidencia con datos sintéticos e idempotencia.
- [ ] JSON versionado está sanitizado y no altera el núcleo ni historial.
- [ ] Contrato de evento y trazabilidad quedan actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-03 | Pendiente | Requiere integración autorizada posterior. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD §10/RF-19; restricciones n8n y secretos. Verificado: 2026-09-17.
