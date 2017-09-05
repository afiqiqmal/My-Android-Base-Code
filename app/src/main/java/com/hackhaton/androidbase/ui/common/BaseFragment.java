package com.hackhaton.androidbase.ui.common;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.WindowManager;

import com.hackhaton.androidbase.BaseApplication;
import com.hackhaton.androidbase.analytics.AnalyticHelper;
import com.hackhaton.androidbase.internal.FragmentGraph;
import com.hackhaton.androidbase.internal.FragmentModule;
import com.hackhaton.androidbase.internal.Graph;
import com.hackhaton.androidbase.permission.RxPermissionHelper;
import com.hackhaton.androidbase.prefs.PreferencesRepository;
import com.hackhaton.androidbase.utils.DeviceUtils;
import com.hackhaton.androidbase.utils.ErrorUtils;
import com.hackhaton.androidbase.utils.ProgressDialogUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author : hafiq on 23/01/2017.
 */

public class BaseFragment extends Fragment {

    protected CompositeDisposable mSubscriptions;
    protected boolean mIsSubscriber = false;

    protected Unbinder unbinder;

    @Inject
    protected PreferencesRepository preferencesRepository;

    @Inject
    protected ErrorUtils errorUtils;

    @Inject
    protected AnalyticHelper analyticHelper;

    @Inject
    protected ProgressDialogUtils progress;

    Activity activity;

    RxPermissionHelper rxPermissions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        fragmentGraph().inject(this);
        rxPermissions = new RxPermissionHelper(getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        DeviceUtils.closeSoftKeyboard(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();

        unsubscribeAll();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder!=null)
            unbinder.unbind();
    }

    public Graph getGraph() {
        return BaseApplication.graph(getActivity());
    }


    protected FragmentGraph fragmentGraph() {
        return getGraph().fragmentGraph(new FragmentModule(getActivity()));
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
