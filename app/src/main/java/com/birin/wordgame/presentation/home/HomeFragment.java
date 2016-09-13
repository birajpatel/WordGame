package com.birin.wordgame.presentation.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.birin.wordgame.R;
import com.birin.wordgame.presentation.BaseFragment;
import com.birin.wordgame.presentation.di.home.HomeModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 Created by Biraj on 9/14/16.
 */
public class HomeFragment extends BaseFragment implements HomeView {

    @Inject HomePresenter presenter;
    @BindView(R.id.high_score) TextView highscore;
    @BindView(R.id.start) Button start;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this, view);
        start.setOnClickListener(v -> presenter.startClicked());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getApplicationComponent().plus(new HomeModule())
                                 .inject(this);
        presenter.setView(this);
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void updateHighScore(int highscore) {
        this.highscore.setText(getString(R.string.highscore, highscore));
    }
}
