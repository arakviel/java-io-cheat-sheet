package com.arakveil.nio;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * In modern realities, Java NIO (Buffer oriented) takes about 80-90% of the work with files,
 * although the share of Java IO is still significant.
 * <p>
 * У сучасних реаліях Java NIO (Buffer oriented) займає близько 80-90% роботи з файлами, хоча частка
 * Java IO теж ще істотна.
 */
public final class FileChannelDemo {

    public static String readLargeFiles(Path path) throws IOException {
        try (var channel = (FileChannel) Files.newByteChannel(path,
                StandardOpenOption.READ)) {
            var buffer = ByteBuffer.allocate(512);
            var result = new StringBuilder();
            while (channel.read(buffer) != -1) {
                buffer.flip();
                result.append(Charset.defaultCharset().decode(buffer));
                buffer.clear();
            }
            return result.toString();
        }
    }

    public static void writeLargeFiles(Path path, String data, boolean append) throws IOException {
        try (var fileChannel = (FileChannel) Files.newByteChannel(path,
                StandardOpenOption.APPEND, StandardOpenOption.CREATE)) {
            if (append) {
                data += System.lineSeparator();
            }

            byte[] byteData = data.getBytes();
            var buffer = ByteBuffer.wrap(byteData);

            fileChannel.write(buffer);
            buffer.clear();
        }
    }

    private FileChannelDemo() {
    }
}
