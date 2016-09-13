package com.birin.wordgame.timer;

import com.birin.wordgame.core.timer.Clock;
import com.birin.wordgame.core.timer.CountdownTimer;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

/**
 Created by Biraj on 9/13/16.
 */
public class TimerUseCase {

    public static final int TOTAL_CLOCK_TICKS = 30;

    private Clock clock;
    private Subscription clockSubscription;
    private Scheduler uiThreadScheduler;
    private BehaviorSubject<Integer> progress = BehaviorSubject.create(TOTAL_CLOCK_TICKS);

    public TimerUseCase(Clock clock, Scheduler uiThreadScheduler) {
        this.clock = clock;
        this.uiThreadScheduler = uiThreadScheduler;
    }

    public void initialize() {
        startTicking(TOTAL_CLOCK_TICKS);
    }

    public Observable<Integer> tickObservable() {
        return progress.asObservable()
                       .observeOn(uiThreadScheduler);
    }

    private void startTicking(int start) {
        Observable<Integer> ticker = new CountdownTimer(start, clock).tick();
        clockSubscription = ticker.subscribe(progress::onNext, t -> {
        }, progress::onCompleted);
    }

    private void unsubscribeClock() {
        if (null != clockSubscription && !clockSubscription.isUnsubscribed()) {
            clockSubscription.unsubscribe();
        }
    }

    public void pauseGame() {
        unsubscribeClock();
    }

    public void resumeGame() {
        startTicking(progress.getValue());
    }
}
