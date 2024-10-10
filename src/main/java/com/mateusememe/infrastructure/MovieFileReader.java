package com.mateusememe.infrastructure;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import com.mateusememe.domain.entity.Indexer;

/**
 * Class responsible for reading movie files and inserting their
 * contents into the inverted index entity.
 * It reads files from the 'data/' folder and performs
 * preprocessing on them.
 */
public class MovieFileReader {

    private final Indexer invertedIndex;

    /**
     * Constructor that initializes the file reader with the inverted index entity.
     *
     * @param invertedIndex Data structure of the inverted index used to
     *                     store the data from the files.
     */
    public MovieFileReader(Indexer invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    /**
     * Loads files from a specific directory (resources/data) and inserts them
     * into the inverted index entity.
     *
     * @param directoryPath Path of the directory where the movie files are located.
     * @throws IOException If an error occurs while reading the files.
     */
    public void loadFiles(String directoryPath) throws IOException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(36);

        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths.filter(Files::isRegularFile)
                    .forEach(file -> executor.submit(() -> {
                        try {
                            String content = Files.readString(file);
                            invertedIndex.insert(file.toString(), content);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }));
        } finally {
            executor.awaitTermination(5, TimeUnit.MILLISECONDS);
            executor.close();
        }
    }

    /**
     * Searches for and returns a snippet of the file content that contains the
     * search term. Highlights the sentence that contains the searched term for
     * easier visualization.
     *
     * @param fileName    Name of the file where the term will be searched.
     * @param searchQuery Search term to be located in the content of the file.
     * @return A string with the sentence that contains the search term.
     */
    public String getSnippet(String fileName, String searchQuery) {
        try {
            String content = Files.readString(Paths.get(fileName));
            String[] sentences = content.split("\\.\\s+");
            for (String sentence : sentences) {
                if (sentence.toLowerCase().contains(searchQuery.toLowerCase())) {
                    return sentence; // Returns the sentence that contains the term
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "No preview available.";
    }
}
