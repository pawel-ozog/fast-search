package com.example.search.engine.supplier;

public abstract class AbstractContentSupplier<T> {
    protected T source;

    protected AbstractContentSupplier(T source) {
        this.source = source;
    }
}