---
id: HU-009
tipo: historia-de-usuario
titulo: Administrar planes de EPS
estado: Pendiente de aprobación
epica: "[[EP-002-catalogos-y-profesionales]]"
esfuerzo: Medio
sprint_sugerido: S3
dependencias: ["[[HU-008-administrar-eps]]"]
relacionadas: ["[[HU-006-gestionar-afiliacion]]"]
---

# HU-009 — Administrar planes de EPS

## Historia de usuario
**COMO** ADMIN **QUIERO** gestionar planes asociados a una EPS **PARA** que USER pueda afiliarse con datos coherentes.

## Contexto, alcance y reglas
PRD RF-06/RF-04. Cada plan pertenece a una EPS; no se repite dentro de ella. Plan referenciado se desactiva, no se borra físicamente. Excluye afiliación de USER.

## Dependencias y relaciones
- Épica: [[EP-002-catalogos-y-profesionales]]. Depende de [[HU-008-administrar-eps]]; habilita [[HU-006-gestionar-afiliacion]].

## Esfuerzo
**Nivel:** Medio. Relación padre-hijo, estados e integridad transaccional.

## Tareas de desarrollo
- [ ] **T-01 — Modelo y migración.** Añadir plan con FK a EPS y único compuesto.
- [ ] **T-02 — CRUD autorizado.** Validar pertenencia, activación y no borrado físico.
- [ ] **T-03 — Pruebas/UI.** Filtrar planes por EPS activa y cubrir referencias.

## Criterios de aceptación
- **CA-01:** Dado ADMIN y EPS activa, cuando crea/edita plan único, entonces queda asociado a esa EPS.
- **CA-02:** Dado plan duplicado en la misma EPS o EPS inexistente/inactiva, cuando guarda, entonces se rechaza.
- **CA-03:** Dado plan referenciado, cuando se intenta borrar, entonces se conserva y puede desactivarse.
- **CA-04:** Dado USER, cuando consulta planes de una EPS para afiliación, entonces solo ve planes activos de esa EPS.

## Definition of Done
- [ ] CA-01 a CA-04 tienen pruebas de integridad, API y UI aplicable.
- [ ] FK/único compuesto/Flyway están justificados en 3FN.
- [ ] Contrato y dependencias con afiliación están actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-04/RF-06; requisitos 1FN–3FN. Verificado: 2026-09-17.
