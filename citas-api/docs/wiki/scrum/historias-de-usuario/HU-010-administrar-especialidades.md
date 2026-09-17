---
id: HU-010
tipo: historia-de-usuario
titulo: Administrar especialidades
estado: Pendiente de aprobación
epica: "[[EP-002-catalogos-y-profesionales]]"
esfuerzo: Medio
sprint_sugerido: S3
dependencias: ["[[HU-002-gestionar-sesion]]"]
relacionadas: ["[[HU-012-asignar-especialidades-profesional]]", "[[HU-014-definir-duracion-especialidad]]"]
---

# HU-010 — Administrar especialidades

## Historia de usuario
**COMO** ADMIN **QUIERO** gestionar especialidades activas **PARA** configurar la oferta de atención.

## Contexto, alcance y reglas
PRD RF-06. CRUD de especialidades, incluida Medicina General, con estado activo/inactivo. Una especialidad referenciada no se borra físicamente. Duración se maneja en [[HU-014-definir-duracion-especialidad]].

## Dependencias y relaciones
- Épica: [[EP-002-catalogos-y-profesionales]]. Depende de [[HU-002-gestionar-sesion]].

## Esfuerzo
**Nivel:** Medio. Autorización y referencias posteriores de oferta/citas.

## Tareas de desarrollo
- [ ] **T-01 — Catálogo normalizado.** Definir identificador, nombre único, estado y migración.
- [ ] **T-02 — Gestión ADMIN.** Crear/editar/activar sin borrado físico referenciado.
- [ ] **T-03 — Pruebas y consumidor.** Verificar roles, unicidad y visibilidad de activos.

## Criterios de aceptación
- **CA-01:** Dado ADMIN, cuando crea o edita especialidad única, entonces queda disponible según su estado.
- **CA-02:** Dado no ADMIN, cuando intenta gestionarla, entonces se deniega.
- **CA-03:** Dada especialidad asignada o usada, cuando se elimina, entonces no se borra físicamente y puede desactivarse.
- **CA-04:** Dada especialidad inactiva, cuando USER busca o reserva, entonces no puede seleccionarla.

## Definition of Done
- [ ] CA-01 a CA-04 cuentan con evidencia de roles, referencias y UI/API.
- [ ] Esquema/Flyway preservan 3FN y la oferta no duplica nombre de especialidad.
- [ ] Contrato y trazabilidad de dependencias están actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-06, RF-08–RF-12; requisitos 3FN. Verificado: 2026-09-17.
