# Guía de inicialización del entorno Docker en Linux — Proyecto FCV Citas

## Objetivo

Esta guía explica cómo inicializar y comprobar el entorno Docker del proyecto de agendamiento de citas en equipos Linux usando los archivos ya preparados:

- `.env.example`
- `docker-compose.yml`
- `citas-api/`
- `citas-web/`

El entorno levanta tres servicios:

1. **MySQL 8.4** para la base de datos.
2. **Java 21 + Maven** para el backend.
3. **Node.js 24 + npm** para el frontend.

> El puerto externo de MySQL se utiliza en **3307** para evitar conflictos con instalaciones locales de MySQL que normalmente usan el puerto `3306`.

---

# 1. Ubicarse en la carpeta raíz del proyecto

Abre una terminal y entra a la carpeta raíz del proyecto.

Ejemplo:

```bash
cd ~/Descargas/FCV_Proyecto_Citas
```

La carpeta debe contener al menos:

```text
FCV_Proyecto_Citas/
├── .env.example
├── docker-compose.yml
├── citas-api/
└── citas-web/
```

Puedes comprobarlo con:

```bash
ls -la
```

---

# 2. Duplicar `.env.example`

El archivo `.env.example` sirve como plantilla.

Crea una copia llamada `.env`:

```bash
cp .env.example .env
```

Comprueba que existen ambos archivos:

```bash
ls -la .env*
```

Debes ver:

```text
.env
.env.example
```

## ¿Para qué sirve `.env`?

Docker Compose lee este archivo para obtener valores como:

- nombre de la base de datos;
- usuario de MySQL;
- contraseña de MySQL;
- contraseña root de MySQL;
- puertos;
- secretos JWT.

Ejemplo:

```env
MYSQL_DATABASE=citas
MYSQL_USER=citas_app
MYSQL_PASSWORD=CitasApp_2026!
MYSQL_ROOT_PASSWORD=RootCitas_2026!

MYSQL_PORT=3307

API_PORT=8080
REACT_PORT=5173
ANGULAR_PORT=4200

JWT_ACCESS_SECRET=una_clave_larga_para_access
JWT_REFRESH_SECRET=otra_clave_larga_para_refresh
```

> `.env` es local y no debe subirse a Git.

---

# 3. Diferencia entre puerto 3307 y 3306

El puerto externo del computador Linux es:

```text
3307
```

El puerto interno de MySQL dentro de Docker sigue siendo:

```text
3306
```

La relación es:

```text
Linux
localhost:3307
      ↓
Docker
      ↓
MySQL:3306
```

Por eso en `docker-compose.yml` debe existir una configuración equivalente a:

```yaml
ports:
  - "${MYSQL_PORT:-3307}:3306"
```

Y para el backend, dentro de Docker:

```text
DB_HOST=mysql
DB_PORT=3306
```

No debe utilizarse `3307` para la comunicación interna entre contenedores.

---

# 4. Inicializar los contenedores

Desde la carpeta raíz ejecuta:

```bash
docker compose up -d
```

La primera ejecución puede tardar varios minutos porque Docker debe descargar las imágenes.

Debe descargar y levantar:

```text
mysql:8.4
maven:3.9-eclipse-temurin-21
node:24-alpine
```

Si el equipo Linux no tiene permisos para usar Docker directamente y aparece un error de permisos, usa temporalmente:

```bash
sudo docker compose up -d
```

En los equipos correctamente configurados para el laboratorio, normalmente no será necesario usar `sudo`.

---

# 5. Verificar el estado de los contenedores

Ejecuta:

```bash
docker compose ps
```

Debes obtener tres servicios similares a:

```text
fcv-citas-mysql
fcv-citas-api-dev
fcv-citas-web-dev
```

El resultado esperado es aproximadamente:

```text
fcv-citas-mysql       healthy
fcv-citas-api-dev     Up
fcv-citas-web-dev     Up
```

Para MySQL debes ver algo equivalente a:

```text
0.0.0.0:3307->3306/tcp
```

Esto confirma que:

```text
Puerto Linux: 3307
Puerto MySQL interno: 3306
```

---

# 6. Comprobar Java 21

Ejecuta:

```bash
docker compose exec citas-api-dev java -version
```

Debes obtener una versión Java 21.

Ejemplo esperado:

```text
openjdk version "21.x.x"
```

Comprueba también el compilador:

```bash
docker compose exec citas-api-dev javac -version
```

Debes obtener:

```text
javac 21.x.x
```

---

# 7. Comprobar Maven

Ejecuta:

```bash
docker compose exec citas-api-dev mvn -version
```

Debes obtener algo equivalente a:

```text
Apache Maven 3.9.x
Java version: 21.x.x
```

Si estos tres comandos funcionan:

```bash
docker compose exec citas-api-dev java -version
docker compose exec citas-api-dev javac -version
docker compose exec citas-api-dev mvn -version
```

el entorno backend está correctamente preparado.

---

# 8. Comprobar Node.js

Ejecuta:

```bash
docker compose exec citas-web-dev node --version
```

Debes obtener una versión:

```text
v24.x.x
```

---

# 9. Comprobar npm

Ejecuta:

```bash
docker compose exec citas-web-dev npm --version
```

Debes obtener una versión de npm compatible con Node 24.

Si ambos funcionan:

```bash
docker compose exec citas-web-dev node --version
docker compose exec citas-web-dev npm --version
```

el entorno frontend está preparado.

---

# 10. Comprobar MySQL

Primero verifica que el contenedor esté saludable:

```bash
docker compose ps
```

Debe aparecer:

```text
healthy
```

También puedes comprobar directamente el servidor:

```bash
docker compose exec mysql mysqladmin ping -h 127.0.0.1 -u root -p
```

Cuando solicite contraseña, utiliza el valor definido en:

```env
MYSQL_ROOT_PASSWORD
```

Si funciona, MySQL responderá:

```text
mysqld is alive
```

---

# 11. Entrar a MySQL

Ejecuta:

```bash
docker compose exec mysql mysql -u root -p
```

Introduce la contraseña definida en:

```env
MYSQL_ROOT_PASSWORD
```

Una vez dentro:

```sql
SHOW DATABASES;
```

Si definiste:

```env
MYSQL_DATABASE=citas
```

debería existir una base llamada:

```text
citas
```

Puedes entrar:

```sql
USE citas;
```

Y comprobar las tablas:

```sql
SHOW TABLES;
```

Si todavía no se han creado migraciones, puede aparecer:

```text
Empty set
```

Eso es correcto.

La base se construirá posteriormente desde el backend mediante Flyway.

Para salir:

```sql
exit;
```

---

# 12. Cómo funciona la persistencia

El contenedor MySQL usa un volumen Docker.

Conceptualmente:

```text
fcv-citas-mysql
      │
      ▼
mysql_data
```

Los datos no dependen de que el contenedor permanezca creado.

## Detener y eliminar contenedores sin perder datos

Puedes ejecutar:

```bash
docker compose down
```

Esto elimina:

- contenedores;
- red Docker.

Pero conserva:

- volumen MySQL;
- datos de la base;
- caché Maven;
- caché npm.

Al volver a ejecutar:

```bash
docker compose up -d
```

MySQL reutiliza los datos existentes.

---

# 13. Comprobar que el volumen existe

Ejecuta:

```bash
docker volume ls
```

Deberías ver volúmenes similares a:

```text
fcv-citas-training_mysql_data
fcv-citas-training_maven_cache
fcv-citas-training_node_cache
```

---

# 14. No borrar los datos accidentalmente

No ejecutes:

```bash
docker compose down -v
```

si quieres conservar la base.

El parámetro:

```text
-v
```

elimina los volúmenes.

Por tanto:

```bash
docker compose down
```

es seguro para conservar los datos.

Pero:

```bash
docker compose down -v
```

elimina la base almacenada.

---

# 15. Volver a iniciar el entorno

Si previamente ejecutaste:

```bash
docker compose down
```

vuelve a levantar todo con:

```bash
docker compose up -d
```

Después:

```bash
docker compose ps
```

---

# 16. Ejecutar el backend durante el desarrollo

Cuando `citas-api` ya contenga un proyecto Spring Boot con:

```text
pom.xml
src/
```

puedes compilar:

```bash
docker compose exec citas-api-dev mvn clean compile
```

Ejecutar pruebas:

```bash
docker compose exec citas-api-dev mvn test
```

Empaquetar:

```bash
docker compose exec citas-api-dev mvn clean package
```

Levantar Spring Boot:

```bash
docker compose exec citas-api-dev mvn spring-boot:run
```

La API quedará disponible normalmente en:

```text
http://localhost:8080
```

La terminal permanecerá ocupada mientras Spring Boot esté funcionando.

---

# 17. Ejecutar el frontend durante el desarrollo

Cuando `citas-web` ya contenga el proyecto exportado desde Google AI Studio y exista:

```text
package.json
```

instala las dependencias:

```bash
docker compose exec citas-web-dev npm install
```

Antes de ejecutar el proyecto, revisa los scripts disponibles:

```bash
docker compose exec citas-web-dev npm run
```

---

# 18. Ejecutar React

Si el proyecto utiliza React + Vite, normalmente se ejecuta:

```bash
docker compose exec citas-web-dev npm run dev -- --host 0.0.0.0
```

Luego abre:

```text
http://localhost:5173
```

---

# 19. Ejecutar Angular

Si el proyecto utiliza Angular, normalmente:

```bash
docker compose exec citas-web-dev npm start -- --host 0.0.0.0
```

o:

```bash
docker compose exec citas-web-dev npx ng serve --host 0.0.0.0
```

Luego abre:

```text
http://localhost:4200
```

---

# 20. Flujo recomendado mientras se desarrolla

## Terminal 1 — Entorno Docker

```bash
docker compose up -d
docker compose ps
```

## Terminal 2 — Backend

```bash
docker compose exec citas-api-dev mvn spring-boot:run
```

## Terminal 3 — Frontend React

```bash
docker compose exec citas-web-dev npm run dev -- --host 0.0.0.0
```

o Angular:

```bash
docker compose exec citas-web-dev npm start -- --host 0.0.0.0
```

## Terminal 4 — Pruebas

Backend:

```bash
docker compose exec citas-api-dev mvn test
```

Frontend:

```bash
docker compose exec citas-web-dev npm run build
```

y los tests disponibles en `package.json`.

---

# 21. Comandos esenciales

## Iniciar

```bash
docker compose up -d
```

## Ver estado

```bash
docker compose ps
```

## Ver logs generales

```bash
docker compose logs -f
```

## Ver logs MySQL

```bash
docker compose logs -f mysql
```

## Ver Java

```bash
docker compose exec citas-api-dev java -version
```

## Ver Maven

```bash
docker compose exec citas-api-dev mvn -version
```

## Ver Node

```bash
docker compose exec citas-web-dev node --version
```

## Ver npm

```bash
docker compose exec citas-web-dev npm --version
```

## Detener conservando datos

```bash
docker compose down
```

## Reiniciar todo

```bash
docker compose restart
```

## Eliminar todo incluyendo datos

```bash
docker compose down -v
```

Usar únicamente cuando se quiera comenzar completamente desde cero.

---

# 22. Opcional: entrar directamente a los contenedores

## Backend Java

```bash
docker compose exec citas-api-dev bash
```

Dentro del contenedor puedes ejecutar:

```bash
mvn test
mvn spring-boot:run
```

Para salir:

```bash
exit
```

## Frontend Node

```bash
docker compose exec citas-web-dev sh
```

Dentro del contenedor puedes ejecutar:

```bash
npm install
npm run
npm run dev -- --host 0.0.0.0
```

Para salir:

```bash
exit
```

---

# 23. Nota sobre permisos en archivos Linux

Como las carpetas del proyecto se montan directamente dentro de los contenedores:

```text
./citas-api → /workspace
./citas-web → /workspace
```

los archivos creados desde Docker aparecen también en Linux.

Si en algún equipo aparecen archivos con propietario `root`, se puede corregir desde la carpeta raíz con:

```bash
sudo chown -R "$USER":"$USER" citas-api citas-web
```

Solo ejecutar este comando si realmente aparece un problema de permisos.

---

# 24. Checklist final

Antes de comenzar el desarrollo debes confirmar:

```text
[ ] Existe .env
[ ] MySQL levanta en localhost:3307
[ ] MySQL aparece healthy
[ ] Java 21 funciona
[ ] javac 21 funciona
[ ] Maven funciona
[ ] Node.js 24 funciona
[ ] npm funciona
[ ] citas-api está montado en el contenedor Java
[ ] citas-web está montado en el contenedor Node
```

Comandos finales de comprobación:

```bash
docker compose ps

docker compose exec citas-api-dev java -version
docker compose exec citas-api-dev javac -version
docker compose exec citas-api-dev mvn -version

docker compose exec citas-web-dev node --version
docker compose exec citas-web-dev npm --version
```

Si todos funcionan, el entorno Linux está correctamente inicializado para comenzar el desarrollo del proyecto.
