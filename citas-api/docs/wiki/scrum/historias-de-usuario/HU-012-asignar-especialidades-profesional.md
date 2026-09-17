---
id: HU-012
tipo: historia-de-usuario
titulo: Asignar especialidades al profesional
estado: Pendiente de aprobación
epica: "[[EP-002-catalogos-y-profesionales]]"
esfuerzo: Medio
sprint_sugerido: S3
dependencias: ["[[HU-010-administrar-especialidades]]", "[[HU-011-crear-y-activar-profesional]]"]
relacionadas: ["[[HU-013-asignar-sedes-profesional]]"]
---

# HU-012 — Asignar especialidades al profesional

## Historia de usuario
**COMO** ADMIN **QUIERO** asignar especialidades y una primaria a un PROFESSIONAL **PARA** definir qué atención puede prestar.

## Contexto, alcance y reglas
PRD RF-07/RN-08. Relación N:M; especialidad debe estar activa. Hay máximo una primaria por profesional y no se duplica asignación. Excluye duración y agenda.

## Dependencias y relaciones
- Épica: [[EP-002-catalogos-y-profesionales]]. Depende de [[HU-010-administrar-especialidades]] y [[HU-011-crear-y-activar-profesional]].

## Esfuerzo
**Nivel:** Medio. N:M, regla de primaria y efecto sobre búsqueda/reserva.

## Tareas de desarrollo
- [ ] **T-01 — Relación N:M.** Modelar puente, único compuesto y primaria en 3FN/Flyway.
- [ ] **T-02 — Administración.** Validar profesional/especialidad activos y única primaria.
- [ ] **T-03 — Pruebas/UI.** Cubrir duplicados, inactivas y reasignación de primaria.

## Criterios de aceptación
- **CA-01:** Dado ADMIN, cuando asigna especialidad activa a profesional activo, entonces queda asociada una sola vez.
- **CA-02:** Dado intento de asignar especialidad inactiva o profesional inexistente, cuando guarda, entonces se rechaza.
- **CA-03:** Dado profesional con primaria, cuando define otra primaria, entonces solo la nueva queda marcada.
- **CA-04:** Dado profesional sin asociación activa, cuando USER busca esa especialidad, entonces no se ofrece como elegible.

## Definition of Done
- [ ] CA-01 a CA-04 tienen evidencia de relación, regla primaria y disponibilidad.
- [ ] Tabla puente/FKs/únicos y migración justifican 3FN.
- [ ] Contrato REST y trazabilidad están actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-07, RN-08; requisitos 1FN–3FN. Verificado: 2026-09-17.
