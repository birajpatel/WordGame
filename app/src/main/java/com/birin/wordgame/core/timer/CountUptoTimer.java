package com.birin.wordgame.core.timer;

import rx.Observable;

/**
 Created by Biraj on 9/13/16.

 Emits ticks for given range in incremental fashion
 [12,19] -> 12, 13, 14, 15, 16, 17, 18, 19
 */
public class CountUptoTimer {

    private final int start;
    private final int end;
    private final Clock clock;

    public CountUptoTimer(int start, int end, Clock clock) {
        this.start = start;
        this.end = end;
        this.clock = clock;
    }

    public Observable<Integer> tick() {
        int count = end - start + 1;
        Observable<Integer> range = Observable.range(start, count);
        return Observable.zip(range, clock.tick(), (progress, tick) -> progress);
    }
}