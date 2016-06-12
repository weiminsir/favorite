package com.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.microedition.khronos.opengles.GL10;

public class Device {
    protected static final String Log_TAG = Device.class.getName();
    protected static final String UNKNOW = "Unknown";
    private static final String MOBILE_NETWORK = "2G/3G";
    private static final String WIFI = "Wi-Fi";
    public static final int DEFAULT_TIMEZONE = 8;

    public static int getAppVersionCode(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            int version_code = pInfo.versionCode;
            return version_code;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return -1;
    }

    public static String getAppVersionName(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);

            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return "Unknown";
    }

    public static boolean checkPermission(Context context, String permission) {
        PackageManager pm = context.getPackageManager();
        if (pm.checkPermission(permission, context.getPackageName()) != 0) {
            return false;
        }
        return true;
    }

    public static String getAppLabel(Context context) {
        PackageManager pm = context.getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            ai = null;
        }
        String applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "");

        return applicationName;
    }

    public static String[] getGPU(GL10 gl) {
        try {
            String[] res = new String[2];
            String vendor = gl.glGetString(7936);
            String renderer = gl.glGetString(7937);
            res[0] = vendor;
            res[1] = renderer;
            return res;
        } catch (Exception e) {
            // Log.e(//Log_TAG, "Could not read gpu infor:", e);
        }
        return new String[0];
    }

    public static String getCPU() {
        String cpuInfo = null;

        FileReader fstream = null;
        BufferedReader in = null;
        try {
            fstream = new FileReader("/proc/cpuinfo");
            if (fstream != null)
                try {
                    in = new BufferedReader(fstream, 1024);
                    cpuInfo = in.readLine();
                    in.close();
                    fstream.close();
                } catch (IOException e) {
                    // Log.e(//Log_TAG,
                    // "Could not read from file /proc/cpuinfo", e);
                }
        } catch (FileNotFoundException e) {
            // Log.e(//Log_TAG, "Could not open file /proc/cpuinfo", e);
        }

        if (cpuInfo != null) {
            int start = cpuInfo.indexOf(58) + 1;
            cpuInfo = cpuInfo.substring(start);
        }
        return cpuInfo.trim();
    }

    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (tm == null) {
            // Log.w(//Log_TAG, "No IMEI.");
        }

        String imei = "";
        try {
            if (checkPermission(context, "android.permission.READ_PHONE_STATE")) {
                imei = tm.getDeviceId();
            }

        } catch (Exception ex) {
            // Log.w(//Log_TAG, "No IMEI.", ex);
        }

        if (TextUtils.isEmpty(imei)) {
            // Log.w(//Log_TAG, "No IMEI.");
            imei = getMac(context);
            if (TextUtils.isEmpty(imei)) {
                // Log.w(//Log_TAG,
                // "Failed to take mac as IMEI. Try to use Secure.ANDROID_ID instead.");

                imei = Settings.Secure.getString(context.getContentResolver(), "android_id");

                // Log.i(//Log_TAG, "getDeviceId: Secure.ANDROID_ID: " + imei);
                return imei;
            }
        }
        return imei;
    }

    public static String getMac(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            if (checkPermission(context, "android.permission.ACCESS_WIFI_STATE")) {
                WifiInfo info = wifi.getConnectionInfo();
                return info.getMacAddress();
            }
            // Log.w(//Log_TAG,
            // "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
        } catch (Exception e) {
            // Log.w(//Log_TAG, "Could not get mac address." + e.toString());
        }
        return "";
    }

    public static String getTimeString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(date);
        return time;
    }

    public static String getToday() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String time = df.format(date);
        return time;
    }

    public static Date toTime(String strDay) {
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sf.parse(strDay);
        } catch (Exception e) {
        }
        return null;
    }

    public static int getIntervalSeconds(Date startTime, Date endTime) {
        if (startTime.after(endTime)) {
            Date cal = startTime;
            startTime = endTime;
            endTime = cal;
        }
        long sl = startTime.getTime();
        long el = endTime.getTime();
        long ei = el - sl;
        return (int) (ei / 1000L);
    }

    public static String getChannel(Context context) {
        String channel = "Unknown";
        try {
            PackageManager manager = context.getPackageManager();
            ApplicationInfo info = manager.getApplicationInfo(context.getPackageName(), 128);

            if ((info != null) && (info.metaData != null)) {
                Object idObject = info.metaData.get("UMENG_CHANNEL");
                if (idObject != null) {
                    String id = idObject.toString();
                    if (id != null)
                        channel = id;
                    else {
                        // Log.i(//Log_TAG,
                        // "Could not read UMENG_CHANNEL meta-data from AndroidManifest.xml.");
                    }
                }
            }
        } catch (Exception e) {
            // Log.i(//Log_TAG,
            // "Could not read UMENG_CHANNEL meta-data from AndroidManifest.xml.");

            e.printStackTrace();
        }
        return channel;
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static String getApplicationLable(Context context) {
        return context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
    }

    public static boolean isDebug(Context context) {
        try {
            return (context.getApplicationInfo().flags & 0x2) != 0;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    public static int dp2px(DisplayMetrics displayMetrics, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, displayMetrics);
    }

    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * 是否为魅族flyme系统
     */
    public static boolean isFlyme() {
        try {
            // Invoke Build.hasSmartBar()
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    public static boolean isMIUI() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        return manufacturer.equalsIgnoreCase("Xiaomi");
//        return true;
    }


    /**
     * @param context
     * @return 友盟集成测试信息
     */
    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }

            if (TextUtils.isEmpty(device_id)) {
                device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final String SCALE_HIGH = "/resize_720x720_90";
    public static final String SCALE_MEDIUM = "/resize_640x640_90";
    public static final String SCALE_SMALL = "/resize_480x480_90";
    public static final String SCALE_TINY = "/resize_150x150_90";

    public static String getCover(Context context, String big_cover, boolean...isList) {
        String pattern = ".com";
        StringBuilder temp = new StringBuilder(big_cover);
        if (isList.length != 0 && isList[0]){
            return temp.insert(temp.indexOf(pattern) + pattern.length(), SCALE_TINY).toString();
        }

        int screenW = getScreenWidth(context);
        if (screenW < 720 && screenW > 480) {
            temp.insert(temp.indexOf(pattern) + pattern.length(), SCALE_MEDIUM);
        } else if (screenW < 480) {
            temp.insert(temp.indexOf(pattern) + pattern.length(), SCALE_SMALL);
        } else {
            temp.insert(temp.indexOf(pattern) + pattern.length(), SCALE_HIGH);
        }
        return temp.toString();
    }
}
