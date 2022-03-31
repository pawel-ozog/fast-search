package com.example.search.engine.strategy;

import com.example.search.engine.model.LongWord;
import com.example.search.engine.model.WordMatch;

import java.util.List;
import java.util.SortedMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MatchAllOccurrencesStrategy implements WordMatchStrategy {

    @Override
    public Function<String, List<WordMatch>> matchAlgorithm(LongWord longWord) {
        return w -> recursiveMatch(longWord, matchWord(longWord.content(), w, resolveMilestone(longWord.milestones(), w))).toList();
    }

    @Override
    public Predicate<String> isEligibleForSearch(SortedMap<Integer, Integer> milestones) {
        return w -> w.length() <= milestones.lastKey();
    }

    @Override
    public Predicate<WordMatch> isEligibleForResult() {
        return wm -> true;
    }

    private Stream<WordMatch> recursiveMatch(final LongWord longWord, final WordMatch wordMatch) {
        WordMatch nextWordMatch = matchWord(longWord.content(), wordMatch.word(), nextIndexOfAtTheEndOfTheWord(wordMatch));
        return Stream.concat(Stream.of(wordMatch), Stream.of(nextWordMatch).takeWhile(wm -> wm.index() >= 0).flatMap(wm -> recursiveMatch(longWord, wm)));
    }

    private Integer nextIndexOfAtTheEndOfTheWord(WordMatch wordMatch) {
        return wordMatch.index() + wordMatch.word().length() + 1;
    }
}
