# Agente de `citas-web`

## Alcance y estado verificado

Este repositorio contiene exclusivamente el frontend. Trabajar aquí no autoriza cambios en `citas-api`, la wiki global ni otros repositorios.

Estado comprobado en el árbol actual:

- React 19.2, React DOM 19.2, Vite 7.3 y TypeScript 5.9; el `package.json` exige Node `>=24 <25`.
- No hay Angular, router, librería de componentes ni exportación visible de Stitch/Google AI Studio.
- `src/main.tsx` contiene los formularios de registro/login, la sesión y los estados principales de UI.
- `src/api.ts` contiene el cliente REST tipado, timeout y traducción de errores.
- `src/styles.css` contiene el diseño CSS adaptable actual.
- El backend se consume directamente mediante `VITE_API_URL`; no existe Express ni BFF.
- El alcance actual es acceso S2. No presentar reserva de citas, agenda, administración ni recuperación de contraseña como funcionales.
- HU-003 y su DoD siguen `Pendiente de aprobación`; una interfaz local no debe describirse como diseño aprobado.

## Responsabilidad y límites

- Implementar solo interfaz, estado, navegación que exista en el stack y consumo REST.
- El backend es la autoridad para autenticación, autorización, validación y reglas de negocio; no duplicar esas reglas en el cliente.
- No cambiar de framework, añadir servicios externos ni rediseñar una propuesta visual sin una decisión/aprobación explícita.
- No editar `citas-api`. Si el contrato REST no alcanza una necesidad aprobada, documentar la brecha para el orquestador.
- Usar únicamente datos sintéticos y no incluir secretos, tokens, credenciales o PII real.

## REST y sesión

- Mantener `VITE_API_URL` configurable por environment y consumir Spring Boot directamente.
- Respetar el contrato de autenticación compartido: registro, login, refresh, `/me` y logout.
- Mantener access/refresh tokens solo en memoria; no usar `localStorage`, cookies nuevas ni logs de tokens.
- No asumir autorización por datos de UI; manejar respuestas 400/401/409 y fallos de red con mensajes accionables sin revelar credenciales.

## UI, accesibilidad y estados

- Conservar etiquetas asociadas, foco visible, orden de teclado, contraste razonable y diseño adaptable.
- Todo envío debe modelar al menos `idle`, `loading`, `success`, `error` y controles `disabled` durante la petición.
- Los errores no deben borrar datos no sensibles ni permitir dobles envíos; tras un fallo debe ser posible reintentar.
- No afirmar procedencia Stitch/AI Studio ni aprobación visual hasta que exista evidencia documentada.

## Flujo de trabajo

1. Leer la HU, criterios de aceptación y DoD relevantes en `../citas-api/docs/wiki/scrum/`; si están pendientes, no ampliar el alcance por inferencia.
2. Identificar pantallas, componentes, servicios REST y estados afectados.
3. Declarar antes de editar la HU, objetivo, archivos previstos y cualquier brecha de contrato.
4. Implementar el mínimo coherente con el stack y diseño aprobado disponible, sin tocar el backend.
5. Ejecutar build, typecheck y pruebas disponibles; verificar el flujo en navegador cuando sea posible.
6. Resumir evidencia real, limitaciones y criterios no verificados para el orquestador.

## Verificación

Con Node 24 y dependencias instaladas:

```text
npm ci
npm run typecheck
npm run build
```

Desde la raíz, el build equivalente es `docker compose exec -T citas-web-dev npm run build`. Probar con datos ficticios registro, login, error genérico, logout, fallos de red y viewport móvil/escritorio. Ignorar `node_modules`, `dist`, secretos y artefactos de TypeScript en Git.

## Documentación y coordinación

- El contrato y la wiki global viven en `../citas-api/docs/wiki/llm-wiki/`; este agente no mantiene una LLM Wiki propia.
- Entregar al orquestador cambios de contrato detectados, evidencia de build/typecheck/pruebas, procedencia visual y limitaciones.
- Trabajar en `develop`; no fusionar, publicar ni reescribir historial sin solicitud.
