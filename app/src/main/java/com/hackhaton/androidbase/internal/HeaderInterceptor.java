package com.hackhaton.androidbase.internal;

import android.support.annotation.NonNull;

import com.hackhaton.androidbase.client.Constant;
import com.hackhaton.androidbase.prefs.PreferencesRepository;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author : hafiq on 23/03/2017.
 */

public class HeaderInterceptor implements Interceptor {

    private PreferencesRepository mPrefs;
    private String mAuth;

    public HeaderInterceptor(PreferencesRepository p) {
        mPrefs = p;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        mAuth = (mPrefs.getAuthToken() != null)?mPrefs.getAuthToken():"";
        Request r = chain.request()
                .newBuilder()
                .addHeader(Constant.HEADER_ACCEPT, Constant.APP_HEADER)
                .addHeader(Constant.HEADER_AUTHORIZE, Constant.AUTH + mAuth)
                .build();

        return chain.proceed(r);
    }
}