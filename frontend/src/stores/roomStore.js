import { create } from 'zustand';
import { roomAPI, docAPI } from '../api';

const useRoomStore = create((set, get) => ({
  rooms: [],
  currentRoom: null,
  documents: [],
  activeDocId: null,
  loading: false,
  error: null,

  fetchMyRooms: async () => {
    set({ loading: true });
    try {
      const { data } = await roomAPI.getMyRooms();
      set({ rooms: data, loading: false });
    } catch (err) {
      set({ error: err.message, loading: false });
    }
  },

  createRoom: async (roomData) => {
    set({ loading: true, error: null });
    try {
      const { data } = await roomAPI.create(roomData);
      set((state) => ({ rooms: [...state.rooms, data], loading: false }));
      return data;
    } catch (err) {
      const msg = err.response?.data?.detail || 'Failed to create room';
      set({ error: msg, loading: false });
      throw new Error(msg);
    }
  },

  joinRoom: async (slug) => {
    set({ loading: true, error: null });
    try {
      const { data } = await roomAPI.join(slug);
      set({ currentRoom: data, loading: false });
      return data;
    } catch (err) {
      set({ error: err.message, loading: false });
      throw err;
    }
  },

  fetchRoom: async (slug) => {
    set({ loading: true, error: null });
    try {
      const { data } = await roomAPI.getBySlug(slug);
      set({ currentRoom: data, loading: false });
      return data;
    } catch (err) {
      set({ error: err.message, loading: false });
      throw err;
    }
  },

  fetchDocuments: async (roomId) => {
    try {
      const { data } = await docAPI.getByRoom(roomId);
      set({ documents: data });
      if (data.length > 0 && !get().activeDocId) {
        set({ activeDocId: data[0].id });
      }
    } catch (err) {
      console.error('Failed to fetch documents:', err);
    }
  },

  setActiveDocument: (docId) => set({ activeDocId: docId }),

  createDocument: async (roomId, filename) => {
    try {
      const { data } = await docAPI.create(roomId, filename);
      set((state) => ({
        documents: [...state.documents, data],
        activeDocId: data.id,
      }));
      return data;
    } catch (err) {
      throw err;
    }
  },

  leaveRoom: () => set({ currentRoom: null, documents: [], activeDocId: null }),
}));

export default useRoomStore;
