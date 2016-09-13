package com.birin.wordgame.core.timer;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

/**
 Created by Biraj on 9/13/16.
 */
public class MillisClockTest {

    private final int INTERVAL = 10;

    private MillisClock timer;
    private TestScheduler testScheduler;
    private TestSubscriber<Long> testSubscriber;


    @Before
    public void setup() {
        testScheduler = Schedulers.test();
        timer = new MillisClock(INTERVAL, testScheduler);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldTickTimerEverySecond() {
        timer.tick()
             .subscribe(testSubscriber);
        testSubscriber.assertValueCount(0);
        // Emits first item after interval-millis
        testScheduler.advanceTimeBy(INTERVAL, TimeUnit.MILLISECONDS);
        testSubscriber.assertValueCount(1);
        // Emits N items after N interval-millis
        testScheduler.advanceTimeBy(4 * INTERVAL, TimeUnit.MILLISECONDS);
        testSubscriber.assertValueCount(5);
    }

}