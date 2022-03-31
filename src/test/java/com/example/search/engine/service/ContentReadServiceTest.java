package com.example.search.engine.service;

import com.example.search.engine.exception.UnableToReadContentException;
import com.example.search.engine.helper.FileContentSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class ContentReadServiceTest {

    @Mock
    FileContentSupplier fileContentSupplier;

    @BeforeEach
    void init() {
        openMocks(this);
    }

    @Test
    void testExpectIllegalArgumentExceptionWhenNewReaderTestCreated() {
        assertThrows(IllegalArgumentException.class, () -> new ContentReadService(null));
    }

    @Test
    void testExpectNoExceptionWhenNewReaderTestCreated() {
        assertDoesNotThrow(() -> new ContentReadService(fileContentSupplier));
    }

    @Test
    void testExpectEmptyList() throws IOException {
        //when
        when(fileContentSupplier.apply()).thenThrow(new RuntimeException("Something went wrong"));

        //given
        final ContentReadService contentReadService = new ContentReadService(fileContentSupplier);

        //assert
        assertThrows(UnableToReadContentException.class, contentReadService::readLinesFromFile);
    }

    @Test
    void testExpectReturnList() throws IOException, UnableToReadContentException {
        //when
        final List<String> returnedList = Arrays.asList("a", "b", "c");
        when(fileContentSupplier.apply()).thenReturn(returnedList);

        //given
        final ContentReadService contentReadService = new ContentReadService(fileContentSupplier);

        //assert
        assertThat(contentReadService.readLinesFromFile()).isEqualTo(returnedList);
    }
}