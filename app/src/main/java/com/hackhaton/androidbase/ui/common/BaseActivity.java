package com.hackhaton.androidbase.ui.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.hackhaton.androidbase.BaseApplication;
import com.hackhaton.androidbase.analytics.AnalyticHelper;
import com.hackhaton.androidbase.internal.ActivityGraph;
import com.hackhaton.androidbase.internal.ActivityModule;
import com.hackhaton.androidbase.internal.Graph;
import com.hackhaton.androidbase.permission.RxPermissionHelper;

import com.hackhaton.androidbase.prefs.PreferencesRepository;
import com.hackhaton.androidbase.utils.DeviceUtils;
import com.hackhaton.androidbase.utils.ErrorUtils;
import com.hackhaton.androidbase.utils.ProgressDialogUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * @author : hafiq on 28/07/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected CompositeDisposable mSubscriptions;

    @Inject
    protected PreferencesRepository preferencesRepository;

    @Inject
    protected ErrorUtils errorUtils;

    @Inject
    protected AnalyticHelper analyticHelper;

    @Inject
    protected ProgressDialogUtils progress;

    protected RxPermissionHelper rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityGraph().inject(this);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        DeviceUtils.closeSoftKeyboard(this);
        rxPermissions = new RxPermissionHelper(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    protected ActivityGraph activityGraph() {
        return getGraph().activityGraph(new ActivityModule(this));
    }

    public Graph getGraph() {
        return BaseApplication.graph(this);
    }

    protected void addSubscription(Disposable s) {
        if (mSubscriptions == null) mSubscriptions = new CompositeDisposable();
        mSubscriptions.add(s);
    }

    protected void unsubscribeAll() {
        if (mSubscriptions == null) return;
        mSubscriptions.clear();
    }

}
