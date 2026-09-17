# Operaciones de la LLM Wiki

## INGEST

1. Confirmar que la fuente está curada y que su origen, versión y hash constan en `../raw/manifest.md`.
2. Leer la fuente completa sin editarla.
3. Contrastar afirmaciones relevantes con el PRD, restricciones, código y pruebas vigentes.
4. Actualizar solo las páginas WIKI afectadas, enlazando la fuente y clasificando cada afirmación.
5. Actualizar `wiki/index.md` si aparece una página o categoría nueva.
6. Añadir una entrada a `wiki/log.md` con fuente, páginas afectadas y limitaciones.

## QUERY

1. Leer primero `wiki/index.md`.
2. Abrir las páginas relevantes y sus fuentes.
3. Verificar hechos contra código, especificaciones y evidencia reciente.
4. Responder separando evidencia, inferencia y preguntas abiertas.
5. Persistir el resultado solo si contiene conocimiento durable y verificable.

## LEARN

Persistir únicamente conocimiento que pueda reutilizarse en futuras sesiones. Etiquetar cada elemento como `HECHO`, `DECISIÓN`, `PREFERENCIA` o `PREGUNTA ABIERTA`, indicando fuente y fecha. No guardar transcripciones ni datos efímeros de una conversación.

## LINT

Revisar periódicamente:

- contradicciones entre páginas o con fuentes más recientes;
- afirmaciones obsoletas o sin evidencia;
- duplicados, páginas huérfanas y enlaces rotos;
- decisiones presentadas como aprobadas sin confirmación;
- contenido sensible o secretos.

El resultado de cada revisión se registra en `wiki/log.md`.
