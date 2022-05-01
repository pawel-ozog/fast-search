package com.example.search.engine.service;

import com.example.search.engine.exception.IllegalCharacterValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class ValidationServiceTest {
    @Autowired
    ValidationService validationService;

    @ParameterizedTest
    @MethodSource("provideInputLists")
    void testValidationException(List<String> input) {
        assertThrows(IllegalCharacterValidationException.class, () -> this.validationService.validate(input));
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

    private static Stream<Arguments> provideInputLists(){
        return Stream.of(
                Arguments.of(List.of("firstWord", "secondWord", "thi@dW&rd")),
                Arguments.of(List.of("firstWord", "s)con!W@r_", "thi@dW&rd")),
                Arguments.of(List.of("$!@#%W()&^%", "s)con!W@r_", "thi@dW&rd"))
        );
    }
}
