# LOOP 2 — guiado avanzado: cita especializada end-to-end

Patrón Builder/Verifier cross-repo para cerrar la HU de solicitud y decisión de cita especializada en S3. No incluye cancelación ni reprogramación (S4).

## Prompt ejecutable

```text
/goal Ejecuta un loop Builder/Verifier para implementar o completar la HU aprobada de solicitud de cita especializada. Máximo 4 iteraciones y una sola HU por ciclo.

Antes de editar, lee PRD, HU/DoD, contrato REST vigente, AGENTS de citas-api y citas-web y la evidencia de S2. Define un contrato de entrada/salida antes del primer cambio. Builder puede modificar ambos repositorios únicamente dentro de ese contrato; debe actualizar migración Flyway, dominio/aplicación/adaptadores, cliente REST, UI, pruebas y documentación cuando correspondan.

La meta verificable exige: USER filtra sede/especialidad/profesional/fecha; solo se ofrecen franjas completas para 30/60 minutos; POST crea REQUESTED y retiene todos sus slots; una segunda solicitud incompatible recibe un error accionable y no crea datos; ADMIN puede consultar la bandeja y aprobar o rechazar; rechazo exige motivo y libera slots; aprobación deja APPROVED; autorización por rol/ownership se comprueba; el frontend interpreta éxito y errores sin guardar tokens en localStorage; mvn verify, typecheck/build y escaneo de secretos pasan.

En cada iteración Builder registra hipótesis, archivos y comandos. Verifier no implementa: revisa HU/CA/DoD, contrato, diff, migración, pruebas backend, build web y evidencia funcional. Si falta un usuario ADMIN/PROFESSIONAL sintético o una decisión de contrato, detente y escala con la pregunta exacta; no inventes credenciales ni datos reales. PASS requiere todos los criterios obligatorios; tras 4 iteraciones sin PASS, termina BLOCKED.
```

## Registro requerido

Guardar una fila por iteración con `goal`, `iteration`, `builder`, `verifier`, `backendTests`, `frontendChecks`, `contract`, `secretsScan`, `result` y `blocker`. No guardar tokens, contraseñas, hashes, cookies ni respuestas completas con PII.

## Stop condition

`PASS` requiere evidencia en ambos repositorios. `FAIL` identifica un criterio concreto. `BLOCKED` exige una dependencia externa o decisión humana no resuelta; no se convierte en `Completada` por compilación aislada.
