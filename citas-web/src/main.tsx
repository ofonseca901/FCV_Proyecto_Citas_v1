import { StrictMode, useEffect, useRef, useState } from 'react';
import type { FormEvent } from 'react';
import { createRoot } from 'react-dom/client';
import { api, ApiError } from './api';
import type { Registration, Session } from './api';
import './styles.css';

const empty: Registration = { firstName: '', lastName: '', documentType: 'CC', documentNumber: '', email: '', phone: '', password: '' };

function App() {
  const [mode, setMode] = useState<'login' | 'register'>('login');
  const [fields, setFields] = useState(empty);
  const [session, setSession] = useState<Session | null>(null);
  const [busy, setBusy] = useState(false);
  const [visible, setVisible] = useState(false);
  const [error, setError] = useState('');
  const [notice, setNotice] = useState('');
  const sessionRef = useRef<Session | null>(null);
  const renewing = useRef<Promise<Session> | null>(null);
  function remember(value: Session | null) { sessionRef.current = value; setSession(value); }
  // Tokens stay only in memory; a page reload intentionally requires login.
  async function renew() {
    if (renewing.current) return renewing.current;
    const current = sessionRef.current;
    if (!current) throw new ApiError('Inicia sesión nuevamente.', 401);
    const work = api.refresh(current.refreshToken).then(next => {
      if (sessionRef.current === current) remember(next);
      return next;
    }).finally(() => { renewing.current = null; });
    renewing.current = work;
    return work;
  }
  useEffect(() => {
    if (!session) return;
    const timer = window.setTimeout(() => {
      void renew().catch(() => {
        remember(null); setError('Tu sesión terminó. Inicia sesión nuevamente.');
      });
    }, Math.max(1000, (session.expiresIn - 30) * 1000));
    return () => window.clearTimeout(timer);
  }, [session]);

  function changeMode(next: 'login' | 'register') {
    setMode(next); setError(''); setNotice(''); setVisible(false);
    setFields(value => ({ ...value, password: '' }));
  }
  function field(name: keyof Registration, value: string) { setFields(current => ({ ...current, [name]: value })); }

  async function submit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault(); if (busy) return;
    setBusy(true); setError(''); setNotice('');
    try {
      if (new TextEncoder().encode(fields.password).length > 72) throw new Error('La contraseña no puede superar 72 bytes; usa menos caracteres.');
      if (mode === 'register') {
        await api.register({ ...fields, email: fields.email.trim().toLowerCase() });
        setFields(current => ({ ...current, password: '' }));
        setMode('login'); setVisible(false);
        setNotice('Tu cuenta fue creada. Ya puedes iniciar sesión.');
      } else {
        const next = await api.login(fields.email.trim().toLowerCase(), fields.password);
        await api.me(next.accessToken);
        remember(next); setFields(empty);
      }
    } catch (failure) { setError(failure instanceof Error ? failure.message : 'Ocurrió un error. Inténtalo nuevamente.'); }
    finally { setBusy(false); }
  }

  async function logout() {
    if (busy) return;
    setBusy(true); setError('');
    try {
      if (renewing.current) await renewing.current;
      const current = sessionRef.current;
      if (current) {
        try { await api.logout(current.accessToken); }
        catch (failure) {
          if (!(failure instanceof ApiError) || failure.status !== 401) throw failure;
          try { const next = await renew(); await api.logout(next.accessToken); }
          catch (retry) { if (!(retry instanceof ApiError) || retry.status !== 401) throw retry; }
        }
      }
      remember(null); setNotice('Cerraste tu sesión de forma segura.');
    } catch { setError('No se pudo cerrar la sesión en el servidor. Comprueba la conexión y vuelve a intentarlo.'); }
    finally { setBusy(false); }
  }

  return <div className="app-shell">
    <header className="topbar">
      <a className="brand" href="/" aria-label="FCV Citas, inicio"><span className="brand-mark" aria-hidden="true">✚</span><span>FCV <strong>Citas</strong><small>PORTAL DE PACIENTES</small></span></a>
      <span className="lab-badge"><span /> Entorno académico</span>
    </header>
    <main>
      <aside className="welcome-panel" aria-label="Bienvenida">
        <div className="eyebrow"><span /> MÁS CERCA DE TU BIENESTAR</div>
        <h1>Tu cuidado <br />empieza con <br /><em>un encuentro.</em></h1>
        <p className="intro">Un espacio para conectar con tu atención y dar el siguiente paso con tranquilidad.</p>
        <div className="illustration" aria-hidden="true">
          <div className="orbit orbit-one" /><div className="orbit orbit-two" />
          <div className="appointment-card"><div className="mini-label">TU PRÓXIMO PASO</div><div className="card-title">Tiempo para cuidarte <span>↗</span></div><div className="calendar-row"><span>L</span><span>M</span><span>M</span><span>J</span><span>V</span></div><div className="calendar-row dates"><span>12</span><span>13</span><span>14</span><span className="selected">15</span><span>16</span></div><div className="card-foot"><span className="circle-check">✓</span> Todo comienza contigo</div></div>
          <span className="floating-cross">✚</span>
        </div>
        <div className="locations"><span>SEDES DEL LABORATORIO</span><p>HIC · Piedecuesta <i /> ICV · Floridablanca</p></div>
      </aside>
      <section className={`access-panel ${mode === 'register' && !session ? 'registration' : ''}`} aria-label="Acceso de pacientes">
        {session ? <div className="form-content session-content">
          <div className="success-icon" aria-hidden="true">✓</div><span className="section-label">SESIÓN INICIADA</span>
          <h2>Hola, {session.user.firstName}.</h2><p className="subtitle">Tu cuenta está lista para el siguiente paso.</p>
          <div className="identity-card"><span>CUENTA DE PACIENTE</span><strong>{session.user.firstName} {session.user.lastName}</strong><p>{session.user.email}</p></div>
          <p className="scope-note">Este primer módulo permite crear tu cuenta y acceder. El agendamiento de citas estará disponible en una siguiente etapa del laboratorio.</p>
          {error && <div className="message error" role="alert">{error}</div>}
          <button className="primary-button" onClick={() => void logout()} disabled={busy}>{busy ? 'Cerrando sesión…' : 'Cerrar sesión'}<span aria-hidden="true">↗</span></button>
        </div> : <div className="form-content">
          <span className="section-label">BIENVENIDO A TU PORTAL</span>
          <h2>{mode === 'login' ? 'Qué bueno verte.' : 'Empecemos por ti.'}</h2>
          <p className="subtitle">{mode === 'login' ? 'Ingresa a tu cuenta para continuar.' : 'Crea tu cuenta con datos ficticios del laboratorio.'}</p>
          <div className="mode-switch" aria-label="Tipo de acceso"><button aria-pressed={mode === 'login'} disabled={busy} onClick={() => changeMode('login')}>Iniciar sesión</button><button aria-pressed={mode === 'register'} disabled={busy} onClick={() => changeMode('register')}>Crear cuenta</button></div>
          {error && <div className="message error" role="alert">{error}</div>}
          {notice && <div className="message success" role="status">{notice}</div>}
          <form onSubmit={event => void submit(event)} aria-busy={busy}>
            <fieldset disabled={busy}>
              {mode === 'register' && <><div className="field-row"><label>Nombres<input name="firstName" autoComplete="given-name" required maxLength={100} value={fields.firstName} onChange={e => field('firstName', e.target.value)} /></label><label>Apellidos<input name="lastName" autoComplete="family-name" required maxLength={100} value={fields.lastName} onChange={e => field('lastName', e.target.value)} /></label></div>
                <div className="field-row document-row"><label>Tipo de documento<select name="documentType" value={fields.documentType} onChange={e => field('documentType', e.target.value)}><option value="CC">CC</option><option value="CE">CE</option><option value="TI">TI</option><option value="PA">Pasaporte</option><option value="PPT">PPT</option></select></label><label>Número de documento<input name="documentNumber" required pattern="[A-Za-z0-9\-]{3,30}" title="Entre 3 y 30 letras, números o guiones" value={fields.documentNumber} onChange={e => field('documentNumber', e.target.value)} /></label></div>
                <label>Teléfono<input name="phone" type="tel" autoComplete="tel" required pattern="[+0-9 ()\-]{7,25}" title="Entre 7 y 25 caracteres: números, +, espacios, paréntesis o guiones" value={fields.phone} onChange={e => field('phone', e.target.value)} /></label></>}
              <label>Correo electrónico<input name="email" type="email" autoComplete="email" placeholder="tu.correo@ejemplo.com" required maxLength={254} value={fields.email} onChange={e => field('email', e.target.value)} /></label>
              <label htmlFor="password">Contraseña</label><div className="password-field"><input id="password" name="password" type={visible ? 'text' : 'password'} autoComplete={mode === 'register' ? 'new-password' : 'current-password'} placeholder={mode === 'register' ? 'Crea una contraseña segura' : 'Ingresa tu contraseña'} required minLength={mode === 'register' ? 8 : 1} maxLength={72} aria-describedby={mode === 'register' ? 'password-help' : undefined} value={fields.password} onChange={e => field('password', e.target.value)} /><button type="button" aria-label={visible ? 'Ocultar contraseña' : 'Mostrar contraseña'} aria-pressed={visible} onClick={() => setVisible(v => !v)}>{visible ? 'Ocultar' : 'Mostrar'}</button></div>
              {mode === 'register' && <small id="password-help" className="field-help">Mínimo 8 caracteres. Máximo 72 bytes UTF-8.</small>}
              <button className="primary-button" type="submit" disabled={busy}>{busy ? 'Un momento…' : mode === 'login' ? 'Entrar a mi cuenta' : 'Crear mi cuenta'}<span aria-hidden="true">↗</span></button>
            </fieldset>
          </form>
          <p className="privacy-note"><span aria-hidden="true">◇</span> Tu acceso es personal. Usa solo datos ficticios.</p>
        </div>}
        <p className="academic-note">Proyecto de formación · No es un servicio oficial de FCV.</p>
      </section>
    </main>
    <footer><span>FCV Citas · Laboratorio de desarrollo</span><span>Hecho para aprender. Diseñado para cuidar.</span></footer>
  </div>;
}

createRoot(document.getElementById('root')!).render(<StrictMode><App /></StrictMode>);
