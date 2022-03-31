package com.example.search.engine.helper;

import io.vavr.CheckedFunction0;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class FileContentSupplier implements CheckedFunction0<List<String>> {
    public static final String INPUT_FILE_NAME = "input";

    private final String path;

    private FileContentSupplier(final String path) {
        Assert.notNull(path, "path must not be null");
        this.path = path;
    }

    public static FileContentSupplier of(String path) {
        return new FileContentSupplier(path);
    }

    @Override
    public List<String> apply() throws IOException {
        log.info("Reading file from path {}", path);
        return Files.readAllLines(Path.of(path, INPUT_FILE_NAME));
    }
}
