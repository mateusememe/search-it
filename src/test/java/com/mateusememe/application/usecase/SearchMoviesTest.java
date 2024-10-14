package com.mateusememe.application.usecase;

import java.util.Set;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.mateusememe.domain.entity.Indexer;

class SearchMoviesTest {

    private Indexer indexer;
    private SearchMovies searchMovies;

    @BeforeEach
    void setUp() {
        indexer = new Indexer();

        indexer.insert("movie1.txt", "action movie with great stunts");
        indexer.insert("movie2.txt", "romantic comedy about love");
        indexer.insert("movie3.txt", "sci-fi action movie in space");
        searchMovies = new SearchMovies(indexer);
    }

    @Test
    @DisplayName("Should return correct search results")
    void shouldReturnCorrectSearchResults() {
        String[] query = {"action", "movie"};
        Set<String> actualResults = searchMovies.execute(query);
        Set<String> expectedResults = new TreeSet<>(Set.of("movie1.txt", "movie3.txt"));

        assertEquals(expectedResults, actualResults);
    }

    @Test
    @DisplayName("Should return empty set when no results found")
    void shouldReturnEmptySetWhenNoResultsFound() {
        String[] query = {"nonexistent", "movie"};
        Set<String> actualResults = searchMovies.execute(query);

        assertTrue(actualResults.isEmpty());
    }

    @Test
    @DisplayName("Should handle single word query")
    void shouldHandleSingleWordQuery() {
        String[] query = {"romantic"};
        Set<String> actualResults = searchMovies.execute(query);
        Set<String> expectedResults = new TreeSet<>(Set.of("movie2.txt"));

        assertEquals(expectedResults, actualResults);
    }

    @Test
    @DisplayName("Should handle empty query array")
    void shouldHandleEmptyQueryArray() {
        String[] query = {};
        Set<String> actualResults = searchMovies.execute(query);

        assertTrue(actualResults.isEmpty());
    }
}
