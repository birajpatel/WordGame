package com.birin.wordgame.core.words;

import java.util.List;

import rx.Observable;

/**
 Created by Biraj on 9/13/16.
 */
public interface WordsRepository {

    Observable<List<WordEntity>> getWords();

}