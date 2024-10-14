package com.mateusememe.infrastructure.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
     *                      store the data from the files.
     */
    public MovieFileReader(Indexer invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    /**
     * Loads files from the specified directory, reading their content and inserting
     * it into an inverted index.
     *
     * @param directoryPath the path to the directory containing the files to be
     *                      loaded
     * @throws IOException if an I/O error occurs while reading files
     */
    public void loadFiles(String directoryPath) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths.filter(Files::isRegularFile).forEach(file -> {
                try {
                    invertedIndex.insert(file.toString(), Files.readString(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Searches for and returns a snippet of the file content that contains any
     * of the search terms. Highlights the sentence that contains the searched term
     * for
     * easier visualization.
     *
     * @param fileName    Name of the file where the terms will be searched.
     * @param searchTerms Array of search terms to be located in the content of the
     *                    file.
     * @return A string with the sentence that contains any of the search terms.
     */
    public String getSnippet(String fileName, String[] searchTerms) {
        try {
            String content = Files.readString(Paths.get(fileName));
            String[] sentences = content.split("\\.\\s+");
            for (String sentence : sentences) {
                String trimmedSentence = sentence.trim();
                for (String term : searchTerms) {
                    if (trimmedSentence.toLowerCase().contains(term.toLowerCase())) {
                        return trimmedSentence + "."; // Returns the sentence that contains the term
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Não há pré-visualização disponível.";
    }
}
