package com.codesync.websocket;

import com.codesync.service.PresenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Set;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PresenceWebSocketHandler {

    private final PresenceService presenceService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/room/{roomId}/join")
    public void handleJoin(
            @DestinationVariable String roomId,
            @Payload Map<String, String> userData) {

        presenceService.userJoined(roomId, userData);
        Set<Object> onlineUsers = presenceService.getOnlineUsers(roomId);

        Map<String, Object> presence = Map.of(
                "type", "JOIN",
                "userId", userData.get("userId"),
                "username", userData.getOrDefault("username", "Anonymous"),
                "color", userData.getOrDefault("color", "#6366f1"),
                "onlineUsers", onlineUsers
        );

        messagingTemplate.convertAndSend(
                "/topic/room/" + roomId + "/presence",
                presence
        );

        log.info("User {} joined room {}", userData.get("userId"), roomId);
    }

    @MessageMapping("/room/{roomId}/leave")
    public void handleLeave(
            @DestinationVariable String roomId,
            @Payload Map<String, String> userData) {

        String userId = userData.get("userId");
        presenceService.userLeft(roomId, userId);
        Set<Object> onlineUsers = presenceService.getOnlineUsers(roomId);

        Map<String, Object> presence = Map.of(
                "type", "LEAVE",
                "userId", userId,
                "username", userData.getOrDefault("username", "Anonymous"),
                "color", userData.getOrDefault("color", "#6366f1"),
                "onlineUsers", onlineUsers
        );

        messagingTemplate.convertAndSend(
                "/topic/room/" + roomId + "/presence",
                presence
        );

        log.info("User {} left room {}", userId, roomId);
    }
}
