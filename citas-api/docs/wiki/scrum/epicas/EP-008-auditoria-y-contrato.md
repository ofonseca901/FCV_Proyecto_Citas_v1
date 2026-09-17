---
id: EP-008
tipo: epica
titulo: Auditoría y contrato
estado: Pendiente de aprobación
historias: ["[[HU-029-consultar-historial-de-estados]]", "[[HU-030-mantener-contrato-rest]]"]
dependencias: ["[[EP-004-citas-generales-y-seguimiento]]", "[[EP-005-citas-especializadas]]", "[[EP-006-reprogramacion]]", "[[EP-007-operacion-profesional]]"]
---

# EP-008 — Auditoría y contrato

## Objetivo y valor
Hacer comprobables los cambios de estado y la integración directa entre web y API.

## Alcance y fuera de alcance
- Alcance: consulta autorizada de auditoría y contrato REST evolutivo de las funcionalidades.
- Fuera de alcance: CRUD de auditoría, BFF, contratos externos o datos clínicos.

## Reglas de negocio
- Cada cambio guarda cita, estado nuevo, actor cuando existe, fuente, fecha/hora y motivo opcional.
- Auditoría no se modifica como CRUD normal; el contrato no expone secretos ni entidades internas.

## Dependencias
- Consolida las transiciones de [[EP-004-citas-generales-y-seguimiento]], [[EP-005-citas-especializadas]], [[EP-006-reprogramacion]] y [[EP-007-operacion-profesional]].

## Historias de usuario
- [[HU-029-consultar-historial-de-estados]]
- [[HU-030-mantener-contrato-rest]]

## Criterio de completitud
- [ ] Cada transición tiene historial verificable y cada contrato consumido por web tiene evidencia cruzada.

## Riesgos e incógnitas
- Definir qué vistas pueden exponer motivos o actor sin quebrar ownership ni mínimos de datos.
