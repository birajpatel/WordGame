package com.birin.wordgame.presentation;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 Created by Biraj on 9/13/16.
 */
public class BaseActivity extends AppCompatActivity {

    public void installFragment(int containerId, BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(containerId, fragment);
        transaction.commit();
    }
}