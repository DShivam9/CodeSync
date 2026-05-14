package com.codesync.websocket;

public class WebSocketDestinations {
    // Client sends operations to:
    public static final String OPERATION_SEND = "/app/document/{docId}/operation";
    public static final String CURSOR_SEND = "/app/document/{docId}/cursor";
    public static final String JOIN_ROOM = "/app/room/{roomId}/join";
    public static final String LEAVE_ROOM = "/app/room/{roomId}/leave";

    // Server broadcasts to:
    public static final String OPERATION_BROADCAST = "/topic/document/{docId}/operations";
    public static final String CURSOR_BROADCAST = "/topic/document/{docId}/cursors";
    public static final String PRESENCE_BROADCAST = "/topic/room/{roomId}/presence";
    public static final String ERROR_USER = "/user/queue/errors";

    private WebSocketDestinations() {}
}
