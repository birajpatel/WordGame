package com.birin.wordgame.domain.words;

import com.birin.wordgame.core.words.WordEntity;
import com.birin.wordgame.core.words.WordsRepository;

import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscription;

/**
 Created by Biraj on 9/13/16.
 */
public class WordsUseCase {

    public static final int TOTAL_TICKS = 200;
    public static final int HUNDRED_PERCENT = 100;
    public static final int PER_WORD_TIME_MILLIS = 3000;
    public static final int PER_WORD_SINGLE_TICK_MILLIS = PER_WORD_TIME_MILLIS / TOTAL_TICKS;

    private WordsRepository repository;
    private AnswerChooser answerChooser;
    private Scheduler uiThreadScheduler;
    private Scheduler backgroundThreadScheduler;
    private Scheduler clockScheduler;


    public WordsUseCase(WordsRepository repository, AnswerChooser answerChooser, Scheduler uiThread,
                        Scheduler backgroundThread, Scheduler clockScheduler) {
        this.repository = repository;
        this.answerChooser = answerChooser;
        this.uiThreadScheduler = uiThread;
        this.backgroundThreadScheduler = backgroundThread;
        this.clockScheduler = clockScheduler;
    }

    public void loadWordsFromRepository() {

    }

    public List<WordEntity> getCachedData() {
        return null;
    }

    public Observable<Word> wordObservable() {
        return null;
    }

    public Observable<Float> wordTimerProgressObservable() {
        return null;
    }

    public void loadNewWord() {

    }

    public void startWordTimerProgress(int start) {

    }

    private void unsubscribeWordTimerProgress() {

    }

    public Subscription getWordTimerProgressSubscription() {
        return null;
    }

    public void pauseGame() {

    }

    public void resumeGame() {

    }

    public int getIndex() {
        return 0;
    }


    public void answered() {

    }

    public void notAnswered() {

    }

}
