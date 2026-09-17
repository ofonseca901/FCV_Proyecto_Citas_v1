---
id: HU-027
tipo: historia-de-usuario
titulo: Consultar agenda profesional
estado: Pendiente de aprobación
epica: "[[EP-007-operacion-profesional]]"
esfuerzo: Medio
sprint_sugerido: S4
dependencias: ["[[HU-018-reservar-cita-general]]", "[[HU-023-decidir-cita-especializada]]"]
relacionadas: ["[[HU-028-cerrar-atencion]]"]
---

# HU-027 — Consultar agenda profesional

## Historia de usuario
**COMO** PROFESSIONAL **QUIERO** consultar mis citas aprobadas por día, semana y sede **PARA** organizar mi atención.

## Contexto y reglas
PRD RF-16. Solo `APPROVED` propias; filtros día/semana/sede. No ve datos de usuarios fuera de sus citas ni agenda de otros profesionales.

## Dependencias y relaciones
- Épica: [[EP-007-operacion-profesional]]. Depende de citas aprobadas general/especializada.

## Esfuerzo
**Nivel:** Medio. Ownership, filtros temporales y minimización de datos.

## Tareas de desarrollo
- [ ] **T-01 — Consulta propia.** Definir filtros e índices por profesional/fecha/sede.
- [ ] **T-02 — Agenda web.** Presentar información mínima de citas propias.
- [ ] **T-03 — Pruebas.** Verificar aislamiento, estados y filtros.

## Criterios de aceptación
- **CA-01:** Dado PROFESSIONAL, cuando consulta por día/semana/sede, entonces ve sus citas `APPROVED` correspondientes.
- **CA-02:** Dado otro profesional o cita no aprobada, cuando consulta, entonces no ve esos datos.
- **CA-03:** Dado rango sin citas, cuando consulta, entonces recibe resultado vacío válido.

## Definition of Done
- [ ] CA-01 a CA-03 tienen evidencia de ownership y filtros.
- [ ] Consulta minimiza datos y usa índices justificados.
- [ ] Contrato web/API y trazabilidad están actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-03 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-16 y seguridad por ownership. Verificado: 2026-09-17.
