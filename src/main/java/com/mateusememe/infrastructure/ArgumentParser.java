package com.mateusememe.infrastructure;

import java.util.ArrayList;
import java.util.List;

public class ArgumentParser {
    private final List<String> args;

    public ArgumentParser(String[] args) {
        this.args = List.of(args);
    }

    public boolean hasFlag(String flag) {
        return args.contains(flag);
    }

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
