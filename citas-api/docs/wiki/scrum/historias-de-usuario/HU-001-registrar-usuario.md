---
id: HU-001
tipo: historia-de-usuario
titulo: Registrar usuario
estado: Pendiente de aprobación
epica: "[[EP-001-acceso-de-usuarios]]"
esfuerzo: Medio
sprint_sugerido: S2
dependencias: []
relacionadas:
  - "[[HU-002-gestionar-sesion]]"
  - "[[HU-003-interfaz-de-acceso]]"
---

# HU-001 — Registrar usuario

**COMO** visitante ficticio, **QUIERO** registrar mi cuenta, **PARA** acceder como paciente al sistema.

## Contexto y alcance
PRD RF-01. Capturar nombres, apellidos, tipo/número de documento, email, teléfono y contraseña. Persistencia MySQL y migración inicial. No incluye afiliación ni recuperación de contraseña.

## Reglas de negocio
Email y documento únicos. Normalizar email; almacenar exclusivamente hash de contraseña. Asignar USER desde el servidor, sin admitir escalamiento por datos del cliente.

## Dependencias y relaciones
Épica [[EP-001-acceso-de-usuarios]]. Habilita [[HU-002-gestionar-sesion]] y [[HU-003-interfaz-de-acceso]].

## Esfuerzo
Medio: coordina validación, caso de uso y persistencia.

## Tareas
- [ ] T-01 (Medio): modelar usuarios, roles y migración Flyway en 3FN.
- [ ] T-02 (Medio): implementar registro, validación y hash adaptativo.
- [ ] T-03 (Medio): verificar persistencia, duplicados y rol del usuario registrado.

## Criterios de aceptación
- CA-01: con datos válidos, el registro responde 201 con identificador y rol USER, sin contraseña ni hash.
- CA-02: email repetido, incluso con diferencias de mayúsculas, o documento repetido responde 409 sin crear otra cuenta.
- CA-03: campos obligatorios vacíos, email inválido o contraseña fuera de límites documentados responde 400.
- CA-04: una petición que intente asignar ADMIN no crea una cuenta privilegiada.

## Definition of Done
- [ ] CA-01 a CA-04 verificados con evidencia.
- [ ] Migración inicial ejecutada en MySQL; esquema justificado en 3FN.
- [ ] Contraseña persistida como hash BCrypt; no aparece en respuestas ni logs.
- [ ] Contrato y trazabilidad actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Sin ejecución al redactar |
| DoD | Pendiente | Requiere implementación y comprobación |

## Historial
S2: propuesta pendiente de aprobación del usuario.
