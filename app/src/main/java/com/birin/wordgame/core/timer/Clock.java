package com.birin.wordgame.core.timer;

import rx.Observable;

/**
 Created by Biraj on 9/13/16.
 */
public interface Clock {

    Observable<Long> tick();
}