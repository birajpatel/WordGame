package com.birin.wordgame.core.timer;

import rx.Observable;

/**
 Created by Biraj on 9/13/16.

 Should emits ticks every clock tick down to zero.
 Example : start 5 -> 4,3,2,1,0
 */
public class CountdownTimer {

    private final int start;
    private final Clock clock;

    public CountdownTimer(int start, Clock clock) {
        this.start = start;
        this.clock = clock;
    }

    public Observable<Integer> tick() {
        Observable<Integer> reversedRange = Observable.range(1, start)
                                                      .map(i -> start - i);
        return Observable.zip(reversedRange, clock.tick(), (progress, tick) -> progress);
    }

}