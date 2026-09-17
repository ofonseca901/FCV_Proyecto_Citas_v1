---
id: EP-003
tipo: epica
titulo: Disponibilidad y agenda
estado: Pendiente de aprobación
historias: ["[[HU-014-definir-duracion-especialidad]]", "[[HU-015-gestionar-bloques-disponibilidad]]", "[[HU-016-consultar-calendario-profesional]]", "[[HU-017-buscar-disponibilidad]]"]
dependencias: ["[[EP-002-catalogos-y-profesionales]]"]
---

# EP-003 — Disponibilidad y agenda

## Objetivo y valor
Convertir la oferta habilitada en franjas publicables y consultables, sin crear reservas aún.

## Alcance y fuera de alcance
- Alcance: duración de especialidad, bloques futuros por sede, calendario profesional y búsqueda filtrada para USER.
- Fuera de alcance: confirmación, retención y gestión del ciclo de las citas.

## Reglas de negocio
- Bloques no pasados, no solapados y solo en sedes asignadas; cada slot dura 30 minutos.
- La especialidad define 30 o 60 minutos; para 60 se requieren dos slots consecutivos.
- Solo se publica una especialidad activa asignada al profesional.

## Dependencias
- Depende de [[EP-002-catalogos-y-profesionales]]; habilita [[EP-004-citas-generales-y-seguimiento]] y [[EP-005-citas-especializadas]].

## Historias de usuario
- [[HU-014-definir-duracion-especialidad]]
- [[HU-015-gestionar-bloques-disponibilidad]]
- [[HU-016-consultar-calendario-profesional]]
- [[HU-017-buscar-disponibilidad]]

## Criterio de completitud
- [ ] Las franjas mostradas satisfacen duración, sede, activación y no solapamiento.
- [ ] El modelo de bloques y consultas queda normalizado, indexado y probado.

## Riesgos e incógnitas
- La protección transaccional exacta contra carreras de reserva se valida en las épicas de citas.
