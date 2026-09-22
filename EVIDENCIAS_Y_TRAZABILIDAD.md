# Evidencias y trazabilidad para evaluación final

La calificación se realiza al finalizar S6, pero el historial debe permitir reconstruir el progreso.

## Evidencia mínima por sesión

| Sesión | Evidencia mínima |
|---|---|
| S2 | commit backend + commit frontend; AGENTS; Scrum specs; baseline ejecutable |
| S3 | commits; tests; hook FAIL/PASS; secreto ficticio bloqueado |
| S4 | commits; logs Builder/Verifier; goal/loop; MVP |
| S5 | commit; WF-001 JSON; evidencia MCP; riesgos residuales |
| S6 | commit final; WF-002 JSON; validaciones; merge/main estable; sustentación |

## Regla Git
- desarrollo en `develop`;
- `main` representa únicamente puntos que el estudiante considera estables;
- no exigir merge por sesión;
- no hacer squash/rebase destructivo que borre el progreso antes de la evaluación.

## Plantilla de registro

```text
Sesión:
Repo:
Branch:
Commit hash:
HU abordadas:
Criterios completados:
Pruebas ejecutadas:
Qué quedó pendiente:
Evidencia adicional:
```

## n8n evaluable
Los JSON exportados deben abrir/importar sin depender de secretos embebidos. Las credenciales se configuran en n8n y nunca deben formar parte del JSON/repositorio en texto claro.
