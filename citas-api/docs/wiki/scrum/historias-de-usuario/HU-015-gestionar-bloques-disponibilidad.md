---
id: HU-015
tipo: historia-de-usuario
titulo: Gestionar bloques de disponibilidad
estado: Pendiente de aprobación
epica: "[[EP-003-disponibilidad-y-agenda]]"
esfuerzo: Alto
sprint_sugerido: S3
dependencias: ["[[HU-011-crear-y-activar-profesional]]", "[[HU-013-asignar-sedes-profesional]]"]
relacionadas: ["[[HU-016-consultar-calendario-profesional]]", "[[HU-017-buscar-disponibilidad]]"]
---

# HU-015 — Gestionar bloques de disponibilidad

## Historia de usuario
**COMO** PROFESSIONAL **QUIERO** crear, editar y eliminar mis bloques futuros por sede **PARA** publicar mi disponibilidad.

## Contexto, alcance y reglas
PRD RF-08/RN-06/RN-07. Múltiples bloques por día, en slots de 30 min; no pasado ni solapamiento. Solo sede asignada. No se edita/elimina futuro con citas comprometidas; excluye reserva.

## Dependencias y relaciones
- Épica: [[EP-003-disponibilidad-y-agenda]]. Depende de [[HU-011-crear-y-activar-profesional]] y [[HU-013-asignar-sedes-profesional]].

## Esfuerzo
**Nivel:** Alto. Reglas temporales, solapamiento, ownership y datos de agenda.

## Tareas de desarrollo
- [ ] **T-01 — Modelo de bloques.** Diseñar fecha/intervalo/sede/profesional en 3FN con índices de consulta y migración.
- [ ] **T-02 — Caso de uso.** Validar ownership, futuro, sede y no solapamiento.
- [ ] **T-03 — UI/calendario y pruebas.** Cubrir crear, editar, borrar y cita comprometida.

## Criterios de aceptación
- **CA-01:** Dado profesional activo y sede asignada, cuando crea bloque futuro válido, entonces queda disponible en intervalos de 30 min.
- **CA-02:** Dado bloque pasado, solapado o sede no asignada, cuando guarda, entonces se rechaza.
- **CA-03:** Dado bloque futuro sin cita comprometida, cuando lo edita/elimina, entonces el calendario se actualiza.
- **CA-04:** Dado bloque con cita comprometida, cuando intenta editar/eliminar, entonces se impide.
- **CA-05:** Dado otro profesional, cuando intenta gestionar bloque ajeno, entonces se deniega.

## Definition of Done
- [ ] CA-01 a CA-05 tienen evidencia de reglas temporales, conflicto y ownership.
- [ ] Esquema/Flyway/índices justifican 3FN y consulta de agenda.
- [ ] API, UI y contrato quedan alineados sin datos reales.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-05 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-08, RN-06/RN-07; requisitos 3FN e índices. Verificado: 2026-09-17.
