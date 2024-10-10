package com.mateusememe.domain.entity;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Data structure used for efficient indexing and searching of strings.
 * This structure stores the contents of files and optimizes the search for
 * terms within them.
 */
public class Indexer {
    private final Map<String, HashSet<String>> index = new LinkedHashMap<>();

    /**
     * Inserts a new content (file) into the indexing structure.
     * Each file is indexed according to the words contained in its content.
     *
     * @param fileName Name of the file to be inserted.
     * @param content  Content of the file to be indexed.
     */
    public synchronized void insert(String fileName, String content) {
        String[] words = content.toLowerCase().split("\\W+");
        for (String word : words) {
            index.computeIfAbsent(word, k -> new HashSet<>()).add(fileName);
        }
    }

    /**
     * Searches for terms in the indexing structure and returns the files
     * that contain all the words (using AND conditions).
     *
     * @param terms Array of search terms to be found.
     * @return Set of files that contain all the search terms.
     */
    public Set<String> search(String[] terms) {
        HashSet<String> result = new HashSet<>();
        boolean firstTerm = true;

        if (terms.length >= 1 && !index.containsKey(terms[0]))
            return new HashSet<>();

        for (String term : terms) {
            HashSet<String> filesContainingTerm = index.getOrDefault(term.toLowerCase(), new HashSet<>());

            if (firstTerm) {
                result.addAll(filesContainingTerm);
                firstTerm = false;
            } else {
                result.retainAll(filesContainingTerm);
            }
        }
        return result;
    }

    /**
     * Counts the number of occurrences of search terms in a specific file.
     * For each term, it checks if the file contains the term and increments the
     * counter.
     *
     * @param fileName The name of the file where the occurrences of terms will be
     *                 counted.
     * @param terms    Array of search terms to be checked in the file.
     * @return The number of terms that appear in the specified file.
     */
    public int countOccurrences(String fileName, String[] terms) {
        int count = 0;
        for (String term : terms) {
            HashSet<String> filesContainingTerm = index.getOrDefault(term.toLowerCase(), new HashSet<>());
            if (filesContainingTerm.contains(fileName)) {
                count++;
            }
        }
        return count;
    }
}
