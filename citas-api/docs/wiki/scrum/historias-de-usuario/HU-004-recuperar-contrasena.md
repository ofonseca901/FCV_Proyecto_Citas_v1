---
id: HU-004
tipo: historia-de-usuario
titulo: Recuperar contraseña
estado: Pendiente de aprobación
epica: "[[EP-001-acceso-y-perfil]]"
esfuerzo: Alto
sprint_sugerido: S2
dependencias: ["[[HU-001-registrar-usuario]]", "[[HU-002-gestionar-sesion]]"]
relacionadas: ["[[HU-003-interfaz-de-acceso]]"]
---

# HU-004 — Recuperar contraseña

## Historia de usuario
**COMO** USER que perdió su contraseña **QUIERO** solicitar y completar su restablecimiento **PARA** recuperar acceso seguro.

## Contexto, alcance y reglas
PRD RF-03. Genera token temporal de un uso; consume/invalida token al cambiar contraseña. En desarrollo su entrega puede ser respuesta/log controlado; SMTP es opcional. Excluye correo real.

## Dependencias y relaciones
- Épica: [[EP-001-acceso-y-perfil]]. Depende de [[HU-001-registrar-usuario]] y [[HU-002-gestionar-sesion]].

## Esfuerzo
**Nivel:** Alto. Token temporal, revocación de seguridad y prevención de filtración.

## Tareas de desarrollo
- [ ] **T-01 — Modelo de token.** Diseñar persistencia 3FN, expiración y un único uso con migración.
- [ ] **T-02 — Flujo seguro.** Solicitar y cambiar sin revelar existencia de cuenta ni secreto.
- [ ] **T-03 — Pruebas.** Cubrir expiración, reutilización, cambio y revocación de sesiones aplicable.

## Criterios de aceptación
- **CA-01:** Dado un email, cuando solicita recuperación, entonces recibe respuesta no reveladora y se crea token temporal solo si corresponde.
- **CA-02:** Dado token vigente de un uso y nueva contraseña válida, cuando confirma, entonces cambia contraseña y token queda consumido.
- **CA-03:** Dado token expirado, usado o inválido, cuando intenta cambio, entonces falla sin alterar contraseña.
- **CA-04:** Dado entorno de desarrollo, cuando se entrega token, entonces el canal controlado no lo persiste en repositorio ni lo registra indebidamente.

## Definition of Done
- [ ] CA-01 a CA-04 tienen pruebas de integración relevantes.
- [ ] Token, contraseña y secretos no se exponen; esquema/Flyway mantienen 3FN.
- [ ] Contrato y pantalla aplicable sincronizados con trazabilidad Scrum.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-03; restricciones de seguridad y entorno. Verificado: 2026-09-17.
