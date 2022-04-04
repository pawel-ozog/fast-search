package com.example.search.engine.service;

import com.example.search.engine.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationServiceTest {
    private final ValidationService validationService = new ValidationService();

    @Test
    void testValidationException() {
        List<String> words = Arrays.asList("firstWord", "secondWord", "thi@dW&rd");
        assertThrows(ValidationException.class, () -> this.validationService.validate(words));
    }

    @Test
    void testProperWord() {
        List<String> words = Arrays.asList("firstWord", "secondWord", "thirdWord");
        assertDoesNotThrow(() -> this.validationService.validate(words));
    }

    @Test
    void testInputBeingNull() {
        assertThrows(IllegalArgumentException.class, () -> this.validationService.validate(null));
    }

    @Test
    void testEmptyList(){
        assertDoesNotThrow(() -> this.validationService.validate(Collections.emptyList()));
    }
}
