package com.birin.wordgame.presentation.di.game;

import com.birin.wordgame.presentation.game.GameFragment;

import dagger.Subcomponent;

/**
 Created by Biraj on 9/14/16.
 */
@GameScope
@Subcomponent(modules = {GameModule.class, UsecasesModule.class})
public interface GameComponent {

    void inject(GameFragment fragment);
}
