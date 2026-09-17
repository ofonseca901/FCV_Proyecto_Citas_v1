# Plantilla de página WIKI

Las páginas nuevas o revisadas mantenidas por el agente deben comenzar con metadatos equivalentes a:

```yaml
---
title: Título de la página
status: vigente | propuesta | pendiente | obsoleta
classification: HECHO | DECISIÓN | PREFERENCIA | PREGUNTA ABIERTA
last_verified: YYYY-MM-DD
sources:
  - ../raw/manifest.md
---
```

El cuerpo debe sintetizar conocimiento, enlazar páginas relacionadas y declarar explícitamente limitaciones o inferencias. No copiar conversaciones completas ni incluir secretos, tokens, hashes o PII real.
