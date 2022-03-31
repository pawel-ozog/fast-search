package com.example.search.engine.factory;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MilestonesFactory {

    public static SortedMap<Integer, Integer> milestones() {
        return new TreeMap<>(Map.of(9,0, 13, 20, 15, 34, 18, 50));
    }
}
