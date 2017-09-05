package com.hackhaton.androidbase.internal;

import com.hackhaton.androidbase.ui.common.BaseFragment;

import dagger.Subcomponent;

/**
 * @author : hafiq on 23/01/2017.
 */

@PerActivity
@Subcomponent(modules = {
        FragmentModule.class,
})

public interface FragmentGraph {

    //Fragment
    void inject(BaseFragment fragment);

}
