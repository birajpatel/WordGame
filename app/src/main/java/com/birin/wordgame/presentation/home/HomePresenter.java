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
        highscoreSubscription = highscoreUseCase.highscoreObservable()
                                                .subscribe(homeView::updateHighScore);
    }

    public void onPause() {
        if (null != highscoreSubscription && !highscoreSubscription.isUnsubscribed()) {
            highscoreSubscription.unsubscribe();
        }
    }

    public void setView(HomeView view) {
        this.homeView = view;
    }

    public void startClicked() {
        homeView.startGame();
    }
}
