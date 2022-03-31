package com.example.search.engine.model;

import java.util.SortedMap;

public record LongWord(String content, SortedMap<Integer, Integer> milestones) {

}
