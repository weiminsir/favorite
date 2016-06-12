package com.common.util;

import android.app.Application;
import android.util.DisplayMetrics;

/**
 * Created by ken on 22/7/15.
 */
public class App {
    private static Application mContext;
    private static DisplayMetrics displayMetrics = null;

    //Must init this first
    public static void init(Application context) {
        mContext = context;
        displayMetrics = mContext.getResources().getDisplayMetrics();
    }

    public static Application getApp() {
        return mContext;
    }

    public static float getScreenDensity() {
        if (displayMetrics == null) {
            setDisplayMetrics(mContext.getResources().getDisplayMetrics());
        }
        return displayMetrics.density;
    }

    public static int getScreenHeight() {
        if (displayMetrics == null) {
            setDisplayMetrics(mContext.getResources().getDisplayMetrics());
        }
        return displayMetrics.heightPixels;
    }

    public static int getScreenWidth() {
        if (displayMetrics == null) {
            setDisplayMetrics(mContext.getResources().getDisplayMetrics());
        }
        return displayMetrics.widthPixels;
    }

    public static void setDisplayMetrics(DisplayMetrics DisplayMetrics) {
        displayMetrics = DisplayMetrics;
    }

    //获取应用的data/data/....Cache目录
    public static String getCacheDirPath() {
        return mContext.getCacheDir().getAbsolutePath();
    }

}
