package com.hackhaton.androidbase.internal;


import com.hackhaton.androidbase.prefs.PreferenceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author : hafiq on 23/01/2017.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        PreferenceModule.class,
        BaseOkHttpClient.class,
        ApiModule.class,
})

public interface Graph {
    ActivityGraph activityGraph(ActivityModule module);
    FragmentGraph fragmentGraph(FragmentModule module);
    ServiceGraph serviceGraph(ServiceModule module);
}


