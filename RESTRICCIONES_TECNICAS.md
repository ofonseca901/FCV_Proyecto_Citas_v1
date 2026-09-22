# Restricciones técnicas y Definition of Architecture

## Backend
- Java 21 LTS.
- Spring Boot 3.5.x.
- Maven.
- Arquitectura hexagonal: dominio/aplicación independientes de adaptadores.
- Spring Data JPA para persistencia.
- MySQL 8.4.
- Flyway para migraciones del proyecto construido por el estudiante.
- Spring Security + JWT access/refresh.
- API REST JSON.
- Actuator health recomendado.

## Frontend
- Node.js 24 LTS.
- TypeScript.
- React **o** Angular según selección/exportación de Stitch + Google AI Studio.
- Sin Express y sin BFF.
- REST directo a `citas-api`.
- URL backend configurable por environment.
- El diseño aprobado en Stitch/AI Studio es fuente de verdad visual del estudiante.

## Base de datos
- Normalización mínima: 3FN.
- `database/reference/db.sql` es solución de referencia del trainer.
- Catálogos fijos se cargan por seed.
- Datos sintéticos para profesionales/usuarios/citas.
- Los estudiantes deben justificar claves, cardinalidades y dependencias funcionales.

## Repositorios y Git
Solo dos repos públicos por estudiante:
- `citas-api`
- `citas-web`

Ramas:
- `main`: estable.
- `develop`: trabajo.

Mínimo un commit trazable por sesión S2-S6. No reescribir el historial para ocultar el progreso.

## Documentación
En `citas-api/docs/wiki/`:
- `scrum/`: producida por `scrum-spec-orchestrator`.
- `llm-wiki/`: wiki global mantenida por el agente orquestador.
- contratos/decisiones pueden enlazarse desde ambas.

## Variables de entorno
Nunca subir:
- contraseñas DB;
- secretos JWT;
- OAuth Gmail/n8n;
- tokens MCP;
- credenciales personales.

Cada repo debe contener `.env.example` sin secretos reales.

## Pruebas S3+
Backend: dominio + aplicación + integración REST/persistencia relevante.
Frontend: build/typecheck y pruebas aplicables al framework elegido.
Cross-repo: validar contrato REST en funcionalidades clave.

## Hooks
- pruebas rojas;
- credencial ficticia/patrón de secreto;
- opcional: documentación obligatoria ausente.

## n8n
- Instancia central y desplegada del trainer.
- Cada estudiante configura sus credenciales Google Cloud/Gmail.
- JSON de workflows debe quedar exportado/versionado en `citas-api/automations/n8n/`.
- El trainer debe disponer de MCP operativo contra la instancia n8n.
