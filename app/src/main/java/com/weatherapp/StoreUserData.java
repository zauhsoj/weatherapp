package com.weatherapp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Eugene on 12-07-2016.
 */
public class StoreUserData {
    private SharedPreferences pref = null;
    private Context parentActivity;
    public static final String APP_KEY = "weather_app";

    public StoreUserData(Context context) {
        parentActivity = context;
    }

    public void setString(String key, String value) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        return pref.getString(key, "");

    }

    public void setBoolean(String key, boolean value) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        return pref.getBoolean(key, false);
    }


    public void setInt(String key, int value) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        return pref.getInt(key, -1);
    }


    public boolean is_exist(String key) {
        pref = parentActivity.getSharedPreferences(APP_KEY,
                Context.MODE_PRIVATE);
        if (pref.contains(key)) {
            return true;
        } else {
            return false;
        }
    }

    public void clearData(Context context) {
        SharedPreferences settings = context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }

}