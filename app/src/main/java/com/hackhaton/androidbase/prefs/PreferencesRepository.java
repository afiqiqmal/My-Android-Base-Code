package com.hackhaton.androidbase.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.facebook.crypto.CryptoConfig;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hackhaton.androidbase.R;
import com.zeroone.conceal.ConcealPrefRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * @author : hafiq on 07/02/2017.
 */

public class PreferencesRepository{

    private static final String KEY_PUSH_TOKEN = "push_token";
    private static final String KEY_PUSH_TOKEN_SENT = "push_token_sent";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_SERVER_TOKEN = "server_token";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_AGE = "user_age";
    private static final String KEY_USER_PHONE = "user_phone";
    private static final String KEY_USER_GENDER = "user_gender";
    private static final String KEY_USER_IMAGE_URL = "user_image_url";
    private static final String KEY_USER_TYPE = "user_type";

    private static final String KEY_FIRST_TIME = "first_time";
    private static final String KEY_HAS_LOGIN = "has_login";

    private static String TOKEN_SWITCH = "token.switch";


    private Context mContext;

    private ConcealPrefRepository concealPrefRepository;
    private ConcealPrefRepository.Editor concealEditor;

    @SuppressLint("CommitPrefEdits")
    public PreferencesRepository(Context context) {
        mContext = context;
        concealPrefRepository = new ConcealPrefRepository.PreferencesBuilder(mContext)
                .useDefaultPrefStorage()
                .sharedPrefsBackedKeyChain(CryptoConfig.KEY_256)
                .enableCrypto(true,true)
                .createPassword(mContext.getString(R.string.app_name))
                .setFolderName(mContext.getString(R.string.app_name))
                .create();

        concealEditor = new ConcealPrefRepository.Editor();
    }

    public void savePushToken(String token) {
        if (token == null) {
            concealPrefRepository.remove(KEY_PUSH_TOKEN);
        } else {
            concealPrefRepository.putString(KEY_PUSH_TOKEN, token);
        }
    }

    public boolean isEnableTokenPush(){
        return concealPrefRepository.getBoolean(TOKEN_SWITCH,true);
    }

    public void enableTokenPush(boolean sent){
        concealPrefRepository.putBoolean(TOKEN_SWITCH,sent);
    }

    public String getPushToken() {
        return concealPrefRepository.getString(KEY_PUSH_TOKEN, null);
    }


    public boolean isPushTokenSent() {
        return concealPrefRepository.getBoolean(KEY_PUSH_TOKEN_SENT, false);
    }

    public void setPushTokenSent(boolean sent) {
        concealPrefRepository.putBoolean(KEY_PUSH_TOKEN_SENT, sent);
    }

    public void setServerToken(String token) {
        if (token == null) {
            concealPrefRepository.remove(KEY_SERVER_TOKEN);
        } else {
            concealPrefRepository.putString(KEY_SERVER_TOKEN, token);
        }
    }

    public String getServerToken() {
        return concealPrefRepository.getString(KEY_SERVER_TOKEN, null);
    }


    public void setAuthToken(String auth) {
        concealPrefRepository.putString(KEY_AUTH_TOKEN, auth);
    }

    public String getAuthToken() {
        return concealPrefRepository.getString(KEY_AUTH_TOKEN, null);
    }
}
