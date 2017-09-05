package com.hackhaton.androidbase.internal;

import com.hackhaton.androidbase.ui.common.BaseActivity;
import com.hackhaton.androidbase.ui.MainActivity;
import com.hackhaton.androidbase.ui.splash.SplashActivity;

import dagger.Subcomponent;

/**
 * @author : hafiq on 23/01/2017.
 */

@PerActivity
@Subcomponent(modules = {
        ActivityModule.class,
})

public interface ActivityGraph {

    //Activity
    void inject(MainActivity activity);
    void inject(BaseActivity activity);
    void inject(SplashActivity activity);

}
