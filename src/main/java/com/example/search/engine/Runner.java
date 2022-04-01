package com.example.search.engine;

import com.example.search.engine.exception.UnableToReadContentException;
import com.example.search.engine.helper.FileContentSupplier;
import com.example.search.engine.service.ContentReadService;
import com.example.search.engine.service.LongWordService;
import com.example.search.engine.service.ValidationService;
import com.example.search.engine.service.WordMatchService;
import com.example.search.engine.strategy.WordMatchStrategy;
import com.example.search.engine.validator.Validator;
import io.vavr.Tuple;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Slf4j
@Component
@ConditionalOnProperty(name="runner", havingValue="true")
public class Runner implements CommandLineRunner {

    @Override
    public void run(String... args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final String inputResource = args[0];
        final String searchResource = args[1];

        WordMatchService wordMatchService = new WordMatchService((WordMatchStrategy) Class.forName(args[2]).getDeclaredConstructor().newInstance());
        Validator validator = new ValidationService();

        Try.of(() -> new ContentReadService(FileContentSupplier.of(inputResource)).readLinesFromFile())
                .peek(input -> log.info("Read input size {}", input.size()))
                .peek(validator::validate)
                .map(LongWordService::new)
                .map(LongWordService::prepareLongWord)
                .peek(longWord -> log.info("Long Word size {}, Prepared milestones {}", longWord.content().length(), longWord.milestones()))
                .map(longWord -> Tuple.of(longWord, new ContentReadService(FileContentSupplier.of(searchResource)).readLinesFromFile()))
                .peek(tuple -> log.info("To match {}", tuple._2()))
                .map(tuple -> wordMatchService.matchWords(tuple._1(), tuple._2()))
                .onFailure(UnableToReadContentException.class, e -> log.error("Failed to perform search, unable to read content file, input ignored"))
                .onFailure(IllegalArgumentException.class, e -> log.error("Failed to perform search, unable to process input data, input ignored"))
                .onSuccess(result -> log.info("Successfully completed, matched words: {}", result));
    }
}
