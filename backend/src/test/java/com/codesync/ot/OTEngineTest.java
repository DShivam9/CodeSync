package com.codesync.ot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

class OTEngineTest {

    private OTEngine engine;

    @BeforeEach
    void setUp() {
        engine = new OTEngine();
    }

    @Test
    @DisplayName("Insert before insert: incoming position unchanged")
    void insertBeforeInsert() {
        Operation incoming = Operation.insert("userA", "doc1", 3, "hello", 0);
        Operation concurrent = Operation.insert("userB", "doc1", 7, "world", 0);
        Operation result = engine.transform(incoming, concurrent);
        assertThat(result.getPosition()).isEqualTo(3);
    }

    @Test
    @DisplayName("Insert after insert: incoming position shifts right")
    void insertAfterInsert() {
        Operation incoming = Operation.insert("userA", "doc1", 10, "hello", 0);
        Operation concurrent = Operation.insert("userB", "doc1", 3, "world", 0);
        Operation result = engine.transform(incoming, concurrent);
        assertThat(result.getPosition()).isEqualTo(10 + "world".length());
    }

    @Test
    @DisplayName("Apply insert operation: content updated correctly")
    void applyInsert() {
        Operation op = Operation.insert("userA", "doc1", 5, " World", 0);
        String result = engine.applyOperation("Hello", op);
        assertThat(result).isEqualTo("Hello World");
    }

    @Test
    @DisplayName("Apply delete operation: content updated correctly")
    void applyDelete() {
        Operation op = Operation.delete("userA", "doc1", 5, 6, 0);
        String result = engine.applyOperation("Hello World", op);
        assertThat(result).isEqualTo("Hello");
    }

    @Test
    @DisplayName("Concurrent inserts produce same document regardless of order")
    void convergence() {
        String initial = "Hello";
        Operation opA = Operation.insert("userA", "doc1", 5, " World", 0);
        Operation opB = Operation.insert("userB", "doc1", 5, "!!!", 0);

        // Path 1: apply A then transformed B
        String afterA = engine.applyOperation(initial, opA);
        Operation bPrime = engine.transform(opB, opA);
        String path1 = engine.applyOperation(afterA, bPrime);

        // Path 2: apply B then transformed A
        String afterB = engine.applyOperation(initial, opB);
        Operation aPrime = engine.transform(opA, opB);
        String path2 = engine.applyOperation(afterB, aPrime);

        // Both paths must converge to same content
        assertThat(path1).isEqualTo(path2);
    }

    @Test
    @DisplayName("Delete overlapping with concurrent delete: no double deletion")
    void deleteDeleteOverlap() {
        String content = "Hello World";
        Operation incoming = Operation.delete("userA", "doc1", 0, 5, 0);
        Operation concurrent = Operation.delete("userB", "doc1", 3, 5, 0);

        Operation transformed = engine.transform(incoming, concurrent);
        String afterConcurrent = engine.applyOperation(content, concurrent);
        String result = engine.applyOperation(afterConcurrent, transformed);

        assertThat(result).doesNotContain("Hello");
    }

    @Test
    @DisplayName("Insert at same position: tie broken by userId")
    void insertSamePosition() {
        Operation opA = Operation.insert("aaa", "doc1", 5, "AAA", 0);
        Operation opB = Operation.insert("zzz", "doc1", 5, "ZZZ", 0);

        // userA < userZ, so userA goes first (no shift)
        Operation aPrime = engine.transform(opA, opB);
        assertThat(aPrime.getPosition()).isEqualTo(5);

        // userZ >= userA, so userZ shifts
        Operation bPrime = engine.transform(opB, opA);
        assertThat(bPrime.getPosition()).isEqualTo(5 + "AAA".length());
    }

    @Test
    @DisplayName("Insert inside delete range: insert moves to delete start")
    void insertInsideDeleteRange() {
        Operation insert = Operation.insert("userA", "doc1", 5, "XYZ", 0);
        Operation delete = Operation.delete("userB", "doc1", 3, 5, 0); // deletes chars 3-7

        Operation transformed = engine.transform(insert, delete);
        assertThat(transformed.getPosition()).isEqualTo(3);
    }

    @Test
    @DisplayName("Delete entire range already deleted: becomes RETAIN no-op")
    void deleteAlreadyDeleted() {
        Operation incoming = Operation.delete("userA", "doc1", 3, 5, 0);
        Operation concurrent = Operation.delete("userB", "doc1", 2, 8, 0);

        Operation transformed = engine.transform(incoming, concurrent);
        assertThat(transformed.getType()).isEqualTo(OperationType.RETAIN);
    }

    @Test
    @DisplayName("Apply operation to empty document")
    void applyToEmpty() {
        Operation op = Operation.insert("userA", "doc1", 0, "Hello", 0);
        String result = engine.applyOperation("", op);
        assertThat(result).isEqualTo("Hello");
    }

    @Test
    @DisplayName("Delete beyond document end: safely truncated")
    void deleteBeyondEnd() {
        Operation op = Operation.delete("userA", "doc1", 3, 100, 0);
        String result = engine.applyOperation("Hello", op);
        assertThat(result).isEqualTo("Hel");
    }
}
