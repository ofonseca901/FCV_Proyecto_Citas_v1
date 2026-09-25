# LOOP 3 — reto independiente

## Propósito

El estudiante asume el rol de Ingeniero de Software y/o Ingeniero Funcional, identifica una brecha real del MVP S4 y ejecuta un ciclo autónomo Builder/Verifier. El reto debe producir una capacidad observable; no es un ejercicio de redacción ni una excusa para ampliar el alcance.

## Reto aplicado a este proyecto

Completar el ciclo de vida de una cita especializada: reservar una nueva franja para reprogramación, mantener intacta la cita vigente mientras la solicitud está pendiente, decidirla desde ADMIN, liberar o conservar los slots correctos y dejar trazabilidad en el historial.

## Contrato del loop

1. **Disparador:** existe una cita `APPROVED` futura y el flujo de reprogramación presenta una brecha o una prueba roja.
2. **Meta verificable:** una solicitud `PENDING` no modifica la cita original; la aprobación mueve la cita a la nueva franja; el rechazo libera la reserva provisional; no se admite doble reserva.
3. **Estado observado/persistente:** código, migración Flyway vigente, contrato REST, pruebas y el registro de iteraciones en `evidence/s4-loop-03.md`.
4. **Alcance del Builder:** cambios mínimos y trazables en backend, frontend, pruebas y documentación del contrato. No puede introducir credenciales, datos reales ni alterar S2/S3 aprobados sin evidencia.
5. **Verifier aislado:** ejecuta inspección de diff, pruebas Maven, build/typecheck web, smoke HTTP y casos negativos. El Verifier no edita código.
6. **Presupuesto:** máximo tres iteraciones Builder → Verifier.
7. **Condición de parada:** todos los criterios de la meta están verdes o existe un impedimento externo documentado.
8. **Escalamiento humano:** detenerse si falta una decisión de negocio, una migración no reversible, un secreto/servicio externo o si la misma prueba falla en tres iteraciones.
9. **Evidencia:** cada iteración registra objetivo, archivos tocados, comando, resultado y decisión; nunca incluye passwords, tokens, hashes ni PII real.
10. **Justificación:** el contrato cruza persistencia, reglas de slots, autorización ADMIN/USER, REST y UI; por ello un único prompt no ofrece una verificación independiente suficiente.

## Definition of Done del loop

- La migración y el contrato describen estados, reservas provisionales e historial.
- Backend cubre los caminos feliz y negativo, incluida concurrencia/doble reserva.
- Frontend consume el contrato y expone estados de carga, éxito y error.
- Las verificaciones `mvn verify`, `npm run typecheck`, `npm run build` y smoke HTTP tienen resultado registrado.
- La evidencia permite reproducir el flujo con datos sintéticos sin revelar secretos.
