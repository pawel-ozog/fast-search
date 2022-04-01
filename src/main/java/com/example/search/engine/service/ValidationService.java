package com.example.search.engine.service;

import com.example.search.engine.exception.ValidationException;
import com.example.search.engine.validator.Validator;

import java.util.List;

public class ValidationService implements Validator {

    public void validate(List<String> words){
        words.stream()
                .filter(word -> word.equals("#"))
                .findFirst()
                .ifPresent(e -> {throw new ValidationException("Illegal Character");});
    }

}
