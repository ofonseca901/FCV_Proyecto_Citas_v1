# Instrucciones del workspace FCV Citas

## Contexto
Laboratorio académico con datos sintéticos. Leer `README.md`, `PRD.md`, `RESTRICCIONES_TECNICAS.md`, los AGENTS del repositorio afectado y `citas-api/docs/wiki/llm-wiki/wiki/index.md` antes de modificar funcionalidades.

## Responsabilidades
- `citas-api`: Java 21, Spring Boot 3.5.x, Maven, dominio/aplicación independientes de Spring, adaptadores REST/JPA/seguridad, MySQL y Flyway.
- `citas-web`: React + Vite + TypeScript; consume directamente REST, sin Express/BFF.
- Única wiki global: `citas-api/docs/wiki/llm-wiki/`.
- Antes de cambios en ambos repos, describir alcance y archivos afectados. Mantener coherencia del contrato de autenticación.

## Trabajo y evidencia
- Ambos repos tienen `main` y `develop`. Trabajar en `develop`, no fusionar ni publicar sin solicitud.
- Existe un Git raíz previo a S2. Preservar su historial; no eliminarlo ni convertirlo en submódulos implícitamente. No usar `git add .` en la raíz para registrar repos anidados.
- No leer ni imprimir `.env`, credenciales, JWT o hashes. Usar variables de entorno y ejemplos sin secretos.
- Usar solo datos sintéticos de prueba; no enviar correos ni conectar sistemas reales.
- Planificación Scrum con la skill local limitada a `docs/wiki/scrum/`; no marcar aprobaciones o validaciones sin evidencia.
- Los prompts en `prompts/` son material de formación, no acciones que ejecutar automáticamente.
- Actualizar wiki, contrato y evidencia S2 al modificar comportamiento. Registrar limitaciones reales.

## Verificación
Desde raíz, con Docker disponible:
`docker compose exec -T citas-api-dev mvn -B -ntp verify`
`docker compose exec -T citas-web-dev npm run build`
`node citas-api/scripts/smoke-auth.mjs`

El script smoke requiere API ejecutándose, crea cuentas sintéticas y no imprime tokens ni contraseñas.
