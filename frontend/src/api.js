import axios from 'axios';

const API_BASE = import.meta.env.VITE_API_URL || '/api';

const api = axios.create({
  baseURL: API_BASE,
  headers: { 'Content-Type': 'application/json' },
});

// Request interceptor: attach JWT token
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('cs_token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Response interceptor: handle 401
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('cs_token');
      localStorage.removeItem('cs_user');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// Auth
export const authAPI = {
  register: (data) => api.post('/auth/register', data),
  login: (data) => api.post('/auth/login', data),
  refresh: () => api.post('/auth/refresh'),
};

// Rooms
export const roomAPI = {
  create: (data) => api.post('/rooms', data),
  getBySlug: (slug) => api.get(`/rooms/${slug}`),
  getMyRooms: () => api.get('/rooms/my'),
  join: (slug) => api.post(`/rooms/${slug}/join`),
  leave: (slug) => api.delete(`/rooms/${slug}/leave`),
};

// Documents
export const docAPI = {
  getByRoom: (roomId) => api.get(`/documents/room/${roomId}`),
  getById: (docId) => api.get(`/documents/${docId}`),
  create: (roomId, filename) => api.post(`/documents/room/${roomId}?filename=${encodeURIComponent(filename)}`),
  delete: (docId) => api.delete(`/documents/${docId}`),
};

// Users
export const userAPI = {
  me: () => api.get('/users/me'),
};

export default api;
