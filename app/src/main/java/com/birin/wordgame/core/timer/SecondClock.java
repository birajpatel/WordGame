package com.birin.wordgame.core.timer;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;

/**
 Created by Biraj on 9/13/16
 Emits Ticks every interval seconds
 */
public class SecondClock implements Clock {

    private final int interval;
    private final Scheduler clockScheduler;

    public SecondClock(Scheduler clockScheduler) {
        this(1, clockScheduler);
    }

    public SecondClock(int interval, Scheduler clockScheduler) {
        this.interval = interval;
        this.clockScheduler = clockScheduler;
    }

    @Override
    public Observable<Long> tick() {
        return Observable.interval(interval, TimeUnit.SECONDS, clockScheduler);
    }

}