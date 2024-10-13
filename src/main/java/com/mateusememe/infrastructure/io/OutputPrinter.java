package com.mateusememe.infrastructure.io;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.mateusememe.domain.entity.Indexer;

/**
 * Class responsible for printing search results in different formats.
 */
public class OutputPrinter {
    private final Indexer indexer;
    private final MovieFileReader movieFileReader;

    /**
     * Constructor for OutputPrinter.
     *
     * @param indexer         The Indexer instance used for searching and counting
     *                        occurrences.
     * @param movieFileReader The MovieFileReader instance used for getting
     *                        snippets.
     */
    public OutputPrinter(Indexer indexer, MovieFileReader movieFileReader) {
        this.indexer = indexer;
        this.movieFileReader = movieFileReader;
    }

    /**
     * Prints the search results in a simple format.
     *
     * @param result      The set of file names that match the search query.
     * @param searchQuery The original search query.
     * @param resultLimit The maximum number of results to display.
     * @param searchTime  The time taken for the search operation.
     */
    public void printSimple(Set<String> result, String searchQuery, int resultLimit, double searchTime) {
        System.out.println("Found " + result.size() + " occurrences for the term \"" + searchQuery + "\".");

        result.stream()
                .limit(resultLimit)
                .forEach(System.out::println);

        System.out.printf("Search time: %.4f milliseconds%n", searchTime);
    }

    /**
     * Prints the search results in a verbose format, highlighting the search terms
     * in the content.
     *
     * @param result      The set of file names that match the search query.
     * @param searchQuery The original search query.
     * @param resultLimit The maximum number of results to display.
     * @param searchTime  The time taken for the search operation.
     */
    public void printVerbose(Set<String> result, String searchQuery, int resultLimit, double searchTime) {
        System.out.println("Found " + result.size() + " occurrences for the term \"" + searchQuery + "\".");

        String[] searchTerms = searchQuery.toLowerCase().split("\\s+");
        Map<String, Integer> sortedResults = new TreeMap<>((a, b) -> {
            int compByOccurrences = Integer.compare(indexer.countOccurrences(b, searchTerms),
                    indexer.countOccurrences(a, searchTerms));
            return compByOccurrences != 0 ? compByOccurrences : a.compareTo(b);
        });

        result.forEach(fileName -> sortedResults.put(fileName, indexer.countOccurrences(fileName, searchTerms)));

        sortedResults.entrySet().stream()
                .limit(resultLimit)
                .forEach(entry -> {
                    System.out.println("\nFile: " + entry.getKey());
                    System.out.println("Occurrences: " + entry.getValue());
                    String snippet = movieFileReader.getSnippet(entry.getKey(), searchQuery);
                    System.out.println("Snippet: " + highlightTerms(snippet, searchTerms));
                });

        System.out.printf("%nSearch time: %.4f milliseconds%n", searchTime);
    }

    /**
     * Highlights whole words that match the search terms in a given text.
     *
     * @param text        The text to process.
     * @param searchTerms The search terms to highlight.
     * @return The text with whole words matching search terms highlighted.
     */
    private String highlightTerms(String text, String[] searchTerms) {
        String lowercaseText = text.toLowerCase();
        TreeSet<Integer> highlightPositions = new TreeSet<>();

        for (String term : searchTerms) {
            int index = lowercaseText.indexOf(term);
            while (index >= 0) {
                highlightPositions.add(index);
                highlightPositions.add(index + term.length());
                index = lowercaseText.indexOf(term, index + 1);
            }
        }

        if (highlightPositions.isEmpty()) {
            return text;
        }

        StringBuilder highlighted = new StringBuilder();
        int lastPos = 0;
        boolean isHighlighted = false;

        for (int pos : highlightPositions) {
            if (pos < lastPos)
                continue; // Skip positions that have already been processed

            if (isHighlighted) {
                highlighted.append(text.substring(lastPos, pos));
                highlighted.append("\u001B[0m"); // Reset formatting
                isHighlighted = false;
            } else {
                highlighted.append(text.substring(lastPos, pos));
                highlighted.append("\u001B[1;33m"); // Start yellow highlight
                isHighlighted = true;
            }
            lastPos = pos;
        }

        // Add any remaining text
        if (lastPos < text.length()) {
            highlighted.append(text.substring(lastPos));
        }

        // Ensure highlighting is closed
        if (isHighlighted) {
            highlighted.append("\u001B[0m");
        }

        return highlighted.toString();
    }
}
