---
title: Contrato REST S4 - ciclo de vida de citas
classification: DECISION
last_verified: 2026-09-24
---

# Contrato S4

Las rutas nuevas se publican bajo `/api/v1`; las rutas S2/S3 se mantienen temporalmente bajo `/api` y `/api/auth` para compatibilidad.

| Método | Ruta | Rol | Función |
|---|---|---|---|
| GET | `/api/v1/appointments?status&from&to` | USER | Mis citas |
| POST | `/api/v1/appointments/{id}/cancel` | USER | Cancela y libera slots |
| POST | `/api/v1/appointments/{id}/reschedule-requests` | USER | Retiene una franja alternativa |
| GET | `/api/v1/appointments/{id}/history` | USER | Historial de cita propia |
| GET | `/api/v1/admin/inbox` | ADMIN | Reprogramaciones pendientes |
| POST | `/api/v1/admin/reschedule-requests/{id}/decision` | ADMIN | Aprueba o rechaza |
| GET | `/api/v1/professional/appointments?from&to&locationId` | PROFESSIONAL | Agenda propia |
| POST | `/api/v1/professional/appointments/{id}/closure` | PROFESSIONAL | COMPLETED o NO_SHOW |
| GET | `/api/v1/professional/specialties` | PROFESSIONAL | Especialidades propias y metadatos del artículo más reciente de PubMed |

Conflictos de slots responden `409 CONFLICT`; validación de negocio responde `400 INVALID`; ownership o rol responde `403 FORBIDDEN`.

`GET /api/v1/professional/specialties` no persiste contenido bibliográfico ni mezcla datos clínicos con usuarios. Consulta ESearch/ESummary de PubMed bajo demanda, conserva una caché en memoria de 30 minutos por especialidad y devuelve `latestArticle.available=false` si la fuente pública no está disponible.

## Seguridad de inactividad

Las sesiones se revocan después de 10 minutos sin actividad autenticada. La API actualiza la actividad al validar una petición o rotar el refresh token; el cliente también cierra su sesión en memoria al alcanzar ese límite. El parámetro operativo es `SESSION_INACTIVITY_MINUTES` y solo admite valores entre 1 y 60.
