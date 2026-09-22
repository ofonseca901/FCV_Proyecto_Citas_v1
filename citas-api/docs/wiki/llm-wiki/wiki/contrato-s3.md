---
title: Contrato REST S3 — disponibilidad y citas
classification: HECHO
last_verified: 2026-09-22
sources:
  - ../../../../src/main/java/co/fcv/citas/adapter/web/SchedulingController.java
---

# Contrato REST S3 — disponibilidad y citas

Autenticación Bearer reutiliza el contrato S2. Errores de negocio: `INVALID` (400), `FORBIDDEN` (403) y `UNAUTHORIZED` (401).

| Método | Ruta | Rol | Propósito |
|---|---|---|---|
| GET | `/api/catalogs/locations` | público | Sedes activas |
| GET | `/api/catalogs/specialties` | público | Especialidades activas, duración y tipo |
| GET | `/api/availability?locationId&specialtyId&date` | público | Franjas libres de 30/60 minutos |
| POST | `/api/appointments` | USER | Reserva una franja; general=`APPROVED`, especializada=`REQUESTED` |
| POST/GET | `/api/professional/blocks` | PROFESSIONAL | Crear/consultar bloques propios |
| POST | `/api/admin/specialties` | ADMIN | Crear especialidad |
| POST | `/api/admin/professionals` | ADMIN | Asociar un usuario existente como profesional |
| POST | `/api/admin/professionals/{id}/specialties` | ADMIN | Asignar especialidad |
| POST | `/api/admin/professionals/{id}/locations` | ADMIN | Asignar sede |
| GET | `/api/admin/appointments/requested` | ADMIN | Bandeja especializada |
| POST | `/api/admin/appointments/{id}/decision` | ADMIN | Aprobar/rechazar; rechazar requiere motivo |

Una reserva crea slots de 30 minutos con clave única `(professional_id, start_at)`. La solicitud especializada retiene slots mientras está `REQUESTED`; al rechazo se eliminan. Cancelación, reprogramación, agenda profesional completa y cierre son S4 y no pertenecen a este contrato.
