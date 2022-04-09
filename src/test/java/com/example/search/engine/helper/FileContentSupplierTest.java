package com.example.search.engine.helper;

import com.example.search.engine.supplier.FileContentSupplier;
import com.example.search.engine.exception.UnableToReadContentException;
import com.example.search.engine.supplier.Source;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileContentSupplierTest {



    @Test
    void testExpectIllegalArgumentExceptionWhenNewSupplierCreated() {
        FileContentSupplier supplier = FileContentSupplier.of(new Source<>(null));
        assertThrows(IllegalArgumentException.class, supplier::supplyContent);
    }

    @Test
    void testExpectIOException() {
        FileContentSupplier supplier = FileContentSupplier.of(new Source<>("/wrong/path/to/file"));
        assertThrows(UnableToReadContentException.class, supplier::supplyContent);
    }

    @Test
    void testExpectInputListOfSizeTwo() {
        FileContentSupplier supplier = FileContentSupplier.of(new Source<>("src/test/resources"));
        List<String> input = supplier.supplyContent();
        assertThat(input).hasSize(2).containsAll(Arrays.asList("name1", "name2"));
    }
}