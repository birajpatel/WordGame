package com.birin.wordgame.domain.words;

/**
 Created by Biraj on 9/13/16.
 */
public interface AnswerChooser {

    boolean shouldChooseCorrectAnswer();

    int randomAnswerIndex(int max);

}
