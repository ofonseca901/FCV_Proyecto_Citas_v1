---
id: HU-013
tipo: historia-de-usuario
titulo: Asignar sedes al profesional
estado: Pendiente de aprobación
epica: "[[EP-002-catalogos-y-profesionales]]"
esfuerzo: Medio
sprint_sugerido: S3
dependencias: ["[[HU-007-consultar-catalogos-fijos]]", "[[HU-011-crear-y-activar-profesional]]"]
relacionadas: ["[[HU-015-gestionar-bloques-disponibilidad]]"]
---

# HU-013 — Asignar sedes al profesional

## Historia de usuario
**COMO** ADMIN **QUIERO** asignar una o ambas sedes fijas a un PROFESSIONAL **PARA** limitar dónde publica agenda.

## Contexto, alcance y reglas
PRD RF-07/RN-07. Relación N:M entre profesional y HIC/ICV; no hay sedes nuevas. Solo una sede asignada permite crear bloque o reservar allí.

## Dependencias y relaciones
- Épica: [[EP-002-catalogos-y-profesionales]]. Depende de [[HU-007-consultar-catalogos-fijos]] y [[HU-011-crear-y-activar-profesional]].

## Esfuerzo
**Nivel:** Medio. N:M y validación transversal de agenda.

## Tareas de desarrollo
- [ ] **T-01 — Relación de sedes.** Modelar tabla puente en 3FN y migración.
- [ ] **T-02 — Gestión ADMIN.** Asignar/revocar sedes sin romper agenda comprometida.
- [ ] **T-03 — Pruebas/UI.** Verificar selección autorizada y regla RN-07.

## Criterios de aceptación
- **CA-01:** Dado ADMIN, cuando asigna HIC, ICV o ambas a profesional, entonces se conserva sin duplicados.
- **CA-02:** Dado profesional sin sede asignada, cuando intenta publicar bloque allí, entonces se rechaza.
- **CA-03:** Dado usuario, cuando busca/reserva para profesional, entonces solo puede elegir sus sedes asignadas.
- **CA-04:** Dado no ADMIN, cuando intenta cambiar asignaciones, entonces se deniega.

## Definition of Done
- [ ] CA-01 a CA-04 tienen evidencia de N:M y reglas de agenda/reserva.
- [ ] FKs, únicos y migración preservan 3FN.
- [ ] Contrato y trazabilidad cruzada están actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-07/RN-07 y sedes fijas; requisitos 3FN. Verificado: 2026-09-17.
