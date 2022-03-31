package com.example.search.engine.service;

import com.example.search.engine.model.LongWord;
import com.example.search.engine.model.WordMatch;
import com.example.search.engine.strategy.WordMatchStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
public class WordMatchService {

    private final WordMatchStrategy wordMatchStrategy;

    public WordMatchService(final WordMatchStrategy wordMatchStrategy) {
        this.wordMatchStrategy = wordMatchStrategy;
    }

    public List<WordMatch> matchWords(final LongWord longWord, List<String> toMatch) {
        Assert.notNull(longWord, "longWord must not be null");
        Assert.notNull(longWord.content(), "content must not be null");
        Assert.notNull(longWord.milestones(), "milestones must not be null");
        Assert.notNull(toMatch, "toMatch must not be null");

        return toMatch.stream()
                .filter(StringUtils::isNotBlank)
                .map(StringUtils::trim)
                .filter(wordMatchStrategy.isEligibleForSearch(longWord.milestones()))
                .map(wordMatchStrategy.matchAlgorithm(longWord))
                .flatMap(List::stream)
                .peek(wm -> log.trace("Word {} matched at index {}", wm.word(), wm.index()))
                .filter(wordMatchStrategy.isEligibleForResult())
                .toList();
    }

}
