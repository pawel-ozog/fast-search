package com.example.search.engine;

import com.example.search.engine.exception.UnableToReadContentException;
import com.example.search.engine.supplier.ContentSupplier;
import com.example.search.engine.supplier.FileContentSupplier;
import com.example.search.engine.service.LongWordService;
import com.example.search.engine.service.ValidationService;
import com.example.search.engine.service.WordMatchService;
import com.example.search.engine.strategy.WordMatchStrategy;
import com.example.search.engine.supplier.Source;
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
    public void run(String... args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        final String inputResource = args[0];
        final String searchResource = args[1];
        final String strategy = args[2];

           match(inputResource,searchResource,strategy);
    }

    private void match(String inputResource, String searchResource, String strategy) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        WordMatchService wordMatchService = new WordMatchService((WordMatchStrategy) Class.forName(strategy).getDeclaredConstructor().newInstance());
        Validator validator = new ValidationService();

        ContentSupplier supplier = FileContentSupplier.of(inputResource);

        Try.of(supplier::supplyContent)
                .peek(input -> log.info("Read input size {}", input.size()))
                .peek(validator::validate)
                .map(LongWordService::new)
                .map(LongWordService::prepareLongWord)
                .peek(longWord -> log.info("Long Word size {}, Prepared milestones {}", longWord.content().length(), longWord.milestones()))
                .map(longWord -> Tuple.of(longWord, (supplier.supplyContent())))
                .peek(tuple -> log.info("To match {}", tuple._2()))
                .peek(tuple -> validator.validate(tuple._2))
                .onFailure(input -> log.info(input.getMessage()))
                .map(tuple -> wordMatchService.matchWords(tuple._1(), tuple._2()))
                .onFailure(UnableToReadContentException.class, e -> log.error("Failed to perform search, unable to read content file, input ignored"))
                .onFailure(IllegalArgumentException.class, e -> log.error("Failed to perform search, unable to process input data, input ignored"))
                .onSuccess(result -> log.info("Successfully completed, matched words: {}", result));
    }
}