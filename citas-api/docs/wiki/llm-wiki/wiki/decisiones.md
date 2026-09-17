---
title: Decisiones explícitas
status: vigente
classification: DECISIÓN
last_verified: 2026-09-17
sources:
  - ../raw/manifest.md
---

# Decisiones explícitas

## DEC-S2-001 — Dos repositorios de aplicación

**Estado:** vigente · **Fecha:** 2026-09-16  
**Fuente:** [README raíz](../../../../../README.md), [AGENTS raíz](../../../../../AGENTS.md)

`citas-api` y `citas-web` son repositorios Git independientes. El Git raíz histórico se conserva y no se convierte en un tercer repositorio de aplicación.

## DEC-S2-002 — API REST directa

**Estado:** vigente · **Fuente:** [PRD RF-20](../../../../../PRD.md), [restricciones técnicas](../../../../../RESTRICCIONES_TECNICAS.md)

El frontend consume Spring Boot directamente; no existe Express ni BFF. Cualquier cambio de contrato requiere coordinación y evidencia en ambos repositorios.

## DEC-S2-003 — Sesión del navegador en memoria

**Estado:** vigente para S2 · **Fuente:** [contrato de autenticación](contrato-auth.md), [AGENTS web](../../../../../citas-web/AGENTS.md)

Los tokens no se guardan en `localStorage` ni se imprimen. Una recarga requiere iniciar sesión de nuevo.

## Decisiones pendientes

- Aprobación documental de HU-001, HU-002 y HU-003.
- Procedencia y aprobación visual Stitch → AI Studio del frontend.
- Modelo físico de slots y retenciones para citas y reprogramaciones.
