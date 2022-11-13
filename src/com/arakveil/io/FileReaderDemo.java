package com.arakveil.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Collectors;

/**
 * InputStreamReader interprets the bytes of an InputStream as text instead of numerical data.
 * InputStreamReader інтерпретує байти InputStream як текст, а не числові дані.
 * <p>
 * There is a better API for simple operations! Для простих операцій є кращий API!
 */
public final class FileReaderDemo {

    /**
     * Used {@link BufferedReader#lines()} for buffered reading of text files using Stream API and
     * iterating over text strings.
     * <p>
     * Використано {@link BufferedReader#lines()} для буферизованого читання текстових файлів за
     * допомогою Stream API та ітерації по текстових рядках.
     *
     * @param path path to file.
     * @return result string of all lines from file.
     * @throws IOException exception with input operations.
     */
    public static String readLinesByBuffered(Path path) throws IOException {
        try (var fileReader = new BufferedReader(new FileReader(path.toFile()))) {
            return fileReader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    /**
     * Used {@link BufferedReader#readLine()} for buffered reading of text files and iterating over
     * text lines.
     * <p>
     * Використано {@link BufferedReader#readLine() } для буферизованого читання текстових файлів та
     * ітерації над текстовими рядками.
     *
     * @param path path to file.
     * @return result string of all lines from file.
     * @throws IOException exception with input operations.
     */
    public static String readAllLineByBuffered(Path path) throws IOException {
        try (var fileReader = new BufferedReader(new FileReader(path.toFile()))) {
            var result = new StringBuilder();
            String line;
            while ((line = fileReader.readLine()) != null) {
                result.append(line).append(System.lineSeparator());
            }

            return result.toString();
        }
    }

    /**
     * Used {@link FileReader#read(char[]) } only for small files.
     * <p>
     * It is better to use {@link FileReaderDemo#readLinesByBuffered(Path)}.
     * <p>
     * Використано {@link FileReader#read()} тільки для невеликих файлів.
     * <p>
     * Завжди краще використовувати {@link FileReaderDemo#readLinesByBuffered(Path)}.
     *
     * @param path path to file.
     * @return result string of all lines from file.
     * @throws IOException exception with input operations.
     */
    public static String readByChars(Path path) throws IOException {
        File file = path.toFile();
        try (var fileReader = new FileReader(file)) {
            char[] chars = new char[(int) file.length()];
            fileReader.read(chars);
            return new String(chars);
        }
    }

    /**
     * Used {@link FileReader#read() } only for small files.
     * <p>
     * It is better to use {@link FileReaderDemo#readLinesByBuffered(Path)}.
     * <p>
     * Використано {@link FileReader#read()} тільки для невеликих файлів.
     * <p>
     * Завжди краще використовувати {@link FileReaderDemo#readLinesByBuffered(Path)}.
     *
     * @param path path to file.
     * @return result string of all lines from file.
     * @throws IOException exception with input operations.
     */
    public static String readByChar(Path path) throws IOException {
        try (var fileReader = new FileReader(path.toFile())) {
            var result = new StringBuilder();
            int symbol;

            while ((symbol = fileReader.read()) != -1) {
                result.append((char) symbol);
            }
            return result.toString();
        }
    }


    private FileReaderDemo() {
    }
}
