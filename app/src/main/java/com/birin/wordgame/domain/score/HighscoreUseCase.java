package com.birin.wordgame.domain.score;

import com.birin.wordgame.core.score.HighscoreRepository;

import rx.Observable;

/**
 Created by Biraj on 9/13/16.
 */
public class HighscoreUseCase {

    public HighscoreRepository highscoreRepository;

    public HighscoreUseCase(HighscoreRepository highscoreRepository) {
        this.highscoreRepository = highscoreRepository;
    }

    public void checkAndSaveHighscore(int finalScore) {
        if (finalScore > highscoreRepository.currentHighscore()) {
            highscoreRepository.updateHighscore(finalScore);
        }
    }

    public Observable<Integer> highscoreObservable() {
        return highscoreRepository.highscoreObservable();
    }

}
