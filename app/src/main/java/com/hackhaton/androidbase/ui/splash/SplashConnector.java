package com.hackhaton.androidbase.ui.splash;

import android.content.BroadcastReceiver;

import com.hackhaton.androidbase.client.entity.response.TokenResponse;


/**
 * @author : hafiq on 23/01/2017.
 */

public interface SplashConnector {

    void showContents(TokenResponse response);
    void showLoading();
    void showError(Throwable throwable);
    void sendToken(String token);
    void sendBroadCast(BroadcastReceiver broadcastReceiver);

}
