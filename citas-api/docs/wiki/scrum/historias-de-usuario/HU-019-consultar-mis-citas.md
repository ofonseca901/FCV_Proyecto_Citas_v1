---
id: HU-019
tipo: historia-de-usuario
titulo: Consultar mis citas
estado: Pendiente de aprobación
epica: "[[EP-004-citas-generales-y-seguimiento]]"
esfuerzo: Medio
sprint_sugerido: S4
dependencias: ["[[HU-018-reservar-cita-general]]"]
relacionadas: ["[[HU-021-solicitar-cita-especializada]]"]
---

# HU-019 — Consultar mis citas

## Historia de usuario
**COMO** USER **QUIERO** consultar y filtrar mis citas **PARA** conocer su programación y estado.

## Contexto, alcance y reglas
PRD RF-13. Incluye sede, profesional, especialidad, fecha/hora, duración, estado y motivo de rechazo si existe; filtra por estado/fecha. Solo muestra citas propias.

## Dependencias y relaciones
- Épica: [[EP-004-citas-generales-y-seguimiento]]. Depende de [[HU-018-reservar-cita-general]]; amplía el mismo flujo para especializadas.

## Esfuerzo
**Nivel:** Medio. Consulta con ownership, filtros y proyección de datos relacionados.

## Tareas de desarrollo
- [ ] **T-01 — Consulta propia.** Definir filtros y proyección sin duplicar datos de catálogo.
- [ ] **T-02 — Interfaz.** Presentar lista/detalle y motivo solo cuando existe.
- [ ] **T-03 — Pruebas.** Validar filtros, vacío, ownership y datos mínimos.

## Criterios de aceptación
- **CA-01:** Dado USER con citas, cuando consulta, entonces ve solo las propias con todos los campos mínimos requeridos.
- **CA-02:** Dado filtro por estado o fecha, cuando lo aplica, entonces recibe únicamente coincidencias.
- **CA-03:** Dada cita rechazada, cuando la consulta, entonces ve motivo; para otra no se inventa motivo.
- **CA-04:** Dado otro USER, cuando intenta consultar citas ajenas, entonces no las obtiene.

## Definition of Done
- [ ] CA-01 a CA-04 tienen evidencia API/UI y de ownership.
- [ ] Consulta usa relaciones/índices justificados, sin datos clínicos ni exposición indebida.
- [ ] Contrato y trazabilidad están actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-13 y seguridad/ownership. Verificado: 2026-09-17.
