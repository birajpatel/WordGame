package com.birin.wordgame.presentation.game;

import com.birin.wordgame.presentation.model.WordModel;

/**
 Created by Biraj on 9/14/16.
 */
public interface GameView {

    void loadWord(WordModel wordModel);

    void updateWordProgress(float wordProgress);

    void updateGameTimer(int gameTimer);

    void updateScore(int newScore);

    void updateHighscore(int highScore);

    void closeGame();
}
