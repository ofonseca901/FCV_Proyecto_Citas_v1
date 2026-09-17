---
id: EP-007
tipo: epica
titulo: Operación profesional
estado: Pendiente de aprobación
historias: ["[[HU-027-consultar-agenda-profesional]]", "[[HU-028-cerrar-atencion]]"]
dependencias: ["[[EP-004-citas-generales-y-seguimiento]]", "[[EP-005-citas-especializadas]]"]
---

# EP-007 — Operación profesional

## Objetivo y valor
Permitir que PROFESSIONAL vea solo su agenda aprobada y cierre una atención aplicable.

## Alcance y fuera de alcance
- Alcance: agenda por día/semana/sede y transición a `COMPLETED` o `NO_SHOW`.
- Fuera de alcance: acceso a citas de otros profesionales y edición de datos ajenos a la cita.

## Reglas de negocio
- Ownership estricto del profesional; solo citas aprobadas en su agenda.
- El cierre se realiza solo para citas pasadas/aplicables y crea historial inmutable.

## Dependencias
- Depende de las citas aprobadas de [[EP-004-citas-generales-y-seguimiento]] y [[EP-005-citas-especializadas]].

## Historias de usuario
- [[HU-027-consultar-agenda-profesional]]
- [[HU-028-cerrar-atencion]]

## Criterio de completitud
- [ ] Se conserva confidencialidad entre profesionales y trazabilidad de cada cierre.

## Riesgos e incógnitas
- Debe precisar la condición temporal de «pasada/aplicable» mediante CA y pruebas.
