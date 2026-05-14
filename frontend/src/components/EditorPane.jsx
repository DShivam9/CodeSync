import { useRef, useEffect } from 'react';
import Editor from '@monaco-editor/react';
import useEditorStore from '../stores/editorStore';

export default function EditorPane({ onChange, onCursorChange }) {
  const { content, language, cursors } = useEditorStore();
  const editorRef = useRef(null);
  const decorationsRef = useRef([]);

  const handleMount = (editor) => {
    editorRef.current = editor;
    editor.onDidChangeCursorPosition(onCursorChange);
  };

  // Update remote cursor decorations
  useEffect(() => {
    const editor = editorRef.current;
    if (!editor) return;

    const newDecorations = Object.values(cursors).map((cursor) => ({
      range: {
        startLineNumber: cursor.line,
        startColumn: cursor.column,
        endLineNumber: cursor.line,
        endColumn: cursor.column + 1,
      },
      options: {
        className: 'remote-cursor',
        beforeContentClassName: 'remote-cursor-label',
        stickiness: 1,
        hoverMessage: { value: cursor.username },
        overviewRuler: { color: cursor.color, position: 1 },
        minimap: { color: cursor.color, position: 1 },
      },
    }));

    decorationsRef.current = editor.deltaDecorations(decorationsRef.current, newDecorations);
  }, [cursors]);

  return (
    <Editor
      height="100%"
      language={language}
      value={content}
      theme="vs-dark"
      onChange={onChange}
      onMount={handleMount}
      options={{
        fontSize: 14,
        fontFamily: "'JetBrains Mono', 'Fira Code', monospace",
        fontLigatures: true,
        lineNumbers: 'on',
        minimap: { enabled: true, maxColumn: 80 },
        scrollBeyondLastLine: false,
        smoothScrolling: true,
        cursorSmoothCaretAnimation: 'on',
        cursorBlinking: 'smooth',
        renderLineHighlight: 'gutter',
        bracketPairColorization: { enabled: true },
        padding: { top: 16 },
        suggest: { preview: true },
        tabSize: 2,
        wordWrap: 'off',
        automaticLayout: true,
      }}
    />
  );
}
