# Convenciones de la wiki

- RAW registra referencias/fuentes curadas; no copiar secretos ni conversaciones.
- WIKI sintetiza HECHOS verificados, DECISIONES de implementación y PREGUNTAS ABIERTAS.
- Indexar toda página nueva desde `wiki/index.md`.
- Registrar cambios en `wiki/log.md` de forma append-only.
- Verificar afirmaciones contra código, pruebas y fuentes antes de cerrar una HU.
- No convertir una propuesta en aprobación de usuario. No copiar datos personales reales.
- Una evidencia debe identificar comando, resultado, entorno y limitación.
- Cada página WIKI nueva o revisada debe declarar estado, clasificación, fecha de verificación y fuentes según `plantilla-pagina.md`; las páginas S2 heredadas se migrarán al revisarse.
- Las rutas relativas se resuelven desde el archivo que las contiene; los enlaces rotos son un hallazgo de LINT.
- Los snapshots RAW se versionan como nuevas revisiones; nunca se modifica silenciosamente una fuente ya incorporada.
