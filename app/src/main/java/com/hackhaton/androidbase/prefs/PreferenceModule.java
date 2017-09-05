package com.hackhaton.androidbase.prefs;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Basyrun Halim
 */

@Module
public class PreferenceModule {

    @Provides
    @Singleton
    protected PreferencesRepository providePreferencesRepository(Context context) {
        return new PreferencesRepository(context);
    }

}
