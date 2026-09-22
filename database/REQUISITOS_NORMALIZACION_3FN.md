# Actividad — Diseñar y normalizar la base de datos hasta 3FN

> Entregar este documento al estudiante **antes** de mostrar `database/reference/`.

## Objetivo
Diseñar el modelo relacional del PRD y justificar su normalización hasta Tercera Forma Normal (3FN).

## Entidades/capacidades que el modelo debe soportar

Sin imponer tablas exactas, el diseño debe representar:
- usuarios y múltiples roles;
- profesionales como usuarios especializados;
- especialidades N:M con profesionales;
- sedes N:M con profesionales;
- EPS, régimen, plan y afiliación del usuario;
- bloques de disponibilidad por profesional/sede/fecha;
- citas de 30/60 minutos;
- estados de cita;
- historial de estados;
- solicitudes de reprogramación y su estado;
- access/refresh/password reset de forma compatible con el PRD.

## Requisitos de 1FN
- atributos atómicos;
- sin listas en columnas (`especialidades='1,2,3'` no es válido);
- grupos repetidos separados en tablas/relaciones.

## Requisitos de 2FN
- en tablas con PK compuesta, todo atributo no clave debe depender de la clave completa;
- las relaciones N:M deben resolverse con tablas puente cuando corresponda.

## Requisitos de 3FN
- ningún atributo no clave debe depender transitivamente de otra columna no clave;
- no repetir `eps_name`, `regime_name`, `plan_name` dentro de usuario/appointment;
- no repetir `specialty_name` dentro del profesional o cita cuando existe un catálogo;
- los estados deben modelarse de forma coherente y evitar textos divergentes.

## Decisiones que debe justificar el estudiante
1. Claves primarias y únicas.
2. Cardinalidades.
3. Qué catálogos son fijos/configurables.
4. Cómo evita doble reserva.
5. Cómo representa citas de 60 minutos.
6. Cómo evita perder la cita original mientras una reprogramación está pendiente.
7. Cómo registra auditoría de estados.
8. Qué datos se almacenan como snapshot y cuáles por FK.
9. Qué índices necesitaría para consultas de agenda.

## Entregables
- diagrama ER;
- lista de dependencias funcionales relevantes;
- explicación breve 1FN → 2FN → 3FN;
- SQL inicial propio;
- comparación posterior contra el modelo de referencia del trainer.
