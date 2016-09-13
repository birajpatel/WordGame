package com.birin.wordgame.presentation.game;

import com.birin.wordgame.domain.score.HighscoreUseCase;
import com.birin.wordgame.domain.score.ScoreUseCase;
import com.birin.wordgame.domain.timer.TimerUseCase;
import com.birin.wordgame.domain.words.Word;
import com.birin.wordgame.domain.words.WordsUseCase;
import com.birin.wordgame.presentation.model.WordsModelMapper;

import rx.subscriptions.CompositeSubscription;

/**
 Created by Biraj on 9/14/16.
 */
public class GamePresenter {

    private GameView gameView;
    private WordsUseCase wordsUseCase;
    private TimerUseCase timerUseCase;
    private ScoreUseCase scoreUseCase;
    private HighscoreUseCase highscoreUseCase;
    private WordsModelMapper wordsModelMapper;
    private CompositeSubscription subscriptions = new CompositeSubscription();
    private Word currentWord;
    private boolean isGamePaused;

    public GamePresenter(WordsUseCase wordsUseCase, TimerUseCase timerUseCase,
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
        wordsUseCase.loadNewWord();
        timerUseCase.initialize();
        subscribe();
    }

    private void subscribe() {
        subscriptions.add(wordsUseCase.wordObservable()
                                      .doOnNext(this::setCurrentWord)
                                      .map(wordsModelMapper::map)
                                      .subscribe(gameView::loadWord));
        subscriptions.add(wordsUseCase.wordTimerProgressObservable()
                                      .subscribe(progress -> {
                                          gameView.updateWordProgress(progress);
                                          if (progress == WordsUseCase.HUNDRED_PERCENT) {
                                              wordsUseCase.notAnswered();
                                              scoreUseCase.notAnswered();
                                          }
                                      }));

        subscriptions.add(timerUseCase.tickObservable()
                                      .subscribe(gameView::updateGameTimer,
                                                 Throwable::printStackTrace, this::handleGameEnd));

        subscriptions.add(scoreUseCase.scoreObservable()
                                      .subscribe(gameView::updateScore));

        subscriptions.add(highscoreUseCase.highscoreObservable()
                                          .subscribe(gameView::updateHighscore));
    }

    public void handleGameEnd() {
        highscoreUseCase.checkAndSaveHighscore(scoreUseCase.currentScore());
        gameView.closeGame();
    }

    public void setCurrentWord(Word newWord) {
        this.currentWord = newWord;
    }

    public boolean isGamePaused() {
        return isGamePaused;
    }

    public void pauseGame() {
        isGamePaused = true;
        unsubscribeAll();
        wordsUseCase.pauseGame();
        timerUseCase.pauseGame();
    }

    public void resumeGame() {
        if (isGamePaused == true) {
            isGamePaused = false;
            subscribe();
            wordsUseCase.resumeGame();
            timerUseCase.resumeGame();
        }
    }

    public void correctClicked() {
        handleAnswer(currentWord.isCorrect());
    }

    public void wrongClicked() {
        handleAnswer(!currentWord.isCorrect());
    }

    private void handleAnswer(boolean isCorrectAnswer) {
        if (isCorrectAnswer) {
            scoreUseCase.rightAnswered();
        } else {
            scoreUseCase.wrongAnswered();
        }
        wordsUseCase.answered();
    }

    public CompositeSubscription getSubscriptions() {
        return subscriptions;
    }

    private void unsubscribeAll() {
        if (!subscriptions.isUnsubscribed()) {
            subscriptions.clear();
        }
    }

}
