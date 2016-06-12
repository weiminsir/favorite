package com.stickercamera.app.camera.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.common.util.FileUtils;
import com.common.util.ImageUtils;
import com.common.util.StringUtils;
import com.customview.PagerSlidingTabStrip;
import com.stickercamera.R;
import com.stickercamera.app.camera.CameraBaseActivity;
import com.stickercamera.app.camera.fragment.AlbumFragment;
import com.stickercamera.app.camera.util.Crop;
import com.stickercamera.app.model.Album;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 相册界面
 * Created by sky on 2015/7/8.
 * Weibo: http://weibo.com/2030683111
 * Email: 1132234509@qq.com
 */
public class AlbumActivity extends CameraBaseActivity {
    public final static String PIC_URL = "pic_url";
    public final static String IS_CROP = "is_crop";

    private Map<String, Album> albums;
    private List<String> paths = new ArrayList<String>();

    PagerSlidingTabStrip tab;
    ViewPager pager;

    private boolean mIsCrop = false;
    private double scaleSize=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        mIsCrop = getIntent().getBooleanExtra(IS_CROP, mIsCrop);
        scaleSize=getIntent().getDoubleExtra("cover_scale",0);

        tab = (PagerSlidingTabStrip) findViewById(R.id.indicator);
        pager = (ViewPager) findViewById(R.id.pager);

        albums = ImageUtils.findGalleries(this, paths, 0);
        //ViewPager的adapter
        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tab.setViewPager(pager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent result) {
        if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            //Intent newIntent = new Intent(this, CropPhotoActivity.class);
//            newIntent.setData(result.getData());
//            startActivity(newIntent);
            Intent intent = new Intent();
            intent.putExtra(PIC_URL, getRealFilePath(AlbumActivity.this, result.getData()));
            setResult(RESULT_OK, intent);
            finish();
        }
//        else if (requestCode == com.soundcloud.android.crop.Crop.REQUEST_CROP && resultCode == RESULT_OK) {
//            Uri uri = (Uri) result.getSerializableExtra("output");
//
//            Intent intent = new Intent();
//            intent.putExtra(AlbumActivity.PIC_URL, getRealFilePath(AlbumActivity.this, uri));
//            setResult(Activity.RESULT_OK, intent);
//            finish();
//        }
    }

    /**
     * ViewPager适配器
     *
     * @author len
     */
    class TabPageIndicatorAdapter extends FragmentPagerAdapter {
        public TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //新建一个Fragment来展示ViewPager item的内容，并传递参数
            return AlbumFragment.newInstance(albums.get(paths.get(position)).getPhotos(), mIsCrop,scaleSize);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Album album = albums.get(paths.get(position % paths.size()));
            if (StringUtils.equalsIgnoreCase(FileUtils.getInst().getSystemPhotoPath(),
                    album.getAlbumUri())) {
                return "胶卷相册";
            } else if (album.getTitle().length() > 13) {
                return album.getTitle().substring(0, 11) + "...";
            }
            return album.getTitle();
        }

        @Override
        public int getCount() {
            return paths.size();
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
