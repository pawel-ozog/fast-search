package com.example.search.engine.strategy;

import com.example.search.engine.builder.WordMatchBuilder;
import com.example.search.engine.helper.MilestoneResolver;
import com.example.search.engine.model.LongWord;
import com.example.search.engine.model.WordMatch;

import java.util.List;
import java.util.SortedMap;
import java.util.function.Function;
import java.util.function.Predicate;

public interface WordMatchStrategy {

    Function<String, List<WordMatch>> matchAlgorithm(final LongWord longWord);

    Predicate<String> isEligibleForSearch(final SortedMap<Integer, Integer> milestones);

    default Predicate<WordMatch> isEligibleForResult() {
        return wm -> wm.index() >= 0;
    };

    default WordMatch matchWord(final String content, final String word, final Integer milestone) {
        return new WordMatchBuilder().content(content).word(word).milestone(milestone).build();
    }

    default Integer resolveMilestone(SortedMap<Integer, Integer> milestones, String word) {
        return MilestoneResolver.of(milestones).resolveForWord(word);
    }
}
