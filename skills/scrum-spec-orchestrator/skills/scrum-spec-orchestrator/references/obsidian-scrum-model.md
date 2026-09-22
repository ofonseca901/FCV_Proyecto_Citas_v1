# Modelo Scrum para Obsidian

## Estructura objetivo

```text
docs/
└── wiki/
    └── scrum/
        ├── epicas/
        │   └── EP-NNN-slug.md
        └── historias-de-usuario/
            └── HU-NNN-slug.md
```

Se permite `docs/wiki/scrum/README.md` como índice y resumen de sprints; no crear otras carpetas por defecto.

## Convenciones de IDs

- Épicas: `EP-001`, `EP-002`, ...
- Historias: `HU-001`, `HU-002`, ...
- Criterios: `CA-01`, `CA-02`, ... reiniciados por HU.
- Tareas: `T-01`, `T-02`, ... reiniciadas por HU.

Mantener IDs estables durante toda la vida del proyecto.

## Convención de nombres

Formato:

`<ID>-<slug-descriptivo>.md`

Ejemplos:
- `EP-001-gestion-de-usuarios.md`
- `HU-004-recuperar-contrasena.md`

Usar kebab-case, sin tildes en el nombre del archivo. El título visible del Markdown sí puede usar español normal.

## Enlaces del grafo

### Épica → historias

En la sección `Historias de usuario`, enlazar cada HU:

```markdown
- [[HU-001-registrar-usuario]]
- [[HU-002-iniciar-sesion]]
```

### Historia → épica

En metadata y cuerpo, enlazar la épica:

```yaml
epica: "[[EP-001-gestion-de-usuarios]]"
```

### Dependencias entre HU

```yaml
dependencias:
  - "[[HU-001-registrar-usuario]]"
```

Cuando una dependencia sea importante para entender el grafo, la historia dependida puede listar la relación inversa en `relacionadas`.

## Estados

Valores permitidos:
- `Borrador`
- `Pendiente de aprobación`
- `Aprobada`
- `En desarrollo`
- `En validación`
- `Completada`
- `Bloqueada`

## Esfuerzo

Valores permitidos:
- `Bajo`
- `Medio`
- `Alto`
- `Muy alto`

No usar el campo para inferir duración.

## Índice opcional

Si se crea `docs/wiki/scrum/README.md`, usarlo como punto de entrada del grafo con:
- objetivo del proyecto;
- stack detectado/seleccionado;
- enlaces a todas las épicas;
- propuesta de sprints e HU incluidas;
- decisiones o incógnitas pendientes.

No duplicar el detalle de cada HU en el índice.
