package com.birin.wordgame.presentation.home;

import com.birin.wordgame.domain.score.HighscoreUseCase;

import rx.Subscription;

/**
 Created by Biraj on 9/14/16.
 */
public class HomePresenter {

    private HomeView homeView;
    private HighscoreUseCase highscoreUseCase;
    private Subscription highscoreSubscription;

    public HomePresenter(HighscoreUseCase highscoreUseCase) {
        this.highscoreUseCase = highscoreUseCase;
    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void setView(HomeView view) {

    }

    public void startClicked() {

    }
}
