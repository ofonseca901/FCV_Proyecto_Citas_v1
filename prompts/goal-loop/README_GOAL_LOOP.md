# Goals y Loops — guía para S2-S4

## Diferencia conceptual

### `/goal`
Úsalo cuando existe **un estado final verificable** y quieres que el agente continúe entre turnos hasta lograrlo.

Codex documenta `/goal <objective>` con control de estado, pause/resume/clear. Si no aparece, el estudiante puede habilitar la feature `goals` según la versión de Codex instalada.

Claude Code también dispone de `/goal`: evalúa una condición de finalización después de cada turno y continúa si todavía no se cumple.

### Loop de ingeniería del curso
El loop de S4 es **Builder → Verifier → feedback → retry → stop/escalate**. No depende de que exista un slash command llamado `/loop`.

En Codex se implementará principalmente mediante `/goal` + instrucciones de checkpoints/verificación/presupuesto.

Claude Code sí tiene un `/loop` nativo, pero su semántica es repetir un prompt por intervalo mientras la sesión permanece abierta. Es útil para checks recurrentes; no debe confundirse automáticamente con el Builder/Verifier loop de S4.

## Regla de clase
- Aprender los ejemplos estandarizados usando Codex.
- Explicar cómo trasladar el patrón a Claude Code.
- El segundo ejercicio puede ser sugerido o elegido por el estudiante.
- El tercer ejercicio es un reto independiente.
