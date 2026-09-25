import { useEffect, useState } from 'react';
import type { FormEvent } from 'react';
import { api, s4Api } from './api';
import type { AppointmentSummary, Catalog, ProfessionalAppointment, RescheduleRequest, Session, StatusHistoryEvent } from './api';

const today = new Date().toISOString().slice(0, 10);
const tomorrow = new Date(Date.now() + 86400000).toISOString().slice(0, 10);

function formatDate(value: string) {
  return new Date(value).toLocaleString('es-CO', { dateStyle: 'medium', timeStyle: 'short' });
}

function statusLabel(status: string) {
  return status === 'APPROVED' ? 'Aprobada' : status === 'REQUESTED' ? 'Pendiente de aprobación' : status === 'REJECTED' ? 'Rechazada' : status === 'CANCELLED' ? 'Cancelada' : status === 'COMPLETED' ? 'Completada' : status === 'NO_SHOW' ? 'No asistió' : status;
}

function terminal(status: string) {
  return ['CANCELLED', 'REJECTED', 'COMPLETED', 'NO_SHOW'].includes(status);
}

function Message({ error, notice }: { error?: string; notice?: string }) {
  return <>{error && <p className="message error" role="alert">{error}</p>}{notice && <p className="message success" role="status">{notice}</p>}</>;
}

export function UserAppointments({ session }: { session: Session }) {
  const [items, setItems] = useState<AppointmentSummary[]>([]);
  const [status, setStatus] = useState('ALL');
  const [date, setDate] = useState('');
  const [busy, setBusy] = useState(false);
  const [action, setAction] = useState<number | null>(null);
  const [historyId, setHistoryId] = useState<number | null>(null);
  const [history, setHistory] = useState<StatusHistoryEvent[]>([]);
  const [rescheduleId, setRescheduleId] = useState<number | null>(null);
  const [rescheduleDate, setRescheduleDate] = useState(tomorrow);
  const [slots, setSlots] = useState<{ startAt: string; professionalId: number }[]>([]);
  const [selectedSlot, setSelectedSlot] = useState('');
  const [error, setError] = useState('');
  const [notice, setNotice] = useState('');

  async function load() {
    setBusy(true);
    setError('');
    try { setItems(await s4Api.appointments(session.accessToken, status, date || undefined)); }
    catch (e) { setError(e instanceof Error ? e.message : 'No fue posible consultar tus citas.'); }
    finally { setBusy(false); }
  }

  useEffect(() => { void load(); }, [session.accessToken]);

  async function filter(e: FormEvent) { e.preventDefault(); await load(); }

  async function cancel(item: AppointmentSummary) {
    if (!window.confirm('¿Confirmas la cancelación de esta cita? Esta acción no se puede deshacer.')) return;
    setAction(item.id); setError(''); setNotice('');
    try { await s4Api.cancel(session.accessToken, item.id); setNotice('La cita fue cancelada y su franja quedó liberada.'); await load(); }
    catch (e) { setError(e instanceof Error ? e.message : 'No fue posible cancelar la cita.'); }
    finally { setAction(null); }
  }

  async function showHistory(id: number) {
    setHistoryId(id); setError('');
    try { setHistory(await s4Api.history(session.accessToken, id)); }
    catch (e) { setError(e instanceof Error ? e.message : 'No fue posible consultar el historial.'); }
  }

  async function findRescheduleSlots(item: AppointmentSummary, dateValue: string) {
    if (!item.locationId || !item.specialtyId || !item.professionalId) {
      setError('La cita no contiene los datos necesarios para buscar una franja de reprogramación.');
      return;
    }
    setAction(item.id); setError(''); setSlots([]); setSelectedSlot('');
    try {
      const available = await api.availability(item.locationId, item.specialtyId, dateValue);
      setSlots(available.filter((slot) => slot.professionalId === item.professionalId).map((slot) => ({ startAt: slot.startAt, professionalId: slot.professionalId })));
    } catch (e) { setError(e instanceof Error ? e.message : 'No fue posible buscar franjas.'); }
    finally { setAction(null); }
  }

  async function reschedule(item: AppointmentSummary) {
    if (!selectedSlot) { setError('Selecciona una nueva franja antes de enviar la solicitud.'); return; }
    setAction(item.id); setError(''); setNotice('');
    try { await s4Api.reschedule(session.accessToken, item.id, { startAt: selectedSlot }); setNotice('Solicitud de reprogramación creada; la cita original permanece vigente hasta la decisión administrativa.'); setRescheduleId(null); await load(); }
    catch (e) { setError(e instanceof Error ? e.message : 'No fue posible solicitar la reprogramación.'); }
    finally { setAction(null); }
  }

  return <section className="feature appointment-workspace" aria-labelledby="my-appointments-title">
    <div className="section-heading"><div><p className="section-label">HU-019 · HU-020 · HU-024 · HU-029</p><h2 id="my-appointments-title">Mis citas</h2></div><button type="button" onClick={() => void load()} disabled={busy}>{busy ? 'Actualizando…' : 'Actualizar'}</button></div>
    <form className="filters appointment-filters" onSubmit={filter}>
      <label>Estado<select value={status} onChange={(e) => setStatus(e.target.value)}><option value="ALL">Todos</option><option value="APPROVED">Aprobadas</option><option value="REQUESTED">Pendientes</option><option value="REJECTED">Rechazadas</option><option value="CANCELLED">Canceladas</option><option value="COMPLETED">Completadas</option><option value="NO_SHOW">No asistió</option></select></label>
      <label>Fecha<input type="date" value={date} onChange={(e) => setDate(e.target.value)} /></label>
      <button type="submit" disabled={busy}>{busy ? 'Consultando…' : 'Aplicar filtros'}</button>
    </form>
    <Message error={error} notice={notice} />
    <div className="appointment-list" aria-live="polite">
      {!busy && items.length === 0 && <p className="empty-state">No hay citas que coincidan con los filtros.</p>}
      {items.map((item) => <article className="appointment-item" key={item.id}>
        <div className="appointment-main"><div><strong>{item.specialty}</strong><span>{item.location}</span><span>Profesional {item.professionalCode}</span></div><span className={`status status-${item.status.toLowerCase()}`}>{statusLabel(item.status)}</span></div>
        <p className="appointment-time">{formatDate(item.scheduledStartAt)} · {item.durationMinutes} minutos</p>
        {item.rejectionReason && <p className="reason"><strong>Motivo:</strong> {item.rejectionReason}</p>}
        <div className="action-row">
          <button type="button" className="secondary-button" onClick={() => void showHistory(item.id)} disabled={action === item.id}>{historyId === item.id ? 'Ocultar historial' : 'Ver historial'}</button>
          {item.status === 'APPROVED' && <><button type="button" className="secondary-button" onClick={() => { setRescheduleId(rescheduleId === item.id ? null : item.id); setSlots([]); setSelectedSlot(''); setError(''); }} disabled={action === item.id}>Reprogramar</button><button type="button" className="danger-button" onClick={() => void cancel(item)} disabled={action === item.id}>{action === item.id ? 'Procesando…' : 'Cancelar'}</button></>}
        </div>
        {historyId === item.id && <div className="history" aria-label="Historial de estados">{history.length === 0 ? <p>No hay eventos de historial.</p> : history.map((event) => <div key={event.id}><strong>{statusLabel(event.status)}</strong><span>{formatDate(event.changedAt)} · {event.source}</span>{event.reason && <span>{event.reason}</span>}</div>)}</div>}
        {rescheduleId === item.id && <div className="reschedule-box"><p><strong>Elige una nueva franja</strong><br />Se conservarán el profesional y la especialidad actuales.</p><label>Nueva fecha<input type="date" min={tomorrow} value={rescheduleDate} onChange={(e) => { setRescheduleDate(e.target.value); void findRescheduleSlots(item, e.target.value); }} /></label><label>Franja<select value={selectedSlot} onChange={(e) => setSelectedSlot(e.target.value)}><option value="">Selecciona una franja</option>{slots.map((slot) => <option value={slot.startAt} key={slot.startAt}>{formatDate(slot.startAt)}</option>)}</select></label><button type="button" onClick={() => void reschedule(item)} disabled={!selectedSlot || action === item.id}>{action === item.id ? 'Enviando…' : 'Solicitar reprogramación'}</button></div>}
      </article>)}
    </div>
  </section>;
}

export function AdminReschedules({ session }: { session: Session }) {
  const [items, setItems] = useState<RescheduleRequest[]>([]);
  const [busy, setBusy] = useState(false);
  const [action, setAction] = useState<number | null>(null);
  const [error, setError] = useState('');
  const [notice, setNotice] = useState('');

  async function load() {
    setBusy(true); setError('');
    try { setItems(await s4Api.pendingReschedules(session.accessToken)); }
    catch (e) { setError(e instanceof Error ? e.message : 'No fue posible cargar las reprogramaciones.'); }
    finally { setBusy(false); }
  }
  useEffect(() => { void load(); }, [session.accessToken]);

  async function decide(item: RescheduleRequest, approve: boolean) {
    const reason = approve ? undefined : window.prompt('Motivo obligatorio del rechazo:')?.trim();
    if (!approve && !reason) { setError('El rechazo requiere un motivo.'); return; }
    setAction(item.id); setError(''); setNotice('');
    try { await s4Api.decideReschedule(session.accessToken, item.id, approve, reason); setNotice(approve ? 'Reprogramación aprobada.' : 'Reprogramación rechazada; se conserva la cita original.'); await load(); }
    catch (e) { setError(e instanceof Error ? e.message : 'No fue posible registrar la decisión.'); }
    finally { setAction(null); }
  }

  return <section className="feature admin-workspace" aria-labelledby="reschedules-title"><div className="section-heading"><div><p className="section-label">HU-025 · HU-026</p><h2 id="reschedules-title">Reprogramaciones pendientes</h2></div><button type="button" onClick={() => void load()} disabled={busy}>{busy ? 'Actualizando…' : 'Actualizar'}</button></div><Message error={error} notice={notice}/>{!busy && items.length === 0 && <p className="empty-state">No hay solicitudes de reprogramación pendientes.</p>}<div className="reschedule-list">{items.map((item) => <article className="reschedule-item" key={item.id}><strong>{item.specialty} · {item.location}</strong><span>Paciente: {item.patientFirstName || 'Usuario sintético'}</span><span>Profesional: {item.professionalCode}</span><div className="comparison"><span>Actual<br/><b>{formatDate(item.originalStartAt)}</b></span><span aria-hidden="true">→</span><span>Propuesta<br/><b>{formatDate(item.newStartAt)}</b></span></div><div className="action-row"><button type="button" onClick={() => void decide(item, true)} disabled={action === item.id}>{action === item.id ? 'Procesando…' : 'Aprobar'}</button><button type="button" className="danger-button" onClick={() => void decide(item, false)} disabled={action === item.id}>Rechazar</button></div></article>)}</div></section>;
}

export function ProfessionalWorkspace({ session }: { session: Session }) {
  const [date, setDate] = useState(today);
  const [locations, setLocations] = useState<Catalog[]>([]);
  const [locationId, setLocationId] = useState('');
  const [items, setItems] = useState<ProfessionalAppointment[]>([]);
  const [busy, setBusy] = useState(false);
  const [action, setAction] = useState<number | null>(null);
  const [error, setError] = useState('');
  const [notice, setNotice] = useState('');

  async function load() {
    setBusy(true); setError('');
    try { setItems(await s4Api.professionalAgenda(session.accessToken, date, locationId ? Number(locationId) : undefined)); }
    catch (e) { setError(e instanceof Error ? e.message : 'No fue posible cargar tu agenda.'); }
    finally { setBusy(false); }
  }
  useEffect(() => { void api.locations().then(setLocations).catch(() => undefined); void load(); }, [session.accessToken]);

  async function close(item: ProfessionalAppointment, status: 'COMPLETED' | 'NO_SHOW') {
    setAction(item.id); setError(''); setNotice('');
    try { await s4Api.closeAppointment(session.accessToken, item.id, status); setNotice(`La cita fue marcada como ${status === 'COMPLETED' ? 'completada' : 'no asistida'}.`); await load(); }
    catch (e) { setError(e instanceof Error ? e.message : 'No fue posible cerrar la atención.'); }
    finally { setAction(null); }
  }

  return <section className="feature professional-workspace" aria-labelledby="agenda-title"><div className="section-heading"><div><p className="section-label">HU-027 · HU-028</p><h2 id="agenda-title">Agenda profesional</h2></div><button type="button" onClick={() => void load()} disabled={busy}>{busy ? 'Actualizando…' : 'Actualizar'}</button></div><form className="filters agenda-filters" onSubmit={(e) => { e.preventDefault(); void load(); }}><label>Día<input type="date" value={date} onChange={(e) => setDate(e.target.value)} /></label><label>Sede<select value={locationId} onChange={(e) => setLocationId(e.target.value)}><option value="">Todas las sedes</option>{locations.map((location) => <option value={location.id} key={location.id}>{location.name}</option>)}</select></label><button type="submit" disabled={busy}>Consultar agenda</button></form><Message error={error} notice={notice}/>{!busy && items.length === 0 && <p className="empty-state">No hay citas aprobadas para el filtro seleccionado.</p>}<div className="professional-list">{items.map((item) => <article className="professional-item" key={item.id}><div><strong>{item.specialty}</strong><span>{formatDate(item.scheduledStartAt)} · {item.location}</span><span>Paciente: {item.patientFirstName} {item.patientLastName}</span></div><span className={`status status-${item.status.toLowerCase()}`}>{statusLabel(item.status)}</span>{item.status === 'APPROVED' && <div className="action-row"><button type="button" onClick={() => void close(item, 'COMPLETED')} disabled={action === item.id}>Marcar completada</button><button type="button" className="secondary-button" onClick={() => void close(item, 'NO_SHOW')} disabled={action === item.id}>Marcar no asistió</button></div>}</article>)}</div></section>;
}
