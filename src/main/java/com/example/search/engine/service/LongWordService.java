package com.example.search.engine.service;

import com.example.search.engine.model.LongWord;
import org.springframework.util.Assert;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class LongWordService {
    public static final Character DELIMITER = '#';

    private final List<String> input;

    public LongWordService(List<String> input) {
        Assert.notNull(input, "input content must not be null");
        this.input = input;
    }

    public LongWord prepareLongWord() {
        String content = prepareContent(input);
        return new LongWord(content, prepareMilestones(content));
    }

    private String prepareContent(List<String> input) {
        final String sDelimiter = String.valueOf(DELIMITER);
        return input.stream()
                .filter(Objects::nonNull)
                .filter(not(String::isBlank))
                .collect(Collectors.toSet())
                .stream()
                .map(StringUtils::trim)
                .sorted(Comparator.comparingInt(String::length))
                .reduce((a, b) -> a.concat(sDelimiter).concat(b))
                .orElse("")
                .concat(sDelimiter);
    }

    private SortedMap<Integer, Integer> prepareMilestones(String content) {
        if(Objects.equals(content, String.valueOf(DELIMITER))) {
            return Collections.emptySortedMap();
        }

        SortedMap<Integer, Integer> milestones = new TreeMap<>();

        int position = 0;
        int length = 0;

        char[] contentChars = content.toCharArray();
        int contentLength = content.length();
        for(int i=0; i < contentLength; i++) {
            position++;
            length++;
            char c = contentChars[i];

            if(c == DELIMITER) {
                milestones.putIfAbsent(length - 1, position - length);
                length = 0;
            }

        }
        return milestones;
    }
}
