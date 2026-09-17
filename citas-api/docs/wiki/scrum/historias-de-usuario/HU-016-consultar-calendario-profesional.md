---
id: HU-016
tipo: historia-de-usuario
titulo: Consultar calendario profesional
estado: Pendiente de aprobación
epica: "[[EP-003-disponibilidad-y-agenda]]"
esfuerzo: Medio
sprint_sugerido: S3
dependencias: ["[[HU-015-gestionar-bloques-disponibilidad]]"]
relacionadas: []
---

# HU-016 — Consultar calendario profesional

## Historia de usuario
**COMO** PROFESSIONAL **QUIERO** consultar mi calendario de bloques **PARA** verificar mi disponibilidad publicada.

## Contexto, alcance y reglas
PRD RF-08. Presenta bloques propios por fecha y sede. No muestra agendas ajenas ni modifica bloques desde esta HU.

## Dependencias y relaciones
- Épica: [[EP-003-disponibilidad-y-agenda]]. Depende de [[HU-015-gestionar-bloques-disponibilidad]].

## Esfuerzo
**Nivel:** Medio. Consulta filtrada, ownership y vista de calendario.

## Tareas de desarrollo
- [ ] **T-01 — Consulta propia.** Definir filtro por fecha/sede con autorización.
- [ ] **T-02 — Vista calendario.** Mostrar intervalos/sede sin revelar datos ajenos.
- [ ] **T-03 — Pruebas.** Verificar filtros, orden y aislamiento entre profesionales.

## Criterios de aceptación
- **CA-01:** Dado PROFESSIONAL autenticado, cuando consulta fecha/sede, entonces ve sus bloques en orden temporal.
- **CA-02:** Dado filtro sin bloques, cuando consulta, entonces recibe lista vacía válida.
- **CA-03:** Dado otro profesional, cuando intenta consultar agenda ajena, entonces no obtiene sus bloques.

## Definition of Done
- [ ] CA-01 a CA-03 cuentan con evidencia API/UI y de ownership.
- [ ] Consulta usa índices justificados y no expone datos innecesarios.
- [ ] Contrato y trazabilidad quedan actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-03 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-08; restricciones de seguridad/BD. Verificado: 2026-09-17.
