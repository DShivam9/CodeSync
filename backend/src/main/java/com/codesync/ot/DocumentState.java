package com.codesync.ot;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Data
public class DocumentState {
    private final String documentId;
    private StringBuilder content;
    private int revision;
    private final List<Operation> history;
    private final ReadWriteLock lock;

    public DocumentState(String documentId, String initialContent) {
        this.documentId = documentId;
        this.content = new StringBuilder(initialContent);
        this.revision = 0;
        this.history = new ArrayList<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public String getContent() {
        lock.readLock().lock();
        try {
            return content.toString();
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getRevision() {
        lock.readLock().lock();
        try {
            return revision;
        } finally {
            lock.readLock().unlock();
        }
    }

    public List<Operation> getOperationsSince(int revision) {
        lock.readLock().lock();
        try {
            return history.stream()
                    .filter(op -> op.getRevision() >= revision)
                    .toList();
        } finally {
            lock.readLock().unlock();
        }
    }
}
