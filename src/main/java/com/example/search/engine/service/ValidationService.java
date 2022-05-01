package com.example.search.engine.service;

import com.example.search.engine.configuration.Properties;
import com.example.search.engine.exception.IllegalCharacterValidationException;
import com.example.search.engine.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
@Service
public class ValidationService implements Validator {

    @Autowired
    Properties properties;

    public void validate(List<String> inputs) {

        Assert.notNull(inputs, "inputs can not be null");

        String illegalSymbols = properties.getIllegalSymbols();

        inputs.parallelStream()
                .filter(word -> StringUtils.containsAny(word, illegalSymbols))
                .findFirst()
                .ifPresent(s -> {
                    throw new IllegalCharacterValidationException(String.format("Illegal %s character appear", s));
                });
    }

}
