package com.example.search.engine.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;

@Slf4j
public class MilestoneResolver {

    /**
     * Key is a word's length
     * Value is a index of first word whose lenght's equal to key
     * */
    private final SortedMap<Integer, Integer> milestones;

    private MilestoneResolver(final SortedMap<Integer, Integer> milestones) {
        Assert.notNull(milestones, "milestones must not be null"); //throws exception if milestone is null
        this.milestones = milestones;
    }

    public static MilestoneResolver of(SortedMap<Integer, Integer> milestones) { //of is a factory method
        return new MilestoneResolver(milestones);
    }

    public Integer resolveForWord(final String word) {
        Assert.notNull(word, "word must not be null");

        Map.Entry<Integer, Integer> current = null;
        boolean isMilestoneFound = false;
        for (Map.Entry<Integer, Integer> next : milestones.entrySet()) {
            if (isWordShorterThanMilestone(next.getKey(), word)) {
                isMilestoneFound = true;
                break;
            }
            current = next;
        }

        if(isWordEqualInLengthWithLastMilestone(milestones.lastKey(), word)) {
            isMilestoneFound = true;
        }

        if(isMilestoneFound && current != null) {
            log.trace("Word {}, milestone {}", word, current.getValue());
            return current.getValue();
        } else {
            return 0;
        }
    }

    private boolean isWordShorterThanMilestone(Integer milestoneLength, String word) {
        return word.length() < milestoneLength;
    }

    private boolean isWordEqualInLengthWithLastMilestone(Integer milestoneLength, String word) {
        return Objects.equals(word.length(), milestoneLength);
    }
}