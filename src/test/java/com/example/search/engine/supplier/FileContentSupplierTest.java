package com.example.search.engine.supplier;

import com.example.search.engine.supplier.FileContentSupplier;
import com.example.search.engine.exception.UnableToReadContentException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileContentSupplierTest {

    @Test
    void testExpectIllegalArgumentExceptionWhenNewSupplierCreated() {
        assertThrows(IllegalArgumentException.class, () -> new FileContentSupplier(null));
    }

    @Test
    void testExpectIOException() {
        ContentSupplier supplier = ContentSupplierFactory.fileContentSupplier("/wrong/path/to/file");
        assertThrows(UnableToReadContentException.class, supplier::supplyContent);
    }

    @Test
    void testExpectInputListOfSizeTwo() {
        ContentSupplier supplier = ContentSupplierFactory.fileContentSupplier("src/test/resources");
        List<String> input = supplier.supplyContent();
        assertThat(input).hasSize(2).containsAll(Arrays.asList("name1", "name2"));
    }
}