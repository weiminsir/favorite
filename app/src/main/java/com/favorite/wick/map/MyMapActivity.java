package com.favorite.wick.map;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyMapActivity extends AppCompatActivity implements AMap.OnMarkerDragListener, AMap.OnMapLoadedListener,
        AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter {

    @BindView(R.id.map)
    protected MapView mMapView;
    private LatLng latlng = new LatLng(121.464427, 31.282114);
    private AMap aMap;
    private Marker marker2;// 有跳动效果的marker对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_map);
//        121.464427,31.282114
        ButterKnife.bind(this);

        aMap = mMapView.getMap();
//        aMap.setTrafficEnabled(true);// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
//        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
        mMapView.onCreate(savedInstanceState);// 此方法必须重写

        init();
        String weidu = "30.26246353";
        String jingdu = "120.13471484";
//        http://beishan.zjol.com.cn/UploadFiles/Upload/201007/14/105101505059.jpg
//        latlng = new LatLng(22.998844, 113.114266);
        latlng = new LatLng(Double.parseDouble(weidu), Double.parseDouble(jingdu));
    }

    private void init() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            setupMap();
        }
    }

    private void setupMap() {

        aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
        aMap.moveCamera(CameraUpdateFactory.zoomTo(20));    //缩放地图
        addMarkersToMap();
    }

    private void addMarkersToMap() {
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title("上海大学")
//				.snippet("36.061, 103.834")
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true));
        marker.showInfoWindow();// 设置默认显示一个infowinfow
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    /**implements AMap.OnMarkerDragListener*/

    /**
     * 监听开始拖动marker事件回调
     */
    @Override
    public void onMarkerDragStart(Marker marker) {

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
        Toast.makeText(MyMapActivity.this, curDes, Toast.LENGTH_SHORT).show();

    }
    /**
     * marker点击时跳动一下
     */
    public void jumpPoint(final Marker marker) {
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
     * 监听拖动marker结束事件回调
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    /**
     * 监听amap地图加载成功事件回调
     */
    @Override
    public void onMapLoaded() {
// 设置所有maker显示在当前可视区域地图中
        LatLngBounds bounds = new LatLngBounds.Builder().include(latlng).build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
    }

    /**
     * 对marker标注点点击响应事件
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(marker2)) {
            if (aMap != null) {
                jumpPoint(marker);
            }
        }
        return false;
    }

    /**
     * 监听点击infowindow窗口事件回调
     */
    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    /**
     * 监听自定义infowindow窗口的infowindow事件回调
     */
    @Override
    public View getInfoWindow(Marker marker) {
        View infoWindow = getLayoutInflater().inflate(R.layout.map_custom_info_window, null);
        render(marker, infoWindow);
        return infoWindow;
    }

    /**
     * 监听自定义infowindow窗口的infocontents事件回调
     */
    @Override
    public View getInfoContents(Marker marker) {
        View infoContent = getLayoutInflater().inflate(R.layout.map_custom_info_contents, null);
        render(marker, infoContent);
        return infoContent;
    }

    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
        String title = marker.getTitle();
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(Color.BLACK), 0, titleText.length(), 0);
            titleUi.setTextSize(15);
            titleUi.setText(titleText);

        } else {
            titleUi.setText("");
        }
        String snippet = marker.getSnippet();
        TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
        if (snippet != null) {
            SpannableString snippetText = new SpannableString(snippet);
            snippetText.setSpan(new ForegroundColorSpan(Color.GREEN), 0, snippetText.length(), 0);
            snippetUi.setTextSize(20);
            snippetUi.setText(snippetText);
        } else {
            snippetUi.setText("");
        }
    }
}
