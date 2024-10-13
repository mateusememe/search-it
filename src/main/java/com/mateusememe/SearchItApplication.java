package com.mateusememe;

import java.util.List;
import java.util.Set;

import com.mateusememe.application.usecase.SearchMovies;
import com.mateusememe.domain.entity.Indexer;
import com.mateusememe.infrastructure.cli.ArgumentParser;
import com.mateusememe.infrastructure.io.MovieFileReader;
import com.mateusememe.infrastructure.io.OutputPrinter;

/**
 * Main class responsible for executing the application via CLI.
 * Receives search arguments, invokes the movie search service,
 * and displays the results in the terminal.
 */
public class SearchItApplication {

    /**
     * Main method that starts the application.
     * Receives a search term via command line, loads movie files,
     * performs the search, and displays the results in the terminal.
     *
     * @param args Command line arguments: search term, result limit, and verbose
     *             flag.
     */
    public static void main(String[] args) {
        ArgumentParser parser = new ArgumentParser(args);

        if (parser.hasFlag("--help") || parser.hasFlag("-h")) {
            printHelp();
            return;
        }

        List<String> searchTerms = parser.getPositionalArgs();
        if (searchTerms.isEmpty() || searchTerms.get(0).isBlank()) {
            System.err.println("Error: A search argument is required.");
            printHelp();
            System.exit(1);
            return;
        }

        String searchQuery = String.join(" ", searchTerms).toLowerCase();
        String[] searchQuerySplitted = String.join(" ", searchTerms).toLowerCase().split("\\s+");
        boolean verbose = parser.hasFlag("-v") || parser.hasFlag("--verbose");
        int resultLimit = parser.getIntValue("-l", Integer.MAX_VALUE);

        Indexer indexer = new Indexer();
        MovieFileReader movieFileReader = new MovieFileReader(indexer);
        SearchMovies searchMovies = new SearchMovies(indexer);
        OutputPrinter printer = new OutputPrinter(indexer, movieFileReader);

        try {
            movieFileReader.loadFiles("data");
            long startSearchTime = System.nanoTime();
            Set<String> result = searchMovies.execute(searchQuerySplitted);
            long endSearchTime = System.nanoTime();
            double searchTime = (endSearchTime - startSearchTime) / 1_000_000.0;

            if (verbose)
                printer.printVerbose(result, searchQuery, resultLimit, searchTime);
            else
                printer.printSimple(result, searchQuery, resultLimit, searchTime);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Displays the help message in the terminal.
     */
    private static void printHelp() {
        System.out.println("Usage: java -jar search.it.jar <search term> [-l <limit>] [-v]");
        System.out.println("Options:");
        System.out.println("  <search term>   A mandatory search term (string) not blank");
        System.out.println("  -l <limit>      Limit the number of results (optional, default: no limit)");
        System.out.println("  -v              Verbose output (optional)");
        System.out.println("  --help, -h      Show this help message");
    }
}
