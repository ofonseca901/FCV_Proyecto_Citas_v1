---
id: HU-002
tipo: historia-de-usuario
titulo: Gestionar sesión JWT
estado: Pendiente de aprobación
epica: "[[EP-001-acceso-y-perfil]]"
esfuerzo: Alto
sprint_sugerido: S2
dependencias: ["[[HU-001-registrar-usuario]]"]
relacionadas: ["[[HU-003-interfaz-de-acceso]]", "[[HU-004-recuperar-contrasena]]"]
---

# HU-002 — Gestionar sesión JWT

## Historia de usuario
**COMO** usuario registrado **QUIERO** iniciar, renovar y cerrar sesión **PARA** acceder de forma autenticada.

## Contexto, alcance y reglas
PRD RF-02. Incluye login, access/refresh separados, refresh rotativo, logout e identidad autenticada. Credenciales inválidas usan error genérico; tokens tienen propósito, expiración y claves separadas; logout revoca solo su sesión. Excluye recuperación.

## Dependencias y relaciones
- Épica: [[EP-001-acceso-y-perfil]]. Depende de [[HU-001-registrar-usuario]]; la consume [[HU-003-interfaz-de-acceso]].

## Esfuerzo
**Nivel:** Alto. Seguridad, persistencia de sesiones, revocación y concurrencia.

## Tareas de desarrollo
- [ ] **T-01 — Contrato de autenticación.** Definir respuestas y errores sin filtrar credenciales.
- [ ] **T-02 — Sesiones seguras.** Implementar emisión, validación, rotación y revocación en capas hexagonales.
- [ ] **T-03 — Protección y pruebas.** Configurar autorización/CORS y verificar tokens inválidos, refresco repetido y logout.

## Criterios de aceptación
- **CA-01:** Dadas credenciales válidas, cuando inicia sesión, entonces recibe access/refresh con expiración e identidad; con credenciales inválidas recibe 401 genérico.
- **CA-02:** Dado token ausente, manipulado, expirado o refresh usado como access, cuando consulta identidad protegida, entonces recibe 401.
- **CA-03:** Dado refresh válido, cuando renueva, entonces recibe un nuevo par y el refresh anterior no se puede reutilizar.
- **CA-04:** Dado logout de una sesión, cuando reutiliza sus tokens, entonces fallan; otras sesiones independientes permanecen válidas.
- **CA-05:** Dado origen no permitido, cuando intenta consumir API, entonces CORS no lo autoriza.

## Definition of Done
- [ ] CA-01 a CA-05 probados, incluida persistencia de sesiones.
- [ ] Secretos solo provienen de entorno; access y refresh no comparten clave ni propósito.
- [ ] Contrato REST y web consumidor quedan sincronizados; no se registran tokens/contraseñas.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-05 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-02 y seguridad mínima; restricciones backend/frontend. Verificado: 2026-09-17.
