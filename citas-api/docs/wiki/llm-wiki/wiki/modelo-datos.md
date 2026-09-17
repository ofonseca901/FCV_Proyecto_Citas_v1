# Modelo inicial propio — autenticación S2

Migración: `src/main/resources/db/migration/V1__identity_and_sessions.sql`. Se implementa el subconjunto de identidad, no todo el esquema de citas del PRD.

```mermaid
erDiagram
    APP_USERS ||--o{ USER_ROLES : tiene
    ROLES ||--o{ USER_ROLES : asignado
    APP_USERS ||--o{ AUTH_SESSIONS : inicia
    APP_USERS {
        bigint id PK
        varchar email UK
        varchar document_type
        varchar document_number
        varchar password_hash
        boolean active
    }
    ROLES {
        varchar code PK
    }
    USER_ROLES {
        bigint user_id PK,FK
        varchar role_code PK,FK
    }
    AUTH_SESSIONS {
        varchar id PK
        bigint user_id FK
        varchar refresh_id
        timestamp expires_at
        boolean revoked
    }
```

## Claves y dependencias funcionales
`app_users.id → first_name, last_name, document_type, document_number, email, phone, password_hash, active`.
Claves candidatas adicionales: email; par (document_type, document_number). La misma cifra puede identificar documentos de tipos distintos.

`roles.code` es el identificador estable del catálogo fijo USER/PROFESSIONAL/ADMIN.
`user_roles(user_id, role_code)` es clave compuesta sin atributos no clave.
`auth_sessions.id → user_id, refresh_id, expires_at, revoked`.

## Normalización
1FN: atributos atómicos; roles en relación N:M, no listas en la tabla de usuario.
2FN: todos los atributos dependen de la clave completa; tabla puente sin dependencias parciales.
3FN: no se repiten nombres de roles, EPS, planes o sedes en usuario/sesión; no hay dependencias transitivas entre atributos no clave de este subconjunto.

FK preservan integridad; índices únicos hacen cumplir duplicados incluso con solicitudes simultáneas. Índices de sesiones por usuario y vencimiento permiten consulta/revocación y futura depuración. Rotación refresh usa condición id+refresh_id+vigencia, no selección seguida de escritura incondicional.

No se almacenan access/refresh completos. `refresh_id` solo identifica el JWT firmado; por sí solo no autentica. La clave criptográfica permanece fuera de BD.

## Pendiente de siguientes incrementos
Modelo de EPS/afiliación, sedes, profesionales, slots, reservas y auditoría. Decisiones de doble reserva y reprogramación se desarrollan con sus HU. Comparación contra modelo del trainer pendiente; no afirmar normalización completa del dominio con este esquema parcial.
