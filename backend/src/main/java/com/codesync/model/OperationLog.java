package com.codesync.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "operation_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "operation_type", nullable = false, length = 10)
    private String operationType;

    @Column(nullable = false)
    private Integer position;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer length;

    @Column(nullable = false)
    private Integer revision;

    @Column(name = "applied_at")
    @Builder.Default
    private OffsetDateTime appliedAt = OffsetDateTime.now();
}
