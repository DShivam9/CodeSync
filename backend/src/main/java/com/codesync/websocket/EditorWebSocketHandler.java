package com.codesync.websocket;

import com.codesync.dto.request.OperationRequest;
import com.codesync.dto.response.OperationResponse;
import com.codesync.ot.Operation;
import com.codesync.ot.OTServer;
import com.codesync.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class EditorWebSocketHandler {

    private final OTServer otServer;
    private final DocumentService documentService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/document/{docId}/operation")
    public void handleOperation(
            @DestinationVariable String docId,
            @Payload OperationRequest request,
            SimpMessageHeaderAccessor headerAccessor) {

        String userId = headerAccessor.getSessionAttributes() != null
                ? (String) headerAccessor.getSessionAttributes().get("userId")
                : null;

        // Fallback: use session ID if userId not set
        if (userId == null && headerAccessor.getUser() != null) {
            userId = headerAccessor.getUser().getName();
        }
        if (userId == null) {
            userId = headerAccessor.getSessionId();
        }

        try {
            Operation incoming = Operation.builder()
                    .id(request.getId())
                    .userId(userId)
                    .documentId(docId)
                    .type(request.getType())
                    .position(request.getPosition())
                    .content(request.getContent())
                    .length(request.getLength())
                    .revision(request.getRevision())
                    .timestamp(System.currentTimeMillis())
                    .build();

            Operation transformed = otServer.processOperation(incoming);

            documentService.persistOperation(transformed);

            OperationResponse response = OperationResponse.builder()
                    .id(transformed.getId())
                    .userId(transformed.getUserId())
                    .documentId(transformed.getDocumentId())
                    .type(transformed.getType())
                    .position(transformed.getPosition())
                    .content(transformed.getContent())
                    .length(transformed.getLength())
                    .revision(transformed.getRevision())
                    .timestamp(transformed.getTimestamp())
                    .build();

            messagingTemplate.convertAndSend(
                "/topic/document/" + docId + "/operations",
                response
            );

        } catch (Exception e) {
            log.error("Error processing operation for doc {}: {}", docId, e.getMessage());
            if (headerAccessor.getUser() != null) {
                messagingTemplate.convertAndSendToUser(
                    headerAccessor.getUser().getName(),
                    "/queue/errors",
                    "Failed to process operation: " + e.getMessage()
                );
            }
        }
    }
}
