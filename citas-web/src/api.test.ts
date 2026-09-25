import { describe, expect, it } from 'vitest';
import { ApiError } from './api';

describe('API errors', () => {
  it('preserves a conflict status for slot contention feedback', () => {
    const error = new ApiError('Horario ocupado', 409);
    expect(error.status).toBe(409);
    expect(error.message).toBe('Horario ocupado');
  });
});
