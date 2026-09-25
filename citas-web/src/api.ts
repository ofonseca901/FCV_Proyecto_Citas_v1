export interface User { id: number; firstName: string; lastName: string; email: string; roles: string[] }
export interface Session { accessToken: string; refreshToken: string; tokenType: string; expiresIn: number; user: User }
export interface Registration {
  firstName: string; lastName: string; documentType: string; documentNumber: string;
  email: string; phone: string; password: string;
}
const base = (import.meta.env.VITE_API_URL || 'http://localhost:8080').replace(/\/$/, '');

export class ApiError extends Error {
  constructor(message: string, public status: number) { super(message); }
}

async function request<T>(path: string, body?: unknown, token?: string, root = '/api/auth'): Promise<T> {
  let response: Response;
  try {
    response = await fetch(`${base}${root}${path}`, {
      method: body === undefined ? 'GET' : 'POST',
      headers: { ...(body === undefined ? {} : { 'Content-Type': 'application/json' }), ...(token ? { Authorization: `Bearer ${token}` } : {}) },
      body: body === undefined ? undefined : JSON.stringify(body),
      signal: AbortSignal.timeout(15000),
    });
  } catch {
    throw new ApiError('No pudimos conectar con el servicio. Revisa tu conexión e inténtalo nuevamente.', 0);
  }
  if (!response.ok) {
    const detail = await response.json().catch(() => null);
    throw new ApiError(detail?.message || 'No pudimos completar la solicitud. Inténtalo nuevamente.', response.status);
  }
  return response.status === 204 ? undefined as T : response.json();
}

export const api = {
  register: (data: Registration) => request<User>('/register', data),
  login: (email: string, password: string) => request<Session>('/login', { email, password }),
  refresh: (refreshToken: string) => request<Session>('/refresh', { refreshToken }),
  me: (token: string) => request<User>('/me', undefined, token),
  logout: (token: string) => request<void>('/logout', {}, token),
  locations: () => request<Catalog[]>('/catalogs/locations', undefined, undefined, '/api'),
  specialties: () => request<Specialty[]>('/catalogs/specialties', undefined, undefined, '/api'),
  availability: (locationId: number, specialtyId: number, date: string) => request<Availability[]>(`/availability?locationId=${locationId}&specialtyId=${specialtyId}&date=${date}`, undefined, undefined, '/api'),
  book: (token: string, data: Booking) => request<{id:number}>('/appointments', data, token, '/api'),
  requested: (token: string) => request<RequestedAppointment[]>('/admin/appointments/requested', undefined, token, '/api'),
  decide: (token: string, id: number, approve: boolean, reason?: string) => request<void>(`/admin/appointments/${id}/decision`, { approve, reason }, token, '/api'),
};
export interface Catalog { id:number; code:string; name:string }
export interface Specialty extends Catalog { durationMinutes:number; general:boolean; requiresApproval:boolean }
export interface Availability { professionalId:number; locationId:number; professionalCode:string; firstName:string; lastName:string; startAt:string; durationMinutes:number }
export interface Booking { professionalId:number; locationId:number; specialtyId:number; startAt:string }
export interface RequestedAppointment { id:number; scheduledStartAt:string; patientFirstName:string; patientLastName:string; professionalCode:string; specialty:string; location:string }

export interface AppointmentSummary {
  id: number;
  scheduledStartAt: string;
  scheduledEndAt: string;
  status: string;
  rejectionReason?: string | null;
  professionalId?: number;
  locationId?: number;
  specialtyId?: number;
  professionalCode: string;
  specialty: string;
  location: string;
  durationMinutes: number;
}

export interface RescheduleRequest {
  id: number;
  appointmentId: number;
  originalStartAt: string;
  originalEndAt?: string;
  newStartAt: string;
  newEndAt?: string;
  status: string;
  professionalCode: string;
  specialty: string;
  location: string;
  patientFirstName?: string;
  patientLastName?: string;
}

export interface ProfessionalAppointment {
  id: number;
  scheduledStartAt: string;
  scheduledEndAt: string;
  status: string;
  patientFirstName: string;
  patientLastName: string;
  specialty: string;
  location: string;
}

export interface StatusHistoryEvent {
  id: number;
  status: string;
  source: string;
  reason?: string | null;
  changedAt: string;
}

export interface RescheduleBooking { startAt: string }

export const s4Api = {
  appointments: (token: string, status?: string, date?: string) => {
    const query = new URLSearchParams();
    if (status && status !== 'ALL') query.set('status', status);
    if (date) query.set('date', date);
    return request<AppointmentSummary[]>(`/appointments/mine${query.size ? `?${query}` : ''}`, undefined, token, '/api');
  },
  cancel: (token: string, id: number, reason?: string) => request<void>(`/appointments/${id}/cancel`, reason ? { reason } : {}, token, '/api'),
  reschedule: (token: string, id: number, data: RescheduleBooking) => request<{ id: number }>(`/appointments/${id}/reschedule`, data, token, '/api'),
  history: (token: string, id: number) => request<StatusHistoryEvent[]>(`/appointments/${id}/history`, undefined, token, '/api'),
  professionalAgenda: (token: string, date: string, locationId?: number) => {
    const query = new URLSearchParams({ date });
    if (locationId) query.set('locationId', String(locationId));
    return request<ProfessionalAppointment[]>(`/professional/appointments?${query}`, undefined, token, '/api');
  },
  closeAppointment: (token: string, id: number, status: 'COMPLETED' | 'NO_SHOW') => request<void>(`/professional/appointments/${id}/close`, { status }, token, '/api'),
  pendingReschedules: (token: string) => request<RescheduleRequest[]>('/admin/reschedules/pending', undefined, token, '/api'),
  decideReschedule: (token: string, id: number, approve: boolean, reason?: string) => request<void>(`/admin/reschedules/${id}/decision`, { approve, reason }, token, '/api'),
};
