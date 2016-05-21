package com.bobomee.android.retrofit2demo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

/**
 * Created by bobomee on 16/5/21.
 */
public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "retrofit2_demo_pref_file";

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";

    private final SharedPreferences mPref;

    public PreferencesHelper(Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void putAccessToken(String accessToken) {
        mPref.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply();
    }

    public void removeAccessToken() {
        mPref.edit().remove(PREF_KEY_ACCESS_TOKEN).apply();
    }

    @Nullable
    public String getAccessToken() {
        return mPref.getString(PREF_KEY_ACCESS_TOKEN, null);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }
}
