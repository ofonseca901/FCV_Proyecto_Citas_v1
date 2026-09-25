# GOAL 3 — reto independiente

## Goal

Implementar y verificar la reprogramación de una cita futura sin perder la reserva original hasta que ADMIN decida.

## Leer primero

- `PRD.md` (RF-15 y RN-01/RN-09/RN-10/RN-11).
- `GUIA_SESIONES_S2_S6.md` (S4).
- `citas-api/docs/wiki/llm-wiki/wiki/contrato-s3.md` y el contrato S4.
- Migraciones Flyway y pruebas actuales de `citas-api`.
- `citas-web/src/api.ts` y la UI de citas.

## Fuera de alcance

- SMTP, Gmail, n8n, pagos, historia clínica o datos reales.
- Cambiar el algoritmo JWT o almacenar tokens en el navegador.
- Cambiar reglas ya verificadas de registro, login, disponibilidad o cita general.

## Artefactos y comandos

- Migración y código bajo `citas-api/src/main/**`.
- Pruebas bajo `citas-api/src/test/**`.
- Consumidor REST/UI bajo `citas-web/src/**`.
- Contrato y evidencia bajo `citas-api/docs/wiki/llm-wiki/**` y `evidence/s4-loop-03.md`.
- `docker compose exec -T citas-api-dev mvn -B -ntp verify`.
- `docker compose exec -T citas-web-dev npm run typecheck` y `npm run build`.
- `node citas-api/scripts/smoke-auth.mjs` más un smoke de reservas/reprogramación sin imprimir tokens.

## Finalización verificable

Given una cita `APPROVED` futura, When USER solicita otra franja disponible, Then la API crea una solicitud `PENDING`, retiene la nueva franja y conserva la original. When ADMIN aprueba, Then actualiza la cita y el historial; When ADMIN rechaza con motivo, Then libera la nueva franja y conserva la cita original. Una franja ocupada debe responder con error controlado.

## Pausa y reintentos

Máximo tres iteraciones Builder/Verifier. Pausar y escalar si una decisión funcional es ambigua, falta una dependencia externa, se requiere un secreto o la misma evidencia falla tres veces.

## Evidencia final

Registrar por iteración el estado inicial, cambios, comandos, resultado y limitaciones. Marcar el goal como completado solo con pruebas verdes y contrato/documentación coherentes.
