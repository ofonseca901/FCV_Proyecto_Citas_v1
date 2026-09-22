# GOAL 2 — guiado avanzado: disponibilidad y cita especializada S3

Ejecutar desde la raíz con visibilidad de `citas-api` y `citas-web`.

```text
/goal Implementa el incremento S3 de disponibilidad y solicitud de cita especializada siguiendo LOOP_02_GUIADO_AVANZADO.md. Antes de editar lee GUIA_SESIONES_S2_S6.md, PRD, épicas/HU aprobadas, AGENTS de ambos repositorios, contrato REST y modelo Flyway vigente.

La condición final verificable es: catálogos activos consultables; PROFESSIONAL publica bloques futuros sin solapamiento en sedes asignadas; USER consulta franjas completas de 30/60 minutos; una cita general queda APPROVED; una especializada queda REQUESTED y retiene slots; doble reserva falla sin datos parciales; ADMIN aprueba/rechaza con motivo obligatorio al rechazar; slots se liberan al rechazo; UI muestra éxito/error; autorización, typecheck/build, pruebas API, smoke y escaneo de secretos pasan.

No implementes S4 (mis citas, cancelación, reprogramación, recuperación, cierre, auditoría completa) ni S5/S6. No uses usuarios, contraseñas, tokens o PII reales. Si el entorno MySQL existente no permite al usuario de aplicación acceder al esquema, detente y solicita la corrección de credenciales/permisos; no borres el volumen.
```

## Criterio de parada

`PASS` solo con evidencia backend y frontend enlazada a HU/CA. Un build aislado no cierra el goal. Registrar iteration/checkpoints y dejar `BLOCKED` si falta una cuenta sintética ADMIN/PROFESSIONAL, una decisión de contrato o acceso a la base.
