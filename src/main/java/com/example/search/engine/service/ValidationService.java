package com.example.search.engine.service;

import com.example.search.engine.exception.IllegalCharacterValidationException;
import com.example.search.engine.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.List;


public class ValidationService implements Validator {

    private static final String ILLEGAL_CHARACTERS = "!@#$%^&*()_+-=";

    public void validate(List<String> inputs) {
        Assert.notNull(inputs, "inputs can not be null");

        inputs.parallelStream()
                .filter(word -> StringUtils.containsAny(word, ILLEGAL_CHARACTERS))
                .findFirst()
                .ifPresent(s -> {
                    throw new IllegalCharacterValidationException(String.format("Illegal %s character appear", s));
                });
    }

}
