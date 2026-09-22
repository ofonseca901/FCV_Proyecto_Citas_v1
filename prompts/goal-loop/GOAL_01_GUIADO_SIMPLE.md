# GOAL 1 — guiado simple: autenticación verificable

## Objetivo

Construir el primer vertical slice de S2 sin mezclar agenda ni citas. La aplicación debe autenticarse contra el esquema configurado por el overlay (`citas_fcv_training`) y la tabla efectiva `app_users`.

## Prompt para Codex/Claude

```text
/goal Implementa la HU aprobada de registro y sesión JWT en citas-api. Lee primero PRD, HU/DoD, contrato-auth, AGENTS y application.yml. Verifica el datasource real sin abrir ni imprimir .env: el entorno S2 debe usar el overlay y el esquema citas_fcv_training.

La condición final exige: registro de USER con email y documento únicos; password hasheado; login que emite access/refresh; refresh rotativo; logout revocable; /me con ownership; errores 400/401/409; CORS explícito; no tokens/passwords en logs; migración Flyway coherente; 6 o más pruebas backend relevantes; smoke-auth HTTP contra MySQL; y build frontend con registro/login/logout. No implementes recuperación, agenda, citas ni roles administrativos en este goal.

Trabaja por checkpoints: red, cambio mínimo, green, revisión de contrato y evidencia. Si la tabla users de una base de referencia no coincide con app_users del esquema S2, detente y documenta la decisión; no renombres tablas ni borres volúmenes automáticamente. Tras 3 intentos con la misma causa, termina BLOCKED con evidencia.
```

## Evidencia

Registrar comando, resultado, commit y limitaciones. El smoke solo debe informar PASS/FAIL y conteos, nunca credenciales o tokens.
