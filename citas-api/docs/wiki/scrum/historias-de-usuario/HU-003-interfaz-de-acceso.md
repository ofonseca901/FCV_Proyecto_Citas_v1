---
id: HU-003
tipo: historia-de-usuario
titulo: Interfaz de acceso
estado: Pendiente de aprobación
epica: "[[EP-001-acceso-de-usuarios]]"
esfuerzo: Medio
sprint_sugerido: S2
dependencias:
  - "[[HU-001-registrar-usuario]]"
  - "[[HU-002-gestionar-sesion]]"
relacionadas: []
---

# HU-003 — Interfaz de acceso

**COMO** paciente, **QUIERO** usar formularios de registro e inicio de sesión, **PARA** acceder desde el navegador.

## Contexto y alcance
Primer frontend S2. Login, registro, confirmación de sesión e integración REST. React/TypeScript propuesto si no existe exportación. No incluye agenda ni recuperación de contraseña.

## Reglas de negocio
Mostrar errores sin revelar credenciales. Evitar doble envío. Etiquetas accesibles y diseño adaptable. URL de API configurable. Identificar el entorno como académico.

## Dependencias y relaciones
Épica [[EP-001-acceso-de-usuarios]]. Depende de [[HU-001-registrar-usuario]] y [[HU-002-gestionar-sesion]].

## Esfuerzo
Medio: interfaz, validación y comunicación REST.

## Tareas
- [ ] T-01 (Medio): confirmar origen del diseño y framework, importar o crear interfaz según decisión.
- [ ] T-02 (Medio): integrar formularios y estados de envío/error/éxito.
- [ ] T-03 (Medio): verificar build, tipos y flujo en navegador.

## Criterios de aceptación
- CA-01: el frontend arranca y permite alternar entre login y registro mediante controles accesibles.
- CA-02: registro exitoso permite iniciar sesión; errores 400/409 se muestran sin perder los datos no sensibles del formulario.
- CA-03: login válido muestra identidad y logout; login inválido muestra error genérico.
- CA-04: una falla de red muestra un mensaje accionable y restablece la posibilidad de enviar.
- CA-05: formularios utilizables en móvil y escritorio, con etiquetas, foco visible y estado de carga.

## Definition of Done
- [ ] CA verificados y build/typecheck correctos.
- [ ] URL configurable y contrato REST coherente con backend.
- [ ] Evidencia visual y procedencia real del diseño documentadas.
- [ ] Trazabilidad actualizada; aprobación visual pendiente hasta revisión del usuario.

## Evidencia de validación
| Elemento | Resultado | Evidencia |
|---|---|---|
| CA-01 a CA-05 | Pendiente | Sin ejecución al redactar |
| DoD | Pendiente | Pendiente decisión visual e implementación |

## Historial
S2: propuesta pendiente de aprobación del usuario.
