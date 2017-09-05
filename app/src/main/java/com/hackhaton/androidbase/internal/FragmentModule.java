package com.hackhaton.androidbase.internal;

import android.app.Activity;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;

/**
 * @author : hafiq on 23/01/2017.
 */

@Module
public class FragmentModule {

    private Activity mContext;

    public FragmentModule(Activity context) {
        mContext = context;
    }

    @Provides
    @PerActivity
    Activity provideContext() {
        return mContext;
    }
}