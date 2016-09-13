package com.birin.wordgame.presentation.di.home;

import com.birin.wordgame.presentation.home.HomeFragment;

import dagger.Subcomponent;

/**
 Created by Biraj on 9/14/16.
 */
@HomeScope
@Subcomponent(modules = HomeModule.class)
public interface HomeComponent {

    void inject(HomeFragment fragment);
}