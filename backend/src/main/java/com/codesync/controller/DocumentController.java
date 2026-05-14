package com.codesync.controller;

import com.codesync.dto.response.DocumentResponse;
import com.codesync.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<DocumentResponse>> getDocumentsByRoom(@PathVariable String roomId) {
        return ResponseEntity.ok(documentService.getDocumentsByRoom(roomId));
    }

    @GetMapping("/{docId}")
    public ResponseEntity<DocumentResponse> getDocument(@PathVariable String docId) {
        return ResponseEntity.ok(documentService.getDocument(docId));
    }

    @PostMapping("/room/{roomId}")
    public ResponseEntity<DocumentResponse> createDocument(
            @PathVariable String roomId,
            @RequestParam String filename) {
        return ResponseEntity.ok(documentService.createDocument(roomId, filename));
    }

    @DeleteMapping("/{docId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String docId) {
        documentService.deleteDocument(docId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{docId}/history")
    public ResponseEntity<?> getDocumentHistory(
            @PathVariable String docId,
            @RequestParam(defaultValue = "0") int fromRevision) {
        return ResponseEntity.ok(documentService.getHistory(docId, fromRevision));
    }
}
