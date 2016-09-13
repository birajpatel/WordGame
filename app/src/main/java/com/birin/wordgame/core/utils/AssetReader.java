package com.birin.wordgame.core.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 Created by Biraj on 9/13/16.
 */
public class AssetReader {

    private Context context;

    public AssetReader(Context context) {
        this.context = context.getApplicationContext();
    }


    public String readFileFromAssets(String fileName) throws IOException {
        InputStream is = context.getAssets()
                                .open(fileName);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        return new String(buffer, "UTF-8");
    }

}