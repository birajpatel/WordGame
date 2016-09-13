package com.birin.wordgame.core.score;

import com.birin.wordgame.core.utils.KeyValueDiskStore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 Created by Biraj on 9/13/16.
 */
public class HighscoreRepositoryTest {

    private static final int INITITAL_FAKE_HIGHSCORE = 2 * 10;

    private HighscoreRepository scoreManager;
    private TestSubscriber<Integer> testScoreSubscriber;
    @Mock private KeyValueDiskStore mockKeyValueStore;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(mockKeyValueStore.readInt(anyString(), anyInt())).thenReturn(INITITAL_FAKE_HIGHSCORE);
        scoreManager = new HighscoreRepository(mockKeyValueStore);
        testScoreSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldUpdateNewHighScore() {
        scoreManager.highscoreObservable()
                    .subscribe(testScoreSubscriber);
        testScoreSubscriber.assertValues(INITITAL_FAKE_HIGHSCORE);
        // somthing higher than current highscore
        final int newHighScore = 3 * INITITAL_FAKE_HIGHSCORE;
        scoreManager.updateHighscore(newHighScore);
        verify(mockKeyValueStore).writeInt(anyString(), eq(newHighScore));
        testScoreSubscriber.assertValues(INITITAL_FAKE_HIGHSCORE, newHighScore); // New score emit
    }

}
