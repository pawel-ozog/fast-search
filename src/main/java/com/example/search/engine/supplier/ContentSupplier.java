package com.example.search.engine.supplier;

import java.util.List;

public abstract class ContentSupplier<T> {
    public abstract List<String> supplyContent(T source);
}
