package com.hackhaton.androidbase.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.crash.FirebaseCrash;
import com.hackhaton.androidbase.R;
import com.hackhaton.androidbase.client.Constant;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;

import retrofit2.HttpException;
import retrofit2.Response;

/**
 * @author : hafiq on 23/01/2017.
 */

@Singleton
public class ErrorUtils {

    private Context mContext;
    private boolean LOG = false;
    private Throwable throwable;

    @Inject
    public ErrorUtils(Context context){
        this.mContext = context;
    }

    public void checkError(Throwable e){
        try {
            throwable = e;
            fireBaseReportError(e);
            Crashlytics.logException(e);
            e.printStackTrace();

            if (e instanceof HttpException) {
                int code = getHttpErrorCode(e);
                httpMessage(code);
            }
            else if (e instanceof ConnectException) {
                showToast(mContext.getString(R.string.slow_internet));
            } else if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
                showToast(mContext.getString(R.string.internet_not_connected));
            } else if (e instanceof SSLHandshakeException || e instanceof SSLPeerUnverifiedException) {
                showToast(mContext.getString(R.string.server_problem));
            } else {
                showToast(mContext.getString(R.string.unknown_error_msg));
            }
        }
        catch (Exception err){
            err.printStackTrace();
        }
    }

    private int getHttpErrorCode(Throwable e){
        Response body = ((HttpException) e).response();
        return body.code();
    }

    //only common http error
    private void httpMessage(int code){
        if (code == 400){
            showToast("Bad Request");
        }
        else if (code == 401) {
            showToast("Not Authorized Access");
        }
        else if (code == 403) {
            showToast("Forbidden Access");
        }
        else if (code == 404) {
            showToast("Request Not Found");
        }
        else if (code == 405) {
            showToast("Request Not Allowed");
        }
        else if (code == 407){
            showToast("Proxy Authentication Required");
        }
        else if (code == 408){
            showToast("Data Request Expired");
        }
        else if (code == 500) {
            showToast("Internal Server Error Occurred");
        }
        else if (code == 502){
            showToast("Bad Url Gateway");
        }
        else if (code == 503){
            showToast("Service is Unavailable. Please Try Again");
        }
        else{
            showErrorLog("Error code : "+code);
        }
    }

    private void showToast(String message){
        if (!LOG) {
            if (mContext != null) {
                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
            }
        }
        showErrorLog(message+": "+throwable.getMessage());
    }

    private void showErrorLog(String message){
        Log.e(Constant.LOGTAG,message);
    }

    private void fireBaseReportError(Throwable e){
        FirebaseCrash.report(e);
    }

    public void recordError(Throwable e){
        try {
            fireBaseReportError(e);
            Crashlytics.logException(e);
            e.printStackTrace();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
