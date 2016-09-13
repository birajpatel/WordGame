package com.birin.wordgame.presentation;

import android.app.Application;

import com.birin.wordgame.presentation.di.ApplicationComponent;
import com.birin.wordgame.presentation.di.ApplicationModule;
import com.birin.wordgame.presentation.di.DaggerApplicationComponent;
import com.birin.wordgame.presentation.di.RepositoryModule;
import com.birin.wordgame.presentation.di.threading.SchedulerModule;
import com.birin.wordgame.presentation.di.timer.ClockModule;

/**
 Created by Biraj on 9/14/16.
 */
public class MainApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = loadComponentBuilder();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    protected ApplicationComponent loadComponentBuilder() {
        return DaggerApplicationComponent.builder()
                                         .applicationModule(new ApplicationModule(this))
                                         .clockModule(new ClockModule())
                                         .repositoryModule(new RepositoryModule())
                                         .schedulerModule(new SchedulerModule())
                                         .build();
    }
}
