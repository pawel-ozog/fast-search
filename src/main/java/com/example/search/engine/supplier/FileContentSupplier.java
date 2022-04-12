package com.example.search.engine.supplier;

import com.example.search.engine.exception.UnableToReadContentException;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class FileContentSupplier extends AbstractContentSupplier<String> implements ContentSupplier {
    public static final String INPUT_FILE_NAME = "input";

    public FileContentSupplier(String source) {
        super(source);
    }

    public static FileContentSupplier of(String source) {
        return new FileContentSupplier(source);
    }

    @Override
    public List<String> supplyContent() {
        Assert.notNull(source, "source cannot be null");
        return Try.of(() -> Files.readAllLines(Path.of(source, INPUT_FILE_NAME)))
                .onFailure(e -> log.error("Cannot read file", e))
                .getOrElseThrow(UnableToReadContentException::new);
    }
}
