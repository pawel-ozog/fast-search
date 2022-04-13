package com.example.search.engine.supplier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SupplierFactoryTest {

    @Test
    void testFileContentSupplierFactor() {
        ContentSupplier fileContentSupplier = ContentSupplierFactory.fileContentSupplier(() -> "src");
        assertNotNull(fileContentSupplier);
        assertInstanceOf(FileContentSupplier.class,fileContentSupplier);
    }

}
