package com.example.search.engine.service;

import com.example.search.engine.exception.UnableToReadContentException;
import com.example.search.engine.helper.FileContentSupplier;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class ContentReadService {
    private static final Consumer<Throwable> ERROR_LOGGER = e -> log.error("Can not read file", e);

    private final FileContentSupplier contentSupplier;

    public ContentReadService(final FileContentSupplier contentSupplier) {
        Assert.notNull(contentSupplier, "contentSupplier must not be null");
        this.contentSupplier = contentSupplier;
    }

    public List<String> readLinesFromFile() {
        return Try.of(contentSupplier)
                .onFailure(ERROR_LOGGER)
                .getOrElseThrow(UnableToReadContentException::new);
    }
}
