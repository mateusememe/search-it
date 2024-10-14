package com.mateusememe.infrastructure.cli;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArgumentParserTest {

    @Nested
    @DisplayName("hasFlag method tests")
    class HasFlagTests {

        @Test
        @DisplayName("Should return true when flag is present")
        void shouldReturnTrueWhenFlagIsPresent() {
            ArgumentParser parser = new ArgumentParser(new String[] { "-h", "somearg" });
            assertTrue(parser.hasFlag("-h"));
        }

        @Test
        @DisplayName("Should return false when flag is not present")
        void shouldReturnFalseWhenFlagIsNotPresent() {
            ArgumentParser parser = new ArgumentParser(new String[] { "somearg" });
            assertFalse(parser.hasFlag("-h"));
        }
    }

    @Nested
    @DisplayName("getIntValue method tests")
    class GetIntValueTests {

        @Test
        @DisplayName("Should return correct integer value when flag is present")
        void shouldReturnCorrectIntegerValueWhenFlagIsPresent() {
            ArgumentParser parser = new ArgumentParser(new String[] { "-l", "5", "somearg" });
            assertEquals(5, parser.getIntValue("-l", 0));
        }

        @Test
        @DisplayName("Should return default value when flag is not present")
        void shouldReturnDefaultValueWhenFlagIsNotPresent() {
            ArgumentParser parser = new ArgumentParser(new String[] { "somearg" });
            assertEquals(10, parser.getIntValue("-l", 10));
        }

        @Test
        @DisplayName("Should return default value when flag value is not a valid integer")
        void shouldReturnDefaultValueWhenFlagValueIsNotValidInteger() {
            ArgumentParser parser = new ArgumentParser(new String[] { "-l", "notanumber", "somearg" });
            assertEquals(10, parser.getIntValue("-l", 10));
        }

        @Test
        @DisplayName("Should return default value when flag is the last argument")
        void shouldReturnDefaultValueWhenFlagIsLastArgument() {
            ArgumentParser parser = new ArgumentParser(new String[] { "somearg", "-l" });
            assertEquals(10, parser.getIntValue("-l", 10));
        }
    }

    @Nested
    @DisplayName("getPositionalArgs method tests")
    class GetPositionalArgsTests {

        @Test
        @DisplayName("Should return correct positional arguments")
        void shouldReturnCorrectPositionalArguments() {
            ArgumentParser parser = new ArgumentParser(new String[] { "-h", "pos1", "-l", "5", "pos2", "pos3" });
            List<String> positionalArgs = parser.getPositionalArgs();
            assertEquals(List.of("pos1", "pos2", "pos3"), positionalArgs);
        }

        @Test
        @DisplayName("Should return empty list when no positional arguments")
        void shouldReturnEmptyListWhenNoPositionalArguments() {
            ArgumentParser parser = new ArgumentParser(new String[] { "-h", "-l", "5" });
            List<String> positionalArgs = parser.getPositionalArgs();
            assertTrue(positionalArgs.isEmpty());
        }

        @Test
        @DisplayName("Should handle arguments after -l flag correctly")
        void shouldHandleArgumentsAfterLFlagCorrectly() {
            ArgumentParser parser = new ArgumentParser(new String[] { "-l", "5", "pos1", "pos2" });
            List<String> positionalArgs = parser.getPositionalArgs();
            assertEquals(List.of("pos1", "pos2"), positionalArgs);
        }
    }

    @Nested
    @DisplayName("Integration tests")
    class IntegrationTests {

        @Test
        @DisplayName("Should parse complex argument list correctly")
        void shouldParseComplexArgumentListCorrectly() {
            ArgumentParser parser = new ArgumentParser(
                    new String[] { "-h", "file1.txt", "-l", "10", "file2.txt", "-v", "file3.txt" });

            assertTrue(parser.hasFlag("-h"));
            assertTrue(parser.hasFlag("-v"));
            assertFalse(parser.hasFlag("-x"));

            assertEquals(10, parser.getIntValue("-l", 0));
            assertEquals(0, parser.getIntValue("-x", 0));

            List<String> positionalArgs = parser.getPositionalArgs();
            assertEquals(List.of("file1.txt", "file2.txt", "file3.txt"), positionalArgs);
        }
    }
}
