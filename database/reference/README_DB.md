<<<<<<< HEAD
# Base de datos — Sistema de Agendamiento de Citas

## Qué contiene

- `db.sql`: esquema MySQL 8.4 normalizado hasta 3FN + datos seed.
- `ERD_citas_FCV.svg`: diagrama ER vectorial.
- `ERD_citas_FCV.png`: diagrama ER en imagen.
- `erd.mmd`: fuente Mermaid del ERD.

## Criterios de diseño

### Usuarios y seguridad
Se separan `users`, `roles` y `user_roles`. Esto permite USER, PROFESSIONAL y ADMIN sin duplicar datos personales.
JWT no se almacena en claro: el modelo contempla `refresh_tokens` con hash y `password_reset_tokens` para recuperación de contraseña. El envío de email no es obligatorio.

### EPS
`insurance_regimes` → `eps` → `eps_plans` → `user_insurance_affiliations`.
El usuario no repite EPS, régimen y plan en la misma tabla; su afiliación referencia un plan y desde allí se conoce EPS y régimen.

### Profesionales
El profesional es primero un `user` con rol PROFESSIONAL. `professionals` agrega los datos propios del profesional.
Las relaciones con especialidades y sedes son N:M mediante tablas puente.

### Agenda
El profesional crea `availability_blocks`, por ejemplo 08:00–12:00 y 14:00–17:00.
Cada bloque se expande en slots atómicos de 30 minutos. Una especialidad de 30 minutos reserva un slot y una de 60 minutos reserva dos slots consecutivos.

### Citas
Las citas generales usan `Medicina General`, quedan aprobadas automáticamente y no requieren decisión del administrador.
Las citas especializadas quedan `REQUESTED` y requieren aprobación ADMIN.

### Reprogramación
La solicitud de reprogramación vive en una entidad propia y conserva la fecha anterior y la nueva propuesta. Si el administrador rechaza, el usuario puede decidir conservar o cancelar su cita.

### Auditoría
`appointment_status_history` conserva la trazabilidad de cambios de estado.

## Datos reales vs. ficticios

Reales y públicos:
- Hospital Internacional de Colombia (HIC).
- Fundación Cardiovascular de Colombia / Instituto Cardiovascular (ICV).
- Nombres de especialidades/servicios publicados por FCV.

Ficticios:
- usuarios;
- profesionales;
- números de registro profesional;
- EPS y planes de demostración;
- duraciones de citas;
- agendas;
- citas;
- afiliaciones.

## Credencial seed

Todos los usuarios sintéticos usan la misma contraseña de laboratorio:

`Demo1234*`

No debe reutilizarse fuera del laboratorio.
=======
# Base de datos — Sistema de Agendamiento de Citas

## Qué contiene

- `db.sql`: esquema MySQL 8.4 normalizado hasta 3FN + datos seed.
- `ERD_citas_FCV.svg`: diagrama ER vectorial.
- `ERD_citas_FCV.png`: diagrama ER en imagen.
- `erd.mmd`: fuente Mermaid del ERD.

## Criterios de diseño

### Usuarios y seguridad
Se separan `users`, `roles` y `user_roles`. Esto permite USER, PROFESSIONAL y ADMIN sin duplicar datos personales.
JWT no se almacena en claro: el modelo contempla `refresh_tokens` con hash y `password_reset_tokens` para recuperación de contraseña. El envío de email no es obligatorio.

### EPS
`insurance_regimes` → `eps` → `eps_plans` → `user_insurance_affiliations`.
El usuario no repite EPS, régimen y plan en la misma tabla; su afiliación referencia un plan y desde allí se conoce EPS y régimen.

### Profesionales
El profesional es primero un `user` con rol PROFESSIONAL. `professionals` agrega los datos propios del profesional.
Las relaciones con especialidades y sedes son N:M mediante tablas puente.

### Agenda
El profesional crea `availability_blocks`, por ejemplo 08:00–12:00 y 14:00–17:00.
Cada bloque se expande en slots atómicos de 30 minutos. Una especialidad de 30 minutos reserva un slot y una de 60 minutos reserva dos slots consecutivos.

### Citas
Las citas generales usan `Medicina General`, quedan aprobadas automáticamente y no requieren decisión del administrador.
Las citas especializadas quedan `REQUESTED` y requieren aprobación ADMIN.

### Reprogramación
La solicitud de reprogramación vive en una entidad propia y conserva la fecha anterior y la nueva propuesta. Si el administrador rechaza, el usuario puede decidir conservar o cancelar su cita.

### Auditoría
`appointment_status_history` conserva la trazabilidad de cambios de estado.

## Datos reales vs. ficticios

Reales y públicos:
- Hospital Internacional de Colombia (HIC).
- Fundación Cardiovascular de Colombia / Instituto Cardiovascular (ICV).
- Nombres de especialidades/servicios publicados por FCV.

Ficticios:
- usuarios;
- profesionales;
- números de registro profesional;
- EPS y planes de demostración;
- duraciones de citas;
- agendas;
- citas;
- afiliaciones.

## Credencial seed

Todos los usuarios sintéticos usan la misma contraseña de laboratorio:

`Demo1234*`

No debe reutilizarse fuera del laboratorio.
>>>>>>> d8afae289e730587ae3ff67961e2196e8aa79fb3
