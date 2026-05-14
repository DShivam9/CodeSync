package com.codesync.websocket;

import com.codesync.dto.request.CursorRequest;
import com.codesync.service.PresenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CursorWebSocketHandler {

    private final PresenceService presenceService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/document/{docId}/cursor")
    public void handleCursor(
            @DestinationVariable String docId,
            @Payload CursorRequest request) {

        // Store cursor in Redis with TTL
        presenceService.updateCursor(docId, request.getUserId(), Map.of(
                "userId", request.getUserId(),
                "username", request.getUsername(),
                "color", request.getColor(),
                "line", request.getLine(),
                "column", request.getColumn()
        ));

        // Broadcast to all subscribers
        messagingTemplate.convertAndSend(
                "/topic/document/" + docId + "/cursors",
                request
        );
    }
}
