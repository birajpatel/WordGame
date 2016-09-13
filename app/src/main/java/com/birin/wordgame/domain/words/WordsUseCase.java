package com.birin.wordgame.domain.words;

import com.birin.wordgame.core.timer.Clock;
import com.birin.wordgame.core.timer.CountUptoTimer;
import com.birin.wordgame.core.timer.MillisClock;
import com.birin.wordgame.core.words.WordEntity;
import com.birin.wordgame.core.words.WordsRepository;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

/**
 Created by Biraj on 9/13/16.
 */
public class WordsUseCase {

    public static final int DEFAULT_START_TICK = 1;
    public static final int TOTAL_TICKS = 200;
    public static final int HUNDRED_PERCENT = 100;
    public static final float TICK_PER_PERCENT = (TOTAL_TICKS / HUNDRED_PERCENT) * 1.0f;

    public static final int PER_WORD_TIME_MILLIS = 3000;
    public static final int PER_WORD_SINGLE_TICK_MILLIS = PER_WORD_TIME_MILLIS / TOTAL_TICKS;

    private WordsRepository repository;
    private AnswerChooser answerChooser;
    private Scheduler uiThreadScheduler;
    private Scheduler backgroundThreadScheduler;
    private Scheduler clockScheduler;

    private List<WordEntity> cachedData;
    private int index = 0;
    private int lastEmittedTick;
    private Subscription wordTimerProgressSubscription;
    private BehaviorSubject<Word> wordsObservable = BehaviorSubject.create();
    private BehaviorSubject<Float> wordsTimerProgress = BehaviorSubject.create();

    public WordsUseCase(WordsRepository repository, AnswerChooser answerChooser, Scheduler uiThread,
                        Scheduler backgroundThread, Scheduler clockScheduler) {
        this.repository = repository;
        this.answerChooser = answerChooser;
        this.uiThreadScheduler = uiThread;
        this.backgroundThreadScheduler = backgroundThread;
        this.clockScheduler = clockScheduler;
    }

    public void loadWordsFromRepository() {
        repository.getWords()
                  .subscribeOn(backgroundThreadScheduler)
                  .observeOn(uiThreadScheduler)
                  .subscribe(data -> cachedData = data, e -> e.printStackTrace(),
                             () -> loadNewWord());
    }

    public List<WordEntity> getCachedData() {
        return cachedData;
    }

    public Observable<Word> wordObservable() {
        return wordsObservable.asObservable()
                              .observeOn(uiThreadScheduler)
                              .filter(this::isNotNull);
    }

    public Observable<Float> wordTimerProgressObservable() {
        return wordsTimerProgress.asObservable()
                                 .observeOn(uiThreadScheduler)
                                 .filter(this::isNotNull);
    }

    private boolean isNotNull(Object data) {
        return null != data;
    }

    public void loadNewWord() {
        if (cachedData == null) {
            loadWordsFromRepository();
            return;
        }
        boolean isCorrect = answerChooser.shouldChooseCorrectAnswer();
        final String question = cachedData.get(index)
                                          .getEnglishText();
        final int answerIndex = isCorrect ? index
                                          : answerChooser.randomAnswerIndex(cachedData.size());
        String answer = cachedData.get(answerIndex)
                                  .getSpanishText();
        updateIndex();
        startWordTimerProgress(DEFAULT_START_TICK);
        wordsObservable.onNext(new Word(question, answer, isCorrect));
    }

    private void updateIndex() {
        index++;
        if (index >= cachedData.size()) {
            // After N words, rotate back to zero'th index
            index = 0;
        }
    }

    public void startWordTimerProgress(int start) {
        Clock singleTickClock = new MillisClock(PER_WORD_SINGLE_TICK_MILLIS, clockScheduler);
        Observable<Integer> wordTimerProgress = new CountUptoTimer(start, TOTAL_TICKS,
                                                                   singleTickClock).tick();
        wordTimerProgressSubscription = wordTimerProgress.doOnNext(tick -> lastEmittedTick = tick)
                                                         .map(tick -> tick / TICK_PER_PERCENT)
                                                         .subscribe(wordsTimerProgress::onNext);
    }

    private void unsubscribeWordTimerProgress() {
        if (null != wordTimerProgressSubscription &&
            !wordTimerProgressSubscription.isUnsubscribed()) {
            wordTimerProgressSubscription.unsubscribe();
        }
    }

    public Subscription getWordTimerProgressSubscription() {
        return wordTimerProgressSubscription;
    }

    public void pauseGame() {
        unsubscribeWordTimerProgress();
    }

    public void resumeGame() {
        startWordTimerProgress(lastEmittedTick + 1);
    }

    public int getIndex() {
        return index;
    }


    public void answered() {
        unsubscribeWordTimerProgress();
        loadNewWord();
    }

    public void notAnswered() {
        loadNewWord();
    }

}
