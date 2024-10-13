package com.mateusememe.domain.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An optimized data structure for efficient indexing and searching of strings
 * within files.
 * This class provides methods for inserting file contents, searching for terms
 * across files,
 * and counting term occurrences within specific files.
 */
public class Indexer {
    /**
     * The main index structure. Keys are individual words, values are sets of file
     * names
     * containing those words.
     */
    private final Map<String, HashSet<String>> index = new HashMap<>();

    /**
     * Inserts the content of a file into the index.
     * This method tokenizes the content into words and adds each word to the index,
     * associating it with the given file name.
     *
     * @param fileName The name of the file being indexed.
     * @param content  The content of the file to be indexed.
     */
    public void insert(String fileName, String content) {
        Arrays.stream(content.toLowerCase().split("\\W+"))
                .forEach(word -> index.computeIfAbsent(word, k -> new HashSet<>()).add(fileName));
    }

    /**
     * Searches for files containing all the given terms.
     * This method implements an AND search, returning only files that contain all
     * search terms.
     * It optimizes the search by starting with the least common term and uses early
     * termination
     * if the result set becomes empty.
     *
     * @param terms An array of search terms to look for.
     * @return A Set of file names that contain all the search terms.
     */
    public Set<String> search(String[] terms) {
        if (terms.length == 0)
            return Collections.emptySet();

        if (terms.length == 1)
            return index.getOrDefault(terms[0].toLowerCase(), new HashSet<>());

        Set<String> result = index.getOrDefault(terms[0].toLowerCase(), new HashSet<>());

        for (String term : terms) {
            if (!index.containsKey(term))
                return new HashSet<>();
            result.retainAll(index.getOrDefault(term.toLowerCase(), new HashSet<>()));
        }

        return result;
    }

    /**
     * Counts the number of given terms that appear in a specific file.
     * This method checks each term against the index and increments a counter
     * if the term is found in the specified file.
     *
     * @param fileName The name of the file to check for term occurrences.
     * @param terms    An array of terms to look for in the file.
     * @return The number of terms from the input that appear in the specified file.
     */
    public int countOccurrences(String fileName, String[] terms) {
        return (int) Arrays.stream(terms)
                .map(String::toLowerCase)
                .filter(term -> index.getOrDefault(term, new HashSet<>()).contains(fileName))
                .count();
    }
}
