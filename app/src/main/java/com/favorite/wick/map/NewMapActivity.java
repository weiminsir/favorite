package com.favorite.wick.map;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.Projection;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.favorite.wick.R;
import com.favorite.wick.util.LogcatUtil;

public class NewMapActivity extends AppCompatActivity  implements AMap.OnMarkerDragListener, AMap.OnMapLoadedListener,
        AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter {
    private MapView mapView;
    private AMap aMap;
//    private Marker marker2;// 有跳动效果的marker对象
    private LatLng latlng = new LatLng( 121.484545 ,31.414343);
//    private LatLng latlng = new LatLng(121.464427, 31.282114);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_map);
        initViews();
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 必须要写
        init();
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    private void setUpMap() {
        LogcatUtil.d("WICK","设置监听");
        aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
//        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
        aMap.moveCamera(CameraUpdateFactory.zoomTo(20));    //缩放地图
        addMarkersToMap();// 往地图上添加marker
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap() {
        drawMarkers();// 添加10个带有系统默认icon的marker
    }

    /**
     * 绘制系统默认的1种marker背景图片
     */
    public void drawMarkers() {
        LogcatUtil.d("WICK","绘制系统默认的1种marker背景图片drawMarkers");
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title("佛山黄飞鸿")
//				.snippet("36.061, 103.834")
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true));
        marker.showInfoWindow();// 设置默认显示一个infowinfow
    }

    /**
     * 对marker标注点点击响应事件
     */
//    @Override
//    public boolean onMarkerClick(final Marker marker) {
//        if (marker.equals(marker2)) {
//            if (aMap != null) {
//                jumpPoint(marker);
//            }
//        }
//        return false;
//    }

    /**
     * marker点击时跳动一下
     */
    public void jumpPoint(final Marker marker) {
        LogcatUtil.d("marker点击时跳动一下");
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        Point startPoint = proj.toScreenLocation(Constants.XIAN);
        startPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 1500;
        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * Constants.XIAN.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * Constants.XIAN.latitude + (1 - t) * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                aMap.invalidate();// 刷新地图
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });

    }

    /**
     * 监听点击infowindow窗口事件回调
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
    }

    /**
     * 监听拖动marker时事件回调
     */
    @Override
    public void onMarkerDrag(Marker marker) {
        @SuppressWarnings("unused")
        String curDes = marker.getTitle() + "拖动时当前位置:(lat,lng)\n("
                + marker.getPosition().latitude + ","
                + marker.getPosition().longitude + ")";
    }

    /**
     * 监听拖动marker结束事件回调
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {
    }

    /**
     * 监听开始拖动marker事件回调
     */
    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    /**
     * 监听amap地图加载成功事件回调
     */
    @Override
    public void onMapLoaded() {
        LogcatUtil.d("监听amap地图加载成功事件回调");
        // 设置所有maker显示在当前可视区域地图中
        LatLngBounds bounds = new LatLngBounds.Builder().include(latlng).build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
    }

    /**
     * 监听自定义infowindow窗口的infocontents事件回调
     */
    @Override
    public View getInfoContents(Marker marker) {
        LogcatUtil.d("监听自定义infowindow窗口的infocontents事件回调");
        View infoContent = getLayoutInflater().inflate(R.layout.map_custom_info_contents, null);
        render(marker, infoContent);
        return infoContent;
    }

    /**
     * 监听自定义infowindow窗口的infowindow事件回调
     */
    @Override
    public View getInfoWindow(Marker marker) {
        LogcatUtil.d("监听自定义infowindow窗口的getInfoWindow事件回调");

        View infoWindow = getLayoutInflater().inflate(R.layout.map_custom_info_window, null);
        render(marker, infoWindow);
        return infoWindow;
    }

    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
        LogcatUtil.d("render 执行");
        String title = marker.getTitle();
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(Color.BLACK), 0,titleText.length(), 0);
            titleUi.setTextSize(15);
            titleUi.setText(titleText);

        } else {
            titleUi.setText("");
        }
        String snippet = marker.getSnippet();
        TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
        if (snippet != null) {
            SpannableString snippetText = new SpannableString(snippet);
            snippetText.setSpan(new ForegroundColorSpan(Color.GREEN), 0,snippetText.length(), 0);
            snippetUi.setTextSize(20);
            snippetUi.setText(snippetText);
        } else {
            snippetUi.setText("");
        }
    }


    private void initViews() {
//        tv_titles = (TextView) findViewById(R.id.tv_titles);
//        tvTime = (TextView) findViewById(R.id.tvTime);
//        ivPic = (ImageView) findViewById(R.id.ivPic);
    }

}


