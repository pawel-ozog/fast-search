package com.example.search.engine.service;

import com.example.search.engine.model.LongWord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static com.example.search.engine.factory.ContentFactory.inputList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LongWordServiceTest {

    @Test
    void testExpectIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new LongWordService(null));
    }

    @ParameterizedTest
    @MethodSource("expectedLongWordWithEmptyInput")
    void testExpectedLongWordWithEmptyInput(List<String> inputCollection) {
        LongWordService longWordService = new LongWordService(inputCollection);

        LongWord longWord = longWordService.prepareLongWord();
        assertThat(longWord.content()).isEqualTo("#");
        assertThat(longWord.milestones()).isNotNull().isEmpty();
    }

    @Test
    void testExpectedLongWordCorrectOneInput() {
        List<String> input = new ArrayList<>(List.of("Genevieve Ferreira"));
        LongWordService longWordService = new LongWordService(input);

        LongWord longWord = longWordService.prepareLongWord();

        assertThat(longWord.content()).isNotNull().isEqualTo("Genevieve Ferreira#");
        assertThat(longWord.milestones()).isNotNull().hasSize(1).containsKey(18).containsValues(0);
    }

    @Test
    void testExpectedLongWordCorrectManyInputs() {
        LongWord longWord = new LongWordService(inputList()).prepareLongWord();

        assertThat(longWord.content())
                .isNotBlank()
                .containsPattern("(#.*?){3}")
                .startsWith("Dan Miles")
                .endsWith("Genevieve Ferreira#")
                .contains("Dougie Joyner", "Layla-Rose Rich", "Lot Prone")
                .hasSize(69);

        System.out.println(longWord.content());
        assertThat(longWord.milestones())
                .isNotNull()
                .isInstanceOfAny(SortedMap.class)
                .hasSize(4)
                .containsKeys(9, 13, 15, 18)
                .containsValues(0, 20, 34, 50);
    }

    public static Stream<Arguments> expectedLongWordWithEmptyInput() {
        return Stream.of(Arguments.of(Collections.emptyList()), Arguments.of(Arrays.asList((String)null)));
    }
}