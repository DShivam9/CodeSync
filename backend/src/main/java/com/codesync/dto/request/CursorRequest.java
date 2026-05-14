package com.codesync.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursorRequest {
    private String userId;
    private String username;
    private String color;
    private int line;
    private int column;
}
