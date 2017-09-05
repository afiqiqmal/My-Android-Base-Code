package com.hackhaton.androidbase.internal;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.hackhaton.androidbase.BuildConfig;
import com.hackhaton.androidbase.client.RestApi;
import com.hackhaton.androidbase.utils.BaseCryptUtils;

import java.util.Date;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : hafiq on 19/08/2017.
 */

@Module
public class ApiModule {

    private Retrofit retrofit;


    @Provides
    @Singleton
    public Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setLenient();
        builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> {
            if (json.getAsJsonPrimitive().isNumber()) {
                return new Date(json.getAsJsonPrimitive().getAsLong() * 1000);
            } else {
                return null;
            }
        });
        return builder.create();
    }

    @Provides
    @Singleton
    public RestApi provideRestApi(Context mContext, OkHttpClient client, Gson g) {
        retrofit = new Retrofit.Builder()
                .baseUrl(new BaseCryptUtils.Builder(mContext).decodeStringWithIteration(BuildConfig.URL_API))
                .addConverterFactory(GsonConverterFactory.create(g))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(RestApi.class);
    }
}
