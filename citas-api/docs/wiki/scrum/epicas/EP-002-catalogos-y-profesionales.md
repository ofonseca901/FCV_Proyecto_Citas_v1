---
id: EP-002
tipo: epica
titulo: Catálogos y profesionales
estado: Pendiente de aprobación
historias: ["[[HU-007-consultar-catalogos-fijos]]", "[[HU-008-administrar-eps]]", "[[HU-009-administrar-planes-eps]]", "[[HU-010-administrar-especialidades]]", "[[HU-011-crear-y-activar-profesional]]", "[[HU-012-asignar-especialidades-profesional]]", "[[HU-013-asignar-sedes-profesional]]"]
dependencias: ["[[EP-001-acceso-y-perfil]]"]
---

# EP-002 — Catálogos y profesionales

## Objetivo y valor
Permitir que ADMIN configure una oferta sintética coherente y habilite profesionales para prestar agenda.

## Alcance y fuera de alcance
- Alcance: catálogos fijos de consulta, EPS, planes y especialidades administrables; profesionales, especialidades y sedes asignadas.
- Fuera de alcance: agenda, disponibilidad y reserva de citas.

## Reglas de negocio
- Roles, estados, regímenes y sedes son fijos; EPS, planes y especialidades son configurables.
- Un catálogo referenciado no se borra físicamente; se activa/desactiva cuando aplique.
- Un profesional tiene código y matrícula sintéticos únicos, una especialidad primaria y sedes asignadas.

## Dependencias
- Depende de [[EP-001-acceso-y-perfil]]; habilita [[EP-003-disponibilidad-y-agenda]].

## Historias de usuario
- [[HU-007-consultar-catalogos-fijos]]
- [[HU-008-administrar-eps]]
- [[HU-009-administrar-planes-eps]]
- [[HU-010-administrar-especialidades]]
- [[HU-011-crear-y-activar-profesional]]
- [[HU-012-asignar-especialidades-profesional]]
- [[HU-013-asignar-sedes-profesional]]

## Criterio de completitud
- [ ] Oferta, catálogos y asignaciones son administrables conforme a las CA de sus HU.
- [ ] Las relaciones N:M, restricciones de unicidad y dependencias funcionales están justificadas en 3FN.

## Riesgos e incógnitas
- La política precisa de desactivación cuando hay datos históricos debe validarse con pruebas de integridad.
