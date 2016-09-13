package com.birin.wordgame.presentation.model;

/**
 Created by Biraj on 9/11/16.
 */
public class WordModel {

    private String englishWord;
    private String spanishWord;

    public WordModel(String englishWord, String spanishWord) {
        this.englishWord = englishWord;
        this.spanishWord = spanishWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public String getSpanishWord() {
        return spanishWord;
    }
}
