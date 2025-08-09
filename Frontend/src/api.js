import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
});

// Add JWT to requests
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const login = (email, password) =>
  api.post('/auth/login', { email, password });

export const getEvents = (city, category) =>
  api.get('/events', { params: { city, category } });

export const getChatHistory = (eventId) =>
  api.get(`/chat/${eventId}`);

export default api;