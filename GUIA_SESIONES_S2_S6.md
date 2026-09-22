<<<<<<< HEAD
# Guía progresiva S2–S6 — Proyecto de citas

Cada sesión dura 3 horas. Los tiempos son guía de facilitación; lo evaluable es la evidencia y trazabilidad.

---

## S2 — Desarrollo dirigido: especificar, inicializar y construir el primer incremento

### Resultado
Los dos repos existen como proyectos reales, están gobernados por agentes, Scrum/DoD está generado y existe un primer incremento funcional demostrable.

### Secuencia
1. **Spec-Driven Development**
   - leer PRD + restricciones;
   - ejecutar `scrum-spec-orchestrator`;
   - revisar/ajustar épicas, HU, CA, DoD;
   - aprobar únicamente las HU que se abordarán.
2. **Agentes**
   - generar AGENTS raíz con prompt orquestador;
   - generar AGENTS backend después de inicializar Spring;
   - frontend AGENTS se genera después de importar AI Studio.
3. **Backend base**
   - inicializar Spring Boot;
   - paquetes hexagonales;
   - MySQL/Flyway;
   - normalización propia 3FN y comparación posterior con referencia;
   - autenticación/registro como primer vertical slice.
4. **Prototipado frontend**
   - Skill Stitch;
   - diseño de pantallas obligatorias;
   - aprobación explícita;
   - handoff a AI Studio;
   - escoger React o Angular;
   - importar a `citas-web`.
5. **Primer GOAL**
   - `GOAL_01_GUIADO_SIMPLE.md`.
6. **Skill / subagentes**
   - usar Scrum Skill para especificación;
   - delegar al menos una investigación acotada a subagente.

### Entregable mínimo S2
- ambos repos inicializados;
- AGENTS principales;
- Scrum docs con HU aprobadas;
- wiki global iniciada;
- BD conectada/migraciones iniciales;
- registro + login JWT funcional en backend;
- frontend importado y ejecutable con al menos login/registro diseñados;
- commit S2 en `develop` de ambos repos.

### Commit sugerido
`feat(s2): bootstrap specs auth and frontend baseline`

---

## S3 — Verificación: construir flujo de citas y la red que dice “no”

### Resultado
El producto ya permite agenda/solicitud de citas y existe una red automatizada que bloquea cambios inválidos.

### Funcionalidad objetivo
- admin CRUD de profesionales/asignaciones;
- professional gestiona bloques de disponibilidad;
- user consulta disponibilidad;
- cita general auto-aprobada;
- cita especializada REQUESTED;
- admin aprueba/rechaza cita especializada;
- UI correspondiente.

### Verificación obligatoria
1. agente escribe pruebas antes/durante implementación;
2. demostrar una prueba que falla intencionalmente;
3. Red → Green;
4. pruebas de reglas de slots 30/60;
5. prueba de doble reserva;
6. pruebas de autorización básicas;
7. frontend build/typecheck/tests disponibles;
8. hook local que ejecuta verificaciones;
9. introducir secreto ficticio y comprobar bloqueo;
10. corregir y mostrar commit permitido.

### GOAL avanzado
Usar `GOAL_02_GUIADO_AVANZADO.md` para una funcionalidad cross-repo.

### Entregable mínimo S3
- flujo general + especializado;
- administración de aprobación;
- pruebas automatizadas;
- hook probado en FAIL y PASS;
- evidencia del bloqueo de secreto ficticio;
- commit S3 por repo.

### Commit sugerido
`test(s3): implement booking flow and automated quality gates`

---

## S4 — Autonomía: completar el MVP con Builder/Verifier

### Resultado
MVP funcional y al menos dos ciclos autónomos ejecutados: uno guiado y otro elegido/creado por el estudiante.

### Funcionalidad a completar
- mis citas;
- cancelación;
- reprogramación;
- agenda del profesional;
- marcar COMPLETED/NO_SHOW;
- EPS/planes/especialidades CRUD según PRD;
- recuperación de contraseña sin SMTP obligatorio;
- historial de estados;
- hardening de contrato y UI.

### Loops
1. enseñar `LOOP_01_GUIADO_SIMPLE.md`;
2. ejecutar `LOOP_02_GUIADO_AVANZADO.md` o una alternativa equivalente;
3. estudiante diseña `LOOP_03_RETO_INDEPENDIENTE.md`.

### Builder / Verifier
- Builder produce cambios dentro del alcance;
- Verifier aislado no implementa;
- máximo de iteraciones;
- stop condition;
- escalamiento humano;
- log de cada iteración.

### Observabilidad
Guardar evidencia en un Markdown o JSON por ejecución, por ejemplo:

```json
{
  "goal": "reschedule appointment",
  "iteration": 3,
  "builder": "completed",
  "backendTests": "pass",
  "frontendBuild": "pass",
  "verifier": "PASS",
  "result": "COMPLETED"
}
```

### Entregable mínimo S4
- MVP S2-S4 completo;
- un loop guiado + uno propio;
- logs;
- DoD de HU abordadas validada;
- commit estable y, cuando el estudiante decida, merge `develop → main`.

### Commit sugerido
`feat(s4): complete appointment lifecycle with autonomous verification loops`

---

## S5 — Agente conectado: MCP, n8n y seguridad frente a contenido no confiable

### Precondición del trainer
Antes de clase:
- n8n accesible;
- cuentas/credenciales de acceso para participantes;
- MCP de n8n configurado y probado;
- webhooks accesibles desde el backend/laboratorio.

### Objetivos
- distinguir cliente/servidor MCP;
- leer n8n como desarrollador: trigger, nodes, credentials, executions;
- configurar Google Cloud OAuth/Gmail individualmente;
- exponer/invocar una automatización desde el agente;
- mantener bloque de 25–30 min de contenido no confiable y riesgos residuales.

### Workflow 1 — recordatorios
`Schedule Trigger → consultar API citas APPROVED próximas → Gmail → registro/resultado`.

Cada estudiante debe crear sus propias credenciales OAuth.

### Seguridad obligatoria
Analizar como contenido no confiable:
- issue;
- comentario de revisión;
- README de dependencia;
- respuesta MCP.

Demo de issue envenenado y declaración de riesgos residuales.

### Evidencia S5
- workflow funcionando;
- invocación MCP exitosa desde agente;
- credenciales con privilegio mínimo razonable;
- JSON exportado a `citas-api/automations/n8n/WF-001-appointment-reminders.json`;
- riesgos residuales por escrito;
- commit S5.

### Commit sugerido
`feat(s5): add n8n reminder workflow and MCP integration evidence`

---

## S6 — Automatizaciones que construye el agente + cierre

### Workflow 2 — notificación de cambio de estado
`Webhook desde Spring → n8n → Gmail → registro/trazabilidad`.

Eventos sugeridos:
- cita especializada aprobada/rechazada;
- reprogramación aprobada/rechazada;
- cancelación.

### Workflow 3 — reserva si el grupo avanza rápido
`Schedule → API resumen del día → agrupar por sede/estado → Gmail resumen operativo`.

### Agente + n8n
El agente debe poder:
- listar/inspeccionar workflows;
- proponer/crear/actualizar según capacidades MCP disponibles;
- validar con ejecución controlada;
- manejar error/reintento razonable;
- exportar/versionar JSON.

No activar un flujo sin validar salida esperada.

### Entregable final
- repos públicos `citas-api` y `citas-web`;
- `main` estable y `develop` con trazabilidad;
- al menos un commit por S2-S6;
- HU/DoD y wiki;
- pruebas/hooks;
- evidencia goals/loops;
- WF-001 y WF-002 JSON obligatorios;
- WF-003 opcional/bonus;
- sustentación técnica.

### Commit sugerido
`feat(s6): finalize agentic appointment platform and version n8n automations`
=======
# Guía progresiva S2–S6 — Proyecto de citas

Cada sesión dura 3 horas. Los tiempos son guía de facilitación; lo evaluable es la evidencia y trazabilidad.

---

## S2 — Desarrollo dirigido: especificar, inicializar y construir el primer incremento

### Resultado
Los dos repos existen como proyectos reales, están gobernados por agentes, Scrum/DoD está generado y existe un primer incremento funcional demostrable.

### Secuencia
1. **Spec-Driven Development**
   - leer PRD + restricciones;
   - ejecutar `scrum-spec-orchestrator`;
   - revisar/ajustar épicas, HU, CA, DoD;
   - aprobar únicamente las HU que se abordarán.
2. **Agentes**
   - generar AGENTS raíz con prompt orquestador;
   - generar AGENTS backend después de inicializar Spring;
   - frontend AGENTS se genera después de importar AI Studio.
3. **Backend base**
   - inicializar Spring Boot;
   - paquetes hexagonales;
   - MySQL/Flyway;
   - normalización propia 3FN y comparación posterior con referencia;
   - autenticación/registro como primer vertical slice.
4. **Prototipado frontend**
   - Skill Stitch;
   - diseño de pantallas obligatorias;
   - aprobación explícita;
   - handoff a AI Studio;
   - escoger React o Angular;
   - importar a `citas-web`.
5. **Primer GOAL**
   - `GOAL_01_GUIADO_SIMPLE.md`.
6. **Skill / subagentes**
   - usar Scrum Skill para especificación;
   - delegar al menos una investigación acotada a subagente.

### Entregable mínimo S2
- ambos repos inicializados;
- AGENTS principales;
- Scrum docs con HU aprobadas;
- wiki global iniciada;
- BD conectada/migraciones iniciales;
- registro + login JWT funcional en backend;
- frontend importado y ejecutable con al menos login/registro diseñados;
- commit S2 en `develop` de ambos repos.

### Commit sugerido
`feat(s2): bootstrap specs auth and frontend baseline`

---

## S3 — Verificación: construir flujo de citas y la red que dice “no”

### Resultado
El producto ya permite agenda/solicitud de citas y existe una red automatizada que bloquea cambios inválidos.

### Funcionalidad objetivo
- admin CRUD de profesionales/asignaciones;
- professional gestiona bloques de disponibilidad;
- user consulta disponibilidad;
- cita general auto-aprobada;
- cita especializada REQUESTED;
- admin aprueba/rechaza cita especializada;
- UI correspondiente.

### Verificación obligatoria
1. agente escribe pruebas antes/durante implementación;
2. demostrar una prueba que falla intencionalmente;
3. Red → Green;
4. pruebas de reglas de slots 30/60;
5. prueba de doble reserva;
6. pruebas de autorización básicas;
7. frontend build/typecheck/tests disponibles;
8. hook local que ejecuta verificaciones;
9. introducir secreto ficticio y comprobar bloqueo;
10. corregir y mostrar commit permitido.

### GOAL avanzado
Usar `GOAL_02_GUIADO_AVANZADO.md` para una funcionalidad cross-repo.

### Entregable mínimo S3
- flujo general + especializado;
- administración de aprobación;
- pruebas automatizadas;
- hook probado en FAIL y PASS;
- evidencia del bloqueo de secreto ficticio;
- commit S3 por repo.

### Commit sugerido
`test(s3): implement booking flow and automated quality gates`

---

## S4 — Autonomía: completar el MVP con Builder/Verifier

### Resultado
MVP funcional y al menos dos ciclos autónomos ejecutados: uno guiado y otro elegido/creado por el estudiante.

### Funcionalidad a completar
- mis citas;
- cancelación;
- reprogramación;
- agenda del profesional;
- marcar COMPLETED/NO_SHOW;
- EPS/planes/especialidades CRUD según PRD;
- recuperación de contraseña sin SMTP obligatorio;
- historial de estados;
- hardening de contrato y UI.

### Loops
1. enseñar `LOOP_01_GUIADO_SIMPLE.md`;
2. ejecutar `LOOP_02_GUIADO_AVANZADO.md` o una alternativa equivalente;
3. estudiante diseña `LOOP_03_RETO_INDEPENDIENTE.md`.

### Builder / Verifier
- Builder produce cambios dentro del alcance;
- Verifier aislado no implementa;
- máximo de iteraciones;
- stop condition;
- escalamiento humano;
- log de cada iteración.

### Observabilidad
Guardar evidencia en un Markdown o JSON por ejecución, por ejemplo:

```json
{
  "goal": "reschedule appointment",
  "iteration": 3,
  "builder": "completed",
  "backendTests": "pass",
  "frontendBuild": "pass",
  "verifier": "PASS",
  "result": "COMPLETED"
}
```

### Entregable mínimo S4
- MVP S2-S4 completo;
- un loop guiado + uno propio;
- logs;
- DoD de HU abordadas validada;
- commit estable y, cuando el estudiante decida, merge `develop → main`.

### Commit sugerido
`feat(s4): complete appointment lifecycle with autonomous verification loops`

---

## S5 — Agente conectado: MCP, n8n y seguridad frente a contenido no confiable

### Precondición del trainer
Antes de clase:
- n8n accesible;
- cuentas/credenciales de acceso para participantes;
- MCP de n8n configurado y probado;
- webhooks accesibles desde el backend/laboratorio.

### Objetivos
- distinguir cliente/servidor MCP;
- leer n8n como desarrollador: trigger, nodes, credentials, executions;
- configurar Google Cloud OAuth/Gmail individualmente;
- exponer/invocar una automatización desde el agente;
- mantener bloque de 25–30 min de contenido no confiable y riesgos residuales.

### Workflow 1 — recordatorios
`Schedule Trigger → consultar API citas APPROVED próximas → Gmail → registro/resultado`.

Cada estudiante debe crear sus propias credenciales OAuth.

### Seguridad obligatoria
Analizar como contenido no confiable:
- issue;
- comentario de revisión;
- README de dependencia;
- respuesta MCP.

Demo de issue envenenado y declaración de riesgos residuales.

### Evidencia S5
- workflow funcionando;
- invocación MCP exitosa desde agente;
- credenciales con privilegio mínimo razonable;
- JSON exportado a `citas-api/automations/n8n/WF-001-appointment-reminders.json`;
- riesgos residuales por escrito;
- commit S5.

### Commit sugerido
`feat(s5): add n8n reminder workflow and MCP integration evidence`

---

## S6 — Automatizaciones que construye el agente + cierre

### Workflow 2 — notificación de cambio de estado
`Webhook desde Spring → n8n → Gmail → registro/trazabilidad`.

Eventos sugeridos:
- cita especializada aprobada/rechazada;
- reprogramación aprobada/rechazada;
- cancelación.

### Workflow 3 — reserva si el grupo avanza rápido
`Schedule → API resumen del día → agrupar por sede/estado → Gmail resumen operativo`.

### Agente + n8n
El agente debe poder:
- listar/inspeccionar workflows;
- proponer/crear/actualizar según capacidades MCP disponibles;
- validar con ejecución controlada;
- manejar error/reintento razonable;
- exportar/versionar JSON.

No activar un flujo sin validar salida esperada.

### Entregable final
- repos públicos `citas-api` y `citas-web`;
- `main` estable y `develop` con trazabilidad;
- al menos un commit por S2-S6;
- HU/DoD y wiki;
- pruebas/hooks;
- evidencia goals/loops;
- WF-001 y WF-002 JSON obligatorios;
- WF-003 opcional/bonus;
- sustentación técnica.

### Commit sugerido
`feat(s6): finalize agentic appointment platform and version n8n automations`
>>>>>>> d8afae289e730587ae3ff67961e2196e8aa79fb3
