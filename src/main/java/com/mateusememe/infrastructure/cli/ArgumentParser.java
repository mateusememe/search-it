package com.mateusememe.infrastructure.cli;

import java.util.ArrayList;
import java.util.List;

/**
 * The ArgumentParser class provides functionality to parse command-line
 * arguments.
 * It allows checking for flags, retrieving integer values associated with
 * flags,
 * and collecting positional arguments that are not prefixed by a flag.
 */
public class ArgumentParser {
    private final List<String> args;

    /**
     * Constructs an ArgumentParser with the provided command-line arguments.
     *
     * @param args the command-line arguments as an array of strings
     */
    public ArgumentParser(String[] args) {
        this.args = List.of(args);
    }

    /**
     * Checks if a specific flag is present in the command-line arguments.
     *
     * @param flag the flag to check for (e.g., "-h")
     * @return true if the flag is present, false otherwise
     */
    public boolean hasFlag(String flag) {
        return args.contains(flag);
    }

    /**
     * Retrieves the integer value associated with a specified flag.
     * If the flag is not present or the value is not a valid integer,
     * the specified default value is returned.
     *
     * @param flag         the flag whose associated integer value is to be
     *                     retrieved
     * @param defaultValue the default value to return if the flag is not found
     * @return the integer value associated with the flag or the default value if
     *         not found
     */
    public int getIntValue(String flag, int defaultValue) {
        int index = args.indexOf(flag);
        if (index != -1 && index + 1 < args.size()) {
            try {
                return Integer.parseInt(args.get(index + 1));
            } catch (NumberFormatException e) {
                System.err.println("Warning: Invalid number format for " + flag + ". Using default value.");
            }
        }
        return defaultValue;
    }

    /**
     * Retrieves a list of positional arguments from the command-line arguments.
     * Positional arguments are those that do not start with a flag (e.g., "-").
     *
     * @return a list of positional arguments
     */
    public List<String> getPositionalArgs() {
        List<String> positionalArgs = new ArrayList<>();
        for (int index = 0; index < args.size(); index++) {
            if (args.get(index).startsWith("-")) {
                if (args.get(index).equals("-l")) {
                    index++; // Skip the next argument as it's the value for -l
                }
            } else {
                positionalArgs.add(args.get(index));
            }
        }
        return positionalArgs;
    }
}
