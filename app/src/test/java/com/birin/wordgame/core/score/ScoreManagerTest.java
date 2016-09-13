package com.birin.wordgame.core.score;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 Created by Biraj on 9/13/16.
 */
public class ScoreManagerTest {

    private static final int RIGHT_ANSWER_BONUS = 10;
    private static final int WRONG_ANSWER_PENALTY = 5;

    private ScoreManager scoreManager;
    private TestSubscriber<Integer> testScoreSubscriber;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        scoreManager = new ScoreManager();
        testScoreSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldIncrementScoreOnRightAnswer() {
        assertThat(scoreManager.currentScore(), is(equalTo(0)));
        scoreManager.incrementScore(RIGHT_ANSWER_BONUS);
        assertThat(scoreManager.currentScore(), is(equalTo(RIGHT_ANSWER_BONUS)));
        scoreManager.incrementScore(RIGHT_ANSWER_BONUS);
        scoreManager.incrementScore(RIGHT_ANSWER_BONUS);
        assertThat(scoreManager.currentScore(), is(equalTo(3 * RIGHT_ANSWER_BONUS)));
    }

    @Test
    public void shouldDecrementScoreOnWrongAnswer() {
        assertThat(scoreManager.currentScore(), is(equalTo(0)));
        scoreManager.decrementScore(WRONG_ANSWER_PENALTY);
        assertThat(scoreManager.currentScore(), is(equalTo(-WRONG_ANSWER_PENALTY)));
        scoreManager.decrementScore(WRONG_ANSWER_PENALTY);
        scoreManager.decrementScore(WRONG_ANSWER_PENALTY);
        assertThat(scoreManager.currentScore(), is(equalTo(3 * -WRONG_ANSWER_PENALTY)));
    }

    @Test
    public void shouldEmitScoreEventsWhenInteracted() {
        scoreManager.scoreObservable()
                    .subscribe(testScoreSubscriber);
        List<Integer> actualScores = new ArrayList<>();
        actualScores.add(scoreManager.currentScore());

        scoreManager.incrementScore(RIGHT_ANSWER_BONUS);
        actualScores.add(scoreManager.currentScore());

        scoreManager.decrementScore(WRONG_ANSWER_PENALTY);
        actualScores.add(scoreManager.currentScore());

        scoreManager.incrementScore(RIGHT_ANSWER_BONUS);
        actualScores.add(scoreManager.currentScore());

        List<Integer> expectedScores = testScoreSubscriber.getOnNextEvents();
        assertEquals(expectedScores, actualScores);
    }

}
