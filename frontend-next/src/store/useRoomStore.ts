import { create } from 'zustand';
import { roomAPI, docAPI } from '@/lib/api';

interface Room {
  id: string | number;
  name: string;
  slug: string;
  [key: string]: any;
}

interface Document {
  id: string | number;
  filename: string;
  roomId: string | number;
  [key: string]: any;
}

interface RoomState {
  rooms: Room[];
  currentRoom: Room | null;
  documents: Document[];
  activeDocId: string | number | null;
  loading: boolean;
  error: string | null;
  fetchMyRooms: () => Promise<void>;
  createRoom: (roomData: any) => Promise<Room>;
  joinRoom: (slug: string) => Promise<Room>;
  fetchRoom: (slug: string) => Promise<Room>;
  fetchDocuments: (roomId: string | number) => Promise<void>;
  setActiveDocument: (docId: string | number | null) => void;
  createDocument: (roomId: string | number, filename: string) => Promise<Document>;
  leaveRoom: () => void;
}

export const useRoomStore = create<RoomState>((set, get) => ({
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
    } catch (err: any) {
      set({ error: err.message, loading: false });
    }
  },

  createRoom: async (roomData) => {
    set({ loading: true, error: null });
    try {
      const { data } = await roomAPI.create(roomData);
      set((state) => ({ rooms: [...state.rooms, data], loading: false }));
      return data;
    } catch (err: any) {
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
    } catch (err: any) {
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
    } catch (err: any) {
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
