package com.birin.wordgame.core.words;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 Created by Biraj on 9/13/16.
 */
public class WordsParser {

    private Gson gson;

    public WordsParser() {
        gson = new Gson();
    }

    public List<WordEntity> parse(String data) throws JsonSyntaxException {
        Type type = new TypeToken<List<WordEntity>>() {
        }.getType();
        return this.gson.fromJson(data, type);
    }
}