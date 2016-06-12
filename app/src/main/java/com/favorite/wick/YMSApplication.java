package com.favorite.wick;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Weimin on 5/31/2016.
 */
public class YMSApplication extends Application {
    public static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Fresco.initialize(context);
    }
}
