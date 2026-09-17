---
id: HU-005
tipo: historia-de-usuario
titulo: Gestionar perfil
estado: Pendiente de aprobación
epica: "[[EP-001-acceso-y-perfil]]"
esfuerzo: Medio
sprint_sugerido: S2
dependencias: ["[[HU-002-gestionar-sesion]]"]
relacionadas: ["[[HU-006-gestionar-afiliacion]]"]
---

# HU-005 — Gestionar perfil

## Historia de usuario
**COMO** USER autenticado **QUIERO** consultar y actualizar mis datos permitidos **PARA** mantener mi información vigente.

## Contexto, alcance y reglas
PRD RF-04. Incluye lectura y actualización del perfil propio. Ownership obligatorio; no permite editar rol, credenciales administrativas ni perfil ajeno. Email/documento conservan unicidad si son editables según contrato decidido.

## Dependencias y relaciones
- Épica: [[EP-001-acceso-y-perfil]]. Depende de [[HU-002-gestionar-sesion]]; se relaciona con [[HU-006-gestionar-afiliacion]].

## Esfuerzo
**Nivel:** Medio. Ownership, validación y compatibilidad con identidad.

## Tareas de desarrollo
- [ ] **T-01 — Contrato de perfil.** Delimitar campos editables y errores de conflicto.
- [ ] **T-02 — Caso de uso y persistencia.** Aplicar ownership y validaciones sin exponer datos sensibles.
- [ ] **T-03 — Vistas y pruebas.** Integrar UI y cubrir lectura/edición propia y acceso indebido.

## Criterios de aceptación
- **CA-01:** Dado USER autenticado, cuando consulta su perfil, entonces recibe solo sus datos permitidos.
- **CA-02:** Dado cambio válido en campo permitido, cuando actualiza, entonces se conserva y se refleja al consultar.
- **CA-03:** Dado email/documento duplicado o dato inválido, cuando actualiza, entonces falla sin cambio parcial.
- **CA-04:** Dado otro usuario o rol enviado, cuando intenta editar perfil ajeno o privilegios, entonces se deniega.

## Definition of Done
- [ ] CA-01 a CA-04 cuentan con evidencia de API y web aplicable.
- [ ] Ownership, validación y únicos se prueban; no se exponen hashes/tokens.
- [ ] Contrato REST y trazabilidad quedan actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-04; restricciones seguridad. Verificado: 2026-09-17.
