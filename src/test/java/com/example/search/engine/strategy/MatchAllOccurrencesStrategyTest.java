package com.example.search.engine.strategy;

import com.example.search.engine.model.LongWord;
import com.example.search.engine.model.WordMatch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.example.search.engine.factory.ContentFactory.content;
import static com.example.search.engine.factory.MilestonesFactory.milestones;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MatchAllOccurrencesStrategyTest {

    private final MatchAllOccurrencesStrategy matchAllOccurrencesStrategy = new MatchAllOccurrencesStrategy();

    @Test
    void testExpectIllegalArgumentException() {
        LongWord longWord = new LongWord(content(), milestones());
        assertThrows(IllegalArgumentException.class, () -> matchAllOccurrencesStrategy.matchAlgorithm(longWord).apply(null));
    }

    @ParameterizedTest
    @MethodSource("testMatchAlgorithmArguments")
    void testMatchAlgorithm(String word, List<WordMatch> expectedWordMatch) {
        LongWord longWord = new LongWord(content(), milestones());
        assertThat(matchAllOccurrencesStrategy.matchAlgorithm(longWord).apply(word)).isEqualTo(expectedWordMatch);
    }

    @Test
    void testIsEligibleForSearchWithEmptyMilestones() {
        assertThat(matchAllOccurrencesStrategy.isEligibleForSearch(Collections.emptySortedMap()).test("123456")).isEqualTo(false);
    }

    @ParameterizedTest
    @MethodSource("testIsEligibleForSearchArguments")
    void testIsEligibleForSearch(String word, boolean expectedResult) {
        assertThat(matchAllOccurrencesStrategy.isEligibleForSearch(milestones()).test(word)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("testIsEligibleForResultArguments")
    void testIsEligibleForResult(WordMatch wordMatch, boolean expectedResult) {
        assertThat(matchAllOccurrencesStrategy.isEligibleForResult().test(wordMatch)).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> testMatchAlgorithmArguments() {
        return Stream.of(Arguments.of("Dan Miles", List.of(new WordMatch("Dan Miles", 0))),
                Arguments.of("D", List.of(new WordMatch("D", 0), new WordMatch("D", 20))),
                Arguments.of("ne", List.of(new WordMatch("ne", 17), new WordMatch("ne", 30), new WordMatch("ne", 52))),
                Arguments.of("Dougie Joyner Sr", List.of(new WordMatch("Dougie Joyner Sr", -1))),
                Arguments.of("Genevieve Ferreira", List.of(new WordMatch("Genevieve Ferreira", 50))),
                Arguments.of("123456789 012345678", List.of(new WordMatch("123456789 012345678", -1))));
    }

    private static Stream<Arguments> testIsEligibleForResultArguments() {
        return Stream.of(Arguments.of(null, false),
                Arguments.of(new WordMatch("", 0), true),
                Arguments.of(new WordMatch("2", -1), true),
                Arguments.of(new WordMatch("1234", 8), true));
    }

    private static Stream<Arguments> testIsEligibleForSearchArguments() {
        return Stream.of(Arguments.of(null, false),
                Arguments.of("", false),
                Arguments.of("12345678", true),
                Arguments.of("1234567890123", true),
                Arguments.of("123456789012345678", true),
                Arguments.of("1234567890123456789", false));
    }

}