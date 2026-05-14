package com.codesync.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PresenceService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String PRESENCE_KEY_PREFIX = "presence:";
    private static final String CURSOR_KEY_PREFIX = "cursor:";
    private static final Duration CURSOR_TTL = Duration.ofSeconds(30);

    public void userJoined(String roomId, Map<String, String> userData) {
        String key = PRESENCE_KEY_PREFIX + roomId;
        redisTemplate.opsForSet().add(key, userData.get("userId"));
        log.debug("User {} joined room {}", userData.get("userId"), roomId);
    }

    public void userLeft(String roomId, String userId) {
        String key = PRESENCE_KEY_PREFIX + roomId;
        redisTemplate.opsForSet().remove(key, userId);
        // Clean up cursor
        String cursorKey = CURSOR_KEY_PREFIX + roomId + ":" + userId;
        redisTemplate.delete(cursorKey);
        log.debug("User {} left room {}", userId, roomId);
    }

    public Set<Object> getOnlineUsers(String roomId) {
        String key = PRESENCE_KEY_PREFIX + roomId;
        Set<Object> members = redisTemplate.opsForSet().members(key);
        return members != null ? members : Collections.emptySet();
    }

    public void updateCursor(String docId, String userId, Map<String, Object> cursorData) {
        String key = CURSOR_KEY_PREFIX + docId + ":" + userId;
        redisTemplate.opsForValue().set(key, cursorData, CURSOR_TTL);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getCursor(String docId, String userId) {
        String key = CURSOR_KEY_PREFIX + docId + ":" + userId;
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? (Map<String, Object>) value : null;
    }
}
