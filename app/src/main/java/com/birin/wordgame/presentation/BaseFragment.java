package com.birin.wordgame.presentation;

import android.support.v4.app.Fragment;

import com.birin.wordgame.presentation.di.ApplicationComponent;

/**
 Created by Biraj on 9/14/16.
 */
public class BaseFragment extends Fragment {

    public ApplicationComponent getApplicationComponent() {
        return ((MainApplication) getContext().getApplicationContext()).getApplicationComponent();
    }

}
