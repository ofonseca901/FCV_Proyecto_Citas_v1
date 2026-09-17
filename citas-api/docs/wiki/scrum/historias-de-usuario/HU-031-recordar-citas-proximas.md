---
id: HU-031
tipo: historia-de-usuario
titulo: Recordar citas próximas
estado: Pendiente de aprobación
epica: "[[EP-009-automatizaciones-posteriores]]"
esfuerzo: Medio
sprint_sugerido: S5/S6
dependencias: ["[[HU-030-mantener-contrato-rest]]"]
relacionadas: ["[[HU-033-generar-resumen-operativo]]"]
---

# HU-031 — Recordar citas próximas

## Historia de usuario
**COMO** USER con cita próxima **QUIERO** recibir un recordatorio sintético **PARA** recordar mi atención programada.

## Contexto y reglas
PRD automatización posterior y restricciones n8n. Workflow n8n consulta datos permitidos y envía por Gmail configurado externamente. JSON versionado no contiene credenciales, OAuth, URLs privadas ni secretos; no modifica núcleo.

## Dependencias y relaciones
- Épica: [[EP-009-automatizaciones-posteriores]]. Depende de [[HU-030-mantener-contrato-rest]].

## Esfuerzo
**Nivel:** Medio. Integración posterior, filtro de estado/fecha y exportación segura.

## Tareas de desarrollo
- [ ] **T-01 — Contrato de datos mínimo.** Definir consulta de citas próximas sin datos clínicos.
- [ ] **T-02 — Workflow n8n.** Configurar flujo en instancia del trainer y exportar JSON sanitizado.
- [ ] **T-03 — Evidencia.** Probar con datos sintéticos y verificar ausencia de secretos.

## Criterios de aceptación
- **CA-01:** Dada cita próxima elegible sintética, cuando corre flujo, entonces prepara/envía recordatorio con datos mínimos autorizados.
- **CA-02:** Dada cita cancelada, rechazada o no próxima, cuando corre, entonces no genera recordatorio.
- **CA-03:** Dada exportación JSON, cuando se revisa, entonces no contiene credenciales, tokens, secretos ni URL privada.

## Definition of Done
- [ ] CA-01 a CA-03 cuentan con evidencia sintética y exportación versionada en ruta autorizada.
- [ ] No se cambió núcleo de citas ni se enviaron datos reales.
- [ ] Trazabilidad del workflow y contrato quedan actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-03 | Pendiente | Requiere integración autorizada posterior. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD §10; restricciones n8n/datos sintéticos. Verificado: 2026-09-17.
