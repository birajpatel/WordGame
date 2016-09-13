package com.birin.wordgame.domain.score;

import com.birin.wordgame.core.score.ScoreManager;

import rx.Observable;

/**
 Created by Biraj on 9/13/16.
 */
public class ScoreUseCase {

    private ScoreManager scoreManager;

    public ScoreUseCase(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
    }

    public void rightAnswered() {

    }

    public void wrongAnswered() {

    }

    public void notAnswered() {

    }

    public int currentScore() {
        return 0;
    }

    public Observable<Integer> scoreObservable() {
        return null;
    }

}