package com.example.search.engine.supplier;

import com.example.search.engine.exception.UnableToReadContentException;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class FileContentSupplier implements ContentSupplier {
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
    public List<String> supplyContent() {
        return Try.of(()->Files.readAllLines(Path.of(path,INPUT_FILE_NAME)))
                .onFailure(e -> log.error("Cannot read file",e))
                .getOrElseThrow(UnableToReadContentException::new);
    }
}
