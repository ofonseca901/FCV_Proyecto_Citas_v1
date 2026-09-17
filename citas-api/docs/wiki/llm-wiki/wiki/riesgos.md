---
title: Riesgos y preguntas abiertas
status: pendiente
classification: PREGUNTA ABIERTA
last_verified: 2026-09-17
sources:
  - ../raw/manifest.md
---

# Riesgos y preguntas abiertas

## R-001 — Aprobaciones pendientes

Las HU S2 y la revisión visual no tienen aprobación explícita. La evidencia técnica de S2 no equivale a cierre Scrum.

## R-002 — Procedencia del frontend

El frontend React actual fue creado localmente. No existe evidencia de exportación desde Stitch/Google AI Studio ni de aprobación visual.

## R-003 — Artefactos n8n

`citas-api/automations/n8n/` contiene actualmente notas Markdown con nombres WF-001/002/003. La norma de evaluación exige exportaciones JSON sin credenciales. Debe decidirse si esas notas se reubican como documentación antes de crear workflows ejecutables.

## R-004 — Reservas y concurrencia

El PRD exige evitar doble reserva y conservar la cita original durante una reprogramación pendiente, pero aún no existe una decisión aprobada sobre tablas de slots, retenciones, bloqueos e índices.

## R-005 — Flyway y MySQL 8.4

La evidencia S2 registra un aviso de compatibilidad de Flyway probado hasta MySQL 8.1, aunque la ejecución local sobre MySQL 8.4 fue correcta. Conviene verificar la versión de Flyway antes de ampliar migraciones.

## R-006 — Contrato automatizado

El contrato de autenticación está documentado y probado por backend/frontend, pero todavía no hay una prueba de contrato cross-repo dedicada.

## Material no resuelto por inferencia

No inferir aprobación de HU, decisiones de UI, catálogo inicial completo, política de recuperación de contraseña, diseño físico de slots ni autorización para usar `database/reference/`. Cada punto requiere decisión o evidencia explícita.
