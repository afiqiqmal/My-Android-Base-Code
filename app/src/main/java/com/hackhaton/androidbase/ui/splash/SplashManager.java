package com.hackhaton.androidbase.ui.splash;

import com.hackhaton.androidbase.client.RestApi;
import com.hackhaton.androidbase.client.entity.request.TokenRequest;
import com.hackhaton.androidbase.client.entity.response.TokenResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * @author : hafiq on 23/01/2017.
 */

@Singleton
public class SplashManager {

    RestApi restApi;

    @Inject
    public SplashManager(RestApi api) {
        restApi = api;
    }

    public Observable<TokenResponse> getToken(TokenRequest request) {
        return restApi.getToken(request);
    }
}

