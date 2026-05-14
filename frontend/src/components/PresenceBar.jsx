import useEditorStore from '../stores/editorStore';

export default function PresenceBar() {
  const { onlineUsers } = useEditorStore();

  if (!onlineUsers || onlineUsers.length === 0) return null;

  return (
    <div className="flex items-center gap-1">
      {onlineUsers.slice(0, 5).map((u, i) => (
        <div
          key={u.userId || i}
          className="w-7 h-7 rounded-full flex items-center justify-center text-xs font-bold text-white border-2 border-surface-dark -ml-1 first:ml-0 relative group"
          style={{ backgroundColor: u.color || '#6366f1' }}
          title={u.username}
        >
          {(u.username || '?')[0].toUpperCase()}
          <div className="absolute w-2 h-2 bg-green-400 rounded-full -bottom-0.5 -right-0.5 border border-surface-dark animate-pulse-dot" />
          <div className="absolute bottom-full left-1/2 -translate-x-1/2 mb-2 px-2 py-1 bg-surface-light border border-surface-border rounded text-xs text-text-primary whitespace-nowrap opacity-0 group-hover:opacity-100 transition-opacity pointer-events-none">
            {u.username}
          </div>
        </div>
      ))}
      {onlineUsers.length > 5 && (
        <div className="w-7 h-7 rounded-full bg-surface-light border-2 border-surface-dark flex items-center justify-center text-xs text-text-muted -ml-1">
          +{onlineUsers.length - 5}
        </div>
      )}
    </div>
  );
}
