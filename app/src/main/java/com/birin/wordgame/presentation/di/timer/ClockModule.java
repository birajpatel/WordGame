package com.birin.wordgame.presentation.di.timer;

import com.birin.wordgame.core.timer.Clock;
import com.birin.wordgame.core.timer.SecondClock;
import com.birin.wordgame.presentation.di.threading.ClockScheduler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

/**
 Created by Biraj on 9/14/16.
 */
@Module
public class ClockModule {

    @Provides
    @Seconds
    @Singleton
    public Clock provideBackgroundScheduler(@ClockScheduler Scheduler workerScheduler) {
        return new SecondClock(workerScheduler);
    }
}
