package com.birin.wordgame.presentation.di.threading;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 Created by Biraj on 9/14/16.
 */
@Module
public class SchedulerModule {

    @Provides
    @UiScheduler
    @Singleton
    public Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @BackgroundScheduler
    @Singleton
    public Scheduler provideBackgroundScheduler() {
        return Schedulers.io();
    }

    @Provides
    @ClockScheduler
    @Singleton
    public Scheduler provideClockScheduler() {
        return Schedulers.computation();
    }

}
