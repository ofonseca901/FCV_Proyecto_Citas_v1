# Agente de citas-web

## Implementación detectada
React 19, Vite 7, TypeScript y CSS. Node 24. Frontend inicial creado localmente: no afirmar que fue exportado de Stitch/AI Studio ni que tiene aprobación visual.

## Organización
- `src/main.tsx`: formularios login/registro, sesión y estados de UI.
- `src/api.ts`: cliente REST tipado, timeout y errores.
- `src/styles.css`: diseño adaptable.
- API configurable con `VITE_API_URL`; no Express/BFF.

## Reglas
- Conservar etiquetas, foco visible, mensajes de error y controles deshabilitados durante envíos.
- Tokens solo en memoria: al recargar se inicia sesión nuevamente. No guardarlos en localStorage ni imprimirlos.
- No presentar reserva de citas como funcional mientras solo exista acceso.
- No usar datos reales; no añadir servicios externos sin necesidad del alcance.
- Contrato y wiki compartidos en `../citas-api/docs/wiki/llm-wiki/`.
- Trabajar en `develop`; ignorar `node_modules`, `dist`, secretos y artefactos TypeScript.

## Verificación
`npm ci`, `npm run build`, `npm run typecheck`.
Desde raíz: `docker compose exec -T citas-web-dev npm run build`.
Verificar en navegador escritorio/móvil, registro, login, logout y errores. Documentar resultados reales.
