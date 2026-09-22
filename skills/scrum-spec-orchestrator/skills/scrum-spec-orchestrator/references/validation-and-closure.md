# Validación y cierre de historias de usuario

## Principio

El cierre de una HU es una afirmación basada en evidencia, no una declaración del modelo ni una simple confirmación de que “el desarrollo terminó”.

La skill recolecta la evidencia disponible por sí misma y evita trasladar esa carga al usuario.

## Fuentes de evidencia permitidas

Priorizar, según disponibilidad:
1. código y configuración existentes, leídos sin modificación;
2. tests existentes y su relación con los criterios;
3. resultados de CI o reportes de test ya generados;
4. historial y diff de Git en modo de lectura;
5. migraciones Flyway existentes;
6. contratos, DTOs, controladores, servicios, componentes y rutas observables;
7. documentación técnica existente;
8. ejecución aislada de validaciones únicamente si el host garantiza que el repositorio original no se modifica.

No considerar suficiente una suposición basada solo en nombres de archivos.

## Matriz de evidencia

Para cada criterio y cada ítem de DoD registrar:

| Elemento | Resultado | Evidencia | Observación |
|---|---|---|---|
| CA-01 | Cumple / No cumple / No verificable | archivo, símbolo, test, log o artefacto | explicación breve |

La evidencia debe ser específica. Preferir rutas y símbolos concretos.

## Resultado

### Cumple

Usar solo cuando la evidencia disponible demuestra razonablemente la condición.

### No cumple

Usar cuando la evidencia contradice la condición o demuestra que falta una parte necesaria.

### No verificable

Usar cuando la comprobación requiere una ejecución o contexto que no está disponible de forma segura.

`No verificable` nunca cuenta como `Cumple` para cerrar una HU.

## Regla de cierre

Una HU puede pasar a `Completada` únicamente cuando:
- todos sus criterios de aceptación obligatorios están en `Cumple`;
- todos los ítems aplicables de DoD están en `Cumple`;
- no existe un bloqueador abierto que invalide el alcance;
- la matriz de evidencia está registrada en la nota de la HU.

Si cualquiera queda en `No cumple` o `No verificable`, mantener la HU en `En validación`, salvo que un impedimento externo justifique `Bloqueada`.

## Prohibición de reparación

Al validar, nunca:
- editar código o tests;
- crear migraciones;
- ajustar configuración;
- instalar dependencias;
- ejecutar despliegues;
- modificar una base de datos;
- hacer commits o cambios de Git;
- corregir automáticamente una condición fallida.

En su lugar, documentar el gap como acción de desarrollo pendiente.

## Validaciones dinámicas

Los builds y tests suelen escribir artefactos. No ejecutarlos en el árbol de trabajo original si la política es de solo lectura.

Solo se permite su ejecución cuando el host ofrece un entorno aislado/desechable y la ejecución no puede modificar el repositorio original. Los resultados pueden usarse como evidencia, pero ningún artefacto generado se copia de regreso.

Si no existe dicho entorno, usar resultados de CI/tests ya disponibles o evidencia estática y marcar como `No verificable` aquello que requiera ejecución real.
