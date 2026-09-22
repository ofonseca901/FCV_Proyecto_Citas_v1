<<<<<<< HEAD
# Stack y criterios técnicos de referencia

## Frontend

### Opción por defecto
- React
- Vite
- TypeScript
- Tailwind CSS
- Node.js como runtime/tooling de frontend

### Alternativa soportada
- Angular
- TypeScript
- Tailwind CSS
- Node.js como runtime/tooling

Antes de generar tareas técnicas, detectar el framework real del repositorio. Si un proyecto nuevo no especifica React o Angular, usar React + Vite como referencia por defecto y declararlo como supuesto.

## Backend

- Java
- Spring Boot
- Maven
- Flyway

No cambiar Maven por Gradle ni introducir frameworks alternativos salvo requerimiento explícito o evidencia del repositorio que obligue a documentar el estado real.

## Base de datos

Soportadas:
- MySQL
- SQL Server

Si el repositorio ya existe, detectar la base configurada. En un proyecto nuevo sin decisión de base de datos, no inventar requisitos que dependan de características exclusivas de un motor. Puede sugerirse MySQL como opción inicial si se necesita una decisión operativa, dejando el supuesto documentado.

## Uso del stack en las historias

La HU describe comportamiento y valor; las tareas pueden indicar las capas afectadas, por ejemplo:
- componente/vista frontend;
- contrato HTTP o DTO;
- controlador/servicio/repositorio de Spring Boot;
- validaciones de dominio;
- migración Flyway si cambia el esquema;
- pruebas aplicables.

No prescribir nombres de clases, endpoints, tablas o componentes sin evidencia o necesidad funcional suficiente.

## Flyway

Una HU que requiera un cambio de esquema debe incluir en su DoD la presencia de una migración Flyway coherente. La skill puede verificar que exista y leerla; nunca debe crearla, editarla ni ejecutar una migración sobre una base de datos.
=======
# Stack y criterios técnicos de referencia

## Frontend

### Opción por defecto
- React
- Vite
- TypeScript
- Tailwind CSS
- Node.js como runtime/tooling de frontend

### Alternativa soportada
- Angular
- TypeScript
- Tailwind CSS
- Node.js como runtime/tooling

Antes de generar tareas técnicas, detectar el framework real del repositorio. Si un proyecto nuevo no especifica React o Angular, usar React + Vite como referencia por defecto y declararlo como supuesto.

## Backend

- Java
- Spring Boot
- Maven
- Flyway

No cambiar Maven por Gradle ni introducir frameworks alternativos salvo requerimiento explícito o evidencia del repositorio que obligue a documentar el estado real.

## Base de datos

Soportadas:
- MySQL
- SQL Server

Si el repositorio ya existe, detectar la base configurada. En un proyecto nuevo sin decisión de base de datos, no inventar requisitos que dependan de características exclusivas de un motor. Puede sugerirse MySQL como opción inicial si se necesita una decisión operativa, dejando el supuesto documentado.

## Uso del stack en las historias

La HU describe comportamiento y valor; las tareas pueden indicar las capas afectadas, por ejemplo:
- componente/vista frontend;
- contrato HTTP o DTO;
- controlador/servicio/repositorio de Spring Boot;
- validaciones de dominio;
- migración Flyway si cambia el esquema;
- pruebas aplicables.

No prescribir nombres de clases, endpoints, tablas o componentes sin evidencia o necesidad funcional suficiente.

## Flyway

Una HU que requiera un cambio de esquema debe incluir en su DoD la presencia de una migración Flyway coherente. La skill puede verificar que exista y leerla; nunca debe crearla, editarla ni ejecutar una migración sobre una base de datos.
>>>>>>> d8afae289e730587ae3ff67961e2196e8aa79fb3
