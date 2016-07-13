package com.favorite.wick.util;

import android.util.Log;

import java.nio.charset.UnsupportedCharsetException;

/**
 * Log统一管理类
 */
public class LogcatUtil {

    private LogcatUtil() {
        // 不能实例化
        throw new UnsupportedCharsetException("cannot be instantiated");
    }

    // 是否需要打印Bug,可以在Application的onCreate函数初始化
    public static boolean isDebug = true;
    // 标记信息为 DATA
    public static final String TAG = "WICK";

    /*
     * 四个默认的Tag函数v的调试颜色为黑色的，任何消息都会输出，这里的v代表verbose啰嗦的意思
     */
    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);

    }

    // d的输出颜色是蓝色的，仅输出debug调试的意思，但他会输出上层的信息
    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);

    }

    // i的输出为绿色，一般提示性的消息information，它不会输出Log.v和Log.d的信息，但会显示i、w和e的信息
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);

    }

    // w的意思为橙色，可以看作为warning警告
    public static void w(String msg) {
        if (isDebug)
            Log.w(TAG, msg);

    }

    // e为红色，可以想到error错误，这里仅显示红色的错误信息，这些错误就需要我们认真的分析，查看栈的信息了
    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);

    }

    /*
     * 下面是传入自定义tag的函数
     */
    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);

    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);

    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);

    }

    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(tag, msg);

    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);

    }

}
