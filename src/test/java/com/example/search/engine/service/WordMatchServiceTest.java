package com.example.search.engine.service;

import com.example.search.engine.model.LongWord;
import com.example.search.engine.model.WordMatch;
import com.example.search.engine.strategy.MatchFirstStrategy;
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

class WordMatchServiceTest {

    private final WordMatchService wordMatchService = new WordMatchService(new MatchFirstStrategy());

    @ParameterizedTest
    @MethodSource("testShouldThrowIllegalArgumentExceptionArguments")
    void testShouldThrowIllegalArgumentException(final LongWord longWord, final List<String> toMatch) {
        assertThrows(IllegalArgumentException.class, () -> wordMatchService.matchWords(longWord, toMatch));
    }

    @ParameterizedTest
    @MethodSource("testMatchWordsArguments")
    void testMatchWords(List<String> toMatch, List<WordMatch> expectedMatches) {
        LongWord longWord = new LongWord(content(), milestones());
        assertThat(wordMatchService.matchWords(longWord, toMatch)).isEqualTo(expectedMatches);
    }

    private static Stream<Arguments> testShouldThrowIllegalArgumentExceptionArguments() {
        return Stream.of(Arguments.of(null, Collections.emptyList()),
                        Arguments.of(new LongWord(null, Collections.emptySortedMap()), Collections.emptyList()),
                        Arguments.of(new LongWord("", null), Collections.emptyList()),
                        Arguments.of(new LongWord("", Collections.emptySortedMap()), null));
    }

    private static Stream<Arguments> testMatchWordsArguments() {
        return Stream.of(Arguments.of(Collections.emptyList(), Collections.emptyList()),
                Arguments.of(List.of("", "  ", "a"), Collections.emptyList()),
                Arguments.of(List.of("xxxxxfsadf"), Collections.emptyList()),
                Arguments.of(List.of("Dan Miles"), List.of(new WordMatch("Dan Miles", 0))),
                Arguments.of(List.of("", "Dan Miles"), List.of(new WordMatch("Dan Miles", 0))),
                Arguments.of(List.of("Layla-Rose Rich"), List.of(new WordMatch("Layla-Rose Rich", 34))),
                Arguments.of(List.of("Lot Prone", "Layla-Rose Rich"), List.of(new WordMatch("Lot Prone", 10), new WordMatch("Layla-Rose Rich", 34))),
                Arguments.of(List.of("Genevieve Ferreira"), List.of(new WordMatch("Genevieve Ferreira", 50)))
                );
    }
}