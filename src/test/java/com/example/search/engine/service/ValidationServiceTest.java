package com.example.search.engine.service;

import com.example.search.engine.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ValidationServiceTest {
    @Test
    void testValidationException() {
        List<String> words = Arrays.asList("firstWord", "secondWord", "thi@dW&rd");
        var validationService = new ValidationService();
        assertThrows(ValidationException.class, () -> validationService.validate(words));
    }

    @Test
    void testProperWord() {
        List<String> words = Arrays.asList("firstWord", "secondWord", "thirdWord");
        var validationService = new ValidationService();
        assertDoesNotThrow(() -> validationService.validate(words));
    }
}
