package com.hackhaton.androidbase.ui;

import android.Manifest;
import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.hackhaton.androidbase.R;
import com.hackhaton.androidbase.permission.RxPermissionView;
import com.hackhaton.androidbase.ui.common.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RxPermissionView.PermissionConnector {

    boolean pressed = false;
    Integer prev = null;
    boolean isCan = false;
    int type;

    String[] permissions_request = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityGraph().inject(this);

        initPermissionLister();
    }

    private void initPermissionLister() {
        rxPermissions.requestPermissions(this, permissions_request);
    }

    private void initProcess(){
        //process here
    }

    @Override
    public void onBackPressed() {

        if (pressed) {
            super.onBackPressed();
            return;
        }
        this.pressed = true;
        Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> pressed = false, 2000);
    }

    @Override
    public void isPermissionGranted(boolean granted) {
        if (granted) {
            initProcess();
        } else {
            rxPermissions.requestPermissionAgain(this, permissions_request);
        }
    }
}
