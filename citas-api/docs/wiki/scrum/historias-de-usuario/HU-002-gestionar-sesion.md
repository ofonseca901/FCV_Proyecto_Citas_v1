---
id: HU-002
tipo: historia-de-usuario
titulo: Gestionar sesión JWT
estado: Pendiente de aprobación
epica: "[[EP-001-acceso-de-usuarios]]"
esfuerzo: Alto
sprint_sugerido: S2
dependencias:
  - "[[HU-001-registrar-usuario]]"
relacionadas:
  - "[[HU-003-interfaz-de-acceso]]"
---

# HU-002 — Gestionar sesión JWT

**COMO** usuario registrado, **QUIERO** iniciar, renovar y cerrar mi sesión, **PARA** acceder a mis datos con autenticación.

## Contexto y alcance
PRD RF-02. Login por email/contraseña, JWT access de corta duración, refresh, logout e identidad autenticada. No incluye recuperación de contraseña ni pantallas de administración.

## Reglas de negocio
Credenciales incorrectas producen un error genérico. Tokens separados por propósito y clave, con expiración. Refresh de un solo uso mediante rotación y revocación persistente. Logout revoca la sesión; no conservar tokens en logs.

## Dependencias y relaciones
Épica [[EP-001-acceso-de-usuarios]]. Depende de [[HU-001-registrar-usuario]]; consumida por [[HU-003-interfaz-de-acceso]].

## Esfuerzo
Alto: coordina seguridad, persistencia y concurrencia de renovación.

## Tareas
- [ ] T-01 (Medio): definir contrato de autenticación y errores.
- [ ] T-02 (Alto): implementar emisión/validación, rotación y revocación.
- [ ] T-03 (Medio): restringir rutas y CORS al origen configurado.
- [ ] T-04 (Alto): probar acceso inválido, propósito de token y renovación repetida.

## Criterios de aceptación
- CA-01: credenciales válidas retornan access/refresh JWT con expiración e identidad; inválidas responden 401 genérico.
- CA-02: la consulta de identidad sin token, con token manipulado, expirado o refresh en lugar de access responde 401.
- CA-03: refresh válido emite un nuevo par; el anterior no puede reutilizarse.
- CA-04: logout invalida los tokens de esa sesión; otras sesiones independientes conservan su validez.
- CA-05: el origen permitido puede consumir la API; un origen distinto no recibe autorización CORS.

## Definition of Done
- [ ] Todos los CA verificados, incluyendo persistencia MySQL de sesiones.
- [ ] Secretos externos al repositorio; access y refresh con claves diferentes.
- [ ] Pruebas de autenticación y autorización pasan.
- [ ] Contrato, documentación y enlaces actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-05 | Pendiente | Sin ejecución al redactar |
| DoD | Pendiente | Requiere implementación y comprobación |

## Historial
S2: propuesta pendiente de aprobación del usuario.
