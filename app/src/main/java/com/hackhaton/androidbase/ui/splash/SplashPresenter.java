package com.hackhaton.androidbase.ui.splash;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.hackhaton.androidbase.client.entity.request.TokenRequest;
import com.hackhaton.androidbase.client.entity.response.TokenResponse;
import com.hackhaton.androidbase.internal.PerActivity;
import com.hackhaton.androidbase.prefs.PreferencesRepository;
import com.hackhaton.androidbase.services.RegistrationIntentService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * @author : hafiq on 23/01/2017.
 */

@PerActivity
public class SplashPresenter {

    private final SplashManager manager;
    private final PreferencesRepository preferences;
    private SplashConnector mView;
    private final CompositeDisposable mSubscription = new CompositeDisposable();
    private TokenBroadCastService tokenBroadcastReceiver;

    @Inject
    SplashPresenter(SplashManager manager, PreferencesRepository preferences) {
        this.manager = manager;
        this.preferences = preferences;
        tokenBroadcastReceiver = new TokenBroadCastService();
    }

    public void getToken(TokenRequest request) {
        Disposable s = manager.getToken(request)
                .doOnSubscribe(disposable -> showLoading())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tokenResponse -> {
                    preferences.setAuthToken(tokenResponse.getToken());
                    showContents(tokenResponse);
                }, throwable -> {
                    throwable.printStackTrace();
                    showError(throwable);
                });
        mSubscription.add(s);
    }

    void requestPushToken(Activity activity){
        if (activity!=null) {
            Intent service = new Intent(activity, RegistrationIntentService.class);
            service.putExtra(RegistrationIntentService.TOKEN, "token");
            activity.startService(service);

            IntentFilter intentFilter = new IntentFilter(RegistrationIntentService.ACTION_TOKEN);
            intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
            activity.registerReceiver(tokenBroadcastReceiver, intentFilter);

            //send back first
            sendBroatCast();
        }
    }

    public class TokenBroadCastService extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String token = intent.getStringExtra(RegistrationIntentService.TOKEN);
            sendToken(token);
        }
    }

    void voidReceiver(Activity activity){
        try {
            activity.unregisterReceiver(tokenBroadcastReceiver);
        }
        catch (Exception ignored){
        }
    }

    private void sendBroatCast(){
        if (mView == null) return;
        mView.sendBroadCast(tokenBroadcastReceiver);
    }

    private void sendToken(String token){
        if (mView == null) return;
        mView.sendToken(token);
    }

    private void showLoading() {
        if (mView == null) return;
        mView.showLoading();
    }

    private void showContents(TokenResponse r) {
        if (mView == null) return;
        mView.showContents(r);
    }

    private void showError(Throwable error) {
        if (mView == null) return;
        mView.showError(error);
    }

    public void setView(SplashConnector view) {
        mView = view;
        if (view == null) mSubscription.clear();
    }
}