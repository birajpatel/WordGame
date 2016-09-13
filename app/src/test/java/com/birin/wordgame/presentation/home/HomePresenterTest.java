package com.birin.wordgame.presentation.home;

import com.birin.wordgame.domain.score.HighscoreUseCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 Created by Biraj on 9/14/16.
 */
public class HomePresenterTest {

    private HomePresenter homePresenter;
    @Mock private HighscoreUseCase highscoreUseCase;
    @Mock private HomeView homeView;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        homePresenter = new HomePresenter(highscoreUseCase);
        homePresenter.setView(homeView);
    }

    @Test
    public void shouldCallStartGameOnStartClick() {
        homePresenter.startClicked();
        verify(homeView).startGame();
    }

}
