package com.tranchau.manageuse.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharePref {
    private static final String USERINFO = "USERINFO";
    private static SharePref instance;
    private SharedPreferences sharedPreferences;

    private SharePref(){}

    public static SharePref getInstance(){
        if (instance == null){
            instance = new SharePref();
        }
        return instance;
    }

    public void initSharePreferencec(Context context){
        sharedPreferences = context.getSharedPreferences(USERINFO, Context.MODE_PRIVATE);
    }

    public void putSharePref(String key, String str){
        sharedPreferences.edit().putString(key, str).apply();
        Log.d("LOG", "PUT" + "  " + str);
    }

    public String getSharePref(String key){
        String str = sharedPreferences.getString(key, "");
        Log.d("LOG", "GET" + "  " +str);
        return str;
    }
}
