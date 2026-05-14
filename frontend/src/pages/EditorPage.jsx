import { useEffect, useRef, useCallback } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import useAuthStore from '../stores/authStore';
import useRoomStore from '../stores/roomStore';
import useEditorStore from '../stores/editorStore';
import { connectWebSocket, disconnectWebSocket, subscribeToOperations, subscribeToCursors, subscribeToPresence, sendOperation, sendCursor, sendJoinRoom, sendLeaveRoom } from '../services/websocket';
import { docAPI } from '../api';
import EditorPane from '../components/EditorPane';
import FileTree from '../components/FileTree';
import PresenceBar from '../components/PresenceBar';
import { ArrowLeft, Wifi, WifiOff } from 'lucide-react';

export default function EditorPage() {
  const { slug } = useParams();
  const navigate = useNavigate();
  const { user, token } = useAuthStore();
  const { currentRoom, fetchRoom, fetchDocuments, documents, activeDocId, setActiveDocument, leaveRoom } = useRoomStore();
  const { content, setContent, setLanguage, setVersion, version, updateCursor, removeCursor, setOnlineUsers, addOnlineUser, removeOnlineUser, isConnected, setConnected, reset } = useEditorStore();
  const contentRef = useRef(content);
  const versionRef = useRef(version);

  useEffect(() => { contentRef.current = content; }, [content]);
  useEffect(() => { versionRef.current = version; }, [version]);

  useEffect(() => {
    if (!token) { navigate('/login'); return; }
    const init = async () => {
      const room = await fetchRoom(slug);
      await fetchDocuments(room.id);
      connectWebSocket(token, () => {
        setConnected(true);
        sendJoinRoom(room.id, { userId: user.userId, username: user.username, color: user.avatarColor });
        subscribeToPresence(room.id, (data) => {
          if (data.type === 'JOIN') addOnlineUser({ userId: data.userId, username: data.username, color: data.color });
          if (data.type === 'LEAVE') { removeOnlineUser(data.userId); removeCursor(data.userId); }
          if (data.onlineUsers) setOnlineUsers(Array.isArray(data.onlineUsers) ? data.onlineUsers : []);
        });
      }, () => setConnected(false));
    };
    init();
    return () => {
      if (currentRoom) sendLeaveRoom(currentRoom.id, { userId: user?.userId, username: user?.username });
      disconnectWebSocket();
      leaveRoom();
      reset();
    };
  }, [slug, token]);

  useEffect(() => {
    if (!activeDocId || !isConnected) return;
    const loadDoc = async () => {
      const { data } = await docAPI.getById(activeDocId);
      setContent(data.content || '');
      setLanguage(data.language || 'javascript');
      setVersion(data.version || 0);
    };
    loadDoc();
    const unsubOps = subscribeToOperations(activeDocId, (op) => {
      if (op.userId === user?.userId) return;
      if (op.type === 'INSERT') {
        const c = contentRef.current;
        const pos = Math.min(op.position, c.length);
        setContent(c.slice(0, pos) + op.content + c.slice(pos));
      } else if (op.type === 'DELETE') {
        const c = contentRef.current;
        const start = Math.min(op.position, c.length);
        const end = Math.min(start + op.length, c.length);
        setContent(c.slice(0, start) + c.slice(end));
      }
      setVersion(op.revision + 1);
    });
    const unsubCursors = subscribeToCursors(activeDocId, (cursor) => {
      if (cursor.userId === user?.userId) return;
      updateCursor(cursor.userId, cursor);
    });
    return () => { unsubOps?.(); unsubCursors?.(); };
  }, [activeDocId, isConnected]);

  const handleEditorChange = useCallback((value, event) => {
    if (!activeDocId || !isConnected) return;
    const changes = event.changes;
    changes.forEach((change) => {
      const offset = change.rangeOffset;
      if (change.text.length > 0) {
        sendOperation(activeDocId, { id: crypto.randomUUID(), type: 'INSERT', position: offset, content: change.text, length: change.text.length, revision: versionRef.current });
      }
      if (change.rangeLength > 0) {
        sendOperation(activeDocId, { id: crypto.randomUUID(), type: 'DELETE', position: offset, length: change.rangeLength, revision: versionRef.current });
      }
    });
    setContent(value);
  }, [activeDocId, isConnected]);

  const handleCursorChange = useCallback((e) => {
    if (!activeDocId || !user) return;
    const pos = e.position;
    sendCursor(activeDocId, { userId: user.userId, username: user.username, color: user.avatarColor, line: pos.lineNumber, column: pos.column });
  }, [activeDocId, user]);

  return (
    <div className="h-screen flex flex-col bg-surface-dark overflow-hidden">
      <header className="glass border-b border-surface-border px-4 py-2 flex items-center justify-between flex-shrink-0">
        <div className="flex items-center gap-3">
          <button onClick={() => navigate('/dashboard')} className="p-1.5 rounded-lg hover:bg-surface-light transition-colors text-text-muted"><ArrowLeft className="w-4 h-4" /></button>
          <span className="font-semibold text-sm">{currentRoom?.name || 'Loading...'}</span>
          <span className="text-xs text-text-muted font-mono">#{currentRoom?.slug}</span>
        </div>
        <div className="flex items-center gap-3">
          <PresenceBar />
          <div className="flex items-center gap-1.5 text-xs">
            {isConnected ? <><Wifi className="w-3.5 h-3.5 text-green-400" /><span className="text-green-400">Connected</span></> : <><WifiOff className="w-3.5 h-3.5 text-red-400" /><span className="text-red-400">Offline</span></>}
          </div>
        </div>
      </header>
      <div className="flex flex-1 overflow-hidden">
        <FileTree />
        <div className="flex-1 relative">
          <EditorPane onChange={handleEditorChange} onCursorChange={handleCursorChange} />
        </div>
      </div>
    </div>
  );
}
