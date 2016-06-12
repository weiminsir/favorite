package com.common.util;

public class DistanceUtil {

    public static int getCameraAlbumWidth() {
        return (App.getScreenWidth() - Device.dp2px(App.getApp(), 10)) / 4 - Device.dp2px(App.getApp(), 4);
    }

    // 相机照片列表高度计算 
    public static int getCameraPhotoAreaHeight() {
        return getCameraPhotoWidth() + Device.dp2px(App.getApp(), 4);
    }

    public static int getCameraPhotoWidth() {
        return App.getScreenWidth() / 4 - Device.dp2px(App.getApp(), 2);
    }

    //活动标签页grid图片高度
    public static int getActivityHeight() {
        return (App.getScreenWidth() - Device.dp2px(App.getApp(), 24)) / 3;
    }
}
