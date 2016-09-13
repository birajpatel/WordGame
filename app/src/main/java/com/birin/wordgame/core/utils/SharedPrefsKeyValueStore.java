package com.birin.wordgame.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 Created by Biraj on 9/13/16.
 */
public class SharedPrefsKeyValueStore implements KeyValueDiskStore {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SharedPrefsKeyValueStore(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
    }

    @Override
    public int readInt(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    @Override
    public void writeInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }
}