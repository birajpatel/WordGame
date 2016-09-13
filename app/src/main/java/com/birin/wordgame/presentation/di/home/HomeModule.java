package com.birin.wordgame.presentation.di.home;

import com.birin.wordgame.domain.score.HighscoreUseCase;
import com.birin.wordgame.presentation.home.HomePresenter;

import dagger.Module;
import dagger.Provides;

/**
 Created by Biraj on 9/14/16.
 */
@HomeScope
@Module
public class HomeModule {

    @Provides
    @HomeScope
    public HomePresenter provideHomePresenter(HighscoreUseCase useCase) {
        return new HomePresenter(useCase);
    }
}