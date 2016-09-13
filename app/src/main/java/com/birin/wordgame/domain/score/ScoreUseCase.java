package com.birin.wordgame.domain.score;

import com.birin.wordgame.core.score.ScoreManager;

import rx.Observable;

/**
 Created by Biraj on 9/13/16.
 */
public class ScoreUseCase {

    public static final int WRONG_ANSWER_PENALTY = 5;
    public static final int RIGHT_ANSWER_BONUS = 10;

    private ScoreManager scoreManager;

    public ScoreUseCase(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
    }

    public void rightAnswered() {
        scoreManager.incrementScore(RIGHT_ANSWER_BONUS);
    }

    public void wrongAnswered() {
        scoreManager.decrementScore(WRONG_ANSWER_PENALTY);
    }

    public void notAnswered() {
        // No penalty yet !!
    }

    public int currentScore() {
        return scoreManager.currentScore();
    }

    public Observable<Integer> scoreObservable() {
        return scoreManager.scoreObservable();
    }

}