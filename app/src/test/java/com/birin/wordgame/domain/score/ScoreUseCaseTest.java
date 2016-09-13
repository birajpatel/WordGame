package com.birin.wordgame.domain.score;

import com.birin.wordgame.core.score.ScoreManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 Created by Biraj on 9/13/16.
 */
public class ScoreUseCaseTest {

    private ScoreUseCase scoreUseCase;
    @Mock private ScoreManager scoreManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        scoreUseCase = new ScoreUseCase(scoreManager);
    }

    @Test
    public void shouldIncrementScoreOnRightAnswer() {
        scoreUseCase.rightAnswered();
        verify(scoreManager).incrementScore(anyInt());
    }

    @Test
    public void shouldDecrementScoreOnWrongAnswer() {
        scoreUseCase.wrongAnswered();
        verify(scoreManager).decrementScore(anyInt());
    }

    @Test
    public void shouldNotCareAboutNoAnswer() {
        scoreUseCase.notAnswered();
        verifyZeroInteractions(scoreManager);
    }

    @Test
    public void shouldReturnScoreObservable() {
        Observable<Integer> fakeObservable = mock(Observable.class);
        when(scoreManager.scoreObservable()).thenReturn(fakeObservable);
        Observable<Integer> actual = scoreUseCase.scoreObservable();
        assertEquals(actual, fakeObservable);
    }


}