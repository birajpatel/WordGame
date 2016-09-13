package com.birin.wordgame.presentation.model;

import com.birin.wordgame.domain.words.Word;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 Created by Biraj on 9/14/16.
 */
public class WordsModelMapperTest {

    private WordsModelMapper wordsModelMapper;

    @Before
    public void setup() {
        wordsModelMapper = new WordsModelMapper();
    }

    @Test
    public void shouldMapToModel() {
        Word word = new Word("eng", "spa", true);
        WordModel mappedModel = wordsModelMapper.map(word);
        assertEquals("eng", mappedModel.getEnglishWord());
        assertEquals("spa", mappedModel.getSpanishWord());
    }

}
