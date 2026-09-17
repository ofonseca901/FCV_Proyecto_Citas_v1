---
id: EP-001
tipo: epica
titulo: Acceso de usuarios
estado: Pendiente de aprobación
historias:
  - "[[HU-001-registrar-usuario]]"
  - "[[HU-002-gestionar-sesion]]"
  - "[[HU-003-interfaz-de-acceso]]"
dependencias: []
---

# EP-001 — Acceso de usuarios

## Objetivo y valor
Permitir a un paciente ficticio crear su cuenta y acceder de forma autenticada al primer incremento S2.

## Actores
Visitante y USER. ADMIN y PROFESSIONAL no se crean por registro público.

## Alcance
Registro, login, tokens access/refresh, logout, consulta de identidad e interfaz de acceso.

## Fuera de alcance
Recuperación de contraseña, citas y administración.

## Reglas y dependencias
Email y documento únicos, hash de contraseña adaptativo, rol USER asignado en servidor, validación de datos y separación entre access y refresh. Requiere MySQL y toolchains del stack objetivo.

## Historias de usuario
- [[HU-001-registrar-usuario]]
- [[HU-002-gestionar-sesion]]
- [[HU-003-interfaz-de-acceso]]

## Criterio de completitud
- [ ] Historias obligatorias completadas con evidencia de sus CA y DoD.
- [ ] Sin dependencias bloqueantes para ejecutar el incremento.

## Riesgos e incógnitas
Docker no respondía durante la inspección inicial. No hay frontend exportado. Aprobación documental pendiente.
