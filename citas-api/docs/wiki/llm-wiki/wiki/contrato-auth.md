# Contrato REST — S2

Base local: `http://localhost:8080`. JSON camelCase. Errores conocidos: `{ "code": "INVALID|DUPLICATE|UNAUTHORIZED", "message": "texto" }`. No enviar campos adicionales. Respuestas de sesión con `Cache-Control: no-store`.

| Método | Ruta | Petición | Éxito |
|---|---|---|---|
| POST | `/api/auth/register` | firstName, lastName, documentType, documentNumber, email, phone, password | 201 User |
| POST | `/api/auth/login` | email, password | 200 Session |
| POST | `/api/auth/refresh` | refreshToken | 200 Session, invalida refresh anterior |
| GET | `/api/auth/me` | Authorization: Bearer accessToken | 200 User |
| POST | `/api/auth/logout` | Authorization: Bearer accessToken | 204, revoca sesión |
| GET | `/actuator/health` | sin autenticación | 200 con status UP cuando saludable |

`User`: id numérico, firstName, lastName, email, roles (array). Nunca devuelve password/hash.

`Session`: accessToken, refreshToken, tokenType=`Bearer`, expiresIn (segundos), user. No incluir tokens en evidencia o logs.

## Validación
Nombres/apellidos 1–100 caracteres no blancos. Email válido hasta 254. Documento tipo CC/CE/TI/PA/PPT y número 3–30 letras ASCII/dígitos/guiones. Teléfono 7–25 caracteres (dígitos, +, espacios, paréntesis, guiones). Contraseña de registro 8 caracteres mínimo, 72 bytes UTF-8 máximo.

400: datos inválidos/campos desconocidos. 409: email o par tipo-documento repetido. 401: credenciales, token o sesión inválidos. Login no diferencia cuenta inexistente de contraseña incorrecta. Roles se derivan de BD y no se aceptan en registro.

Renovación: no enviar access como refresh. El límite de refresh no se extiende con cada renovación. Logout es por sesión, no global. Frontend permite repetir logout tras fallo de red, sin fingir revocación.

CORS: `FRONTEND_ORIGIN`, por defecto `http://localhost:5173`. Si se cambia el puerto o hostname del frontend se debe actualizar el origen y reiniciar backend.
