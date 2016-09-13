package com.birin.wordgame.core.score;


import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 Created by Biraj on 9/13/16.
 */
public class ScoreManager {

    private BehaviorSubject<Integer> score = BehaviorSubject.create(0);

    public void incrementScore(final int incrementBy) {
        score.onNext(currentScore() + incrementBy);
    }

    public void decrementScore(final int decrementBy) {
        score.onNext(currentScore() - decrementBy);
    }

    public int currentScore() {
        return score.getValue();
    }

    public Observable<Integer> scoreObservable() {
        return score.asObservable();
    }

}