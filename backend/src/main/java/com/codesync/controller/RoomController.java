package com.codesync.controller;

import com.codesync.dto.request.CreateRoomRequest;
import com.codesync.dto.response.RoomResponse;
import com.codesync.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomResponse> createRoom(
            @Valid @RequestBody CreateRoomRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(roomService.createRoom(request, userDetails.getUsername()));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable String slug) {
        return ResponseEntity.ok(roomService.getRoomBySlug(slug));
    }

    @GetMapping("/my")
    public ResponseEntity<List<RoomResponse>> getMyRooms(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(roomService.getRoomsForUser(userDetails.getUsername()));
    }

    @PostMapping("/{slug}/join")
    public ResponseEntity<RoomResponse> joinRoom(
            @PathVariable String slug,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(roomService.joinRoom(slug, userDetails.getUsername()));
    }

    @DeleteMapping("/{slug}/leave")
    public ResponseEntity<Void> leaveRoom(
            @PathVariable String slug,
            @AuthenticationPrincipal UserDetails userDetails) {
        roomService.leaveRoom(slug, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
