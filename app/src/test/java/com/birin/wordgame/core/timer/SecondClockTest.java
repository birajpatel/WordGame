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
public class SecondClockTest {

    private SecondClock timer;
    private TestScheduler testScheduler;
    private TestSubscriber<Long> testSubscriber;


    @Before
    public void setup() {
        testScheduler = Schedulers.test();
        timer = new SecondClock(testScheduler);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldTickTimerEverySecond() {
        timer.tick()
             .subscribe(testSubscriber);
        testSubscriber.assertValueCount(0);
        // Emits first item after 1 second
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        testSubscriber.assertValueCount(1);
        // Emits N items after N seconds
        testScheduler.advanceTimeBy(4, TimeUnit.SECONDS);
        testSubscriber.assertValueCount(5);
    }

}