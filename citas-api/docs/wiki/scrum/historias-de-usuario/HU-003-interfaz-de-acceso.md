---
id: HU-003
tipo: historia-de-usuario
titulo: Interfaz de acceso
estado: Pendiente de aprobación
epica: "[[EP-001-acceso-y-perfil]]"
esfuerzo: Medio
sprint_sugerido: S2
dependencias: ["[[HU-001-registrar-usuario]]", "[[HU-002-gestionar-sesion]]"]
relacionadas: []
---

# HU-003 — Interfaz de acceso

## Historia de usuario
**COMO** visitante o USER **QUIERO** usar registro, login y cierre desde el navegador **PARA** acceder al producto.

## Contexto, alcance y reglas
PRD RF-01/RF-02 y pantallas obligatorias. Incluye formularios y consumo REST directo con URL configurable. Framework y diseño visual quedan pendientes de aprobación. No expone tokens/contraseñas ni implementa agenda.

## Dependencias y relaciones
- Épica: [[EP-001-acceso-y-perfil]]. Depende de [[HU-001-registrar-usuario]] y [[HU-002-gestionar-sesion]].

## Esfuerzo
**Nivel:** Medio. Estados de interfaz, accesibilidad e integración de contrato.

## Tareas de desarrollo
- [ ] **T-01 — Base visual aprobada.** Confirmar React/Angular y la fuente visual antes de construir vistas.
- [ ] **T-02 — Formularios y sesión.** Integrar validación, estados de carga, éxito, error y logout.
- [ ] **T-03 — Verificación web.** Ejecutar build/typecheck y pruebas aplicables contra contrato real.

## Criterios de aceptación
- **CA-01:** Dado un visitante, cuando abre acceso, entonces puede navegar a registro o login mediante controles etiquetados.
- **CA-02:** Dado registro exitoso o errores 400/409, cuando envía el formulario, entonces ve resultado sin perder datos no sensibles.
- **CA-03:** Dado login válido o inválido, cuando envía, entonces ve identidad y logout o error genérico, respectivamente.
- **CA-04:** Dada falla de red, cuando ocurre, entonces recibe aviso accionable y puede reenviar.
- **CA-05:** Dado móvil o escritorio, cuando usa formularios, entonces hay foco visible, etiquetas y estado de carga.

## Definition of Done
- [ ] CA-01 a CA-05 cuentan con evidencia visual y pruebas web aplicables.
- [ ] URL de API configurable y contrato REST coinciden con [[HU-002-gestionar-sesion]].
- [ ] Diseño aprobado y trazabilidad Scrum registrados sin atribuir aprobación inexistente.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-05 | Pendiente | Requiere diseño e implementación aprobados. |
| DoD | Pendiente | Framework visual aún por decidir. |

## Notas y fuentes
PRD RF-01/RF-02 y pantallas; restricciones frontend. Verificado: 2026-09-17.
