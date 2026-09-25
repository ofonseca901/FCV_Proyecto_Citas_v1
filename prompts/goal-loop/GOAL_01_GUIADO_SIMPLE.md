# GOAL 1 — guiado sencillo: autenticación JWT

## Objetivo didáctico
Aprender a escribir una condición final verificable sin abarcar todo el producto.

### Codex

```text
/goal Implementa la HU aprobada de registro + login JWT en citas-api sin salir de su alcance. Antes de editar lee la HU, sus criterios de aceptación y DoD. Trabaja por checkpoints. Debes detenerte únicamente cuando: (1) registro de USER funcione con email/documento únicos, (2) password quede hasheado, (3) login emita access y refresh token, (4) refresh válido genere una sesión renovada según el diseño elegido, (5) casos negativos definidos por la HU tengan pruebas, (6) mvn test pase. No implementes UI ni recuperación de contraseña. Si necesitas cambiar el esquema, usa Flyway. Si después de 3 intentos el mismo criterio sigue fallando, pausa y reporta bloqueo con evidencia.
```

### Claude Code equivalente
Usa la misma condición con `/goal ...`. No necesita `/loop` para este ejercicio.
