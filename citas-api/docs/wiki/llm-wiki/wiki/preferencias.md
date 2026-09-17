---
title: Preferencias y guardrails duraderos
status: vigente
classification: PREFERENCIA
last_verified: 2026-09-17
sources:
  - ../raw/manifest.md
---

# Preferencias y guardrails duraderos

- Usar únicamente datos sintéticos del laboratorio.
- No leer, imprimir ni persistir `.env`, credenciales, contraseñas, JWT o hashes.
- Trabajar en `develop`; `main` representa puntos estables y no se publica sin solicitud.
- Mantener dominio y aplicación del backend independientes de Spring y JPA.
- Mantener validación server-side, autorización por rol/ownership y CORS explícito.
- Documentar limitaciones reales y no convertir una propuesta o una inferencia en aprobación.
- Mantener el contrato REST y la wiki global dentro de `citas-api/docs/wiki/llm-wiki/`, con enlaces desde ambos repositorios.
