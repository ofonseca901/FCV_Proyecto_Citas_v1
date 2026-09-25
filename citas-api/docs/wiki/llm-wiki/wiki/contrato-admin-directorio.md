---
title: Contrato REST - Directorio y analítica ADMIN
classification: DECISION
last_verified: 2026-09-24
---

# Directorio administrativo

| Método | Ruta | Propósito |
|---|---|---|
| GET | `/api/v1/admin/dashboard` | Métricas operativas sin PII innecesaria |
| GET | `/api/v1/admin/users?q&page&size` | Directorio paginado de cuentas |
| POST | `/api/v1/admin/users` | Crea USER; la contraseña solo se recibe y hashea |
| PATCH | `/api/v1/admin/users/{id}/active` | Activa o desactiva USER |
| GET | `/api/v1/admin/professionals` | Directorio de profesionales y asignaciones |
| PATCH | `/api/v1/admin/professionals/{id}/active` | Activa o desactiva profesional |

Todas las operaciones exigen el rol `ADMIN`; ninguna respuesta expone hashes, refresh tokens o contraseñas.
