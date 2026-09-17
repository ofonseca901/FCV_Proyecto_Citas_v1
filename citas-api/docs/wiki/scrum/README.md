---
tipo: indice-scrum
estado: Pendiente de aprobación
fuentes_verificadas: 2026-09-17
---

# Mapa Scrum / Spec-Driven Development — FCV Citas

## Propósito y límites

Mapa completo de especificaciones del laboratorio de citas ficticias. Deriva exclusivamente de `PRD.md`, `RESTRICCIONES_TECNICAS.md` y `database/REQUISITOS_NORMALIZACION_3FN.md`, verificadas el 2026-09-17. No autoriza implementación ni modifica código.

Todas las épicas y HU están en **Pendiente de aprobación**. Una selección para S2, S3 o S4 solo puede hacerse después de aprobación explícita del usuario; ninguna de estas notas constituye dicha aprobación.

## Arquitectura de referencia declarada

- Backend: Java 21, Spring Boot 3.5.x, Maven, arquitectura hexagonal, JPA, MySQL 8.4, Flyway, REST/JSON y JWT access/refresh.
- Frontend: TypeScript, React o Angular según el diseño aprobado de Stitch/AI Studio; consume REST directamente, sin Express/BFF.
- Datos: modelo normalizado hasta 3FN, catálogos fijos precargados, datos estrictamente sintéticos.

## Épicas

1. [[EP-001-acceso-y-perfil]] — identidad, sesión, recuperación y afiliación.
2. [[EP-002-catalogos-y-profesionales]] — catálogos administrables y profesionales habilitados.
3. [[EP-003-disponibilidad-y-agenda]] — bloques, duración y búsqueda de disponibilidad.
4. [[EP-004-citas-generales-y-seguimiento]] — reserva inmediata, consulta y cancelación.
5. [[EP-005-citas-especializadas]] — solicitud retenida y decisión administrativa.
6. [[EP-006-reprogramacion]] — reserva provisional y decisión sin perder la cita original.
7. [[EP-007-operacion-profesional]] — agenda propia y cierre de atención.
8. [[EP-008-auditoria-y-contrato]] — historial inmutable y contrato REST trazable.
9. [[EP-009-automatizaciones-posteriores]] — recordatorios y avisos n8n posteriores al núcleo.

## Incrementos sugeridos (sin duración ni capacidad)

| Incremento | Resultado funcional verificable | HU en orden secuencial |
|---|---|---|
| S2 — Acceso y perfil inicial | Un USER sintético puede registrarse, autenticarse y gestionar sus datos básicos. | [[HU-001-registrar-usuario]] → [[HU-002-gestionar-sesion]] → [[HU-003-interfaz-de-acceso]] → [[HU-004-recuperar-contrasena]] → [[HU-005-gestionar-perfil]] → [[HU-006-gestionar-afiliacion]] |
| S3 — Configuración y disponibilidad | ADMIN configura la oferta; PROFESSIONAL publica agenda; USER consulta franjas reservables. | [[HU-007-consultar-catalogos-fijos]] → [[HU-008-administrar-eps]] → [[HU-009-administrar-planes-eps]] → [[HU-010-administrar-especialidades]] → [[HU-011-crear-y-activar-profesional]] → [[HU-012-asignar-especialidades-profesional]] → [[HU-013-asignar-sedes-profesional]] → [[HU-014-definir-duracion-especialidad]] → [[HU-015-gestionar-bloques-disponibilidad]] → [[HU-016-consultar-calendario-profesional]] → [[HU-017-buscar-disponibilidad]] |
| S4 — Ciclo de citas | USER reserva, sigue y ajusta citas; ADMIN decide las especializadas y reprogramaciones; PROFESSIONAL opera su agenda. | [[HU-018-reservar-cita-general]] → [[HU-019-consultar-mis-citas]] → [[HU-020-cancelar-cita]] → [[HU-021-solicitar-cita-especializada]] → [[HU-022-consultar-bandeja-citas-especializadas]] → [[HU-023-decidir-cita-especializada]] → [[HU-024-solicitar-reprogramacion]] → [[HU-025-consultar-bandeja-reprogramaciones]] → [[HU-026-decidir-reprogramacion]] → [[HU-027-consultar-agenda-profesional]] → [[HU-028-cerrar-atencion]] → [[HU-029-consultar-historial-de-estados]] → [[HU-030-mantener-contrato-rest]] |
| S5/S6 — Automatizaciones posteriores | Operación de notificaciones sintéticas sin alterar el núcleo de citas. | [[HU-031-recordar-citas-proximas]] → [[HU-032-notificar-cambios-de-estado]] → [[HU-033-generar-resumen-operativo]] |

## Reglas de selección y trazabilidad

- Una HU es seleccionable para un incremento solo si su estado cambia explícitamente a `Aprobada`; sus dependencias también deben estar aprobadas o ya completadas.
- Cada HU enlaza su épica, sus dependencias, requisitos PRD/RN aplicables, tareas, CA, DoD y matriz de evidencia pendiente.
- Los cambios de esquema exigidos por una HU deben preservar 3FN, incluir migración Flyway y justificar claves, cardinalidades, dependencias funcionales e índices relevantes.
- Las HU que cruzan frontend/backend exigen contrato REST documentado y pruebas coherentes en ambos repositorios. No hay BFF.

## Decisiones e incógnitas que requieren revisión

- El framework final del frontend y la procedencia/aprobación visual quedan pendientes de Stitch/AI Studio; las HU web son deliberadamente independientes de React o Angular.
- El mecanismo concreto de retención concurrente de slots y sus índices se debe decidir en la HU correspondiente, demostrando RN-01 sin romper 3FN.
- La recuperación puede usar una respuesta/log controlado solo en desarrollo; SMTP no es obligatorio.
- Las automatizaciones son posteriores al núcleo, no usan datos reales ni almacenan secretos, credenciales u OAuth en sus exportaciones.
