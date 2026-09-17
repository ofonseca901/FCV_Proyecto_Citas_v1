---
id: HU-007
tipo: historia-de-usuario
titulo: Consultar catálogos fijos
estado: Pendiente de aprobación
epica: "[[EP-002-catalogos-y-profesionales]]"
esfuerzo: Medio
sprint_sugerido: S3
dependencias: ["[[HU-002-gestionar-sesion]]"]
relacionadas: ["[[HU-006-gestionar-afiliacion]]"]
---

# HU-007 — Consultar catálogos fijos

## Historia de usuario
**COMO** usuario autorizado **QUIERO** consultar los catálogos fijos del laboratorio **PARA** seleccionar valores consistentes.

## Contexto, alcance y reglas
PRD RF-05. Roles, estados de cita, estados de reprogramación, regímenes y sedes se precargan y son de solo lectura. Incluye las dos sedes fijas sintéticas del PRD; excluye CRUD y borrado.

## Dependencias y relaciones
- Épica: [[EP-002-catalogos-y-profesionales]]. Depende de [[HU-002-gestionar-sesion]]; habilita [[HU-006-gestionar-afiliacion]].

## Esfuerzo
**Nivel:** Medio. Seeds, autorización de consulta y esquema de referencia.

## Tareas de desarrollo
- [ ] **T-01 — Catálogos y seed.** Modelar catálogos en 3FN y precargarlos por migración/versionado.
- [ ] **T-02 — Consulta controlada.** Exponer valores necesarios sin permitir modificación.
- [ ] **T-03 — Pruebas.** Verificar valores, inmutabilidad y uso por consumidor.

## Criterios de aceptación
- **CA-01:** Dado usuario autorizado, cuando consulta catálogos, entonces obtiene roles, estados, regímenes y ambas sedes fijas.
- **CA-02:** Dado intento de modificar catálogo fijo por API/UI, cuando se envía, entonces se rechaza.
- **CA-03:** Dado valor usado por otra función, cuando se consulta, entonces su identificador y etiqueta son consistentes.

## Definition of Done
- [ ] CA-01 a CA-03 tienen evidencia de seed y API/UI aplicable.
- [ ] Seeds, FKs y migraciones respetan 3FN y no contienen datos reales.
- [ ] Contrato y trazabilidad de catálogos quedan actualizados.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-03 | Pendiente | Requiere implementación aprobada. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-05 y sedes; restricciones BD. Verificado: 2026-09-17.
