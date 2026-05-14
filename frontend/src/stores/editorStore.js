import { create } from 'zustand';

const useEditorStore = create((set) => ({
  content: '',
  language: 'javascript',
  version: 0,
  cursors: {},
  onlineUsers: [],
  isConnected: false,

  setContent: (content) => set({ content }),
  setLanguage: (language) => set({ language }),
  setVersion: (version) => set({ version }),

  updateCursor: (userId, cursor) =>
    set((state) => ({
      cursors: { ...state.cursors, [userId]: cursor },
    })),

  removeCursor: (userId) =>
    set((state) => {
      const { [userId]: _, ...rest } = state.cursors;
      return { cursors: rest };
    }),

  setOnlineUsers: (users) => set({ onlineUsers: users }),

  addOnlineUser: (user) =>
    set((state) => {
      const exists = state.onlineUsers.some((u) => u.userId === user.userId);
      if (exists) return state;
      return { onlineUsers: [...state.onlineUsers, user] };
    }),

  removeOnlineUser: (userId) =>
    set((state) => ({
      onlineUsers: state.onlineUsers.filter((u) => u.userId !== userId),
    })),

  setConnected: (connected) => set({ isConnected: connected }),

  reset: () =>
    set({
      content: '',
      language: 'javascript',
      version: 0,
      cursors: {},
      onlineUsers: [],
      isConnected: false,
    }),
}));

export default useEditorStore;
