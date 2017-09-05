package com.hackhaton.androidbase.internal;

import android.app.Application;
import android.content.Context;

import com.hackhaton.androidbase.BuildConfig;
import com.hackhaton.androidbase.client.Constant;
import com.hackhaton.androidbase.prefs.PreferencesRepository;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author : hafiq on 19/08/2017.
 */

@Module
public class BaseOkHttpClient {

    @Provides
    @Singleton
    public OkHttpClient provideClient(Context mContext, PreferencesRepository u) {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(new File(mContext.getCacheDir(), "http"), SIZE_OF_CACHE);

        if (BuildConfig.DEBUG) {
            return new okhttp3.OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .addInterceptor(new HeaderInterceptor(u))
                    .connectTimeout(Constant.CONNECTTIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(Constant.READTIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(Constant.WRITETIMEOUT, TimeUnit.SECONDS)
                    .build();
        }
        else{
            return new okhttp3.OkHttpClient.Builder()
                    .addInterceptor(new HeaderInterceptor(u))
                    .cache(cache)
                    .addNetworkInterceptor(new CacheInterceptor(mContext))
                    .connectTimeout(Constant.CONNECTTIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(Constant.READTIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(Constant.WRITETIMEOUT, TimeUnit.SECONDS)
                    .build();
        }
    }
}
