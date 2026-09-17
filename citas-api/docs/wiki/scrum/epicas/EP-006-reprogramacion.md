---
id: EP-006
tipo: epica
titulo: Reprogramación
estado: Pendiente de aprobación
historias: ["[[HU-024-solicitar-reprogramacion]]", "[[HU-025-consultar-bandeja-reprogramaciones]]", "[[HU-026-decidir-reprogramacion]]"]
dependencias: ["[[EP-004-citas-generales-y-seguimiento]]", "[[EP-005-citas-especializadas]]"]
---

# EP-006 — Reprogramación

## Objetivo y valor
Ofrecer a USER una reprogramación segura que conserva la cita original hasta la decisión administrativa.

## Alcance y fuera de alcance
- Alcance: solicitud `PENDING`, retención nueva, bandeja de ADMIN y aprobación/rechazo.
- Fuera de alcance: cambio de profesional dentro de una reprogramación; eso es una cita nueva.

## Reglas de negocio
- Solo citas aprobadas y futuras; conserva profesional y especialidad.
- Al aprobar libera la franja antigua y asigna la nueva; al rechazar libera la nueva y mantiene la original.

## Dependencias
- Depende de [[EP-004-citas-generales-y-seguimiento]], [[EP-005-citas-especializadas]] y [[HU-017-buscar-disponibilidad]].

## Historias de usuario
- [[HU-024-solicitar-reprogramacion]]
- [[HU-025-consultar-bandeja-reprogramaciones]]
- [[HU-026-decidir-reprogramacion]]

## Criterio de completitud
- [ ] Nunca se pierde la cita original mientras la solicitud está pendiente.
- [ ] Todas las transiciones y reservas provisionales tienen evidencia auditable.

## Riesgos e incógnitas
- Requiere tratamiento transaccional particularmente cuidadoso para dos franjas relacionadas.
