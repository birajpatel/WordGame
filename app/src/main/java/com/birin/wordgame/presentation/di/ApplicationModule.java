package com.birin.wordgame.presentation.di;

import android.content.Context;

import com.birin.wordgame.core.score.HighscoreRepository;
import com.birin.wordgame.domain.score.HighscoreUseCase;
import com.birin.wordgame.presentation.MainApplication;
import com.birin.wordgame.presentation.model.WordsModelMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 Created by Biraj on 9/14/16.
 */
@Module
public class ApplicationModule {

    private MainApplication mainApplication;

    public ApplicationModule(MainApplication mainApplication) {
        this.mainApplication = mainApplication;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return mainApplication.getApplicationContext();
    }

    @Provides
    @Singleton
    public WordsModelMapper provideWordsModelMapper() {
        return new WordsModelMapper();
    }

    @Provides
    @Singleton
    public HighscoreUseCase provideHighscoreUsecase(HighscoreRepository highscoreRepository) {
        return new HighscoreUseCase(highscoreRepository);
    }
}
