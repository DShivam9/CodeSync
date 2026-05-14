import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useAuthStore from '../stores/authStore';
import useRoomStore from '../stores/roomStore';
import { Code2, Plus, LogOut, Hash, Users, Loader2 } from 'lucide-react';

export default function DashboardPage() {
  const navigate = useNavigate();
  const { user, logout } = useAuthStore();
  const { rooms, fetchMyRooms, createRoom, joinRoom, loading } = useRoomStore();
  const [showCreate, setShowCreate] = useState(false);
  const [showJoin, setShowJoin] = useState(false);
  const [roomName, setRoomName] = useState('');
  const [joinSlug, setJoinSlug] = useState('');

  useEffect(() => { fetchMyRooms(); }, [fetchMyRooms]);

  const handleCreate = async (e) => {
    e.preventDefault();
    if (!roomName.trim()) return;
    const room = await createRoom({ name: roomName });
    navigate(`/room/${room.slug}`);
  };

  const handleJoin = async (e) => {
    e.preventDefault();
    if (!joinSlug.trim()) return;
    await joinRoom(joinSlug.trim());
    navigate(`/room/${joinSlug.trim()}`);
  };

  return (
    <div className="min-h-screen bg-surface-dark">
      <nav className="glass border-b border-surface-border px-6 py-4 flex items-center justify-between sticky top-0 z-50">
        <div className="flex items-center gap-3">
          <div className="w-9 h-9 rounded-lg bg-gradient-to-br from-accent to-purple-500 flex items-center justify-center">
            <Code2 className="w-5 h-5 text-white" />
          </div>
          <span className="text-lg font-bold bg-gradient-to-r from-accent to-purple-400 bg-clip-text text-transparent">CodeSync</span>
        </div>
        <div className="flex items-center gap-4">
          <div className="flex items-center gap-2">
            <div className="w-8 h-8 rounded-full flex items-center justify-center text-sm font-bold text-white" style={{ backgroundColor: user?.avatarColor || '#6366f1' }}>
              {user?.username?.[0]?.toUpperCase() || '?'}
            </div>
            <span className="text-sm text-text-secondary hidden sm:block">{user?.displayName || user?.username}</span>
          </div>
          <button onClick={() => { logout(); navigate('/login'); }} className="p-2 rounded-lg hover:bg-surface-light transition-colors text-text-muted hover:text-text-primary">
            <LogOut className="w-5 h-5" />
          </button>
        </div>
      </nav>

      <main className="max-w-4xl mx-auto px-6 py-10">
        <div className="flex items-center justify-between mb-8">
          <div>
            <h1 className="text-3xl font-bold mb-1">Your Rooms</h1>
            <p className="text-text-secondary">Create or join a collaborative coding room</p>
          </div>
          <div className="flex gap-3">
            <button onClick={() => setShowJoin(true)} className="btn-secondary text-sm"><Hash className="w-4 h-4 inline mr-1" />Join</button>
            <button onClick={() => setShowCreate(true)} className="btn-primary text-sm"><Plus className="w-4 h-4 inline mr-1" />New Room</button>
          </div>
        </div>

        {loading ? (
          <div className="flex items-center justify-center py-20"><Loader2 className="w-8 h-8 text-accent animate-spin" /></div>
        ) : rooms.length === 0 ? (
          <div className="card p-12 text-center">
            <Code2 className="w-10 h-10 text-text-muted mx-auto mb-4" />
            <h3 className="text-lg font-semibold mb-2">No rooms yet</h3>
            <p className="text-text-secondary mb-6">Create your first room to start coding</p>
            <button onClick={() => setShowCreate(true)} className="btn-primary text-sm">Create Room</button>
          </div>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            {rooms.map((room) => (
              <button key={room.id} onClick={() => navigate(`/room/${room.slug}`)} className="card p-5 text-left hover:border-accent/30 transition-all group">
                <div className="flex items-start justify-between mb-3">
                  <h3 className="text-lg font-semibold group-hover:text-accent transition-colors">{room.name}</h3>
                  <span className="text-xs bg-accent/10 text-accent px-2 py-1 rounded-md font-mono">#{room.slug}</span>
                </div>
                <div className="flex items-center gap-4 text-text-muted text-sm">
                  <span className="flex items-center gap-1"><Users className="w-3.5 h-3.5" />{room.memberCount}</span>
                  <span className="flex items-center gap-1"><Code2 className="w-3.5 h-3.5" />{room.documentNames?.length || 0} files</span>
                </div>
              </button>
            ))}
          </div>
        )}

        {showCreate && (
          <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-50" onClick={() => setShowCreate(false)}>
            <div className="card p-6 w-full max-w-sm animate-fade-in-up" onClick={(e) => e.stopPropagation()}>
              <h2 className="text-xl font-semibold mb-4">Create New Room</h2>
              <form onSubmit={handleCreate}>
                <input type="text" className="input-field mb-4" placeholder="Room name" value={roomName} onChange={(e) => setRoomName(e.target.value)} autoFocus required />
                <div className="flex gap-3 justify-end">
                  <button type="button" onClick={() => setShowCreate(false)} className="btn-secondary text-sm">Cancel</button>
                  <button type="submit" className="btn-primary text-sm">Create</button>
                </div>
              </form>
            </div>
          </div>
        )}

        {showJoin && (
          <div className="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-50" onClick={() => setShowJoin(false)}>
            <div className="card p-6 w-full max-w-sm animate-fade-in-up" onClick={(e) => e.stopPropagation()}>
              <h2 className="text-xl font-semibold mb-4">Join Room</h2>
              <form onSubmit={handleJoin}>
                <input type="text" className="input-field mb-4" placeholder="Room code (e.g. abc12345)" value={joinSlug} onChange={(e) => setJoinSlug(e.target.value)} autoFocus required />
                <div className="flex gap-3 justify-end">
                  <button type="button" onClick={() => setShowJoin(false)} className="btn-secondary text-sm">Cancel</button>
                  <button type="submit" className="btn-primary text-sm">Join</button>
                </div>
              </form>
            </div>
          </div>
        )}
      </main>
    </div>
  );
}
