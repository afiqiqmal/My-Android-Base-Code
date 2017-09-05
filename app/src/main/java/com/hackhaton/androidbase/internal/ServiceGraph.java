package com.hackhaton.androidbase.internal;


import com.hackhaton.androidbase.services.RegistrationIntentService;

import dagger.Subcomponent;


/**
 * @author : hafiq on 23/01/2017.
 */

@PerService
@Subcomponent(modules = {
        ServiceModule.class,
})

public interface ServiceGraph {
    void inject(RegistrationIntentService service);
}