package com.example.search.engine.supplier;

import java.util.function.Supplier;

public class ContentSupplierFactory {
    public static ContentSupplier fileContentSupplier(Supplier<String> sourceS){
        return new FileContentSupplier(sourceS.get());
    }
}
