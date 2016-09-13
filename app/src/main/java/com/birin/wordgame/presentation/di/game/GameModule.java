package com.birin.wordgame.presentation.di.game;


import com.birin.wordgame.core.score.ScoreManager;
import com.birin.wordgame.domain.score.HighscoreUseCase;
import com.birin.wordgame.domain.score.ScoreUseCase;
import com.birin.wordgame.domain.timer.TimerUseCase;
import com.birin.wordgame.domain.words.AnswerChooser;
import com.birin.wordgame.domain.words.RandomAnswerChooser;
import com.birin.wordgame.domain.words.WordsUseCase;
import com.birin.wordgame.presentation.game.GamePresenter;
import com.birin.wordgame.presentation.model.WordsModelMapper;

import dagger.Module;
import dagger.Provides;

/**
 Created by Biraj on 9/14/16.
 */
@Module
public class GameModule {

    @Provides
    @GameScope
    public GamePresenter providePresenter(WordsUseCase wordsUseCase, TimerUseCase timerUseCase,
                                          ScoreUseCase scoreUseCase,
                                          HighscoreUseCase highscoreUseCase,
                                          WordsModelMapper wordsModelMapper) {
        return new GamePresenter(wordsUseCase, timerUseCase, scoreUseCase, highscoreUseCase,
                                 wordsModelMapper);
    }

    @Provides
    @GameScope
    public AnswerChooser provideAnswerChooser() {
        return new RandomAnswerChooser();
    }

    @Provides
    @GameScope
    public ScoreManager provideScoreManager() {
        return new ScoreManager();
    }

}
