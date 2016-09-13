package com.birin.wordgame.presentation.game;

import com.birin.wordgame.domain.score.HighscoreUseCase;
import com.birin.wordgame.domain.score.ScoreUseCase;
import com.birin.wordgame.domain.timer.TimerUseCase;
import com.birin.wordgame.domain.words.Word;
import com.birin.wordgame.domain.words.WordsUseCase;
import com.birin.wordgame.presentation.model.WordModel;
import com.birin.wordgame.presentation.model.WordsModelMapper;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.schedulers.TestScheduler;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 Created by Biraj on 9/14/16.
 */
public class GamePresenterTest {

    private GamePresenter gamePresenter;
    private Word mockWord;
    private WordModel mockWordModel;

    @Mock private GameView gameView;
    @Mock private WordsUseCase wordsUseCase;
    @Mock private ScoreUseCase scoreUseCase;
    @Mock private HighscoreUseCase highscoreUseCase;
    @Mock private TimerUseCase timerUseCase;
    @Mock private WordsModelMapper modelMapper;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        gamePresenter = new GamePresenter(wordsUseCase, timerUseCase, scoreUseCase,
                                          highscoreUseCase, modelMapper);
        gamePresenter.setGameView(gameView);

        mockWord = new Word("English", "Spanish", true);
        mockWordModel = new WordModel("English", "Spanish");
        when(wordsUseCase.wordObservable()).thenReturn(Observable.just(mockWord));
        when(modelMapper.map(mockWord)).thenReturn(mockWordModel);
        when(wordsUseCase.wordTimerProgressObservable()).thenReturn(Observable.just(1f));
        when(timerUseCase.tickObservable()).thenReturn(Observable.just(1));
        when(scoreUseCase.scoreObservable()).thenReturn(Observable.just(1));
        when(highscoreUseCase.highscoreObservable()).thenReturn(Observable.just(1));
    }

    @Test
    public void shouldLoadWordOnInitialize() {
        gamePresenter.initialize();
        verify(wordsUseCase).wordObservable();
        verify(gameView).loadWord(mockWordModel);
    }

    @Test
    public void shouldUpdateWordTimerOnInitialize() {
        gamePresenter.initialize();
        verify(wordsUseCase).wordTimerProgressObservable();
        verify(gameView).updateWordProgress(anyInt());
    }

    @Test
    public void shouldUpdateGameTimerOnInitialize() {
        gamePresenter.initialize();
        verify(timerUseCase).tickObservable();
        verify(gameView).updateGameTimer(anyInt());
    }

    @Test
    public void shouldNotifyGamePauseToUsecases() {
        gamePresenter.pauseGame();
        verify(timerUseCase).pauseGame();
        verify(wordsUseCase).pauseGame();
    }

    @Test
    public void shouldUnsubscribeOnGamePause() {
        gamePresenter.initialize();
        assertEquals(false, gamePresenter.getSubscriptions()
                                         .isUnsubscribed());
        gamePresenter.pauseGame();
        assertEquals(false, gamePresenter.getSubscriptions()
                                         .hasSubscriptions());
    }

    @Test
    public void shouldNotifyGameResumeToUseCases() {
        gamePresenter.initialize();
        gamePresenter.resumeGame();
        assertEquals(false, gamePresenter.isGamePaused());
        verify(timerUseCase, times(0)).resumeGame();
        verify(wordsUseCase, times(0)).resumeGame();

        gamePresenter.pauseGame();
        assertEquals(true, gamePresenter.isGamePaused());
        gamePresenter.resumeGame();
        assertEquals(false, gamePresenter.isGamePaused());
        verify(timerUseCase).resumeGame();
        verify(wordsUseCase).resumeGame();
    }

    @Test
    public void shouldCallNotAnsweredCalledOn100Percent() {
        TestScheduler mockScheduler = new TestScheduler();
        when(wordsUseCase.wordTimerProgressObservable()).thenReturn(
                Observable.interval(WordsUseCase.PER_WORD_SINGLE_TICK_MILLIS, TimeUnit.MILLISECONDS,
                                    mockScheduler)
                          .map(Long::floatValue)
                          .filter(i -> i == WordsUseCase.HUNDRED_PERCENT));
        gamePresenter.initialize();
        mockScheduler.advanceTimeBy(WordsUseCase.PER_WORD_SINGLE_TICK_MILLIS * 201,
                                    TimeUnit.MILLISECONDS);
        verify(wordsUseCase).notAnswered();
        verify(scoreUseCase).notAnswered();
    }

    @Test
    public void shouldUpdateScoresOnInitialize() {
        gamePresenter.initialize();
        verify(gameView).updateScore(anyInt());
        verify(gameView).updateHighscore(anyInt());
    }

    @Test
    public void shouldCallUsecaseOnCorrectAnswer() {
        Word mockWord = new Word("English", "Spanish", true);
        when(wordsUseCase.wordObservable()).thenReturn(Observable.just(mockWord));
        gamePresenter.initialize();
        gamePresenter.correctClicked();
        verify(wordsUseCase).answered();
        verify(scoreUseCase).rightAnswered();
    }

    @Test
    public void shouldCallUsecaseOnWrongAnswer() {
        Word mockWord = new Word("English", "Spanish", false);
        when(wordsUseCase.wordObservable()).thenReturn(Observable.just(mockWord));
        gamePresenter.initialize();
        gamePresenter.wrongClicked();
        verify(wordsUseCase).answered();
        verify(scoreUseCase).rightAnswered();
    }

    @Test
    public void shouldHandleGameEnd() {
        TestScheduler testScheduler = new TestScheduler();
        when(timerUseCase.tickObservable()).thenReturn(
                Observable.interval(1, TimeUnit.SECONDS, testScheduler)
                          .take(TimerUseCase.TOTAL_CLOCK_TICKS)
                          .map(Long::intValue));
        gamePresenter.initialize();
        testScheduler.advanceTimeBy(TimerUseCase.TOTAL_CLOCK_TICKS, TimeUnit.SECONDS);
        verify(highscoreUseCase).checkAndSaveHighscore(anyInt());
        verify(gameView).closeGame();
    }

}
