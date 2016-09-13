/* Copyright (C) 2012 The Android Open Source Project

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.birin.wordgame.presentation.game;

import android.os.Bundle;

import com.birin.wordgame.R;
import com.birin.wordgame.presentation.BaseActivity;

/**
 Created by Biraj on 9/14/16.
 */
public class GameActivity extends BaseActivity {

    private static final int CONTAINER = R.id.container;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        GameFragment gameFragment = (GameFragment) getSupportFragmentManager().findFragmentById(
                CONTAINER);
        if (gameFragment == null) {
            gameFragment = GameFragment.newInstance();
            installFragment(CONTAINER, gameFragment);
        }
    }

}

