package com.birin.wordgame.presentation.di;

import android.content.Context;

import com.birin.wordgame.core.score.HighscoreRepository;
import com.birin.wordgame.core.utils.AssetReader;
import com.birin.wordgame.core.utils.KeyValueDiskStore;
import com.birin.wordgame.core.utils.SharedPrefsKeyValueStore;
import com.birin.wordgame.core.words.AssetWordsRepository;
import com.birin.wordgame.core.words.WordsParser;
import com.birin.wordgame.core.words.WordsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 Created by Biraj on 9/14/16.
 */
@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public AssetReader provideAssetReader(Context context) {
        return new AssetReader(context);
    }

    @Provides
    @Singleton
    public WordsParser provideParser() {
        return new WordsParser();
    }

    @Provides
    @Singleton
    public KeyValueDiskStore provideKeyValueStore(Context context) {
        return new SharedPrefsKeyValueStore(context);
    }

    @Provides
    @Singleton
    public HighscoreRepository provideHighscoreRepository(KeyValueDiskStore keyValueDiskStore) {
        return new HighscoreRepository(keyValueDiskStore);
    }

    @Provides
    @Singleton
    public WordsRepository provideWordsRepository(AssetReader assetReader, WordsParser parser) {
        return new AssetWordsRepository(assetReader, parser);
    }

}
