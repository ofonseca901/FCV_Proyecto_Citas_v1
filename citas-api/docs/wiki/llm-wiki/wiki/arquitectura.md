# Arquitectura y decisiones S2

## Hechos
- Backend Java 21 en contenedor Maven; Java del host es 26 y no se usa para compilar.
- Spring Boot 3.5.16; selección dentro del rango 3.5.x obligatorio. [Compatibilidad oficial](https://docs.spring.io/spring-boot/3.5/system-requirements.html).
- Dominio y aplicación usan Java puro. Adaptadores Spring implementan persistencia, REST y seguridad; fachada delimita transacciones.
- MySQL 8.4; Flyway gestiona esquema y JPA valida, sin crear tablas automáticamente.
- El volumen previo tiene el esquema `citas_fcv_training`; `.env` refería otro nombre. Overlay S2 usa el esquema accesible sin alterar datos/credenciales. `scripts/start-s2.ps1` genera claves JWT independientes en `.env.s2`, ignorado por Git.
- React/TypeScript/Vite consume REST directamente.

## Decisiones de implementación
- BCrypt coste 12, contraseña 8 caracteres mínimo y 72 bytes UTF-8 máximo.
- Email normalizado a minúsculas, documento único por tipo+número. Tipos iniciales CC/CE/TI/PA/PPT, decisión S2 ajustable antes de ampliar el dominio.
- JWT HS256, issuer y audience explícitos, claves separadas. Access 15 minutos; refresh con límite absoluto de 7 días por defecto.
- Refresh rota su identificador mediante UPDATE condicional. Solo un solicitante concurrente puede renovarlo.
- Cada request autenticado comprueba sesión y usuario activo en BD; logout revoca access y refresh de la sesión, otras sesiones permanecen independientes.
- Tokens del navegador únicamente en memoria; una recarga requiere login. Cliente renueva antes de expirar. No hay cookies de autenticación y se desactiva CSRF para REST Bearer, con CORS explícito.
- No se persisten JWT completos ni contraseñas de prueba en documentación.

## Límites
- Frontend creado localmente como base revisable; no existe evidencia de exportación Stitch/AI Studio ni aprobación visual.
- Git raíz ya existía. Se preservó; los dos repos de aplicación son independientes y deben operarse con `git -C`.
- S2 no incluye recuperación de contraseña, agenda, roles administrativos en UI, rate limiting, envío de correos ni despliegue público.
- Google Fonts es opcional para presentación; fuentes de sistema sirven como respaldo si no hay conexión.
