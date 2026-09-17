---
id: HU-008
tipo: historia-de-usuario
titulo: Administrar EPS
estado: Pendiente de aprobación
epica: "[[EP-002-catalogos-y-profesionales]]"
esfuerzo: Medio
sprint_sugerido: S3
dependencias: ["[[HU-002-gestionar-sesion]]"]
relacionadas: ["[[HU-009-administrar-planes-eps]]"]
---

# HU-008 — Administrar EPS

## Historia de usuario
**COMO** ADMIN **QUIERO** crear, editar y activar/desactivar EPS sintéticas **PARA** mantener el catálogo disponible.

## Contexto, alcance y reglas
PRD RF-06. CRUD protegido para ADMIN. EPS referenciada no se elimina físicamente; se desactiva. Excluye planes, que pertenecen a [[HU-009-administrar-planes-eps]].

## Dependencias y relaciones
- Épica: [[EP-002-catalogos-y-profesionales]]. Depende de [[HU-002-gestionar-sesion]].

## Esfuerzo
**Nivel:** Medio. Autorización, integridad referencial y estados activos.

## Tareas de desarrollo
- [ ] **T-01 — Catálogo EPS.** Definir único, estado y migración en 3FN.
- [ ] **T-02 — Administración.** Implementar operaciones protegidas y baja lógica.
- [ ] **T-03 — Pruebas/UI.** Cubrir roles, duplicados y EPS referenciada.

## Criterios de aceptación
- **CA-01:** Dado ADMIN, cuando crea o edita EPS válida, entonces queda disponible con nombre único.
- **CA-02:** Dado no ADMIN, cuando intenta gestionarla, entonces se deniega.
- **CA-03:** Dada EPS usada por afiliación/plan, cuando se elimina, entonces no se borra físicamente; puede desactivarse.
- **CA-04:** Dada EPS inactiva, cuando USER selecciona afiliación nueva, entonces no aparece disponible.

## Definition of Done
- [ ] CA-01 a CA-04 se prueban por rol e integridad.
- [ ] Migración/FK preservan 3FN; UI y API diferencian activo/inactivo.
- [ ] Contrato y trazabilidad están actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-06; requisitos 3FN y restricciones de BD. Verificado: 2026-09-17.
