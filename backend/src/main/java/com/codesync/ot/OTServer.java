package com.codesync.ot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OT Server manages in-memory document states and applies/broadcasts operations.
 * Acts as the central authority for all document revisions.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OTServer {

    private final OTEngine otEngine;
    private final Map<String, DocumentState> documentStates = new ConcurrentHashMap<>();

    /**
     * Load or create document state in memory.
     */
    public DocumentState getOrCreateState(String documentId, String content, int revision) {
        return documentStates.computeIfAbsent(documentId, id -> {
            DocumentState state = new DocumentState(id, content);
            return state;
        });
    }

    /**
     * Process an incoming operation from a client.
     * Transforms it against all concurrent operations the client hasn't seen,
     * applies it to the document, and returns the transformed operation.
     */
    public synchronized Operation processOperation(Operation incoming) {
        DocumentState state = documentStates.get(incoming.getDocumentId());
        if (state == null) {
            throw new IllegalStateException("Document state not found: " + incoming.getDocumentId());
        }

        state.getLock().writeLock().lock();
        try {
            List<Operation> missedOps = state.getOperationsSince(incoming.getRevision());

            Operation transformed = otEngine.transformAgainstAll(incoming, missedOps);
            transformed = Operation.builder()
                    .id(transformed.getId())
                    .userId(transformed.getUserId())
                    .documentId(transformed.getDocumentId())
                    .type(transformed.getType())
                    .position(transformed.getPosition())
                    .content(transformed.getContent())
                    .length(transformed.getLength())
                    .revision(state.getRevision())
                    .timestamp(transformed.getTimestamp())
                    .build();

            if (transformed.getType() != OperationType.RETAIN) {
                String newContent = otEngine.applyOperation(state.getContent().toString(), transformed);
                state.setContent(new StringBuilder(newContent));
            }

            state.setRevision(state.getRevision() + 1);
            state.getHistory().add(transformed);

            log.debug("Applied op {} to doc {} -> revision {}",
                transformed.getId(), incoming.getDocumentId(), state.getRevision());

            return transformed;

        } finally {
            state.getLock().writeLock().unlock();
        }
    }

    public DocumentState getState(String documentId) {
        return documentStates.get(documentId);
    }

    public void removeDocument(String documentId) {
        documentStates.remove(documentId);
    }
}
