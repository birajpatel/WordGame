package com.birin.wordgame.domain.timer;

import com.birin.wordgame.core.timer.Clock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import static com.birin.wordgame.domain.timer.TimerUseCase.TOTAL_CLOCK_TICKS;
import static org.mockito.Mockito.when;

/**
 Created by Biraj on 9/13/16.
 */
public class TimerUseCaseTest {

    private TimerUseCase timerUseCase;
    private TestScheduler testScheduler;
    private TestSubscriber<Integer> testSubscriber;
    @Mock private Clock clock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        testSubscriber = new TestSubscriber<>();
        timerUseCase = new TimerUseCase(clock, Schedulers.immediate());
        Observable<Long> mockTicks = Observable.interval(1, TimeUnit.SECONDS, testScheduler);
        when(clock.tick()).thenReturn(mockTicks);
        timerUseCase.initialize();
    }

    @Test
    public void shouldOnlyTakeMaxCountTicks() {
        timerUseCase.tickObservable()
                    .subscribe(testSubscriber);
        testScheduler.advanceTimeBy(TOTAL_CLOCK_TICKS + 10, TimeUnit.SECONDS);
        testSubscriber.assertValueCount(TOTAL_CLOCK_TICKS + 1);// +1 for initital
        System.out.println(testSubscriber.getOnNextEvents());
        testSubscriber.assertCompleted();
    }

    @Test
    public void shouldNotEmitTicksOnGamePause() {
        timerUseCase.tickObservable()
                    .subscribe(testSubscriber);
        testScheduler.advanceTimeBy(4, TimeUnit.SECONDS);
        testSubscriber.assertValues(30, 29, 28, 27, 26);

        timerUseCase.pauseGame();
        testScheduler.advanceTimeBy(4, TimeUnit.SECONDS);
        testSubscriber.assertValues(30, 29, 28, 27, 26);
    }

    @Test
    public void shouldResumeTimerWhereLeftOff() {
        timerUseCase.tickObservable()
                    .subscribe(testSubscriber);
        testScheduler.advanceTimeBy(4, TimeUnit.SECONDS);
        testSubscriber.assertValues(30, 29, 28, 27, 26);

        timerUseCase.pauseGame();
        testScheduler.advanceTimeBy(4, TimeUnit.SECONDS);

        timerUseCase.resumeGame();
        testScheduler.advanceTimeBy(4, TimeUnit.SECONDS);
        testSubscriber.assertValues(30, 29, 28, 27, 26, 25, 24, 23, 22);

        testScheduler.advanceTimeBy(50, TimeUnit.SECONDS);
        testSubscriber.assertValues(30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15,
                                    14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
    }
}
