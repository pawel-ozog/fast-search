package com.example.search.engine.factory;

import java.util.List;

public class ContentFactory {

    public static List<String> inputList() {
        return List.of("Genevieve Ferreira", "Dan Miles", "Lot Prone", "Lot Prone", " Dougie Joyner ", "Layla-Rose Rich", "", "   ");
    }

    public static String content() {
        return "Dan Miles#Lot Prone#Dougie Joyner#Layla-Rose Rich#Genevieve Ferreira#";
    }
}
