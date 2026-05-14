package com.codesync.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private String id;
    private String name;
    private String slug;
    private String ownerId;
    private String ownerUsername;
    private Boolean isPublic;
    private OffsetDateTime createdAt;
    private int memberCount;
    private List<String> documentNames;
}
