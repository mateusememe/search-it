package com.mateusememe.domain.entity;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IndexerTest {

    private Indexer indexer;

    @BeforeEach
    void setUp() {
        indexer = new Indexer();
    }

    @Test
    @DisplayName("Insert method should correctly index files")
    void testInsert() {
        indexer.insert("file1.txt", "Hello world");
        indexer.insert("file2.txt", "Hello Java");

        Set<String> result = indexer.search(new String[]{"hello"});
        assertEquals(2, result.size());
        assertTrue(result.contains("file1.txt"));
        assertTrue(result.contains("file2.txt"));
    }

    @Test
    @DisplayName("Search should return correct results for a single term")
    void testSearch_SingleTerm() {
        indexer.insert("file1.txt", "Hello world");
        indexer.insert("file2.txt", "Hello Java");
        indexer.insert("file3.txt", "Java programming");

        Set<String> result = indexer.search(new String[]{"java"});
        assertEquals(2, result.size());
        assertTrue(result.contains("file2.txt"));
        assertTrue(result.contains("file3.txt"));
    }

    @Test
    @DisplayName("Search should return correct results for multiple terms (AND logic)")
    void testSearch_MultipleTerms() {
        indexer.insert("file1.txt", "Hello world Java");
        indexer.insert("file2.txt", "Hello Java programming");
        indexer.insert("file3.txt", "Java programming world");

        Set<String> result = indexer.search(new String[]{"java", "programming"});
        assertEquals(2, result.size());
        assertTrue(result.contains("file2.txt"));
        assertTrue(result.contains("file3.txt"));
    }

    @Test
    @DisplayName("Search should return empty set when no results are found")
    void testSearch_NoResults() {
        indexer.insert("file1.txt", "Hello world");
        indexer.insert("file2.txt", "Hello Java");

        Set<String> result = indexer.search(new String[]{"python"});
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Search with empty terms should return an empty set")
    void testSearch_EmptyTerms() {
        indexer.insert("file1.txt", "Hello world");

        Set<String> result = indexer.search(new String[]{});
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("CountOccurrences should return correct count of matching terms")
    void testCountOccurrences() {
        indexer.insert("file1.txt", "Hello world Java programming");
        indexer.insert("file2.txt", "Hello Java");

        int count = indexer.countOccurrences("file1.txt", new String[]{"hello", "java", "python"});
        assertEquals(2, count);
    }

    @Test
    @DisplayName("CountOccurrences should return zero when no terms match")
    void testCountOccurrences_NoMatches() {
        indexer.insert("file1.txt", "Hello world");

        int count = indexer.countOccurrences("file1.txt", new String[]{"java", "python"});
        assertEquals(0, count);
    }

    @Test
    @DisplayName("Search should be case-insensitive")
    void testCaseInsensitivity() {
        indexer.insert("file1.txt", "Hello World");
        indexer.insert("file2.txt", "HELLO JAVA");

        Set<String> result = indexer.search(new String[]{"hello"});
        assertEquals(2, result.size());
        assertTrue(result.contains("file1.txt"));
        assertTrue(result.contains("file2.txt"));
    }

    @Test
    @DisplayName("Search should respect word boundaries")
    void testWordBoundaries() {
        indexer.insert("file1.txt", "Java programming");
        indexer.insert("file2.txt", "JavaScript");

        Set<String> result = indexer.search(new String[]{"java"});
        assertEquals(1, result.size());
        assertTrue(result.contains("file1.txt"));
    }
}
