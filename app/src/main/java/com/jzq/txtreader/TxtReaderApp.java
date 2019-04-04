package com.jzq.txtreader;

import android.app.Application;
import android.content.Context;

public class TxtReaderApp extends Application {

    private static TxtReaderApp sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static Context getContext() {
        return sInstance.getApplicationContext();
    }
}
