import { useState } from 'react';
import useRoomStore from '../stores/roomStore';
import { FileCode, FilePlus, ChevronRight, X } from 'lucide-react';
import clsx from 'clsx';

const LANG_ICONS = { javascript: '🟨', typescript: '🔵', python: '🐍', java: '☕', html: '🌐', css: '🎨', json: '📋', markdown: '📝' };

export default function FileTree() {
  const { documents, activeDocId, setActiveDocument, createDocument, currentRoom } = useRoomStore();
  const [showNew, setShowNew] = useState(false);
  const [newFilename, setNewFilename] = useState('');

  const handleCreate = async (e) => {
    e.preventDefault();
    if (!newFilename.trim() || !currentRoom) return;
    await createDocument(currentRoom.id, newFilename.trim());
    setNewFilename('');
    setShowNew(false);
  };

  return (
    <aside className="w-56 bg-surface border-r border-surface-border flex flex-col flex-shrink-0">
      <div className="px-3 py-3 border-b border-surface-border flex items-center justify-between">
        <span className="text-xs font-semibold uppercase tracking-wider text-text-muted">Files</span>
        <button onClick={() => setShowNew(true)} className="p-1 rounded hover:bg-surface-light transition-colors text-text-muted hover:text-accent">
          <FilePlus className="w-4 h-4" />
        </button>
      </div>
      <div className="flex-1 overflow-y-auto py-1">
        {documents.map((doc) => (
          <button
            key={doc.id}
            onClick={() => setActiveDocument(doc.id)}
            className={clsx(
              'w-full flex items-center gap-2 px-3 py-2 text-sm transition-colors',
              doc.id === activeDocId ? 'bg-accent/10 text-accent border-l-2 border-accent' : 'text-text-secondary hover:text-text-primary hover:bg-surface-light'
            )}
          >
            <span className="text-xs">{LANG_ICONS[doc.language] || '📄'}</span>
            <span className="truncate">{doc.filename}</span>
          </button>
        ))}
        {showNew && (
          <form onSubmit={handleCreate} className="px-2 py-1">
            <div className="flex items-center gap-1">
              <input type="text" className="flex-1 bg-surface-light border border-surface-border rounded px-2 py-1 text-xs text-text-primary focus:outline-none focus:border-accent" placeholder="filename.ext" value={newFilename} onChange={(e) => setNewFilename(e.target.value)} autoFocus />
              <button type="button" onClick={() => setShowNew(false)} className="p-0.5 text-text-muted hover:text-text-primary"><X className="w-3 h-3" /></button>
            </div>
          </form>
        )}
      </div>
    </aside>
  );
}
