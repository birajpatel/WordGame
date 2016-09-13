package com.birin.wordgame.domain.score;

import com.birin.wordgame.core.score.HighscoreRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.Observable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 Created by Biraj on 9/13/16.
 */
public class HighscoreUseCaseTest {

    private HighscoreUseCase highscoreUseCase;
    @Mock private HighscoreRepository highscoreRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        highscoreUseCase = new HighscoreUseCase(highscoreRepository);
    }

    @Test
    public void shouldCallUpdateOnlyIfGreatThanCurrentHighscore() {
        when(highscoreRepository.currentHighscore()).thenReturn(10);
        highscoreUseCase.checkAndSaveHighscore(5);
        verify(highscoreRepository, times(0)).updateHighscore(5);

        highscoreUseCase.checkAndSaveHighscore(10);
        verify(highscoreRepository, times(0)).updateHighscore(10);

        highscoreUseCase.checkAndSaveHighscore(15);
        verify(highscoreRepository, times(1)).updateHighscore(15);
    }

    @Test
    public void shouldSimplyReturnObservableFromRepository() {
        Observable<Integer> actual = Observable.just(1);
        when(highscoreRepository.highscoreObservable()).thenReturn(actual);
        Observable<Integer> expected = highscoreUseCase.highscoreObservable();
        assertEquals(expected, actual);
    }

}
