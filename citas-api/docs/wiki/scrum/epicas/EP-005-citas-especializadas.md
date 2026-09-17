---
id: EP-005
tipo: epica
titulo: Citas especializadas
estado: Pendiente de aprobación
historias: ["[[HU-021-solicitar-cita-especializada]]", "[[HU-022-consultar-bandeja-citas-especializadas]]", "[[HU-023-decidir-cita-especializada]]"]
dependencias: ["[[EP-003-disponibilidad-y-agenda]]"]
---

# EP-005 — Citas especializadas

## Objetivo y valor
Permitir solicitud especializada con retención segura y decisión verificable de ADMIN.

## Alcance y fuera de alcance
- Alcance: solicitud `REQUESTED`, bandeja filtrable, aprobación/rechazo con motivo y liberación de slots.
- Fuera de alcance: reprogramar una cita aprobada.

## Reglas de negocio
- La solicitud retiene la franja; ADMIN aprueba o rechaza, y el rechazo requiere motivo y libera la retención.
- Especialidad activa, profesional asociado y sede válida son condiciones previas.

## Dependencias
- Depende de [[EP-003-disponibilidad-y-agenda]] y se relaciona con [[EP-008-auditoria-y-contrato]].

## Historias de usuario
- [[HU-021-solicitar-cita-especializada]]
- [[HU-022-consultar-bandeja-citas-especializadas]]
- [[HU-023-decidir-cita-especializada]]

## Criterio de completitud
- [ ] Las decisiones administrativas, motivos y slots retenidos se verifican en todas las transiciones.
- [ ] No existe doble reserva ante solicitudes concurrentes.

## Riesgos e incógnitas
- La semántica de retención debe quedar explícita en contrato, modelo de datos y pruebas.
