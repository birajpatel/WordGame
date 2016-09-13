package com.birin.wordgame.domain.words;

import com.birin.wordgame.core.words.WordEntity;
import com.birin.wordgame.core.words.WordsRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import static com.birin.wordgame.domain.words.WordsUseCase.HUNDRED_PERCENT;
import static com.birin.wordgame.domain.words.WordsUseCase.PER_WORD_SINGLE_TICK_MILLIS;
import static com.birin.wordgame.domain.words.WordsUseCase.TOTAL_TICKS;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 Created by Biraj on 9/13/16.
 */
public class WordsUseCaseTest {

    private WordsUseCase wordsUseCase;
    private List<WordEntity> mockWords;
    private Scheduler mockUiScheduler;
    private Scheduler mockBackgroundScheduler;
    private TestScheduler mockClockScheduler;
    @Mock private WordsRepository mockRepository;
    @Mock private AnswerChooser mockAnswerChooser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockBackgroundScheduler = Schedulers.immediate();
        mockUiScheduler = Schedulers.immediate();
        mockClockScheduler = new TestScheduler();
        mockWords = new ArrayList<>();
        mockWords.add(new WordEntity("hello1", "spa_hello1"));
        mockWords.add(new WordEntity("hello2", "spa_hello2"));
        mockWords.add(new WordEntity("hello3", "spa_hello3"));
        mockWords.add(new WordEntity("hello4", "spa_hello4"));
        mockWords.add(new WordEntity("hello5", "spa_hello5"));
        wordsUseCase = new WordsUseCase(mockRepository, mockAnswerChooser, mockUiScheduler,
                                        mockBackgroundScheduler, mockClockScheduler);
        when(mockRepository.getWords()).thenReturn(Observable.just(mockWords));
    }

    @Test
    public void shouldLoadIfNoCachedData() {
        wordsUseCase.loadNewWord();
        verify(mockRepository).getWords();
        assertEquals(wordsUseCase.getCachedData(), mockWords);
    }

    @Test
    public void shouldLoadWordsOnlyOnce() {
        wordsUseCase.loadNewWord();
        assertEquals(5, wordsUseCase.getCachedData()
                                    .size());
        wordsUseCase.loadNewWord();
        verify(mockRepository, times(1)).getWords();
    }

    @Test
    public void shouldEmitRightAnsweredWord() {
        when(mockAnswerChooser.shouldChooseCorrectAnswer()).thenReturn(true);
        TestSubscriber<Word> testSubscriber = new TestSubscriber(new Subscriber<Word>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Word o) {
                assertEquals(o.getEnglishWord(), "hello1");
                assertEquals(o.getSpanishWord(), "spa_hello1");
                assertEquals(true, o.isCorrect());
            }
        });
        wordsUseCase.wordObservable()
                    .subscribe(testSubscriber);
        wordsUseCase.loadNewWord();
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void shouldEmitWrongAnsweredWord() {
        TestSubscriber<Word> testSubscriber = new TestSubscriber(new Subscriber<Word>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Word o) {
                assertEquals(o.getEnglishWord(), "hello1");
                assertEquals(o.getSpanishWord(), "spa_hello3");
                assertEquals(false, o.isCorrect());
            }
        });
        when(mockAnswerChooser.shouldChooseCorrectAnswer()).thenReturn(false);
        when(mockAnswerChooser.randomAnswerIndex(anyInt())).thenReturn(2);
        wordsUseCase.wordObservable()
                    .subscribe(testSubscriber);
        wordsUseCase.loadNewWord();
        verify(mockAnswerChooser).shouldChooseCorrectAnswer();
        verify(mockAnswerChooser).randomAnswerIndex(anyInt());
        testSubscriber.assertValueCount(1);
    }

    @Test
    public void shouldIncrementsIndexOnNewWord() {
        wordsUseCase.loadNewWord();
        wordsUseCase.loadNewWord();
        assertEquals(2, wordsUseCase.getIndex());
    }

    @Test
    public void shouldResetIndexToZeroOnDataExhaust() {
        for (int i = 0; i < mockWords.size(); i++) {
            wordsUseCase.loadNewWord();
        }
        // After N words, rotate back to zero'th index
        assertEquals(0, wordsUseCase.getIndex());
    }

    @Test
    public void shouldStartEmitingProgressOnNextWord() {
        TestSubscriber<Float> timerProgressSubscriber = new TestSubscriber<>();
        wordsUseCase.wordTimerProgressObservable()
                    .subscribe(timerProgressSubscriber);
        timerProgressSubscriber.assertNoValues();
        wordsUseCase.loadNewWord();
        mockClockScheduler.advanceTimeBy(PER_WORD_SINGLE_TICK_MILLIS * 10, TimeUnit.MILLISECONDS);
        timerProgressSubscriber.assertValueCount(10);
    }

    @Test
    public void shouldLoadNewWordOnNoAnswer() {
        TestSubscriber<Float> timerProgressSubscriber = new TestSubscriber<>();
        wordsUseCase.wordTimerProgressObservable()
                    .subscribe(timerProgressSubscriber);
        int beforeIndex = wordsUseCase.getIndex();
        wordsUseCase.notAnswered();
        int afterIndex = wordsUseCase.getIndex();
        // check if next question is loaded
        assertThat(beforeIndex, is(not(equalTo(afterIndex))));
        // check if emitting again from 1 to 100%
        mockClockScheduler.advanceTimeBy(PER_WORD_SINGLE_TICK_MILLIS * 10, TimeUnit.MILLISECONDS);
        timerProgressSubscriber.assertValueCount(10);
    }

    @Test
    public void shouldUnsubsribeOnAnswered() {
        wordsUseCase.loadNewWord();
        Subscription s1 = wordsUseCase.getWordTimerProgressSubscription();
        wordsUseCase.answered();
        assertEquals(true, s1.isUnsubscribed());
    }

    @Test
    public void shouldLoadNewWordOnAnswer() {
        TestSubscriber<Float> timerProgressSubscriber = new TestSubscriber<>();
        wordsUseCase.wordTimerProgressObservable()
                    .subscribe(timerProgressSubscriber);
        int beforeIndex = wordsUseCase.getIndex();
        wordsUseCase.answered();
        int afterIndex = wordsUseCase.getIndex();
        // check if next question is loaded
        assertThat(beforeIndex, is(not(equalTo(afterIndex))));
        // check if emitting again from 1 to 100%
        mockClockScheduler.advanceTimeBy(PER_WORD_SINGLE_TICK_MILLIS * 10, TimeUnit.MILLISECONDS);
        timerProgressSubscriber.assertValueCount(10);
    }

    @Test
    public void shouldStopEmittingOnGamePause() {
        TestSubscriber<Float> timerProgressSubscriber = new TestSubscriber<>();
        wordsUseCase.wordTimerProgressObservable()
                    .subscribe(timerProgressSubscriber);
        wordsUseCase.loadNewWord();
        mockClockScheduler.advanceTimeBy(PER_WORD_SINGLE_TICK_MILLIS * 10, TimeUnit.MILLISECONDS);
        timerProgressSubscriber.assertValueCount(10);
        wordsUseCase.pauseGame();
        mockClockScheduler.advanceTimeBy(PER_WORD_SINGLE_TICK_MILLIS * 10, TimeUnit.MILLISECONDS);
        timerProgressSubscriber.assertValueCount(10);
    }

    @Test
    public void shouldResumeWhereLeftOf() {
        final float perTickPercent = HUNDRED_PERCENT / (TOTAL_TICKS * 1.0f);
        TestSubscriber<Float> timerProgressSubscriber = new TestSubscriber<>();
        wordsUseCase.wordTimerProgressObservable()
                    .subscribe(timerProgressSubscriber);
        wordsUseCase.loadNewWord();
        mockClockScheduler.advanceTimeBy(PER_WORD_SINGLE_TICK_MILLIS * 5, TimeUnit.MILLISECONDS);
        timerProgressSubscriber.assertValueCount(5);

        // Nothing happens in paused state.
        wordsUseCase.pauseGame();
        mockClockScheduler.advanceTimeBy(PER_WORD_SINGLE_TICK_MILLIS * 5, TimeUnit.MILLISECONDS);
        timerProgressSubscriber.assertValueCount(5);

        // resumes from last state
        wordsUseCase.resumeGame();
        mockClockScheduler.advanceTimeBy(PER_WORD_SINGLE_TICK_MILLIS * 5, TimeUnit.MILLISECONDS);
        timerProgressSubscriber.assertValueCount(10);
        mockClockScheduler.advanceTimeBy(PER_WORD_SINGLE_TICK_MILLIS * TOTAL_TICKS,
                                         TimeUnit.MILLISECONDS);
        timerProgressSubscriber.assertValueCount(TOTAL_TICKS);
    }

}