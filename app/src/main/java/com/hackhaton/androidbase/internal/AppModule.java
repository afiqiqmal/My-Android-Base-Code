package com.hackhaton.androidbase.internal;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.hackhaton.androidbase.BuildConfig;
import com.hackhaton.androidbase.client.Constant;
import com.hackhaton.androidbase.client.RestApi;
import com.hackhaton.androidbase.prefs.PreferencesRepository;
import com.hackhaton.androidbase.utils.BaseCryptUtils;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : hafiq on 23/01/2017.
 */

@Module
public class AppModule {

    protected final Context mContext;

    public AppModule(Application context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mContext;
    }


}
