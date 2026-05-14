import { create } from 'zustand';
import { authAPI } from '../api';

const useAuthStore = create((set) => ({
  user: JSON.parse(localStorage.getItem('cs_user') || 'null'),
  token: localStorage.getItem('cs_token') || null,
  loading: false,
  error: null,

  login: async (credentials) => {
    set({ loading: true, error: null });
    try {
      const { data } = await authAPI.login(credentials);
      localStorage.setItem('cs_token', data.token);
      localStorage.setItem('cs_user', JSON.stringify(data));
      set({ user: data, token: data.token, loading: false });
      return data;
    } catch (err) {
      const msg = err.response?.data?.detail || 'Login failed';
      set({ error: msg, loading: false });
      throw new Error(msg);
    }
  },

  register: async (credentials) => {
    set({ loading: true, error: null });
    try {
      const { data } = await authAPI.register(credentials);
      localStorage.setItem('cs_token', data.token);
      localStorage.setItem('cs_user', JSON.stringify(data));
      set({ user: data, token: data.token, loading: false });
      return data;
    } catch (err) {
      const msg = err.response?.data?.detail || 'Registration failed';
      set({ error: msg, loading: false });
      throw new Error(msg);
    }
  },

  logout: () => {
    localStorage.removeItem('cs_token');
    localStorage.removeItem('cs_user');
    set({ user: null, token: null });
  },

  clearError: () => set({ error: null }),
}));

export default useAuthStore;
