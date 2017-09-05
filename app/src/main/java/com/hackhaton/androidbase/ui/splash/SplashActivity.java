package com.hackhaton.androidbase.ui.splash;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;


import com.hackhaton.androidbase.R;
import com.hackhaton.androidbase.analytics.Screen;
import com.hackhaton.androidbase.client.entity.request.TokenRequest;
import com.hackhaton.androidbase.client.entity.response.TokenResponse;
import com.hackhaton.androidbase.ui.MainActivity;
import com.hackhaton.androidbase.ui.common.BaseActivity;
import com.hackhaton.androidbase.utils.DeviceUtils;
import com.hackhaton.androidbase.utils.SubUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * @author : hafiq on 23/01/2017.
 */

public class SplashActivity extends BaseActivity implements SplashConnector {

    BroadcastReceiver tokenBroadcastReceiver;
    String version;
    String udid = null;

    @Inject
    SplashPresenter mPresenter;

    boolean isTimerFinished = false;
    boolean isTokenFinished = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activityGraph().inject(this);

        mPresenter.setView(this);

        udid = DeviceUtils.getUDID(this);
        if (SubUtils.checkPlayServices(this)) {
            version = DeviceUtils.getDeviceVersion(this);


            Observable.timer(2, TimeUnit.SECONDS)
                    .subscribe(timer->{
                        isTimerFinished = true;

                        checkFinished();
                    },t->{
                        errorUtils.recordError(t);
                    });

            if (preferencesRepository.getPushToken() == null)
                mPresenter.requestPushToken(this);
            else {
                mPresenter.getToken(requestDetail());
            }

        } else {
            System.exit(0);
        }

        analyticHelper.sendScreenName(Screen.SPLASHSCREEN);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.voidReceiver(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.voidReceiver(this);
    }

    private TokenRequest requestDetail() {
        return new TokenRequest(
                udid,
                "Android",
                DeviceUtils.getAPIlevel(),
                DeviceUtils.getDeviceVersion(this),
                preferencesRepository.getPushToken()
        );
    }

    @Override
    public void showContents(TokenResponse response) {
        isTokenFinished = true;

        checkFinished();
    }

    @Override
    public void showLoading() {
//        runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));
    }

    @Override
    public void showError(Throwable throwable) {
        errorUtils.checkError(throwable);
    }

    @Override
    public void sendToken(String token) {
        if (token != null) {
            mPresenter.getToken(requestDetail());
        } else {
            errorUtils.checkError(new RuntimeException("Token is Null"));
        }
    }

    @Override
    public void sendBroadCast(BroadcastReceiver broadcastReceiver) {
        tokenBroadcastReceiver = broadcastReceiver;
    }

    private void checkFinished(){
        if (isTimerFinished && isTokenFinished) {

            Intent intent;
            intent = new Intent(new Intent(SplashActivity.this, MainActivity.class));
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
