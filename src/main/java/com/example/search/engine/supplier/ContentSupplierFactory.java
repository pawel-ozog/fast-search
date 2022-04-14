package com.example.search.engine.supplier;

public class ContentSupplierFactory {
    public static ContentSupplier fileContentSupplier(String content){
        return new FileContentSupplier(content);
    }
}
