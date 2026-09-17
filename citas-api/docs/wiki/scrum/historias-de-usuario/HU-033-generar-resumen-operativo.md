---
id: HU-033
tipo: historia-de-usuario
titulo: Generar resumen operativo
estado: Pendiente de aprobación
epica: "[[EP-009-automatizaciones-posteriores]]"
esfuerzo: Medio
sprint_sugerido: S5/S6
dependencias: ["[[HU-030-mantener-contrato-rest]]"]
relacionadas: ["[[HU-031-recordar-citas-proximas]]"]
---

# HU-033 — Generar resumen operativo

## Historia de usuario
**COMO** ADMIN **QUIERO** recibir un resumen diario sintético por sede y estado **PARA** revisar la operación agregada.

## Contexto y reglas
PRD §10. Automatización posterior n8n usa agregados por sede/estado; no expone pacientes ni datos clínicos, no cambia citas y exporta JSON sin credenciales.

## Dependencias y relaciones
- Épica: [[EP-009-automatizaciones-posteriores]]. Depende de [[HU-030-mantener-contrato-rest]].

## Esfuerzo
**Nivel:** Medio. Consulta agregada segura, programación de workflow y exportación.

## Tareas de desarrollo
- [ ] **T-01 — Contrato agregado.** Definir agrupación por sede/estado con datos mínimos e índices necesarios.
- [ ] **T-02 — Workflow n8n.** Programar resumen en instancia del trainer y exportar JSON sanitizado.
- [ ] **T-03 — Evidencia.** Validar conteos sintéticos, acceso ADMIN y ausencia de secretos.

## Criterios de aceptación
- **CA-01:** Dados datos sintéticos de citas, cuando corre resumen, entonces produce conteos por sede y estado coherentes.
- **CA-02:** Dado actor no ADMIN, cuando solicita el agregado operativo, entonces se deniega.
- **CA-03:** Dado JSON exportado, cuando se inspecciona, entonces no contiene credenciales, secretos, URLs privadas ni PII real.

## Definition of Done
- [ ] CA-01 a CA-03 cuentan con evidencia sintética, de autorización y de exportación segura.
- [ ] Consulta usa datos agregados mínimos y no modifica citas.
- [ ] Contrato, workflow y trazabilidad quedan actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-03 | Pendiente | Requiere integración autorizada posterior. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD §10; restricciones n8n/datos sintéticos. Verificado: 2026-09-17.
