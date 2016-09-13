package com.birin.wordgame.core.words;

import com.birin.wordgame.core.utils.AssetReader;
import com.google.gson.JsonSyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 Created by Biraj on 9/13/16.
 */
public class AssetWordsRepositoryTest {

    private AssetWordsRepository wordsRepository;
    private TestSubscriber<List<WordEntity>> testSubscriber;
    @Mock private AssetReader mockReader;
    @Mock private WordsParser mockParser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        wordsRepository = new AssetWordsRepository(mockReader, mockParser);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldEmitCorrectlyOnCorrectFile() throws IOException {
        List<WordEntity> mockWords = new ArrayList<>();
        mockWords.add(mock(WordEntity.class));
        mockWords.add(mock(WordEntity.class));
        when(mockParser.parse(anyString())).thenReturn(mockWords);
        wordsRepository.getWords()
                       .subscribe(testSubscriber);
        verify(mockReader).readFileFromAssets(anyString());
        verify(mockParser).parse(anyString());
        testSubscriber.assertValue(mockWords);
        testSubscriber.assertValueCount(1);
        testSubscriber.assertCompleted();
    }

    @Test
    public void shouldEmitErrorOnBadFileRead() throws IOException {
        Exception readException = new IOException();
        when(mockReader.readFileFromAssets(anyString())).thenThrow(readException);
        wordsRepository.getWords()
                       .subscribe(testSubscriber);
        testSubscriber.assertError(readException);
    }

    @Test
    public void shouldEmitErrorOnParsingFailure() throws IOException {
        Exception parseException = new JsonSyntaxException("Mock parse error");
        when(mockParser.parse(anyString())).thenThrow(parseException);
        wordsRepository.getWords()
                       .subscribe(testSubscriber);
        testSubscriber.assertError(parseException);
    }

}