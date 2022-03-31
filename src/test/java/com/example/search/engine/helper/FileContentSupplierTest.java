package com.example.search.engine.helper;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileContentSupplierTest {

    @Test
    void testExpectIllegalArgumentExceptionWhenNewSupplierCreated() {
        assertThrows(IllegalArgumentException.class, () -> FileContentSupplier.of(null).apply());
    }

    @Test
    void testExpectIOException() {
        assertThrows(IOException.class, () -> FileContentSupplier.of("/wrong/path/to/file").apply());
    }

    @Test
    void testExpectInputListOfSizeTwo() throws IOException {
        List<String> input = FileContentSupplier.of("src/test/resources").apply();
        assertThat(input).hasSize(2).containsAll(Arrays.asList("name1", "name2"));
    }
}