package com.birin.wordgame.core.utils;

/**
 Created by Biraj on 9/13/16.
 */
public interface KeyValueDiskStore {

    int readInt(String key, int defaultValue);

    void writeInt(String key, int value);
}