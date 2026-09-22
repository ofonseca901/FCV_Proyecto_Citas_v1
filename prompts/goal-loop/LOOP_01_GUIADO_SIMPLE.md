# LOOP 1 — guiado simple: impedir doble reserva

Patrón Builder/Verifier para S3. Solo se ejecuta sobre una HU aprobada/en desarrollo y dentro de `citas-api` salvo que el contrato obligue a coordinar `citas-web`.

## Prompt ejecutable

```text
/goal Ejecuta un loop Builder/Verifier para corregir exclusivamente el defecto de doble reserva de slots de la HU aprobada. Máximo 3 iteraciones.

Antes de editar: lee PRD, HU/DoD, contrato REST, AGENTS y la prueba roja. Builder debe describir la hipótesis, aplicar el cambio mínimo, conservar arquitectura hexagonal y ejecutar la suite afectada. Verifier trabaja después, en revisión separada y de solo lectura: contrasta criterios, diff, migración, autorización, errores y resultado de pruebas.

La condición de éxito exige evidencia de: (1) dos solicitudes incompatibles al mismo slot dejan una sola reserva, (2) una cita de 60 minutos ocupa dos slots consecutivos, (3) una solicitud inválida no modifica datos, (4) el actor ajeno recibe 401/403 según corresponda, (5) el contrato y el consumidor web siguen coherentes, (6) mvn verify y las verificaciones web aplicables pasan, y (7) el escaneo de secretos no detecta credenciales.

Si una iteración falla, la siguiente usa únicamente la causa concreta reportada por Verifier. No cambies UI, esquema o contrato salvo que la evidencia demuestre que es imprescindible y registra la justificación. Detente con BLOCKED después de 3 iteraciones si persiste la misma causa o falta una decisión humana.
```

## Evidencia mínima por iteración

Registrar en `docs/wiki/llm-wiki/wiki/` o en el artefacto de la HU: número de iteración, estado Builder, estado Verifier, tests ejecutados, archivos afectados, resultado `PASS|FAIL|BLOCKED` y causa. Nunca registrar tokens, contraseñas, hashes ni PII real.

## Stop condition

`PASS` solo cuando la prueba de concurrencia y la suite relacionada pasan. `BLOCKED` requiere causa reproducible y decisión solicitada; no se arregla aumentando iteraciones.
