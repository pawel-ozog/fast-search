package com.example.search.engine.helper;

import com.example.search.engine.supplier.FileContentSupplier;
import com.example.search.engine.exception.UnableToReadContentException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileContentSupplierTest {
    private final FileContentSupplier supplier = new FileContentSupplier();


    @Test
    void testExpectIllegalArgumentExceptionWhenNewSupplierCreated() {
        assertThrows(IllegalArgumentException.class, () -> supplier.supplyContent(null));
    }

    @Test
    void testExpectIOException() {
        assertThrows(UnableToReadContentException.class, () -> supplier.supplyContent("/wrong/path/to/file"));
    }

    @Test
    void testExpectInputListOfSizeTwo() throws IOException {
        List<String> input = supplier.supplyContent("src/test/resources");
        assertThat(input).hasSize(2).containsAll(Arrays.asList("name1", "name2"));
    }
}