package com.mateusememe.infrastructure.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.mateusememe.domain.entity.Indexer;

class MovieFileReaderTest {

    private Indexer indexer;
    private MovieFileReader movieFileReader;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        indexer = new Indexer();
        movieFileReader = new MovieFileReader(indexer);
    }

    @Test
    @DisplayName("loadFiles should read files from directory and insert into indexer")
    void testLoadFiles() throws IOException {
        // Create test files
        Path file1 = tempDir.resolve("file1.txt");
        Path file2 = tempDir.resolve("file2.txt");
        Files.writeString(file1, "Content of file 1");
        Files.writeString(file2, "Content of file 2");

        // Call loadFiles
        movieFileReader.loadFiles(tempDir.toString());

        // Verify that the content was indexed
        Set<String> result1 = indexer.search(new String[] { "content" });
        Set<String> result2 = indexer.search(new String[] { "file" });

        assertTrue(result1.contains(file1.toString()));
        assertTrue(result1.contains(file2.toString()));
        assertTrue(result2.contains(file1.toString()));
        assertTrue(result2.contains(file2.toString()));
    }

    @Test
    @DisplayName("getSnippet should return correct sentence containing search term")
    void testGetSnippet() throws IOException {
        // Create a test file
        Path testFile = tempDir.resolve("test.txt");
        String content = "This is the first sentence. This is the second sentence with a search term. This is the third sentence.";
        Files.writeString(testFile, content);

        movieFileReader.loadFiles(tempDir.toString());

        String snippet = movieFileReader.getSnippet(testFile.toString(), new String[] { "search term" });

        assertEquals("This is the second sentence with a search term.", snippet);
    }

    @Test
    @DisplayName("getSnippet should be case-insensitive")
    void testGetSnippet_CaseInsensitive() throws IOException {
        Path testFile = tempDir.resolve("test.txt");
        String content = "This is a Test Sentence. Another SENTENCE here.";
        Files.writeString(testFile, content);

        movieFileReader.loadFiles(tempDir.toString());

        String snippet = movieFileReader.getSnippet(testFile.toString(), new String[] { "sentence" });

        assertEquals("This is a Test Sentence.", snippet);
    }

    @Test
    @DisplayName("getSnippet should return default message when no match is found")
    void testGetSnippet_NoMatch() throws IOException {
        Path testFile = tempDir.resolve("test.txt");
        String content = "This is a test sentence.";
        Files.writeString(testFile, content);

        movieFileReader.loadFiles(tempDir.toString());

        String snippet = movieFileReader.getSnippet(testFile.toString(), new String[] { "nonexistent" });

        assertEquals("Não há pré-visualização disponível.", snippet);
    }

    @Test
    @DisplayName("getSnippet should handle IOException gracefully")
    void testGetSnippet_IOException() {
        String snippet = movieFileReader.getSnippet("nonexistent.txt", new String[] { "test" });

        assertEquals("Não há pré-visualização disponível.", snippet);
    }
}
