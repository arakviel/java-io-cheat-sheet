package com.arakveil.io;

import com.arakveil.progressbar.ProgressBarTraditional;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;

/**
 * Write the contents of a file as a stream of <b>bytes</b>. If the file is not found, it will
 * create it.
 * <p>
 * Запис вмісту файлу у вигляді потоку байт. Якщо файл не знайдено, він створить його.
 */
public final class FileOutputStreamDemo {

    /**
     * Used {@link BufferedOutputStream#write(byte[])} for any bytes files.
     * <p>
     * Використано {@link BufferedOutputStream#write(byte[])} для будь-яких байтових файлів.
     *
     * @param path   path to file.
     * @param data   array of bytes.
     * @param append rewrite if {@code false}, else append to end of file.
     * @throws IOException exception with output operations.
     */
    public static void writeByBuffered(Path path, byte[] data, boolean append) throws IOException {
        try (var outputStream = new BufferedOutputStream(
                new FileOutputStream(path.toFile(), append))) {
            outputStream.write(data);
            if (append) {
                outputStream.write(System.lineSeparator().getBytes());
            }
        }
    }

    /**
     * Used {@link FileOutputStream#write(byte[])} for small bytes files.
     * <p>
     * Використано {@link FileOutputStream#write(byte[])} для маленьких байтових файлів.
     *
     * @param path   path to file.
     * @param data   array of bytes.
     * @param append rewrite if {@code false}, else append to end of file.
     * @throws IOException exception with output operations.
     */
    public static void writeByByteArray(Path path, byte[] data, boolean append) throws IOException {
        try (var outputStream = new FileOutputStream(path.toFile(), append)) {
            outputStream.write(data);
            if (append) {
                outputStream.write(System.lineSeparator().getBytes());
            }
        }
    }

    /**
     * Used {@link FileOutputStream#write(byte[])} for small bytes files.
     * <p>
     * Використано {@link FileOutputStream#write(byte[])} для маленьких байтових файлів.
     *
     * @param path   path to file.
     * @param data   String data for store.
     * @param append rewrite if {@code false}, else append to end of file.
     * @throws IOException exception with output operations.
     */
    public static void writeByString(Path path, String data, boolean append) throws IOException {
        writeByByteArray(path, data.getBytes(), append);
    }

    /**
     * Used {@link FileOutputStream#write(int)} only for small files with demonstrate progress.
     * <p>
     * Використано {@link FileOutputStream#write(int)} тільки для невеликих файлів з демонстрацією
     * прогресу.
     *
     * @param path   path to file.
     * @param data   array of bytes.
     * @param append rewrite if {@code false}, else append to end of file.
     * @throws IOException exception with output operations.
     */
    public static void writeByByte(Path path, int[] data, boolean append) throws IOException {
        try (var outputStream = new FileOutputStream(path.toFile(), append)) {
            for (int element : data) {
                outputStream.write(element);
            }
            if (append) {
                outputStream.write(System.lineSeparator().getBytes());
            }
        }
    }

    public static void writeByByteWithProgressBar(Path path, int[] data, boolean append)
            throws IOException {
        var progressBar = new ProgressBarTraditional();
        progressBar.start();
        writeByByte(path, data, append);
        progressBar.show = false;
    }

    /**
     * Used {@link PrintStream#print(String)} and {@link PrintStream#println(String)}.
     * <p>
     * A PrintStream can be used to add functionality to an output stream. But all characters
     * printed by a PrintStream are <b>converted into bytes</b> using the platform’s default
     * character encoding.
     * <p>
     * It is better not to use it for working with files.
     * <p>
     * PrintStream можна використовувати для додавання функціональності до потоку виводу. Але всі
     * символи, надруковані потоком PrintStream, перетворюються в байти з використанням кодування
     * символів за замовчуванням платформи.
     * <p>
     * Краще не використовувати для роботи із файлами.
     *
     * @param path   path to file.
     * @param data   array of bytes.
     * @param append rewrite if {@code false}, else append to end of file.
     * @throws IOException exception with output operations.
     */
    public static void writeByPrintStream(Path path, String data, boolean append)
            throws IOException {
        try (var out = new PrintStream(new BufferedOutputStream(
                new FileOutputStream(path.toFile(), append)))) {
            if (append) {
                out.println(data);
            } else {
                out.print(data);
            }
        }
    }


    private FileOutputStreamDemo() {
    }
}
