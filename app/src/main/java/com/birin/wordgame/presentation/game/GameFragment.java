package com.birin.wordgame.presentation.game;

import com.birin.wordgame.presentation.BaseFragment;
import com.birin.wordgame.presentation.model.WordModel;

/**
 Created by Biraj on 9/14/16.
 */
public class GameFragment extends BaseFragment implements GameView {

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Override
    public void loadWord(WordModel wordModel) {

    }

    @Override
    public void updateWordProgress(float wordProgress) {

    }

    @Override
    public void updateGameTimer(int gameTimer) {

    }

    @Override
    public void updateScore(int newScore) {

    }

    @Override
    public void updateHighscore(int highScore) {

    }

    @Override
    public void closeGame() {

    }
}
