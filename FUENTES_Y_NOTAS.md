# Fuentes y notas de diseño

## Formación FCV
La formación conserva la progresión de la propuesta: desarrollo dirigido, verificación, loops/autonomía, MCP/n8n y automatizaciones creadas/versionadas por el agente.

## LLM Wiki
Patrón conceptual de Andrej Karpathy:
https://gist.githubusercontent.com/karpathy/442a6bf555914893e9891c11519de94f/raw/ac46de1ad27f92b28ac95459c782c07f6b8c964a/llm-wiki.md

Este paquete implementa una adaptación resumida: raw sources inmutables, wiki Markdown mantenida por el LLM, schema de gobierno, index/log e ingest/query/lint; añade LEARN como convención del curso.

## Goals / Claude Code
- Codex: usar `/goal` para objetivos durables con condición verificable; el ejercicio de loop del curso se expresa como Builder/Verifier dentro de un goal.
- Claude Code: dispone de `/goal` y `/loop`; `/loop` repite prompts por intervalo y no debe confundirse automáticamente con el loop Builder/Verifier.

## Datos públicos
Las sedes y nombres de especialidades del modelo de referencia se usan únicamente como información institucional pública. Los datos personales/profesionales y transaccionales del laboratorio son ficticios.
