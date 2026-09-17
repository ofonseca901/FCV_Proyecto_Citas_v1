---
id: EP-001
tipo: epica
titulo: Acceso y perfil
estado: Pendiente de aprobación
historias: ["[[HU-001-registrar-usuario]]", "[[HU-002-gestionar-sesion]]", "[[HU-003-interfaz-de-acceso]]", "[[HU-004-recuperar-contrasena]]", "[[HU-005-gestionar-perfil]]", "[[HU-006-gestionar-afiliacion]]"]
dependencias: []
---

# EP-001 — Acceso y perfil

## Objetivo y valor
Permitir que un paciente ficticio obtenga y mantenga una identidad segura para usar el producto.

## Actores, alcance y fuera de alcance
- Actores: visitante y USER.
- Alcance: registro, sesión JWT, interfaz de acceso, recuperación, perfil y afiliación.
- Fuera de alcance: creación pública de ADMIN/PROFESSIONAL, citas y catálogos administrables.

## Reglas de negocio
- Email y documento son únicos; las contraseñas se guardan con hash adaptativo.
- El registro público solo otorga `USER`; autorización, ownership y CORS son explícitos.
- Una afiliación no duplica EPS, régimen o plan dentro del usuario.

## Dependencias
- Habilita [[EP-002-catalogos-y-profesionales]] y todas las épicas de citas.

## Historias de usuario
- [[HU-001-registrar-usuario]]
- [[HU-002-gestionar-sesion]]
- [[HU-003-interfaz-de-acceso]]
- [[HU-004-recuperar-contrasena]]
- [[HU-005-gestionar-perfil]]
- [[HU-006-gestionar-afiliacion]]

## Criterio de completitud
- [ ] Todas sus HU obligatorias están `Completada` con CA y DoD evidenciados.
- [ ] Persistencia de identidad y afiliación justificada hasta 3FN.

## Riesgos e incógnitas
- La decisión visual y el framework web final aún no están aprobados.
