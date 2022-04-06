package com.example.search.engine.service;

import com.example.search.engine.exception.IllegalCharacterValidationException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationServiceTest {
    private final ValidationService validationService = new ValidationService();

    @Test
    void testValidationException() {
        List<String> first = Arrays.asList("firstWord", "secondWord", "thi@dW&rd");
        List<String> second = Arrays.asList("firstWord", "s)con!W@r_", "thi@dW&rd");
        List<String> third = Arrays.asList("$!@#%W()&^%", "s)con!W@r_", "thi@dW&rd");
        assertThrows(IllegalCharacterValidationException.class, () -> this.validationService.validate(first));
        assertThrows(IllegalCharacterValidationException.class, () -> this.validationService.validate(second));
        assertThrows(IllegalCharacterValidationException.class, () -> this.validationService.validate(third));
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
