package com.example.search.engine.supplier;

import org.springframework.util.Assert;

public abstract class AbstractContentSupplier<T> {
    protected T source;

    protected AbstractContentSupplier(T source) {
        Assert.notNull(source, "source cannot be null");
        this.source = source;
    }
}