---
id: EP-009
tipo: epica
titulo: Automatizaciones posteriores
estado: Pendiente de aprobación
historias: ["[[HU-031-recordar-citas-proximas]]", "[[HU-032-notificar-cambios-de-estado]]", "[[HU-033-generar-resumen-operativo]]"]
dependencias: ["[[EP-008-auditoria-y-contrato]]"]
---

# EP-009 — Automatizaciones posteriores

## Objetivo y valor
Añadir automatizaciones n8n posteriores al núcleo funcional usando únicamente datos sintéticos y exportaciones seguras.

## Alcance y fuera de alcance
- Alcance: recordatorio de próximas citas, aviso de cambio de estado y resumen diario por sede/estado.
- Fuera de alcance: modificar el núcleo, SMS/WhatsApp, SMTP obligatorio o credenciales embebidas.

## Reglas de negocio
- Los JSON de workflow no contienen credenciales, tokens OAuth, URLs privadas ni secretos.
- La instancia central del trainer y las credenciales de Gmail se configuran fuera del repositorio.

## Dependencias
- Depende del contrato y estados verificables de [[EP-008-auditoria-y-contrato]].

## Historias de usuario
- [[HU-031-recordar-citas-proximas]]
- [[HU-032-notificar-cambios-de-estado]]
- [[HU-033-generar-resumen-operativo]]

## Criterio de completitud
- [ ] Cada workflow exportado es reproducible, no filtra secretos y no cambia el núcleo funcional.

## Riesgos e incógnitas
- La conexión real a la instancia del trainer y Gmail requiere coordinación posterior, no está autorizada por este mapa.
