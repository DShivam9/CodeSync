package com.codesync.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "room_members")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RoomMember.RoomMemberId.class)
public class RoomMember {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    @Column(name = "joined_at", updatable = false)
    private OffsetDateTime joinedAt;

    @Column(length = 20)
    @Builder.Default
    private String role = "EDITOR";

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomMemberId implements Serializable {
        private UUID room;
        private UUID user;
    }
}
