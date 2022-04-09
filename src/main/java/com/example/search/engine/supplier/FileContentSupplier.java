package com.example.search.engine.supplier;

import com.example.search.engine.exception.UnableToReadContentException;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
public class FileContentSupplier extends ContentSupplier<String> {
    public static final String INPUT_FILE_NAME = "input";

    @Override
    public List<String> supplyContent(String source) {
        Assert.notNull(source,"source cannot be null");
        return Try.of(()->Files.readAllLines(Path.of(source,INPUT_FILE_NAME)))
                .onFailure(e -> log.error("Cannot read file",e))
                .getOrElseThrow(UnableToReadContentException::new);
    }
}
