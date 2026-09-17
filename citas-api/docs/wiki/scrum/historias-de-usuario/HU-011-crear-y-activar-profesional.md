---
id: HU-011
tipo: historia-de-usuario
titulo: Crear y activar profesional
estado: Pendiente de aprobación
epica: "[[EP-002-catalogos-y-profesionales]]"
esfuerzo: Alto
sprint_sugerido: S3
dependencias: ["[[HU-002-gestionar-sesion]]"]
relacionadas: ["[[HU-012-asignar-especialidades-profesional]]", "[[HU-013-asignar-sedes-profesional]]"]
---

# HU-011 — Crear y activar profesional

## Historia de usuario
**COMO** ADMIN **QUIERO** crear y activar/desactivar un PROFESSIONAL sintético **PARA** habilitarlo como prestador de agenda.

## Contexto, alcance y reglas
PRD RF-07. Crea usuario profesional administrativamente, con código y matrícula ficticia únicos. Activación controla disponibilidad futura; no registra profesionales reales. Asignaciones posteriores son HU separadas.

## Dependencias y relaciones
- Épica: [[EP-002-catalogos-y-profesionales]]. Depende de [[HU-002-gestionar-sesion]]; habilita [[HU-012-asignar-especialidades-profesional]] y [[HU-013-asignar-sedes-profesional]].

## Esfuerzo
**Nivel:** Alto. Identidad por rol, datos especializados, estados e integridad.

## Tareas de desarrollo
- [ ] **T-01 — Modelo profesional.** Extender identidad mediante relación normalizada y únicos de código/matrícula.
- [ ] **T-02 — Administración protegida.** Crear, activar/desactivar y evitar alta pública de PROFESSIONAL.
- [ ] **T-03 — Pruebas/UI.** Validar rol, únicos y exclusión de profesionales inactivos.

## Criterios de aceptación
- **CA-01:** Dado ADMIN y datos sintéticos válidos, cuando crea profesional, entonces se asigna rol PROFESSIONAL con código y matrícula únicos.
- **CA-02:** Dado código/matrícula duplicado o dato inválido, cuando crea, entonces se rechaza sin alta parcial.
- **CA-03:** Dado profesional activo, cuando ADMIN lo desactiva, entonces no queda elegible para nueva agenda/reserva.
- **CA-04:** Dado visitante/USER, cuando intenta crear PROFESSIONAL, entonces se deniega.

## Definition of Done
- [ ] CA-01 a CA-04 tienen evidencia de autorización e integridad.
- [ ] Modelo/Flyway justifican especialización de usuario en 3FN; solo datos sintéticos.
- [ ] Contrato, UI y trazabilidad están actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-07; restricciones seguridad/BD; requisitos 3FN. Verificado: 2026-09-17.
