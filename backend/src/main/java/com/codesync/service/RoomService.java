package com.codesync.service;

import com.codesync.dto.request.CreateRoomRequest;
import com.codesync.dto.response.RoomResponse;
import com.codesync.exception.RoomNotFoundException;
import com.codesync.model.Document;
import com.codesync.model.Room;
import com.codesync.model.RoomMember;
import com.codesync.model.User;
import com.codesync.repository.DocumentRepository;
import com.codesync.repository.RoomRepository;
import com.codesync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String SLUG_CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SLUG_LENGTH = 8;

    @Transactional
    public RoomResponse createRoom(CreateRoomRequest request, String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String slug = generateUniqueSlug();

        Room room = Room.builder()
                .name(request.getName())
                .slug(slug)
                .owner(owner)
                .isPublic(request.getIsPublic() != null ? request.getIsPublic() : true)
                .build();

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            room.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        room = roomRepository.save(room);

        // Auto-add owner as member
        RoomMember member = RoomMember.builder()
                .room(room)
                .user(owner)
                .role("OWNER")
                .build();
        room.getMembers().add(member);
        room = roomRepository.save(room);

        // Create a default main.js document
        Document defaultDoc = Document.builder()
                .room(room)
                .filename("main.js")
                .language("javascript")
                .content("// Welcome to CodeSync!\n// Start coding collaboratively...\n")
                .createdBy(owner)
                .build();
        documentRepository.save(defaultDoc);

        log.info("Room created: {} (slug: {}) by {}", room.getName(), slug, username);
        return toRoomResponse(room);
    }

    public RoomResponse getRoomBySlug(String slug) {
        Room room = roomRepository.findBySlug(slug)
                .orElseThrow(() -> new RoomNotFoundException("Room not found: " + slug));
        return toRoomResponse(room);
    }

    public List<RoomResponse> getRoomsForUser(String username) {
        List<Room> owned = roomRepository.findByOwnerUsername(username);
        List<Room> member = roomRepository.findRoomsByMemberUsername(username);

        // Merge and deduplicate
        owned.addAll(member);
        return owned.stream()
                .distinct()
                .map(this::toRoomResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public RoomResponse joinRoom(String slug, String username) {
        Room room = roomRepository.findBySlug(slug)
                .orElseThrow(() -> new RoomNotFoundException("Room not found: " + slug));
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        boolean alreadyMember = room.getMembers().stream()
                .anyMatch(m -> m.getUser().getId().equals(user.getId()));
        if (!alreadyMember) {
            RoomMember member = RoomMember.builder()
                    .room(room)
                    .user(user)
                    .role("EDITOR")
                    .build();
            room.getMembers().add(member);
            roomRepository.save(room);
            log.info("User {} joined room {}", username, slug);
        }

        return toRoomResponse(room);
    }

    @Transactional
    public void leaveRoom(String slug, String username) {
        Room room = roomRepository.findBySlug(slug)
                .orElseThrow(() -> new RoomNotFoundException("Room not found: " + slug));
        room.getMembers().removeIf(m -> m.getUser().getUsername().equals(username));
        roomRepository.save(room);
        log.info("User {} left room {}", username, slug);
    }

    private String generateUniqueSlug() {
        SecureRandom random = new SecureRandom();
        String slug;
        do {
            StringBuilder sb = new StringBuilder(SLUG_LENGTH);
            for (int i = 0; i < SLUG_LENGTH; i++) {
                sb.append(SLUG_CHARS.charAt(random.nextInt(SLUG_CHARS.length())));
            }
            slug = sb.toString();
        } while (roomRepository.existsBySlug(slug));
        return slug;
    }

    private RoomResponse toRoomResponse(Room room) {
        List<String> docNames = documentRepository.findByRoomId(room.getId())
                .stream().map(Document::getFilename).toList();

        return RoomResponse.builder()
                .id(room.getId().toString())
                .name(room.getName())
                .slug(room.getSlug())
                .ownerId(room.getOwner().getId().toString())
                .ownerUsername(room.getOwner().getUsername())
                .isPublic(room.getIsPublic())
                .createdAt(room.getCreatedAt())
                .memberCount(room.getMembers().size())
                .documentNames(docNames)
                .build();
    }
}
