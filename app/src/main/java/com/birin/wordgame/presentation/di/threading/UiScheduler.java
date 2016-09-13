package com.birin.wordgame.presentation.di.threading;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 Created by Biraj on 9/14/16.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface UiScheduler {

}
