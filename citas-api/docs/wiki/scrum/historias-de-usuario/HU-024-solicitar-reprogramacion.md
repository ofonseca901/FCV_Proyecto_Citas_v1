---
id: HU-024
epica: EP-004
estado: En desarrollo
esfuerzo: Alto
fuentes: [PRD.md#RF-15, PRD.md#RN-01, PRD.md#RN-10]
---

# HU-024 — solicitar reprogramación

**COMO** USER  
**QUIERO** proponer otra franja para una cita aprobada futura  
**PARA** ajustar mi atención sin perder la reserva vigente mientras ADMIN decide.

## Alcance

La nueva franja conserva profesional y especialidad, crea una solicitud `PENDING`, retiene sus slots y deja la cita original intacta hasta la decisión administrativa.

## Criterios de aceptación

- **CA-01:** una cita aprobada futura propia admite una única solicitud pendiente.
- **CA-02:** la nueva franja debe estar en un bloque válido y no puede ocupar slots retenidos.
- **CA-03:** mientras está `PENDING`, la cita original y sus slots siguen vigentes.
- **CA-04:** ADMIN puede aprobar o rechazar; el rechazo exige motivo.
- **CA-05:** aprobar libera los slots originales, asigna los nuevos y registra historial; rechazar libera solo los nuevos.
- **CA-06:** una solicitud de otro usuario responde con autorización denegada sin filtrar datos.

## Definition of Done

- Migración Flyway y contrato REST documentados.
- Pruebas de reglas, autorización y doble reserva.
- Consumidor React con estados idle/loading/success/error.
- Evidencia registrada en `evidence/s4-loop-03.md`.
