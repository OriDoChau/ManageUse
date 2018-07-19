package com.tranchau.manageuse.utils;

import android.app.Application;

import com.tranchau.manageuse.utils.SharePref;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharePref.getInstance().initSharePreferencec(this);
    }
}
