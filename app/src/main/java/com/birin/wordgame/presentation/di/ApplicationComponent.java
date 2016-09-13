package com.birin.wordgame.presentation.di;


import com.birin.wordgame.presentation.di.threading.SchedulerModule;
import com.birin.wordgame.presentation.di.timer.ClockModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 Created by Biraj on 9/14/16.
 */
@Singleton
@Component(modules = {ApplicationModule.class, RepositoryModule.class, SchedulerModule.class,
        ClockModule.class})
public interface ApplicationComponent {


}
