import { nanoid } from 'nanoid';

/**
 * Client-side OT utility.
 * Generates operations and transforms them locally before sending.
 */
export const OTOperationType = {
  INSERT: 'INSERT',
  DELETE: 'DELETE',
  RETAIN: 'RETAIN',
};

export function createInsertOp(userId, docId, position, content, revision) {
  return {
    id: nanoid(),
    userId,
    documentId: docId,
    type: OTOperationType.INSERT,
    position,
    content,
    length: content.length,
    revision,
  };
}

export function createDeleteOp(userId, docId, position, length, revision) {
  return {
    id: nanoid(),
    userId,
    documentId: docId,
    type: OTOperationType.DELETE,
    position,
    length,
    revision,
  };
}

/**
 * Transform incoming (server) operation against local pending operation.
 * This mirrors the server-side OT engine logic.
 */
export function transformOperation(incoming, local) {
  if (!local) return incoming;

  const { type: iType, position: iPos } = incoming;
  const { type: lType, position: lPos } = local;

  // INSERT vs INSERT
  if (iType === 'INSERT' && lType === 'INSERT') {
    if (iPos <= lPos) return incoming;
    return { ...incoming, position: iPos + (local.content?.length || 0) };
  }

  // INSERT vs DELETE
  if (iType === 'INSERT' && lType === 'DELETE') {
    if (iPos <= lPos) return incoming;
    return { ...incoming, position: Math.max(lPos, iPos - local.length) };
  }

  // DELETE vs INSERT
  if (iType === 'DELETE' && lType === 'INSERT') {
    if (iPos + incoming.length <= lPos) return incoming;
    if (iPos >= lPos) return { ...incoming, position: iPos + (local.content?.length || 0) };
    return incoming;
  }

  // DELETE vs DELETE
  if (iType === 'DELETE' && lType === 'DELETE') {
    if (iPos + incoming.length <= lPos) return incoming;
    if (iPos >= lPos + local.length) return { ...incoming, position: iPos - local.length };
    return { ...incoming, type: 'RETAIN', length: 0 };
  }

  return incoming;
}

/**
 * Apply a text operation to content string.
 */
export function applyOperation(content, op) {
  if (op.type === 'INSERT') {
    const pos = Math.min(op.position, content.length);
    return content.slice(0, pos) + op.content + content.slice(pos);
  }
  if (op.type === 'DELETE') {
    const start = Math.min(op.position, content.length);
    const end = Math.min(start + op.length, content.length);
    return content.slice(0, start) + content.slice(end);
  }
  return content;
}
