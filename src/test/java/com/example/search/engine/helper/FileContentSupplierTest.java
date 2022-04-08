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

    @Test
    void testExpectIllegalArgumentExceptionWhenNewSupplierCreated() {
        assertThrows(IllegalArgumentException.class, () -> FileContentSupplier.of(null).supplyContent());
    }

    @Test
    void testExpectIOException() {
        assertThrows(UnableToReadContentException.class, () -> FileContentSupplier.of("/wrong/path/to/file").supplyContent());
    }

    @Test
    void testExpectInputListOfSizeTwo() throws IOException {
        List<String> input = FileContentSupplier.of("src/test/resources").supplyContent();
        assertThat(input).hasSize(2).containsAll(Arrays.asList("name1", "name2"));
    }
}