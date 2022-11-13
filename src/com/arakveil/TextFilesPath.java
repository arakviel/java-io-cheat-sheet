package com.arakveil;

import java.nio.file.Path;

public enum TextFilesPath {
    LOREM_ENG("lorem_eng.txt"),
    LOREM_URK("lorem_ukr.txt");

    private final String fileName;

    TextFilesPath(String fileName) {
        this.fileName = fileName;
    }

    public Path getPath() {
        return Path.of("resources", fileName);
    }
}
