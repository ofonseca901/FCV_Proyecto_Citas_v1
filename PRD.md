# PRD — Sistema ficticio de Agendamiento de Citas

**Versión:** 1.0 · **Uso:** laboratorio FCV · **Fuente de especificación:** este PRD + restricciones técnicas + modelo de datos de referencia.

## 1. Objetivo

Construir una aplicación web completa de agendamiento de citas que permita practicar desarrollo asistido por agentes, Spec-Driven Development, verificación automatizada, ciclos autónomos y automatizaciones n8n, sin utilizar sistemas, profesionales, pacientes, credenciales o datos privados reales de FCV.

## 2. Actores

### USER
Usuario final/paciente ficticio. Puede registrarse por sí mismo.

### PROFESSIONAL
Profesional ficticio creado por ADMIN. Puede pertenecer a una o varias especialidades y trabajar en una o ambas sedes. No aprueba citas; gestiona su disponibilidad y consulta su agenda.

### ADMIN
Administra profesionales, catálogos configurables, solicitudes especializadas y reprogramaciones.

## 3. Sedes fijas

1. **Hospital Internacional de Colombia (HIC)** — Km 7 Autopista Bucaramanga–Piedecuesta, Valle de Menzulí, Santander.
2. **Fundación Cardiovascular de Colombia / Instituto Cardiovascular (ICV)** — Calle 155A No. 23-58, Urbanización El Bosque, Floridablanca, Santander.

Estas sedes se consideran catálogo fijo del laboratorio.

## 4. Alcance funcional

### RF-01 · Registro de usuario
- Un visitante puede crear una cuenta `USER`.
- Datos mínimos: nombres, apellidos, tipo/número de documento, email, teléfono, contraseña.
- Email y documento deben ser únicos.
- Contraseñas nunca se almacenan en texto plano.

### RF-02 · Login y sesión JWT
- Login por email + contraseña.
- Emitir access token de corta duración y refresh token.
- Permitir refresh y revocación/logout.
- Los roles deben formar parte del contexto de autorización.

### RF-03 · Recuperación de contraseña
- Solicitar recuperación por email.
- Crear token temporal y de un solo uso.
- El envío real de correo es opcional: en desarrollo puede exponerse de forma segura en log/respuesta controlada para completar el ejercicio.
- Cambiar contraseña invalida/consume el token.

### RF-04 · Perfil y afiliación
- USER consulta/actualiza datos permitidos de su perfil.
- Puede asociar su EPS/plan/régimen mediante una afiliación.
- La aplicación debe evitar duplicar EPS, régimen y plan dentro del usuario.

### RF-05 · Catálogos fijos
Precargados y de solo lectura:
- roles;
- estados de cita;
- estados de reprogramación;
- regímenes;
- sedes.

### RF-06 · Catálogos configurables
ADMIN puede gestionar mediante CRUD:
- EPS;
- planes de EPS;
- especialidades.

No se permite borrar físicamente un catálogo referenciado por transacciones; se debe usar activación/desactivación cuando aplique.

### RF-07 · Gestión de profesionales
ADMIN puede:
- crear el usuario PROFESSIONAL;
- registrar código profesional y matrícula ficticia;
- asignar una o varias especialidades;
- marcar una especialidad primaria;
- asignar una o ambas sedes;
- activar/desactivar al profesional.

Los nombres y matrículas del laboratorio son sintéticos.

### RF-08 · Agenda del profesional
PROFESSIONAL puede:
- crear múltiples bloques por día;
- seleccionar sede por bloque;
- editar/eliminar bloques futuros que no tengan citas comprometidas;
- consultar su calendario.

Ejemplo de un día válido:
- 08:00–12:00 HIC;
- 14:00–17:00 HIC.

Reglas:
- no crear bloques en el pasado;
- no solapar bloques del mismo profesional;
- el profesional debe estar habilitado en la sede;
- el bloque se discretiza en slots de 30 min.

### RF-09 · Duración por especialidad
Cada especialidad define 30 o 60 minutos.
- 30 min = 1 slot.
- 60 min = 2 slots consecutivos disponibles.

El profesional no sobrescribe la duración de la especialidad.

### RF-10 · Consulta de disponibilidad
USER puede filtrar por:
- sede;
- tipo de cita general/especializada;
- especialidad;
- profesional;
- fecha.

Solo se muestran horarios que puedan completar toda la duración requerida.

### RF-11 · Cita general
- El usuario selecciona `Medicina General`.
- Debe escoger uno de los profesionales generales disponibles.
- Si el horario sigue disponible al confirmar, la cita se crea en estado `APPROVED` automáticamente.
- No requiere intervención del ADMIN.

### RF-12 · Cita especializada
- USER selecciona especialidad, sede, profesional y horario.
- La solicitud nace en `REQUESTED`.
- El horario queda retenido para evitar doble reserva.
- ADMIN puede aprobar o rechazar.
- Rechazo exige motivo.
- Al aprobar: estado `APPROVED`.
- Al rechazar: estado `REJECTED` y se liberan los slots.

### RF-13 · Mis citas
USER puede consultar sus citas y filtrar por estado/fecha.
Debe visualizar como mínimo:
- sede;
- profesional;
- especialidad;
- fecha/hora;
- duración;
- estado;
- motivo de rechazo cuando exista.

### RF-14 · Cancelación
USER puede cancelar una cita futura no terminal.
- `CANCELLED` libera los slots.
- Una cita cancelada no se reactiva directamente.
- Debe registrarse historial del cambio.

### RF-15 · Reprogramación
- Solo una cita aprobada y futura puede solicitar reprogramación.
- La reprogramación conserva profesional y especialidad; cambiar profesional se trata como nueva cita.
- USER selecciona nueva fecha/hora disponible.
- La nueva franja se retiene mientras la solicitud está `PENDING`.
- La cita original conserva su franja hasta que ADMIN decida.
- ADMIN aprueba/rechaza con motivo cuando corresponda.
- Si aprueba: libera slots antiguos, asigna nuevos y actualiza la cita.
- Si rechaza: libera la nueva reserva provisional y mantiene la cita original.
- Después de rechazo, USER puede conservar la cita o cancelarla.

### RF-16 · Agenda visible al profesional
PROFESSIONAL puede consultar sus citas `APPROVED` por día/semana y sede.
No puede ver datos de usuarios fuera de sus propias citas.

### RF-17 · Cierre de atención
PROFESSIONAL puede marcar una cita pasada/aplicable como:
- `COMPLETED`, o
- `NO_SHOW`.

Debe registrarse historial.

### RF-18 · Bandeja administrativa
ADMIN ve:
- citas especializadas `REQUESTED`;
- reprogramaciones `PENDING`;
- filtros por sede, profesional, especialidad y fecha.

### RF-19 · Auditoría de estados
Todo cambio de estado de cita guarda:
- cita;
- estado nuevo;
- actor cuando existe;
- fuente `SYSTEM`, `USER` o `ADMIN`;
- fecha/hora;
- motivo opcional.

### RF-20 · REST entre frontend y backend
El frontend consume directamente una API REST de Spring Boot. No existe Express/BFF.
El contrato debe diseñarse y documentarse durante el proyecto.

## 5. Reglas de negocio esenciales

- RN-01: ninguna cita puede ocupar slots ya reservados/retenidos.
- RN-02: citas generales se aprueban automáticamente.
- RN-03: citas especializadas requieren ADMIN.
- RN-04: rechazo administrativo requiere motivo.
- RN-05: slots deben ser consecutivos cuando la duración es 60 min.
- RN-06: no se permiten citas ni bloques en el pasado.
- RN-07: un profesional solo publica agenda en sedes asignadas.
- RN-08: una especialidad debe estar activa y asociada al profesional para reservarse.
- RN-09: cancelar/rechazar libera reservas correspondientes.
- RN-10: la reprogramación no destruye la cita anterior hasta que sea aprobada.
- RN-11: transiciones de estado deben ser explícitas y verificables.
- RN-12: datos de auditoría no se modifican como CRUD normal.

## 6. Pantallas obligatorias; estética libre

El estudiante define estética con Stitch/AI Studio, pero el producto final debe cubrir:
- registro;
- login;
- recuperación/cambio de contraseña;
- home/dashboard USER;
- buscar disponibilidad;
- solicitar cita;
- mis citas/detalle;
- solicitar reprogramación;
- dashboard PROFESSIONAL;
- gestionar bloques/calendario;
- agenda del profesional;
- dashboard ADMIN;
- aprobar/rechazar citas;
- aprobar/rechazar reprogramaciones;
- CRUD de profesionales;
- CRUD de especialidades;
- CRUD EPS/planes.

## 7. Datos

La BD debe diseñarse/explicarse hasta 3FN. El estudiante recibe requisitos de normalización y posteriormente el trainer puede mostrar el modelo de referencia.

## 8. Seguridad mínima obligatoria

- passwords con hash adaptativo (BCrypt/Argon2 compatible con Spring Security);
- secretos únicamente por `.env`/variables de entorno;
- access/refresh JWT separados;
- autorización por rol y ownership;
- CORS explícito;
- validación server-side;
- nunca usar información real de pacientes/profesionales de FCV;
- evitar logging de passwords/tokens.

## 9. Fuera de alcance

- historia clínica;
- facturación real;
- pagos;
- diagnósticos/tratamientos;
- datos reales de FCV;
- integración real con sistemas clínicos;
- CI/CD obligatorio;
- SMS/WhatsApp;
- SMTP obligatorio para recuperación.

## 10. Automatización posterior

S5/S6 agregan n8n sin cambiar el núcleo funcional:
1. recordatorios de citas próximas por Gmail;
2. notificación de cambio de estado por webhook + Gmail;
3. resumen operativo diario por sede/estado como caso adicional.
