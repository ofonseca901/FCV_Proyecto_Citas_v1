---
id: HU-001
tipo: historia-de-usuario
titulo: Registrar usuario
estado: Pendiente de aprobación
epica: "[[EP-001-acceso-y-perfil]]"
esfuerzo: Medio
sprint_sugerido: S2
dependencias: []
relacionadas: ["[[HU-002-gestionar-sesion]]", "[[HU-003-interfaz-de-acceso]]"]
---

# HU-001 — Registrar usuario

## Historia de usuario
**COMO** visitante ficticio **QUIERO** crear una cuenta USER **PARA** acceder al sistema de citas.

## Contexto, alcance y reglas
PRD RF-01; RN de seguridad. Captura nombres, apellidos, documento, email, teléfono y contraseña. Email y documento son únicos (email normalizado); servidor asigna solo USER y persiste hash adaptativo. Excluye afiliación y cuentas ADMIN/PROFESSIONAL.

## Dependencias y relaciones
- Épica: [[EP-001-acceso-y-perfil]]. Habilita [[HU-002-gestionar-sesion]] y [[HU-003-interfaz-de-acceso]].

## Esfuerzo
**Nivel:** Medio. Coordinación de validación, identidad y esquema inicial 3FN.

## Tareas de desarrollo
- [ ] **T-01 — Modelo de identidad y migración.** Justificar PK, únicos y dependencias funcionales; crear migración Flyway.
- [ ] **T-02 — Registro seguro.** Aplicar caso de uso, validación y hash sin exponer secretos.
- [ ] **T-03 — Contrato y pruebas.** Cubrir alta válida, duplicados y escalamiento de rol.

## Criterios de aceptación
- **CA-01:** Dado un formulario válido, cuando se registra, entonces se crea USER y se devuelve identificador sin contraseña ni hash.
- **CA-02:** Dado email (sin distinguir mayúsculas) o documento existente, cuando se registra, entonces responde conflicto y no crea otra cuenta.
- **CA-03:** Dado dato obligatorio, email o contraseña inválidos, cuando se registra, entonces responde error de validación identificable.
- **CA-04:** Dado un rol privilegiado enviado por cliente, cuando se registra, entonces la cuenta no adquiere privilegios.

## Definition of Done
- [ ] CA-01 a CA-04 tienen evidencia automatizada o de integración.
- [ ] Esquema y migración preservan 3FN; hash y secretos no aparecen en respuesta ni logs.
- [ ] Contrato REST, consumidor web y trazabilidad Scrum quedan coherentes.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-01; restricciones de seguridad y BD; requisitos 1FN–3FN. Verificado: 2026-09-17.
