package com.codesync.service;

import com.codesync.dto.response.DocumentResponse;
import com.codesync.model.Document;
import com.codesync.model.OperationLog;
import com.codesync.model.Room;
import com.codesync.model.User;
import com.codesync.ot.OTServer;
import com.codesync.ot.Operation;
import com.codesync.repository.DocumentRepository;
import com.codesync.repository.OperationRepository;
import com.codesync.repository.RoomRepository;
import com.codesync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final OperationRepository operationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final OTServer otServer;

    public List<DocumentResponse> getDocumentsByRoom(String roomId) {
        return documentRepository.findByRoomId(UUID.fromString(roomId)).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public DocumentResponse getDocument(String docId) {
        Document doc = documentRepository.findById(UUID.fromString(docId))
                .orElseThrow(() -> new IllegalArgumentException("Document not found: " + docId));

        // Initialize OT state in memory if not already present
        otServer.getOrCreateState(docId, doc.getContent(), doc.getVersion());

        return toResponse(doc);
    }

    @Transactional
    public DocumentResponse createDocument(String roomId, String filename) {
        Room room = roomRepository.findById(UUID.fromString(roomId))
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + roomId));

        // Detect language from file extension
        String language = detectLanguage(filename);

        Document doc = Document.builder()
                .room(room)
                .filename(filename)
                .language(language)
                .content("")
                .build();

        doc = documentRepository.save(doc);
        otServer.getOrCreateState(doc.getId().toString(), "", 0);

        log.info("Document created: {} in room {}", filename, roomId);
        return toResponse(doc);
    }

    @Transactional
    public void deleteDocument(String docId) {
        documentRepository.deleteById(UUID.fromString(docId));
        otServer.removeDocument(docId);
        log.info("Document deleted: {}", docId);
    }

    public List<OperationLog> getHistory(String docId, int fromRevision) {
        return operationRepository
                .findByDocumentIdAndRevisionGreaterThanEqualOrderByRevisionAsc(
                        UUID.fromString(docId), fromRevision);
    }

    /**
     * Persist an OT operation asynchronously for history replay.
     */
    @Async
    @Transactional
    public void persistOperation(Operation op) {
        try {
            Document doc = documentRepository.findById(UUID.fromString(op.getDocumentId()))
                    .orElse(null);
            User user = null;
            try {
                user = userRepository.findById(UUID.fromString(op.getUserId())).orElse(null);
            } catch (IllegalArgumentException ex) {
                log.debug("User ID is not a valid UUID (likely a guest session): {}", op.getUserId());
            }

            if (doc == null || user == null) {
                log.warn("Skipping persist for op {} — doc or user not found", op.getId());
                return;
            }

            OperationLog opLog = OperationLog.builder()
                    .document(doc)
                    .user(user)
                    .operationType(op.getType().name())
                    .position(op.getPosition())
                    .content(op.getContent())
                    .length(op.getLength())
                    .revision(op.getRevision())
                    .build();

            operationRepository.save(opLog);

            // Update document content + version in DB periodically
            doc.setContent(otServer.getState(op.getDocumentId()).getContent());
            doc.setVersion(op.getRevision() + 1);
            documentRepository.save(doc);

        } catch (Exception e) {
            log.error("Failed to persist operation {}: {}", op.getId(), e.getMessage());
        }
    }

    private String detectLanguage(String filename) {
        if (filename == null) return "plaintext";
        String ext = filename.contains(".") ? filename.substring(filename.lastIndexOf(".") + 1) : "";
        return switch (ext.toLowerCase()) {
            case "js" -> "javascript";
            case "jsx" -> "javascript";
            case "ts" -> "typescript";
            case "tsx" -> "typescript";
            case "py" -> "python";
            case "java" -> "java";
            case "rb" -> "ruby";
            case "go" -> "go";
            case "rs" -> "rust";
            case "cpp", "cc", "cxx" -> "cpp";
            case "c", "h" -> "c";
            case "cs" -> "csharp";
            case "html" -> "html";
            case "css" -> "css";
            case "json" -> "json";
            case "md" -> "markdown";
            case "sql" -> "sql";
            case "yaml", "yml" -> "yaml";
            case "xml" -> "xml";
            case "sh", "bash" -> "shell";
            case "php" -> "php";
            default -> "plaintext";
        };
    }

    private DocumentResponse toResponse(Document doc) {
        return DocumentResponse.builder()
                .id(doc.getId().toString())
                .roomId(doc.getRoom().getId().toString())
                .filename(doc.getFilename())
                .content(doc.getContent())
                .language(doc.getLanguage())
                .version(doc.getVersion())
                .build();
    }
}
