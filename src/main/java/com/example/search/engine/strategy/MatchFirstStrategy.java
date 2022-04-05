package com.example.search.engine.strategy;

import com.example.search.engine.model.LongWord;
import com.example.search.engine.model.WordMatch;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.SortedMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class MatchFirstStrategy implements WordMatchStrategy {

    @Override
    public Function<String, List<WordMatch>> matchAlgorithm(final LongWord longWord) {
        return w -> List.of(matchWord(longWord.content(), w, resolveMilestone(longWord.milestones(), w)));
    }

    @Override
    public Predicate<String> isEligibleForSearch(SortedMap<Integer, Integer> milestones) {
        return w -> milestones.containsKey(StringUtils.length(w));
    }
}
