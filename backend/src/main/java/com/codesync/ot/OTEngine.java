package com.codesync.ot;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Core Operational Transformation Engine.
 *
 * Implements the Jupiter OT algorithm for transforming concurrent operations.
 *
 * Key invariant: transform(op1, op2) returns op1' such that
 * applying op2 then op1' yields the same result as
 * applying op1 then op2'.
 */
@Component
public class OTEngine {

    /**
     * Transform operation 'incoming' against 'concurrent' operation.
     * Returns a new operation that can be safely applied after 'concurrent'.
     */
    public Operation transform(Operation incoming, Operation concurrent) {
        if (incoming.getType() == OperationType.INSERT && concurrent.getType() == OperationType.INSERT) {
            return transformInsertInsert(incoming, concurrent);
        } else if (incoming.getType() == OperationType.INSERT && concurrent.getType() == OperationType.DELETE) {
            return transformInsertDelete(incoming, concurrent);
        } else if (incoming.getType() == OperationType.DELETE && concurrent.getType() == OperationType.INSERT) {
            return transformDeleteInsert(incoming, concurrent);
        } else if (incoming.getType() == OperationType.DELETE && concurrent.getType() == OperationType.DELETE) {
            return transformDeleteDelete(incoming, concurrent);
        }
        return incoming;
    }

    /**
     * Apply a list of concurrent operations to transform incoming.
     * Called when client is behind multiple revisions.
     */
    public Operation transformAgainstAll(Operation incoming, List<Operation> concurrentOps) {
        Operation transformed = incoming;
        for (Operation concurrent : concurrentOps) {
            transformed = transform(transformed, concurrent);
        }
        return transformed;
    }

    /**
     * Apply an operation to document content and return new content.
     */
    public String applyOperation(String content, Operation op) {
        StringBuilder sb = new StringBuilder(content);
        switch (op.getType()) {
            case INSERT -> {
                int pos = Math.min(op.getPosition(), sb.length());
                sb.insert(pos, op.getContent());
            }
            case DELETE -> {
                int start = Math.min(op.getPosition(), sb.length());
                int end = Math.min(start + op.getLength(), sb.length());
                if (start < end) {
                    sb.delete(start, end);
                }
            }
            default -> { /* RETAIN: no-op */ }
        }
        return sb.toString();
    }

    // --- Private transformation methods ---

    private Operation transformInsertInsert(Operation incoming, Operation concurrent) {
        if (incoming.getPosition() < concurrent.getPosition()) {
            return incoming;
        }
        if (incoming.getPosition() == concurrent.getPosition()) {
            if (incoming.getUserId().compareTo(concurrent.getUserId()) < 0) {
                return incoming;
            }
        }
        return Operation.builder()
                .id(incoming.getId())
                .userId(incoming.getUserId())
                .documentId(incoming.getDocumentId())
                .type(OperationType.INSERT)
                .position(incoming.getPosition() + concurrent.getContent().length())
                .content(incoming.getContent())
                .length(incoming.getLength())
                .revision(incoming.getRevision())
                .timestamp(incoming.getTimestamp())
                .build();
    }

    private Operation transformInsertDelete(Operation incoming, Operation concurrent) {
        if (concurrent.getPosition() + concurrent.getLength() <= incoming.getPosition()) {
            return Operation.builder()
                    .id(incoming.getId())
                    .userId(incoming.getUserId())
                    .documentId(incoming.getDocumentId())
                    .type(OperationType.INSERT)
                    .position(incoming.getPosition() - concurrent.getLength())
                    .content(incoming.getContent())
                    .length(incoming.getLength())
                    .revision(incoming.getRevision())
                    .timestamp(incoming.getTimestamp())
                    .build();
        }
        if (concurrent.getPosition() >= incoming.getPosition()) {
            return incoming;
        }
        return Operation.builder()
                .id(incoming.getId())
                .userId(incoming.getUserId())
                .documentId(incoming.getDocumentId())
                .type(OperationType.INSERT)
                .position(concurrent.getPosition())
                .content(incoming.getContent())
                .length(incoming.getLength())
                .revision(incoming.getRevision())
                .timestamp(incoming.getTimestamp())
                .build();
    }

    private Operation transformDeleteInsert(Operation incoming, Operation concurrent) {
        if (concurrent.getPosition() >= incoming.getPosition() + incoming.getLength()) {
            return incoming;
        }
        if (concurrent.getPosition() <= incoming.getPosition()) {
            return Operation.builder()
                    .id(incoming.getId())
                    .userId(incoming.getUserId())
                    .documentId(incoming.getDocumentId())
                    .type(OperationType.DELETE)
                    .position(incoming.getPosition() + concurrent.getContent().length())
                    .length(incoming.getLength())
                    .revision(incoming.getRevision())
                    .timestamp(incoming.getTimestamp())
                    .build();
        }
        return Operation.builder()
                .id(incoming.getId())
                .userId(incoming.getUserId())
                .documentId(incoming.getDocumentId())
                .type(OperationType.DELETE)
                .position(incoming.getPosition())
                .length(incoming.getLength() + concurrent.getContent().length())
                .revision(incoming.getRevision())
                .timestamp(incoming.getTimestamp())
                .build();
    }

    private Operation transformDeleteDelete(Operation incoming, Operation concurrent) {
        int inStart = incoming.getPosition();
        int inEnd = inStart + incoming.getLength();
        int conStart = concurrent.getPosition();
        int conEnd = conStart + concurrent.getLength();

        if (conStart >= inEnd) {
            return incoming;
        }
        if (conEnd <= inStart) {
            return Operation.builder()
                    .id(incoming.getId())
                    .userId(incoming.getUserId())
                    .documentId(incoming.getDocumentId())
                    .type(OperationType.DELETE)
                    .position(incoming.getPosition() - concurrent.getLength())
                    .length(incoming.getLength())
                    .revision(incoming.getRevision())
                    .timestamp(incoming.getTimestamp())
                    .build();
        }
        int overlapStart = Math.max(inStart, conStart);
        int overlapEnd = Math.min(inEnd, conEnd);
        int newLength = incoming.getLength() - (overlapEnd - overlapStart);
        int adjustedStart = inStart <= conStart ? inStart : Math.max(0, inStart - concurrent.getLength());

        if (newLength <= 0) {
            return Operation.builder()
                    .id(incoming.getId())
                    .userId(incoming.getUserId())
                    .documentId(incoming.getDocumentId())
                    .type(OperationType.RETAIN)
                    .position(0)
                    .length(0)
                    .revision(incoming.getRevision())
                    .timestamp(incoming.getTimestamp())
                    .build();
        }

        return Operation.builder()
                .id(incoming.getId())
                .userId(incoming.getUserId())
                .documentId(incoming.getDocumentId())
                .type(OperationType.DELETE)
                .position(adjustedStart)
                .length(newLength)
                .revision(incoming.getRevision())
                .timestamp(incoming.getTimestamp())
                .build();
    }
}
