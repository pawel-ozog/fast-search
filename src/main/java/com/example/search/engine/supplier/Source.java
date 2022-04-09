package com.example.search.engine.supplier;

public class Source<T> {
    private final T source;

    public Source(T source) {
        this.source = source;
    }

    public T getSource() {
        return source;
    }
}
