package com.birin.wordgame.timer;

import com.birin.wordgame.core.timer.Clock;

import rx.Observable;
import rx.Scheduler;

/**
 Created by Biraj on 9/13/16.
 */
public class TimerUseCase {

    public static final int TOTAL_CLOCK_TICKS = 30;

    private Clock clock;
    private Scheduler uiThreadScheduler;

    public TimerUseCase(Clock clock, Scheduler uiThreadScheduler) {
        this.clock = clock;
        this.uiThreadScheduler = uiThreadScheduler;
    }

    public void initialize() {

    }

    public Observable<Integer> tickObservable() {
        return null;
    }

    private void startTicking(int start) {

    }

    private void unsubscribeClock() {

    }

    public void pauseGame() {

    }

    public void resumeGame() {

    }
}
