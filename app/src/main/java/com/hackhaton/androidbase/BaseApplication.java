package com.hackhaton.androidbase;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.StrictMode;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.hackhaton.androidbase.client.Constant;
import com.hackhaton.androidbase.internal.AppModule;
import com.hackhaton.androidbase.internal.DaggerGraph;
import com.hackhaton.androidbase.internal.Graph;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * @author : hafiq on 23/01/2017.
 */

public class BaseApplication extends Application {

    private Graph mGraph;
    private String mUdid;
    private Activity activity;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setGraph(DaggerGraph.builder()
                .appModule(new AppModule(this))
                .build());

        loadLogger();
        errorHandler();

    }

    private void loadLogger(){
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag(Constant.LOGTAG)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
    }

    private void errorHandler(){
        Crashlytics crashlyticsKit = new Crashlytics.Builder().core(new CrashlyticsCore.Builder().disabled(false).build()).build();
        Fabric.with(this, crashlyticsKit,new Answers());

        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(BuildConfig.DEBUG);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    public Graph getGraph() {
        return mGraph;
    }

    public void setGraph(Graph graph) {
        this.mGraph = graph;
    }

    public static Graph graph(Context context) {
        BaseApplication app = (BaseApplication) context.getApplicationContext();
        return app.getGraph();
    }

    private void setDebugConfigs() {
        StrictMode.enableDefaults();
    }


    public static BaseApplication getApp(Context c) {
        return (BaseApplication) c.getApplicationContext();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
