package com.example.search.engine.strategy;

import com.example.search.engine.model.LongWord;
import com.example.search.engine.model.WordMatch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.example.search.engine.factory.ContentFactory.content;
import static com.example.search.engine.factory.MilestonesFactory.milestones;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatchFirstStrategyTest {

    private final MatchFirstStrategy matchFirstStrategy = new MatchFirstStrategy();

    @Test
    void testExpectIllegalArgumentException() {
        LongWord longWord = new LongWord(content(), milestones());
        assertThrows(IllegalArgumentException.class, () -> matchFirstStrategy.matchAlgorithm(longWord).apply(null));
    }

    @ParameterizedTest
    @MethodSource("testMatchAlgorithmArguments")
    void testMatchAlgorithm(String word, List<WordMatch> expectedWordMatch) {
        LongWord longWord = new LongWord(content(), milestones());
        assertThat(matchFirstStrategy.matchAlgorithm(longWord).apply(word)).isEqualTo(expectedWordMatch);
    }

    @ParameterizedTest
    @MethodSource("testIsEligibleForSearchArguments")
    void testIsEligibleForSearch(String word, boolean expectedResult) {
        assertThat(matchFirstStrategy.isEligibleForSearch(milestones()).test(word)).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> testMatchAlgorithmArguments() {
        return Stream.of(Arguments.of("Dan Miles", List.of(new WordMatch("Dan Miles", 0))),
                         Arguments.of("Dougie Joyner", List.of(new WordMatch("Dougie Joyner", 20))),
                         Arguments.of("Dougie Joyner Sr", List.of(new WordMatch("Dougie Joyner Sr", -1))),
                         Arguments.of("Genevieve Ferreira", List.of(new WordMatch("Genevieve Ferreira", 50))),
                         Arguments.of("123456789012345678", List.of(new WordMatch("123456789012345678", -1))));
    }

    private static Stream<Arguments> testIsEligibleForSearchArguments() {
        return Stream.of(Arguments.of(null, false),
                         Arguments.of("", false),
                         Arguments.of("12345678", false),
                         Arguments.of("123456789", true),
                         Arguments.of("1234567890123", true),
                         Arguments.of("12345678901234", false),
                         Arguments.of("123456789012345678", true),
                         Arguments.of("1234567890123456789", false));
    }
}