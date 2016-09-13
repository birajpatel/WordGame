package com.birin.wordgame.core.timer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.observers.TestSubscriber;
import rx.schedulers.TestScheduler;

import static org.mockito.Mockito.when;

/**
 Created by Biraj on 9/13/16.
 */
public class CountUptoTimerTest {

    private CountUptoTimer timer;
    private TestScheduler mockScheduler;
    private TestSubscriber<Integer> mockSubscriber;
    @Mock private Clock mockClock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockScheduler = new TestScheduler();
        mockSubscriber = new TestSubscriber<>();
        when(mockClock.tick()).thenReturn(Observable.interval(1, TimeUnit.SECONDS, mockScheduler));
    }

    @Test
    public void shouldEmitAndComplete() {
        timer = new CountUptoTimer(12, 19, mockClock);
        timer.tick()
             .subscribe(mockSubscriber);
        mockSubscriber.assertValueCount(0);
        mockScheduler.advanceTimeBy(10, TimeUnit.SECONDS);
        mockSubscriber.assertValues(12, 13, 14, 15, 16, 17, 18, 19);
        mockSubscriber.assertCompleted();
    }

    @Test
    public void shouldNotEmitOnUnsubscribe() {
        timer = new CountUptoTimer(12, 19, mockClock);
        Subscription s = timer.tick()
                              .subscribe(mockSubscriber);
        mockSubscriber.assertValueCount(0);
        mockScheduler.advanceTimeBy(5, TimeUnit.SECONDS);
        mockSubscriber.assertValueCount(5);
        s.unsubscribe();
        mockScheduler.advanceTimeBy(5, TimeUnit.SECONDS);
        mockSubscriber.assertValueCount(5);
        mockSubscriber.assertNotCompleted();
    }
}