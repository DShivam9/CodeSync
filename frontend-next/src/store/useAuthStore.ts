import { create } from 'zustand';
import { authAPI } from '@/lib/api';

interface AuthState {
  user: any;
  token: string | null;
  loading: boolean;
  error: string | null;
  login: (credentials: any) => Promise<any>;
  register: (credentials: any) => Promise<any>;
  logout: () => void;
  clearError: () => void;
}

export const useAuthStore = create<AuthState>((set) => ({
  user: typeof window !== 'undefined' ? JSON.parse(localStorage.getItem('cs_user') || 'null') : null,
  token: typeof window !== 'undefined' ? localStorage.getItem('cs_token') || null : null,
  loading: false,
  error: null,

  login: async (credentials) => {
    set({ loading: true, error: null });
    try {
      const { data } = await authAPI.login(credentials);
      if (typeof window !== 'undefined') {
        localStorage.setItem('cs_token', data.token);
        localStorage.setItem('cs_user', JSON.stringify(data));
      }
      set({ user: data, token: data.token, loading: false });
      return data;
    } catch (err: any) {
      const msg = err.response?.data?.detail || 'Login failed';
      set({ error: msg, loading: false });
      throw new Error(msg);
    }
  },

  register: async (credentials) => {
    set({ loading: true, error: null });
    try {
      const { data } = await authAPI.register(credentials);
      if (typeof window !== 'undefined') {
        localStorage.setItem('cs_token', data.token);
        localStorage.setItem('cs_user', JSON.stringify(data));
      }
      set({ user: data, token: data.token, loading: false });
      return data;
    } catch (err: any) {
      const msg = err.response?.data?.detail || 'Registration failed';
      set({ error: msg, loading: false });
      throw new Error(msg);
    }
  },

  logout: () => {
    if (typeof window !== 'undefined') {
      localStorage.removeItem('cs_token');
      localStorage.removeItem('cs_user');
    }
    set({ user: null, token: null });
  },

  clearError: () => set({ error: null }),
}));
