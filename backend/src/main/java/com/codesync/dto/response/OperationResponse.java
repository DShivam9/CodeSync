package com.codesync.dto.response;

import com.codesync.ot.OperationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationResponse {
    private String id;
    private String userId;
    private String documentId;
    private OperationType type;
    private int position;
    private String content;
    private int length;
    private int revision;
    private long timestamp;
}
