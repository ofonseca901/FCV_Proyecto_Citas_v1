---
id: HU-014
tipo: historia-de-usuario
titulo: Definir duración por especialidad
estado: Pendiente de aprobación
epica: "[[EP-003-disponibilidad-y-agenda]]"
esfuerzo: Bajo
sprint_sugerido: S3
dependencias: ["[[HU-010-administrar-especialidades]]"]
relacionadas: ["[[HU-017-buscar-disponibilidad]]"]
---

# HU-014 — Definir duración por especialidad

## Historia de usuario
**COMO** ADMIN **QUIERO** definir duración de 30 o 60 minutos por especialidad **PARA** que disponibilidad y citas ocupen slots correctos.

## Contexto, alcance y reglas
PRD RF-09/RN-05. Duración pertenece a especialidad, no al profesional: 30 min = un slot y 60 min = dos consecutivos. Excluye duración libre.

## Dependencias y relaciones
- Épica: [[EP-003-disponibilidad-y-agenda]]. Depende de [[HU-010-administrar-especialidades]]; habilita [[HU-017-buscar-disponibilidad]].

## Esfuerzo
**Nivel:** Bajo. Regla localizada con impacto en consumidores posteriores.

## Tareas de desarrollo
- [ ] **T-01 — Atributo normalizado.** Añadir duración válida a especialidad mediante Flyway.
- [ ] **T-02 — Administración/contrato.** Restringir valores a 30/60 y exponerlos al consumidor.
- [ ] **T-03 — Pruebas.** Verificar cálculo de slots en búsqueda y reserva.

## Criterios de aceptación
- **CA-01:** Dado ADMIN, cuando define 30 o 60 para especialidad, entonces el valor queda disponible.
- **CA-02:** Dado valor distinto de 30/60, cuando guarda, entonces se rechaza.
- **CA-03:** Dada especialidad de 60, cuando se busca o reserva, entonces solo se considera una pareja consecutiva de slots.

## Definition of Done
- [ ] CA-01 a CA-03 tienen evidencia en catálogo y cálculo de disponibilidad.
- [ ] Migración/validación preservan 3FN y profesional no puede sobrescribir duración.
- [ ] Contrato y trazabilidad están actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-03 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-09, RN-05; requisitos 3FN. Verificado: 2026-09-17.
