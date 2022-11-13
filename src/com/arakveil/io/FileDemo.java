package com.arakveil.io;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Some methods from {@code java.io.File}.
 * <p>
 * There is a better API!
 */
public final class FileDemo {

    public static Map<String, String> getAllInformation(File file) throws IOException {
        var result = new HashMap<String, String>();
        result.put("Exist?", String.valueOf(file.exists()));
        result.put("It's file?", String.valueOf(file.isFile()));
        result.put("It's directory?", String.valueOf(file.isDirectory()));
        result.put("Name:", file.getName());
        result.put("Parent directory:", file.getParent());
        result.put("Absolute path:", file.getAbsolutePath());

        return result;
    }

    public static boolean createFile(File file) throws IOException {
        return file.createNewFile();
    }

    public static boolean deleteFile(File file) {
        return file.delete();
    }

    private FileDemo() {
    }
}
