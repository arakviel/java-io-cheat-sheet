package com.arakveil.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public final class AsynchronousFileChannelDemo {

    private static final int BUFFER_SIZE = 256;
    private static long position = 0;

    /**
     * For small files, it's ok to used only 1 asynchronous thread.
     * TODO: refactor?
     */
    public static String readAllFromSmallFileByFuture(Path path)
            throws IOException, InterruptedException {
        try (ExecutorService executorService = Executors.newSingleThreadExecutor();
                var channel = AsynchronousFileChannel.open(path,
                        EnumSet.of(StandardOpenOption.READ), executorService)) {
            var data = new StringBuilder();
            ByteBuffer buffer = ByteBuffer.allocate((int) Files.size(path));
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    buffer.flip();
                    data.append(Charset.defaultCharset().decode(buffer));
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.err.println(exc.getMessage());
                }
            });

            executorService.awaitTermination(1, TimeUnit.SECONDS);
            return data.toString();
        }
    }

    /**
     * It will use the number of threads available to the current user by OS information. Воно
     * використає ту кількість потоків, яка доступна у поточного користувача використовуючи
     * інформацію ОС.
     * TODO: refactor?
     */
    public static String readAllFromLargeFileByFuture(Path path)
            throws IOException, ExecutionException, InterruptedException {
        try (var channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {
            var buffer = ByteBuffer.allocate(BUFFER_SIZE);
            var data = new StringBuilder();
            int position = 0;

            do {
                Future<Integer> result = channel.read(buffer, position);

                // Checking whether the task is being completed or not
                while (!result.isDone()) {
                    System.out.printf(
                            "The process of reading file is in progress asynchronously. %s%n",
                            position);
                }

                // run other code as operation continues in background
                // here some code... for example, progressbar
                //----------------------------------------------------

                int bytesWasRead = result.get();
                if (bytesWasRead < 0) {
                    break;
                }
                // read a next block with the current position
                position += bytesWasRead;

                // Print and display statements
                System.out.printf("Is the reading done? %s%n", result.isDone());
                System.out.printf("The number of bytes read from file is %s%n", bytesWasRead);

                buffer.flip();
                data.append(Charset.defaultCharset().decode(buffer));
                buffer.clear();
            } while (true);

            return data.toString();
        }
    }

    /**
     * It will use the number of threads available to the current user by OS information. Воно
     * використає ту кількість потоків, яка доступна у поточного користувача використовуючи
     * інформацію ОС.
     * TODO: refactor?
     */
    public static String readAllFromLargeFileByCompletionHandler(Path path)
            throws IOException, InterruptedException {
        try (var channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ)) {
            BlockingQueue<Boolean> done = new ArrayBlockingQueue<>(1);
            var buffer = ByteBuffer.allocate(BUFFER_SIZE);
            var data = new StringBuilder();

            var handler = new CompletionHandler<Integer, BlockingQueue<Boolean>>() {

                @Override
                public void completed(Integer bytesWasRead, BlockingQueue<Boolean> done) {
                    if (bytesWasRead < 0) {
                        try {
                            done.put(true);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        return;
                    }

                    position += bytesWasRead;
                    buffer.flip();
                    data.append(Charset.defaultCharset().decode(buffer));
                    buffer.clear();

                    channel.read(buffer, position, done, this);
                }

                @Override
                public void failed(Throwable e, BlockingQueue<Boolean> attachment) {
                    try {
                        done.put(false);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.err.println(e.getMessage());
                }
            };

            channel.read(buffer, position, done, handler);
            // waiting for the file reading until it is done
            done.take();
            return data.toString();
        }
    }

    /**
     * Without append :(
     * TODO: refactor?
     */
    private static void writeByCompletionHandler(Path path, String data) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap(data.getBytes());

        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(path,
                StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
            var handler = new CompletionHandler<Integer, Object>() {

                @Override
                public void completed(Integer result, Object attachment) {
                    System.out.println("Attachment: " + attachment + " " + result
                            + " bytes written");
                    System.out.println("CompletionHandler Thread ID2: "
                            + Thread.currentThread().getName());
                }

                @Override
                public void failed(Throwable e, Object attachment) {
                    System.err.println("Attachment: " + attachment + " failed with:");
                    e.printStackTrace();
                }
            };
            System.out.printf("CompletionHandler Thread ID: %s", Thread.currentThread().getName());
            channel.write(buffer, 0, "Async Task", handler);
        }
    }

    private AsynchronousFileChannelDemo() {
    }
}
