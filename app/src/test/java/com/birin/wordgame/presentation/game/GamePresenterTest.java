package com.birin.wordgame.presentation.game;

import com.birin.wordgame.domain.score.HighscoreUseCase;
import com.birin.wordgame.domain.score.ScoreUseCase;
import com.birin.wordgame.domain.timer.TimerUseCase;
import com.birin.wordgame.domain.words.Word;
import com.birin.wordgame.domain.words.WordsUseCase;
import com.birin.wordgame.presentation.model.WordsModelMapper;

/**
 Created by Biraj on 9/14/16.
 */
public class GamePresenterTest {

    private GameView gameView;
    private WordsUseCase wordsUseCase;
    private TimerUseCase timerUseCase;
    private ScoreUseCase scoreUseCase;
    private HighscoreUseCase highscoreUseCase;
    private WordsModelMapper wordsModelMapper;
    private Word currentWord;
    private boolean isGamePaused;

    public GamePresenterTest(WordsUseCase wordsUseCase, TimerUseCase timerUseCase,
                             ScoreUseCase scoreUseCase, HighscoreUseCase highscoreUseCase,
                             WordsModelMapper wordsModelMapper) {
        this.wordsUseCase = wordsUseCase;
        this.timerUseCase = timerUseCase;
        this.scoreUseCase = scoreUseCase;
        this.highscoreUseCase = highscoreUseCase;
        this.wordsModelMapper = wordsModelMapper;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }


    public void initialize() {

    }

    public void setCurrentWord(Word newWord) {
        this.currentWord = newWord;
    }

    public boolean isGamePaused() {
        return isGamePaused;
    }

    public void pauseGame() {
        isGamePaused = true;
    }

    public void resumeGame() {
        if (isGamePaused == true) {
            isGamePaused = false;
        }
    }

    public void correctClicked() {

    }

    public void wrongClicked() {

    }


}
