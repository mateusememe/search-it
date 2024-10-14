package com.mateusememe.application.usecase;

import java.util.Set;

import com.mateusememe.domain.entity.Indexer;

/**
 * Use case responsible for searching movies containing the words in the query.
 * Uses an Indexer to optimize the search process.
 */
public class SearchMovies {
    private final Indexer indexer;

    /**
     * Constructor that initializes the movie search use case.
     *
     * @param indexer Data structure used to perform the search.
     */
    public SearchMovies(Indexer indexer) {
        this.indexer = indexer;
    }

    /**
     * Performs the search of the query in the indexed files and returns the files
     * that contain all the words in the search term.
     *
     * @param searchQuery The query to be searched in the files.
     * @return A sorted set of file names that match the search query.
     */
    public Set<String> execute(String[] searchQuery) {
        return indexer.search(searchQuery);
    }
}
