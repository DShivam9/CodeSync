import { nanoid } from 'nanoid';

export enum OTOperationType {
  INSERT = 'INSERT',
  DELETE = 'DELETE',
  RETAIN = 'RETAIN',
}

export interface Operation {
  id: string;
  userId: string;
  documentId: string;
  type: OTOperationType;
  position: number;
  content?: string;
  length: number;
  revision: number;
}

/**
 * OT Client manages the 'Jupiter' state machine.
 * It tracks acknowledged revision and buffers local unacknowledged operations.
 */
export class OTClient {
  private content: string;
  private revision: number;
  private awaitingAck: Operation | null = null;
  private pending: Operation[] = [];

  constructor(initialContent: string, initialRevision: number) {
    this.content = initialContent;
    this.revision = initialRevision;
  }

  getContent() { return this.content; }
  getRevision() { return this.revision; }

  /**
   * Apply a local operation.
   * Transforms it against awaitingAck and pending buffers.
   */
  applyLocal(op: Operation): Operation {
    // Apply to local shadow content
    this.content = this.applyToContent(this.content, op);

    // If we are waiting for an ACK, this op needs to be buffered
    if (this.awaitingAck) {
      this.pending.push(op);
    } else {
      this.awaitingAck = op;
    }

    return op;
  }

  /**
   * Handle an ACK from the server for our own operation.
   */
  handleAck(revision: number) {
    this.revision = revision;
    this.awaitingAck = null;

    if (this.pending.length > 0) {
      this.awaitingAck = this.pending.shift() || null;
    }

    return this.awaitingAck;
  }

  /**
   * Handle an incoming operation from another user.
   */
  handleRemote(remoteOp: Operation): Operation | null {
    // If it's our own operation (same ID), ignore (we handle it in handleAck)
    if (this.awaitingAck && remoteOp.id === this.awaitingAck.id) return null;

    // Transform remoteOp against awaitingAck and pending
    let transformedRemote = remoteOp;
    if (this.awaitingAck) {
      transformedRemote = this.transform(transformedRemote, this.awaitingAck);
    }

    for (let i = 0; i < this.pending.length; i++) {
      transformedRemote = this.transform(transformedRemote, this.pending[i]);
    }

    // Apply transformedRemote to our local content
    this.content = this.applyToContent(this.content, transformedRemote);
    this.revision++;

    // Crucially, we also need to transform our local buffers against the remote operation
    if (this.awaitingAck) {
      this.awaitingAck = this.transform(this.awaitingAck, remoteOp);
    }
    for (let i = 0; i < this.pending.length; i++) {
      this.pending[i] = this.transform(this.pending[i], remoteOp);
    }

    return transformedRemote;
  }

  private applyToContent(content: string, op: Operation): string {
    if (op.type === OTOperationType.INSERT) {
      const pos = Math.min(op.position, content.length);
      return content.slice(0, pos) + op.content + content.slice(pos);
    }
    if (op.type === OTOperationType.DELETE) {
      const start = Math.min(op.position, content.length);
      const end = Math.min(start + op.length, content.length);
      return content.slice(0, start) + content.slice(end);
    }
    return content;
  }

  private transform(incoming: Operation, other: Operation): Operation {
    const iPos = incoming.position;
    const oPos = other.position;

    // INSERT vs INSERT
    if (incoming.type === OTOperationType.INSERT && other.type === OTOperationType.INSERT) {
      if (iPos < oPos || (iPos === oPos && incoming.userId < other.userId)) return incoming;
      return { ...incoming, position: iPos + (other.content?.length || 0) };
    }

    // INSERT vs DELETE
    if (incoming.type === OTOperationType.INSERT && other.type === OTOperationType.DELETE) {
      if (iPos <= oPos) return incoming;
      if (iPos >= oPos + other.length) return { ...incoming, position: iPos - other.length };
      return { ...incoming, position: oPos };
    }

    // DELETE vs INSERT
    if (incoming.type === OTOperationType.DELETE && other.type === OTOperationType.INSERT) {
      if (iPos + incoming.length <= oPos) return incoming;
      if (iPos >= oPos) return { ...incoming, position: iPos + (other.content?.length || 0) };
      // Intersection: incoming covers the insertion point
      return { ...incoming, length: incoming.length + (other.content?.length || 0) };
    }

    // DELETE vs DELETE
    if (incoming.type === OTOperationType.DELETE && other.type === OTOperationType.DELETE) {
      if (iPos + incoming.length <= oPos) return incoming;
      if (iPos >= oPos + other.length) return { ...incoming, position: iPos - other.length };
      
      // Overlap cases
      const start = Math.max(iPos, oPos);
      const end = Math.min(iPos + incoming.length, oPos + other.length);
      if (start >= end) return incoming; // Should be handled by cases above
      
      return { ...incoming, length: Math.max(0, incoming.length - (end - start)) };
    }

    return incoming;
  }

  static createInsert(userId: string, docId: string, position: number, content: string, revision: number): Operation {
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

  static createDelete(userId: string, docId: string, position: number, length: number, revision: number): Operation {
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
}
