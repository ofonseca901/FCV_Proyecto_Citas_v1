# Proyecto FCV — Sistema ficticio de agendamiento de citas

## Incrementos S2/S3 implementados

El workspace ya incluye backend Spring Boot y frontend React para registro e inicio de sesión, renovaciones JWT y logout. La descripción de plantilla que sigue conserva el contexto original del curso.

Desde PowerShell en esta carpeta: `./scripts/start-s2.ps1`.
Frontend: http://localhost:5173. API health: http://localhost:8080/actuator/health.

El overlay usa el esquema `citas_fcv_training` del volumen existente; consultar [README backend](citas-api/README.md) para otros entornos. No borra volúmenes ni modifica `.env`; el script genera secretos JWT locales en `.env.s2`, ignorado por Git.

Ver [evidencia S2](citas-api/docs/wiki/llm-wiki/wiki/s2-evidencia.md), [wiki](citas-api/docs/wiki/llm-wiki/wiki/index.md) e [historias propuestas](citas-api/docs/wiki/scrum/README.md). Aprobación de historias y procedencia/aprobación visual del frontend pendientes. El Git raíz preexistente se conserva; operar cada repo con `git -C citas-api` o `git -C citas-web`.

## Contexto original del paquete

Plantilla de trabajo para las sesiones **S2 a S6** de la formación de agentes de desarrollo. El paquete deja deliberadamente **vacíos de lógica de negocio** los repositorios `citas-api` y `citas-web`: el objetivo es que cada estudiante los construya con agentes, especificaciones, pruebas y automatización, manteniendo evidencia mediante Git.

> **Importante:** el dominio es académico. Las sedes y algunos nombres de especialidades se apoyan en información pública de FCV; pacientes, profesionales, credenciales, EPS, planes, horarios y citas son datos sintéticos. No representa sistemas ni procesos internos reales de FCV.

## Estructura

```text
FCV_Proyecto_Citas_v1/
├── README.md
├── PRD.md
├── RESTRICCIONES_TECNICAS.md
├── EVIDENCIAS_Y_TRAZABILIDAD.md
├── docker-compose.yml
├── .env.example
├── database/
├── prompts/
├── skills/
├── scripts/
├── citas-api/       # Repo Git 1: Java/Spring Boot, inicialmente sin implementación
└── citas-web/       # Repo Git 2: React o Angular, inicialmente sin implementación
```

## Stack objetivo

### Backend
- Java 21 LTS.
- Spring Boot 3.5.x.
- Maven.
- Arquitectura hexagonal.
- Spring Data JPA.
- Flyway durante la implementación.
- MySQL 8.4 LTS.
- REST/JSON.
- JWT access + refresh token.

### Frontend
El estudiante decide el framework al exportar/continuar desde Stitch y Google AI Studio:
- React + TypeScript, **o**
- Angular + TypeScript.

Node.js 24 LTS es el runtime/toolchain. **No se usa Express ni BFF.** El frontend consume directamente `citas-api` por REST.

## Dos repositorios, un workspace

`citas-api` y `citas-web` son repositorios Git independientes. La carpeta raíz solo los orquesta y permite que Codex/Claude tengan visibilidad de ambos cuando el trabajo sea cross-repo.

La única LLM Wiki global se versionará dentro de:

```text
citas-api/docs/wiki/llm-wiki/
```

## Skills incluidas

En `skills/` se incluyen las dos Skills proporcionadas por el trainer:
- `scrum-spec-orchestrator`: genera épicas, historias, tareas, criterios de aceptación y DoD en `docs/wiki/scrum/` sin implementar código.
- `stitch-design-to-frontend`: guía prototipado, aprobación visual, handoff a Google AI Studio y reconciliación del frontend.

## Flujo de desarrollo esperado

```text
PRD + restricciones
      ↓
scrum-spec-orchestrator
      ↓
Épicas + HU + CA + DoD
      ↓
Stitch → diseño aprobado → Google AI Studio
      ↓
citas-web
      ↕ REST
citas-api
      ↓
MySQL
      ↓
S3 verificación
      ↓
S4 autonomía / goals / loops
      ↓
S5-S6 MCP + n8n
```

## Inicio rápido de infraestructura

1. Instala Docker Desktop y habilita WSL Integration.
2. Desde esta carpeta:

```powershell
Copy-Item .env.example .env
.\scripts\preflight.ps1
```

3. Levanta únicamente MySQL:

```powershell
docker compose up -d mysql
```

4. Comprueba:

```powershell
docker compose ps
.\scripts\db-smoke-test.ps1
```

5. Si quieres disponer también de toolchains Java/Node dentro de contenedores:

```powershell
docker compose --profile dev up -d
```

Estos contenedores **no contienen la aplicación**. Solo montan los repos vacíos y ofrecen Java/Maven y Node para que el estudiante inicialice sus proyectos.

## Inicializar Git

```powershell
.\scripts\init-repos.ps1
```

El script crea en cada repo:
- `main` como rama estable;
- `develop` como rama de trabajo.

La lógica se construye en `develop`. El estudiante fusiona a `main` cuando considere el incremento estable.

## Orden recomendado

1. Lee `PRD.md` y `RESTRICCIONES_TECNICAS.md`.
2. Ejecuta `scrum-spec-orchestrator` desde `citas-api` para producir las épicas/HU/DoD.
3. Ejecuta el prompt del agente orquestador desde la raíz.
4. Genera los agentes de `citas-api` y `citas-web` con los prompts de `prompts/agents/`.
5. Sigue `GUIA_SESIONES_S2_S6.md`.
6. Usa `prompts/goal-loop/` en S2-S4.
7. Versiona los JSON de n8n en `citas-api/automations/n8n/` durante S5-S6.

## Referencias de base de datos

El paquete incluye la solución de referencia `database/reference/db.sql` y su ERD. Para la actividad de normalización, el trainer puede **ocultar temporalmente** esa carpeta y entregar únicamente `database/REQUISITOS_NORMALIZACION_3FN.md`.
