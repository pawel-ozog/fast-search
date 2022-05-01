package com.example.search.engine.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class Properties {
    @Value("${illegalSymbols}")
    private String illegalSymbols;

    public String getIllegalSymbols() {
        return illegalSymbols;
    }
}
