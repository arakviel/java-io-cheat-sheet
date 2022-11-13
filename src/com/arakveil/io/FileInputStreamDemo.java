package com.arakveil.io;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Read the contents of a file as a stream of <b>bytes</b>.
 * <p>
 * Читання вмісту файлу у вигляді потоку байт.
 */
public final class FileInputStreamDemo {

    /**
     * Used {@link BufferedInputStream#read()} for all kind files (but not text files) to read data
     * by chunks.
     * <p>
     * Використано {@link BufferedInputStream#read()} для всіх видів файлів (але не текстових) для
     * зчитування даних фрагментами.
     */
    public static String readByBuffered(Path path) throws IOException {
        try (var inputStream = new BufferedInputStream(new FileInputStream(path.toFile()))) {
            byte[] bytes = new byte[inputStream.available()];
            int counter = 0;

            while (inputStream.available() > 0) {
                bytes[counter++] += (byte) inputStream.read();
            }

            return new String(bytes);
        }
    }

    /**
     * Used {@link FileInputStream#read(byte[])} for more efficient to read a large file by data
     * chunks; for instance 1024 bytes in each method call.
     * <p>
     * Використано {@link FileInputStream#read(byte[])} для більш ефективного читання великого файлу
     * по даних шматками; наприклад, по 1024 байти у кожному виклику методу.
     */
    public static String readByBuffer(Path path) throws IOException {
        try (var inputStream = new FileInputStream(path.toFile())) {
            var result = new StringBuilder();
            byte[] buffer = new byte[1024];
            int totalBytes;

            while ((totalBytes = inputStream.read(buffer)) != -1) {
                result.append(new String(buffer, 0, totalBytes));
            }

            return result.toString();
        }
    }

    /**
     * Used {@link FileInputStream#readAllBytes()} only for small files and not for a text files!
     * <p>
     * Використовував {@link FileInputStream#readAllBytes()} тільки для невеликих файлів, і не для
     * текстових!
     */
    public static String readAllBytes(Path path) throws IOException {
        try (var inputStream = new FileInputStream(path.toFile())) {
            byte[] bytes = inputStream.readAllBytes();

            return new String(bytes);
        }
    }

    /**
     * Used {@link FileInputStream#read()} only for portioned reading of, not a large file by byte.
     * <p>
     * Використовується {@link FileInputStream#read()} тільки для порційного побайтового читання
     * невеликого файлу.
     */
    public static String readByByte(Path path) throws IOException {
        try (var inputStream = new FileInputStream(path.toFile())) {
            byte[] bytes = new byte[inputStream.available()];
            int counter = 0;

            while (inputStream.available() > 0) {
                bytes[counter++] += (byte) inputStream.read();
            }

            return new String(bytes);
        }
    }

    private FileInputStreamDemo() {
    }
}
