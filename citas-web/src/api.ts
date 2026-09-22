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
