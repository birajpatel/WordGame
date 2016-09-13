package com.birin.wordgame.presentation.di.game;


import com.birin.wordgame.core.score.ScoreManager;
import com.birin.wordgame.core.timer.Clock;
import com.birin.wordgame.core.words.WordsRepository;
import com.birin.wordgame.domain.score.ScoreUseCase;
import com.birin.wordgame.domain.timer.TimerUseCase;
import com.birin.wordgame.domain.words.AnswerChooser;
import com.birin.wordgame.domain.words.WordsUseCase;
import com.birin.wordgame.presentation.di.threading.BackgroundScheduler;
import com.birin.wordgame.presentation.di.threading.ClockScheduler;
import com.birin.wordgame.presentation.di.threading.UiScheduler;
import com.birin.wordgame.presentation.di.timer.Seconds;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 Created by Biraj on 9/14/16.
 */
@Module
public class UsecasesModule {

    @GameScope
    @Provides
    public TimerUseCase provideTimerUseCase(@Seconds Clock clock,
                                            @UiScheduler Scheduler uiThreadScheduler) {
        return new TimerUseCase(clock, uiThreadScheduler);
    }

    @GameScope
    @Provides
    public WordsUseCase provideWordsUseCase(WordsRepository repository, AnswerChooser answerChooser,
                                            @UiScheduler Scheduler uiScheduler,
                                            @BackgroundScheduler Scheduler backgroundScheduler,
                                            @ClockScheduler Scheduler clockScheduler) {
        return new WordsUseCase(repository, answerChooser, uiScheduler, backgroundScheduler,
                                clockScheduler);
    }

    @Provides
    @GameScope
    public ScoreUseCase provideScoreUseCase(ScoreManager scoreManager) {
        return new ScoreUseCase(scoreManager);
    }
}
