package com.birin.wordgame.core.score;

import com.birin.wordgame.core.utils.KeyValueDiskStore;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 Created by Biraj on 9/13/16.
 */
public class HighscoreRepository {

    public static final String KEY_HIGHSCORE = "high_score";

    private KeyValueDiskStore keyValueDiskStore;
    private BehaviorSubject<Integer> highscore = BehaviorSubject.create();

    public HighscoreRepository(KeyValueDiskStore keyValueDiskStore) {
        this.keyValueDiskStore = keyValueDiskStore;
        this.highscore.onNext(keyValueDiskStore.readInt(KEY_HIGHSCORE, 0));
    }

    public void updateHighscore(int newScore) {
        keyValueDiskStore.writeInt(KEY_HIGHSCORE, newScore);
        highscore.onNext(newScore);
    }

    public int currentHighscore() {
        return highscore.getValue();
    }

    public Observable<Integer> highscoreObservable() {
        return highscore.asObservable();
    }

}