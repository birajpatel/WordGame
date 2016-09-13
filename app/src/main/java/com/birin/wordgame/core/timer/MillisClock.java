package com.birin.wordgame.core.timer;

import rx.Observable;
import rx.Scheduler;

/**
 Created by Biraj on 9/13/16.
 Emits Ticks every interval milli-seconds
 */
public class MillisClock implements Clock {

    private final int interval;
    private final Scheduler clockScheduler;

    public MillisClock(Scheduler clockScheduler) {
        this(1, clockScheduler);
    }

    public MillisClock(int interval, Scheduler clockScheduler) {
        this.interval = interval;
        this.clockScheduler = clockScheduler;
    }

    @Override
    public Observable<Long> tick() {
        return null;
    }
}