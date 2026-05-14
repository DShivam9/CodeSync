package com.codesync.dto.request;

import com.codesync.ot.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationRequest {
    private String id;
    private OperationType type;
    private int position;
    private String content;
    private int length;
    private int revision;
}
