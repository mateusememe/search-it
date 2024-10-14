package com.mateusememe.infrastructure.io;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.mateusememe.domain.entity.Indexer;

class OutputPrinterTest {

    private OutputPrinter outputPrinter;
    private Indexer indexer;
    private MovieFileReader movieFileReader;

    @BeforeEach
    void setUp() {
        indexer = new Indexer();
        movieFileReader = new MovieFileReader(indexer);
        outputPrinter = new OutputPrinter(indexer, movieFileReader);
    }

    @Test
    @DisplayName("Integration Test for printSimple")
    void testPrintSimple_Integration() {
        Set<String> result = Set.of("file1.txt", "file2.txt");
        String searchQuery = "example query";
        int resultLimit = 2;
        double searchTime = 0.5;

        outputPrinter.printSimple(result, searchQuery, resultLimit, searchTime);

        assertTrue(result.contains("file1.txt"));
    }

    @Test
    @DisplayName("Integration Test for printVerbose")
    void testPrintVerbose_Integration() {
        Set<String> result = Set.of("file1.txt", "file2.txt");
        String searchQuery = "example query";
        int resultLimit = 2;
        double searchTime = 0.5;

        outputPrinter.printVerbose(result, searchQuery, resultLimit, searchTime);

        assertTrue(result.contains("file1.txt"));
    }
}
