package com.birin.wordgame.core.words;

/**
 Created by Biraj on 9/13/16.
 */
public class WordEntity {

    private String text_eng;
    private String text_spa;

    public WordEntity(String english, String spanish) {
        this.text_eng = english;
        this.text_spa = spanish;
    }

    public String getEnglishText() {
        return text_eng;
    }

    public String getSpanishText() {
        return text_spa;
    }

}