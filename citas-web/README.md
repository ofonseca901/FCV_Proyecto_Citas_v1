# citas-web — incremento S2

React + TypeScript + Vite con formularios de login y registro, mensajes de error, vista de sesión y logout. Consume Spring Boot directamente por REST, sin Express/BFF.

## Ejecutar
Con Node 24:

```text
npm ci
npm run dev
```

O desde la raíz del workspace:

```powershell
./scripts/start-s2.ps1
```

Abrir http://localhost:5173. API por defecto en http://localhost:8080. Configurar `VITE_API_URL` en un `.env` local si cambia. El backend debe permitir el origen del navegador en `FRONTEND_ORIGIN`.

## Verificación
```text
npm run build
npm run typecheck
```

Probar con datos ficticios: crear cuenta, iniciar sesión y cerrarla. La sesión vive solo en memoria; recargar vuelve al login. No guardar tokens en almacenamiento del navegador.

## Procedencia y alcance
Esta interfaz fue creada localmente durante S2 como propuesta revisable. **No fue importada desde Stitch/Google AI Studio y aún no tiene aprobación visual del usuario.** Si el curso exige ese proceso, se debe completar y reconciliar el diseño/exportación antes de cerrar el entregable.

No incluye reserva de citas ni recuperación de contraseña. Wiki y contrato global en `../citas-api/docs/wiki/llm-wiki/`.
