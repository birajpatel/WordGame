package com.birin.wordgame.core.score;

import com.birin.wordgame.core.utils.KeyValueDiskStore;

import rx.Observable;

/**
 Created by Biraj on 9/13/16.
 */
public class HighscoreRepository {

    private KeyValueDiskStore keyValueDiskStore;

    public HighscoreRepository(KeyValueDiskStore keyValueDiskStore) {
        this.keyValueDiskStore = keyValueDiskStore;
    }

    public void updateHighscore(int newScore) {

    }

    public int currentHighscore() {
        return 0;
    }

    public Observable<Integer> highscoreObservable() {
        return null;
    }

}