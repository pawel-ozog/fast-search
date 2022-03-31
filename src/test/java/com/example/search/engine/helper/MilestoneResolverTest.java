package com.example.search.engine.helper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.example.search.engine.factory.MilestonesFactory.milestones;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MilestoneResolverTest {

    @Test
    void shouldThrowIllegalArgumentExceptionWhenCreating() {
        assertThrows(IllegalArgumentException.class, () -> MilestoneResolver.of(null));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenResolvingWord() {
        assertThrows(IllegalArgumentException.class, () -> MilestoneResolver.of(milestones()).resolveForWord(null));
    }

    @ParameterizedTest
    @MethodSource("testResolveWordArguments")
    void testResolveWord(String word, Integer expectedMilestone) {
        MilestoneResolver milestoneResolver = MilestoneResolver.of(milestones());
        assertThat(milestoneResolver.resolveForWord(word)).isEqualTo(expectedMilestone);
    }

    private static Stream<Arguments> testResolveWordArguments() {
        return Stream.of(Arguments.of("", 0), Arguments.of("    ", 0), Arguments.of("a", 0), Arguments.of("123456789", 0),
                Arguments.of("ThirteenChars", 20), Arguments.of("EighteenChars Long", 50), Arguments.of("More Than Thirteen Chars", 0));
    }

}