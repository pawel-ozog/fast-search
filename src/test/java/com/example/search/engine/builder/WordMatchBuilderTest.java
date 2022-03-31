package com.example.search.engine.builder;

import com.example.search.engine.model.WordMatch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WordMatchBuilderTest {

    @ParameterizedTest
    @MethodSource("testExpectIllegalArgumentExceptionArguments")
    void testExpectIllegalArgumentException(WordMatchBuilder wordMatchBuilder) {
        assertThrows(IllegalArgumentException.class, wordMatchBuilder::build);
    }

    @Test
    void testExpectCorrectResult() {
        assertThat(new WordMatchBuilder().word("word").content("content#").milestone(0).build())
                .isEqualTo(new WordMatch("word", -1));
    }

    private static Stream<Arguments> testExpectIllegalArgumentExceptionArguments() {
        return Stream.of(Arguments.of(new WordMatchBuilder().word(null).content("content#").milestone(0)),
                Arguments.of(new WordMatchBuilder().word("word").content(null).milestone(0)),
                Arguments.of(new WordMatchBuilder().word("word").content("content#").milestone(null)));
    }
}