package com.snail.ptrdemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * author：created by Snail.江
 * time: 4/17/2017 10:12
 * email：409962004@qq.com
 * TODO:
 */
public class SpUtils {

    private static SpUtils mInstance;
    private SharedPreferences sharedPreferences;

    private SpUtils(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SpUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SpUtils.class) {
                if (mInstance == null) {
                    mInstance = new SpUtils(context);
                }
            }
        }
        return mInstance;
    }

    public void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key,value).apply();
    }

    public int getInt(String key, int def) {
        return sharedPreferences.getInt(key,def);
    }

    public void putLong(String key, long value) {
        sharedPreferences.edit().putLong(key,value).apply();
    }

    public long getLong(String key, long def) {
        return sharedPreferences.getLong(key, def);
    }

    public void putString(String key,String value) {
        sharedPreferences.edit().putString(key,value).apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key,value).apply();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean("isLogin",false);
    }

    public void login() {
        sharedPreferences.edit().putBoolean("isLogin",true).apply();
    }

    public void logout() {
        sharedPreferences.edit().clear().apply();
    }


}
