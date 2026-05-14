package com.codesync.repository;

import com.codesync.model.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OperationRepository extends JpaRepository<OperationLog, UUID> {
    List<OperationLog> findByDocumentIdAndRevisionGreaterThanEqualOrderByRevisionAsc(UUID documentId, int revision);
    List<OperationLog> findByDocumentIdOrderByRevisionAsc(UUID documentId);
}
