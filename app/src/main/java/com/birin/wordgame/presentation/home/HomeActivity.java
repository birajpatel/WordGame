package com.birin.wordgame.presentation.home;

import android.os.Bundle;

import com.birin.wordgame.R;
import com.birin.wordgame.presentation.BaseActivity;

/**
 Created by Biraj on 9/14/16.
 */
public class HomeActivity extends BaseActivity {

    private static final int CONTAINER = R.id.container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentById(
                CONTAINER);
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
            installFragment(CONTAINER, homeFragment);
        }
    }
}
