# citas-api — incremento S2

Backend académico ejecutable: registro USER, login JWT, refresh con rotación, logout y consulta de identidad. Java 21, Spring Boot 3.5.16, Maven, arquitectura hexagonal, JPA, MySQL 8.4 y Flyway. Sin pacientes ni profesionales reales.

## Ejecutar en este workspace
Desde la raíz:

```powershell
./scripts/start-s2.ps1
docker compose logs -f citas-api-dev
```

El script genera dos secretos JWT aleatorios en `.env.s2` (ignorado por Git) solo si el archivo no existe. El overlay S2 conecta al esquema `citas_fcv_training`, disponible en el volumen existente, e inicia la aplicación. Conserva `.env` y datos previos. En un entorno nuevo, configurar las variables del ejemplo raíz y asegurar que `MYSQL_DATABASE` y `DB_NAME` coincidan con ese esquema o ajustar el overlay antes de iniciar.

Comando equivalente tras generar secretos: `docker compose --env-file .env --env-file .env.s2 -f docker-compose.yml -f docker-compose.s2.yml up -d`. Usar siempre el script/overlay para iniciar; `docker compose up` sin overlay vuelve al contenedor de herramientas de la plantilla.

Health: http://localhost:8080/actuator/health. No hay usuarios ni contraseñas preconfigurados: registrarse con datos ficticios.

## Ejecutar fuera de Docker
Requiere Java 21 y Maven. Exportar las variables de `.env.example` en el proceso: Spring no carga automáticamente ese archivo. Crear una BD vacía accesible con la cuenta de aplicación. Definir dos secretos JWT diferentes de al menos 32 bytes, sin usar placeholders.

```text
mvn spring-boot:run
```

## Verificación
```powershell
docker compose exec -T citas-api-dev mvn -B -ntp verify
node citas-api/scripts/smoke-auth.mjs
```

El primer comando ejecuta pruebas de integración con H2 aislado; no escribe en MySQL. El segundo requiere API activa y realiza el flujo HTTP contra su BD real; crea una cuenta sintética nueva por ejecución y revoca la sesión al terminar. No imprime secretos. Configurar `API_BASE_URL` si cambia el puerto.

## Documentación
- [Contrato REST](docs/wiki/llm-wiki/wiki/contrato-auth.md)
- [Modelo 3FN inicial](docs/wiki/llm-wiki/wiki/modelo-datos.md)
- [Wiki global](docs/wiki/llm-wiki/wiki/index.md)
- [Historias S2](docs/wiki/scrum/README.md)
- [Evidencias y pendientes](docs/wiki/llm-wiki/wiki/s2-evidencia.md)

Agenda, citas, recuperación de contraseña y automatizaciones pertenecen a siguientes incrementos. Las HU y el diseño no se consideran aprobados sin confirmación del usuario.
