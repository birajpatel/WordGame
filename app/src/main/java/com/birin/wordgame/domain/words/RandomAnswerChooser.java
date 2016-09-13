package com.birin.wordgame.domain.words;

import java.util.Random;

/**
 Created by Biraj on 9/13/16.
 */
public class RandomAnswerChooser implements AnswerChooser {

    private Random random;

    public RandomAnswerChooser() {
        this.random = new Random();
    }

    @Override
    public boolean shouldChooseCorrectAnswer() {
        return random.nextBoolean();
    }

    @Override
    public int randomAnswerIndex(int max) {
        return random.nextInt(max);
    }
}
