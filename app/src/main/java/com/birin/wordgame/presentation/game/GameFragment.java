package com.birin.wordgame.presentation.game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.birin.wordgame.R;
import com.birin.wordgame.presentation.BaseFragment;
import com.birin.wordgame.presentation.di.game.GameModule;
import com.birin.wordgame.presentation.di.game.UsecasesModule;
import com.birin.wordgame.presentation.model.WordModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 Created by Biraj on 9/14/16.
 */
public class GameFragment extends BaseFragment implements GameView {

    @Inject GamePresenter presenter;

    @BindView(R.id.game_timer) TextView gameTimer;
    @BindView(R.id.word_english) TextView english;
    @BindView(R.id.word_translated) TextView translation;
    @BindView(R.id.score) TextView score;
    @BindView(R.id.high_score) TextView highscore;
    @BindView(R.id.correct) Button correct;
    @BindView(R.id.wrong) Button wrong;
    @BindView(R.id.falling_word_container) LinearLayout fallingWordContainer;
    @BindView(R.id.action_container) LinearLayout actionButtonContainer;

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_fragment, container, false);
        ButterKnife.bind(this, view);
        correct.setOnClickListener(v -> presenter.correctClicked());
        wrong.setOnClickListener(v -> presenter.wrongClicked());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadDependancies();
        presenter.setGameView(this);
        presenter.initialize();
        calculateTranslationContainerHeight();
    }

    private void calculateTranslationContainerHeight() {

    }

    private void loadDependancies() {
        getApplicationComponent().plus(new GameModule(), new UsecasesModule())
                                 .inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resumeGame();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.pauseGame();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.handleGameEnd();
    }

    @Override
    public void loadWord(WordModel wordModel) {
        english.setText(wordModel.getEnglishWord());
        translation.setText(wordModel.getSpanishWord());
    }

    @Override
    public void updateWordProgress(float wordProgress) {
        float wordContainerY = fallingWordContainer.getY();
        float buttonsContainerY = actionButtonContainer.getY();
        float containerHeight = buttonsContainerY - wordContainerY;
        translation.setY(containerHeight * wordProgress / 100.0f);
    }

    @Override
    public void updateGameTimer(int gameTimer) {
        this.gameTimer.setText(getString(R.string.timer, gameTimer));
    }

    @Override
    public void updateScore(int newScore) {
        this.score.setText(getString(R.string.score, newScore));
    }

    @Override
    public void updateHighscore(int highScore) {
        this.highscore.setText(getString(R.string.highscore, highScore));
    }

    @Override
    public void closeGame() {
        Toast.makeText(getActivity().getApplicationContext(), R.string.game_over, Toast.LENGTH_LONG)
             .show();
        getActivity().finish();
    }
}
