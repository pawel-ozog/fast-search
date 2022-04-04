package com.example.search.engine.service;

import com.example.search.engine.exception.ValidationException;
import com.example.search.engine.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.List;


public class ValidationService implements Validator {

    public void validate(List<String> input) {
        Assert.notNull(input,"input can not be null");
        String illegalSymbols = "!@#$%^&*()_+-=";
        input.stream()
                .filter(word -> StringUtils.containsAny(word, illegalSymbols))
                .findFirst()
                .ifPresent(s -> {
                    throw new ValidationException("Illegal argument appeared");
                });
    }

}
