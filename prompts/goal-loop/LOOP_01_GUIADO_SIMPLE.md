# LOOP 1 — guiado sencillo: corregir doble reserva

> Este es el **patrón de loop S4**, no necesariamente el slash `/loop` de Claude.

## Escenario
Existe una prueba roja que demuestra que dos solicitudes pueden intentar tomar el mismo slot.

## Prompt estandarizado para Codex (usar con `/goal`)

```text
/goal Ejecuta un loop Builder/Verifier para corregir exclusivamente el defecto de doble reserva de slots. Máximo 3 iteraciones. En cada iteración: BUILDER lee la prueba fallida y aplica el cambio mínimo; ejecuta las pruebas; luego VERIFIER, en revisión separada, inspecciona HU/DoD, git diff y resultado de pruebas y devuelve PASS o FAIL con causa concreta. Si FAIL, usa únicamente ese feedback para la siguiente iteración. No cambies UI ni esquema salvo que sea imprescindible y esté justificado. Finaliza cuando el test de concurrencia/reserva incompatible pase junto con la suite relacionada, o detente BLOCKED después de 3 iteraciones dejando un log de cada intento.
```

## En Claude Code
El equivalente recomendado para este caso es `/goal` con la misma condición. El `/loop` temporal de Claude sirve para repetición por intervalo, no es necesario aquí.
