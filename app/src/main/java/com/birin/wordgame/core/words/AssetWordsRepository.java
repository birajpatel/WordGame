package com.birin.wordgame.core.words;

import com.birin.wordgame.core.utils.AssetReader;

import java.util.List;

import rx.Observable;

/**
 Created by Biraj on 9/13/16.
 */
public class AssetWordsRepository implements WordsRepository {

    private AssetReader assetReader;
    private WordsParser wordsParser;

    public AssetWordsRepository(AssetReader assetReader, WordsParser parser) {
        this.assetReader = assetReader;
        this.wordsParser = parser;
    }

    @Override
    public Observable<List<WordEntity>> getWords() {
        return null;
    }
}