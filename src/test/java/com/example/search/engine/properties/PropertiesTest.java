package com.example.search.engine.properties;

import com.example.search.engine.configuration.Properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PropertiesTest {

    @Autowired
    Properties properties;

    @Test
    void testIllegalSymbolsReading(){
        String symbs = properties.getIllegalSymbols();
        Assertions.assertEquals("!@#$%^&*()_+-=", symbs);
    }
}
