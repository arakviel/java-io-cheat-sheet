package com.arakveil.nio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * New NIO2 API for work with streams and files in blocking mode.
 */
public final class FilesByStreamsDemo {

    public static String readString(Path path) throws IOException {
        return Files.readString(path);
    }

    public static String readAllBytes(Path path) throws IOException {
        byte[] encoded = Files.readAllBytes(path);
        return new String(encoded);
    }

    public static String readAllLines(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path);
        return String.join(System.lineSeparator(), lines);
    }

    public static String readAllByLines(Path path) throws IOException {
        try (Stream<String> lines = Files.lines(path)) {
            return lines.collect(Collectors.joining(System.lineSeparator()));
        }
    }

    public static String readAllByBuffered(Path path) throws IOException {
        try (var bufferedReader = Files.newBufferedReader(path)) {
            return bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    public static String readAllByInputStream(Path path) throws IOException {
        try (var inputStream = Files.newInputStream(path)) {
            return new String(inputStream.readAllBytes());
        }
    }

    public static void writeString(Path path, String data) throws IOException {
        Files.writeString(path, data, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
    }

    public static void write(Path path, String data) throws IOException {
        Files.write(path, data.getBytes(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
    }

    public static void writeByBufferedWriter(Path path, String data) throws IOException {
        try (var bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND,
                StandardOpenOption.CREATE)) {
            bufferedWriter.append(data);
        }
    }

    private FilesByStreamsDemo() {
    }
}
