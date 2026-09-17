---
id: HU-017
tipo: historia-de-usuario
titulo: Buscar disponibilidad
estado: Pendiente de aprobación
epica: "[[EP-003-disponibilidad-y-agenda]]"
esfuerzo: Alto
sprint_sugerido: S3
dependencias: ["[[HU-012-asignar-especialidades-profesional]]", "[[HU-013-asignar-sedes-profesional]]", "[[HU-014-definir-duracion-especialidad]]", "[[HU-015-gestionar-bloques-disponibilidad]]"]
relacionadas: ["[[HU-018-reservar-cita-general]]", "[[HU-021-solicitar-cita-especializada]]"]
---

# HU-017 — Buscar disponibilidad

## Historia de usuario
**COMO** USER **QUIERO** filtrar horarios disponibles por sede, tipo, especialidad, profesional y fecha **PARA** elegir una franja reservable.

## Contexto, alcance y reglas
PRD RF-10/RN-05/RN-08. Devuelve solo especialidad activa, profesional activo/asociado y slots suficientes/consecutivos según duración. No crea retención ni cita.

## Dependencias y relaciones
- Épica: [[EP-003-disponibilidad-y-agenda]]. Depende de las asignaciones, duración y bloques de agenda.

## Esfuerzo
**Nivel:** Alto. Consulta combinada, reglas de elegibilidad/duración y proyección web.

## Tareas de desarrollo
- [ ] **T-01 — Consulta de disponibilidad.** Diseñar proyección e índices sin duplicar catálogos.
- [ ] **T-02 — Filtros/validaciones.** Aplicar sede, tipo, especialidad, profesional, fecha y consecutividad.
- [ ] **T-03 — Interfaz y pruebas.** Mostrar solo franjas elegibles y cubrir 30/60 min.

## Criterios de aceptación
- **CA-01:** Dado filtro válido, cuando USER busca, entonces ve solo profesionales/sedes/especialidades activos y asociados.
- **CA-02:** Dada especialidad de 30/60 min, cuando busca, entonces ve uno/dos slots consecutivos completos, respectivamente.
- **CA-03:** Dado bloque ocupado, retenido, incompleto o pasado, cuando busca, entonces no se ofrece.
- **CA-04:** Dado combinación sin resultados, cuando busca, entonces obtiene respuesta vacía clara sin datos ajenos.

## Definition of Done
- [ ] CA-01 a CA-04 tienen pruebas de consulta y evidencia visual aplicable.
- [ ] Índices, relaciones y cálculo preservan 3FN; contrato es consumible por web directo.
- [ ] Trazabilidad con reserva general/especializada está actualizada.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-10, RN-01/RN-05/RN-08; requisitos de índices/3FN. Verificado: 2026-09-17.
