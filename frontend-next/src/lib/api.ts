import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
  headers: { 'Content-Type': 'application/json' },
});

// Request interceptor: attach JWT token
api.interceptors.request.use((config) => {
  if (typeof window !== 'undefined') {
    const token = localStorage.getItem('cs_token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
  }
  return config;
});

// Response interceptor: handle 401
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      if (typeof window !== 'undefined') {
        localStorage.removeItem('cs_token');
        localStorage.removeItem('cs_user');
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);

// Auth
export const authAPI = {
  register: (data: any) => api.post('/auth/register', data),
  login: (data: any) => api.post('/auth/login', data),
  refresh: () => api.post('/auth/refresh'),
};

// Rooms
export const roomAPI = {
  create: (data: any) => api.post('/rooms', data),
  getBySlug: (slug: string) => api.get(`/rooms/${slug}`),
  getMyRooms: () => api.get('/rooms/my'),
  join: (slug: string) => api.post(`/rooms/${slug}/join`),
  leave: (slug: string) => api.delete(`/rooms/${slug}/leave`),
};

// Documents
export const docAPI = {
  getByRoom: (roomId: number | string) => api.get(`/documents/room/${roomId}`),
  getById: (docId: number | string) => api.get(`/documents/${docId}`),
  create: (roomId: number | string, filename: string) => 
    api.post(`/documents/room/${roomId}?filename=${encodeURIComponent(filename)}`),
  delete: (docId: number | string) => api.delete(`/documents/${docId}`),
};

// Users
export const userAPI = {
  me: () => api.get('/users/me'),
};

export default api;
