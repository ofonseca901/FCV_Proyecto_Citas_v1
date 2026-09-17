# Evidencia S2 — 2026-09-16

## Estado del entregable

| Punto mínimo | Resultado verificable |
|---|---|
| Dos repos inicializados | `citas-api/.git` y `citas-web/.git`, ramas main y develop; trabajo actual en develop |
| AGENTS principales | AGENTS raíz, backend y frontend creados |
| Scrum con HU aprobadas | Una épica y tres HU redactadas; **aprobación explícita pendiente** |
| Wiki global iniciada | Índice, dominio, arquitectura, contrato, modelo, fuentes, convenciones y log |
| BD y migraciones iniciales | MySQL 8.4 conectado; Flyway V1 aplicada y JPA valida el esquema |
| Registro y login JWT | Implementados, con refresh rotativo, logout revocable e identidad |
| Frontend importado/ejecutable | React ejecutable y probado; **creado localmente, no importado de Stitch/AI Studio** |
| Commit S2 en develop | Commit `feat(s2): bootstrap specs auth and frontend baseline` en cada repo; obtener hash con `git -C <repo> log -1 --oneline` |

## Validaciones ejecutadas

1. `docker compose exec -T citas-api-dev mvn -B -ntp verify`: **BUILD SUCCESS**, 6 tests, 0 fallos, 0 errores. Última ejecución finalizó 2026-09-16 20:45 UTC. Pruebas REST/persistencia en H2 aislado, no equivalen a integración MySQL.
2. `node citas-api/scripts/smoke-auth.mjs`: **PASS, 18 comprobaciones HTTP**, API real conectada a MySQL. Health, registro, duplicados email/documento, intento de asignar roles, validación, login incorrecto/correcto, acceso sin token, propósito incorrecto, manipulación, refresh, reutilización y revocación.
3. `docker compose exec -T citas-web-dev npm run build`: **PASS**, TypeScript y build Vite 7.3.6. `npm install` reportó 0 vulnerabilidades en la instalación realizada.
4. Navegador Chrome local: registro sintético → mensaje de cuenta creada → login incorrecto con error genérico → login correcto con identidad → logout con confirmación. Ningún dato real fue enviado mediante las pruebas.
5. Inspección visual: formulario de registro en escritorio y login en móvil; corregida separación de palabras del título móvil. Viewport móvil solicitado 390×844; ancho útil medido 375px, scrollWidth 375px (sin desbordamiento horizontal). Restaurado viewport original al terminar.
6. `docker compose ps`: API y frontend Up; MySQL healthy. `git diff --check` sin errores de espacios. `.env.s2` confirmado como ignorado por Git.

## Cobertura de historias

| Historia | Evidencia técnica | Pendiente |
|---|---|---|
| HU-001 | Test registrationPersistsHashAndOnlyUserRole, validationAndPrivilegeEscalationAreRejected; smoke y navegador | Aprobación documental |
| HU-002 | Test sessionLifecycleAndTokenSeparation, concurrentRefreshHasExactlyOneWinner, expiredAccessAndMissingAuthenticationAreRejected, corsOnlyAllowsConfiguredFrontend; smoke MySQL | Aprobación documental; concurrencia comprobada en H2, no bajo carga MySQL |
| HU-003 | Build/typecheck y recorrido real de registro/login/error/logout; móvil sin desbordamiento | Aprobación visual, proceso Stitch/AI Studio, prueba de fallo de red en navegador no realizada |

No se marcan historias Completadas ni se inventa aprobación del usuario. La evidencia aquí registra implementación técnica, no cierre Scrum.

## Arranque y configuración

Desde PowerShell en la raíz: `./scripts/start-s2.ps1`.
Frontend http://localhost:5173; health http://localhost:8080/actuator/health.

Se conservó el volumen existente. La configuración original refería otro nombre de base; el overlay usa `citas_fcv_training`, esquema al que la cuenta tenía acceso y que estaba vacío al inspeccionarlo. Se generaron secretos JWT independientes en `.env.s2`, sin leer/imprimir ni modificar el `.env` original.

## Limitaciones restantes

- Aprobación explícita de las tres historias y revisión visual del usuario.
- La procedencia Stitch → AI Studio exigida por el material del curso no se cumplió; frontend local entregado como propuesta funcional.
- Git raíz preexistente conservado con cambios del workspace sin commit raíz; los dos repos de aplicación contienen sus commits propios. No se han creado remotos ni publicado repos.
- Flyway administrado por Spring Boot emitió aviso de compatibilidad probada hasta MySQL 8.1. En este entorno MySQL 8.4 sí ejecutó V1 y pasó el smoke; revisar actualización específica antes de ampliar migraciones.
- No incluye citas/agenda, recuperación de contraseña, administración ni automatizaciones de sesiones posteriores.
- Las pruebas crean cuentas sintéticas. No se eliminaron datos/volúmenes para preparar o cerrar la sesión.
