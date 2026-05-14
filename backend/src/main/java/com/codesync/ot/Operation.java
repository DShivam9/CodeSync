package com.codesync.ot;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
    private String id;
    private String userId;
    private String documentId;
    private OperationType type;
    private int position;
    private String content;
    private int length;
    private int revision;
    private long timestamp;

    public static Operation insert(String userId, String documentId, int position, String content, int revision) {
        return Operation.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .documentId(documentId)
                .type(OperationType.INSERT)
                .position(position)
                .content(content)
                .length(content.length())
                .revision(revision)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public static Operation delete(String userId, String documentId, int position, int length, int revision) {
        return Operation.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .documentId(documentId)
                .type(OperationType.DELETE)
                .position(position)
                .length(length)
                .revision(revision)
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
