---
id: EP-004
estado: En desarrollo
incremento: S4
fuentes: [PRD.md, GUIA_SESIONES_S2_S6.md]
---

# EP-004 — ciclo de citas

## Objetivo

Completar el MVP de citas sintéticas: consulta propia, cancelación, reprogramación, operación profesional, decisiones administrativas e historial auditable.

## Actores

- `USER`: solicita, consulta, cancela y reprograma sus citas.
- `PROFESSIONAL`: consulta su agenda y cierra atenciones.
- `ADMIN`: decide solicitudes especializadas/reprogramaciones y mantiene catálogos.

## Reglas de negocio

- Los slots se reservan de forma transaccional y no pueden duplicarse.
- Una reprogramación pendiente conserva la cita original.
- Rechazar una solicitud exige motivo y libera su reserva provisional.
- Solo se permiten cambios sobre citas propias o recursos del profesional autenticado.
- Todo cambio de estado produce una entrada append-only de historial.

## Historias relacionadas

- [[HU-018-reservar-cita-general]]
- [[HU-019-consultar-mis-citas]]
- [[HU-020-cancelar-cita]]
- [[HU-024-solicitar-reprogramacion]]
- [[HU-026-decidir-reprogramacion]]
- [[HU-027-consultar-agenda-profesional]]
- [[HU-028-cerrar-atencion]]
- [[HU-029-consultar-historial-de-estados]]

## Definition of Done de la épica

Todas las historias implementadas tienen contrato REST, pruebas backend, consumidor web y evidencia ejecutada. Las historias no verificadas permanecen en `En validación` y no se presentan como completadas.
