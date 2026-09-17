---
id: EP-004
tipo: epica
titulo: Citas generales y seguimiento
estado: Pendiente de aprobación
historias: ["[[HU-018-reservar-cita-general]]", "[[HU-019-consultar-mis-citas]]", "[[HU-020-cancelar-cita]]"]
dependencias: ["[[EP-003-disponibilidad-y-agenda]]"]
---

# EP-004 — Citas generales y seguimiento

## Objetivo y valor
Permitir a USER obtener una cita de Medicina General aprobada automáticamente y administrarla después.

## Alcance y fuera de alcance
- Alcance: reserva general, consulta propia y cancelación futura no terminal.
- Fuera de alcance: solicitudes especializadas y reprogramación.

## Reglas de negocio
- La confirmación vuelve a comprobar disponibilidad; no puede ocupar una franja reservada.
- La cita general nace `APPROVED`; cancelar libera slots, no reactiva y deja historial.

## Dependencias
- Depende de [[EP-003-disponibilidad-y-agenda]] y habilita [[EP-006-reprogramacion]].

## Historias de usuario
- [[HU-018-reservar-cita-general]]
- [[HU-019-consultar-mis-citas]]
- [[HU-020-cancelar-cita]]

## Criterio de completitud
- [ ] El ciclo general respeta estados, ownership, concurrencia y auditoría.
- [ ] Cada cambio de estado es visible en [[HU-029-consultar-historial-de-estados]].

## Riesgos e incógnitas
- Debe demostrarse que una carrera de confirmaciones no genera doble reserva.
