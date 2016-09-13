package com.birin.wordgame.presentation.model;


import com.birin.wordgame.domain.words.Word;

/**
 Created by Biraj on 9/14/16.
 */
public class WordsModelMapper {

    public WordModel map(Word word) {
        if (word == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        return new WordModel(word.getEnglishWord(), word.getSpanishWord());
    }

}
