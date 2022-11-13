package com.arakveil.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

/**
 * Write the contents of a file as a stream of chars. If the file is not found, it will create it.
 * Запис вмісту файлу у вигляді потоку символів. Якщо файл не знайдено, він створить його.
 * <p>
 * There is a better API for simple operations! Для простих операцій є кращий API!
 */
public final class FileWriterDemo {

    /**
     * Used {@link BufferedWriter#append(CharSequence)} for any text files.
     * <p>
     * Використано {@link BufferedWriter#append(CharSequence)} для будь-яких текстових файлів.
     *
     * @param path   path to file.
     * @param data   String data for store.
     * @param append rewrite if {@code false}, else append to end of file.
     * @throws IOException exception with output operations.
     */
    public static void writeByBuffered(Path path, String data, boolean append) throws IOException {
        try (var fileWriter = new BufferedWriter(new FileWriter(path.toFile(), append))) {
            fileWriter.append(data);
            if (append) {
                fileWriter.newLine();
            }
        }
    }

    /**
     * Used {@link FileWriter#append(CharSequence)} for small text files.
     * <p>
     * Still it is better to use {@link FileWriterDemo#writeByBuffered(Path, String, boolean)}.
     * <p>
     * Використано {@link FileWriter#append(CharSequence)} для не великих текстових файлів.
     * <p>
     * Завжди краще використовувати {@link FileWriterDemo#writeByBuffered(Path, String, boolean)}.
     *
     * @param path   path to file.
     * @param data   String data for store.
     * @param append rewrite if {@code false}, else append to end of file.
     * @throws IOException exception with output operations.
     */
    public static void writeByAppend(Path path, String data, boolean append) throws IOException {
        try (var fileWriter = new FileWriter(path.toFile(), append)) {
            fileWriter.append(data);
            if (append) {
                fileWriter.append(System.lineSeparator());
            }
        }
    }

    /**
     * Used {@link FileWriter#append(char)} for small text files.
     * <p>
     * Still it is better to use {@link FileWriterDemo#writeByBuffered(Path, String, boolean)}.
     * <p>
     * Використано {@link FileWriter#append(char)} для невеликих текстових файлів.
     * <p>
     * Завжди краще використовувати {@link FileWriterDemo#writeByBuffered(Path, String, boolean)}.
     *
     * @param path   path to file.
     * @param data   String data for store.
     * @param append rewrite if {@code false}, else append to end of file.
     * @throws IOException exception with output operations.
     */
    public static void writeByChar(Path path, char[] data, boolean append) throws IOException {
        try (var outputStream = new FileWriter(path.toFile(), append)) {
            for (char element : data) {
                outputStream.append(element);
            }
            if (append) {
                outputStream.write(System.lineSeparator());
            }
        }
    }

    /**
     * Used {@link PrintWriter#print(String)} and {@link PrintWriter#println(String)} for any text
     * files.
     * <p>
     * Використано {@link PrintWriter#print(String)} і {@link PrintWriter#println(String)} для
     * будь-яких текстових файлів.
     *
     * @param path   path to file.
     * @param data   String data for store.
     * @param append rewrite if {@code false}, else append to end of file.
     * @throws IOException exception with output operations.
     */
    public static void writeByPrintWriter(Path path, String data, boolean append)
            throws IOException {
        try (var out = new PrintWriter(new BufferedWriter(new FileWriter(path.toFile(), append)))) {
            if (!append) {
                out.print(data);
            } else {
                out.println(data);
            }
        }
    }

    private FileWriterDemo() {
    }
}
