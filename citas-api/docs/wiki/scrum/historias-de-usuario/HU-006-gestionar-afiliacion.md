---
id: HU-006
tipo: historia-de-usuario
titulo: Gestionar afiliación
estado: Pendiente de aprobación
epica: "[[EP-001-acceso-y-perfil]]"
esfuerzo: Medio
sprint_sugerido: S2
dependencias: ["[[HU-005-gestionar-perfil]]", "[[HU-007-consultar-catalogos-fijos]]", "[[HU-009-administrar-planes-eps]]"]
relacionadas: []
---

# HU-006 — Gestionar afiliación

## Historia de usuario
**COMO** USER autenticado **QUIERO** asociar mi EPS, plan y régimen **PARA** completar mi perfil de afiliación sin duplicados.

## Contexto, alcance y reglas
PRD RF-04. Usa EPS, plan y régimen de catálogos. Una afiliación no repite nombres de catálogos en usuario y evita duplicar la misma combinación. Excluye administración de catálogos.

## Dependencias y relaciones
- Épica: [[EP-001-acceso-y-perfil]]. Depende de [[HU-005-gestionar-perfil]], [[HU-007-consultar-catalogos-fijos]] y [[HU-009-administrar-planes-eps]].

## Esfuerzo
**Nivel:** Medio. Relaciones normalizadas, validación de coherencia y UI.

## Tareas de desarrollo
- [ ] **T-01 — Modelo de afiliación.** Definir FK y único compuesto justificados en 3FN; migración Flyway.
- [ ] **T-02 — Gestión propia.** Validar plan perteneciente a EPS y ownership.
- [ ] **T-03 — Integración y pruebas.** Mostrar opciones activas y cubrir duplicados/relaciones inválidas.

## Criterios de aceptación
- **CA-01:** Dado catálogo activo, cuando USER elige EPS, régimen y plan válido, entonces puede guardar su afiliación.
- **CA-02:** Dado plan que no pertenece a EPS o catálogo inactivo, cuando intenta asociarlo, entonces se rechaza.
- **CA-03:** Dada combinación ya asociada, cuando intenta repetirla, entonces no se crea duplicado.
- **CA-04:** Dado otro USER, cuando intenta modificar afiliación ajena, entonces se deniega.

## Definition of Done
- [ ] CA-01 a CA-04 tienen evidencia relevante.
- [ ] FKs, único compuesto, 3FN y migración están justificados y probados.
- [ ] UI/API consumen el contrato sin repetir nombres de catálogos en datos transaccionales.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-04 | Pendiente | Requiere dependencias aprobadas e implementación. |
| DoD | Pendiente | Sin validación ejecutada. |

## Notas y fuentes
PRD RF-04/RF-05; requisitos 1FN–3FN. Verificado: 2026-09-17.
