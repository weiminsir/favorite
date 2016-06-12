package com.stickercamera.app.camera.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.common.util.App;
import com.common.util.FileUtils;
import com.common.util.IOUtil;
import com.common.util.ImageUtils;
import com.imagezoom.ImageViewTouch;
import com.stickercamera.R;
import com.stickercamera.app.camera.CameraBaseActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * 裁剪图片界面
 * Created by sky on 2015/7/8.
 * Weibo: http://weibo.com/2030683111
 * Email: 1132234509@qq.com
 */
public class CropPhotoActivity extends CameraBaseActivity {

    private static final boolean IN_MEMORY_CROP = Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD_MR1;
    private Uri fileUri;
    private Bitmap oriBitmap;
    private int initWidth, initHeight;
    private static final int MAX_WRAP_SIZE = 2048;

    private double scaleSize=0;

    ImageViewTouch cropImage;
    ViewGroup drawArea;
    View wrapImage;
    View btnCropType;
    ImageView imageCenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 显示界面
        setContentView(R.layout.activity_new_crop);

        cropImage = (ImageViewTouch) findViewById(R.id.crop_image);
        drawArea = (ViewGroup) findViewById(R.id.draw_area);
        wrapImage = findViewById(R.id.wrap_image);
        btnCropType = findViewById(R.id.btn_crop_type);
        imageCenter = (ImageView) findViewById(R.id.image_center);


        fileUri = getIntent().getData();
        scaleSize=getIntent().getDoubleExtra("cover_scale",0);
        initView();
        initEvent();
    }

    private void initEvent() {
        btnCropType.setOnClickListener(v -> {
            if (cropImage.getVisibility() == View.VISIBLE) {
                Log.e("Tag", "Visible");
                btnCropType.setSelected(true);
                cropImage.setVisibility(View.GONE);
                wrapImage.setVisibility(View.VISIBLE);
            } else {
                Log.e("Tag", "InVisible");
                btnCropType.setSelected(false);
                cropImage.setVisibility(View.VISIBLE);
                wrapImage.setVisibility(View.GONE);
            }
        });
        imageCenter.setOnClickListener(v -> wrapImage.setSelected(!wrapImage.isSelected()));
        findViewById(R.id.cancel).setOnClickListener(v -> finish());
        findViewById(R.id.picked).setOnClickListener(v -> {
            showProgressDialog("图片处理中...");
            new Thread() {
                public void run() {
                    if (btnCropType.isSelected()) {
                        wrapImage();
                    } else {
                        cropImage();
                    }
                    dismissProgressDialog();
                }

                ;
            }.start();
        });
    }


    protected void wrapImage() {
        Log.e("Tag","选择了裁剪");
        int width = initWidth > initHeight ? initWidth : initHeight;
        int imageSize = width < MAX_WRAP_SIZE ? width : MAX_WRAP_SIZE;

        int move = (int) ((initHeight - initWidth) / 2 / (float) width * (float) imageSize);
        int moveX = initWidth < initHeight ? move : 0;
        int moveY = initHeight < initWidth ? -move : 0;
        Bitmap croppedImage = null;
        try {
            croppedImage = Bitmap.createBitmap(imageSize, imageSize, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(croppedImage);
            Paint p = new Paint();
            p.setColor(wrapImage.isSelected() ? Color.BLACK : Color.WHITE);
            canvas.drawRect(0, 0, imageSize, imageSize, p);
            Matrix matrix = new Matrix();
            matrix.postScale((float) imageSize / (float) width, (float) imageSize / (float) width);
            matrix.postTranslate(moveX, moveY);
            canvas.drawBitmap(oriBitmap, matrix, null);
        } catch (OutOfMemoryError e) {
            Log.e("OOM cropping image: " + e.getMessage(), e.toString());
            System.gc();
        }
        saveImageToCache(croppedImage);
    }

    private void initView() {
        drawArea.getLayoutParams().height = App.getScreenWidth();
        InputStream inputStream = null;
        try {
            //得到图片宽高比
            double rate = ImageUtils.getImageRadio(getContentResolver(), fileUri);
            oriBitmap = ImageUtils.decodeBitmapWithOrientation(fileUri.getPath(), App.getScreenWidth(), App.getScreenHeight());

            initWidth = oriBitmap.getWidth();
            initHeight = oriBitmap.getHeight();

            cropImage.setImageBitmap(oriBitmap, new Matrix(), (float) rate, 10);
            imageCenter.setImageBitmap(oriBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeStream(inputStream);
        }
    }

    private void cropImage() {
        Bitmap croppedImage;
        Log.e("Tag","裁剪图片:"+IN_MEMORY_CROP);
        if (IN_MEMORY_CROP) {
            croppedImage = inMemoryCrop(cropImage);
        } else {
            try {
                croppedImage = decodeRegionCrop(cropImage);
            } catch (IllegalArgumentException e) {
                croppedImage = inMemoryCrop(cropImage);
            }
        }
        saveImageToCache(croppedImage);
    }

    private void saveImageToCache(Bitmap croppedImage) {
        if (croppedImage != null) {
            try {
                ImageUtils.saveToFile(FileUtils.getInst().getCacheDir() + "/croppedcache",
                        false, croppedImage);
                Intent i = new Intent();
                i.setData(Uri.parse("file://" + FileUtils.getInst().getCacheDir()
                        + "/croppedcache"));
                setResult(RESULT_OK, i);
                dismissProgressDialog();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                toast("裁剪图片异常，请稍后重试", Toast.LENGTH_LONG);
            }
        }
    }

    @TargetApi(10)
    private Bitmap decodeRegionCrop(ImageViewTouch cropImage) {
        Log.e("Tag","initWidth:"+initWidth+"    initHeight:"+initHeight +"scaleSize:"+scaleSize);
        int width = initWidth > initHeight ? initHeight : initWidth;
        double newWidth=scaleSize==0?width:(scaleSize>1?(width/scaleSize):(width*scaleSize));
        int screenWidth = App.getScreenWidth();
        float scale = cropImage.getScale() / getImageRadio();
        Log.e("Tag","cropImage.getScale:"+cropImage.getScale()+"    getImageRadio:"+getImageRadio() );
        RectF rectf = cropImage.getBitmapRect();
        Log.e("Tag","Rectf.left:"+rectf.left+"    Rectf.top:"+rectf.top+"   scale:"+scale);
        int left = -(int) (rectf.left * width / screenWidth / scale);
        int top = -(int) (rectf.top * width / screenWidth / scale);
        int right=0;
        int bottom=0;
        Log.e("Tag","newWidth:"+newWidth);
        if(scaleSize>1)
        {
             right = left + (int) (width / scale);
             bottom = top + (int) (newWidth / scale);
        }
        else
        {
             right = left + (int) (newWidth / scale);
             bottom = top + (int) (width / scale);
        }

        Log.e("Tag","left:"+left+"  top:"+top+"     right:"+right+"     bottom:"+bottom+"   screenWidth:"+screenWidth);
        Rect rect = new Rect(left, top, right, bottom);
        InputStream is = null;
        System.gc();
        Bitmap croppedImage = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            oriBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            is = new ByteArrayInputStream(baos.toByteArray());
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(is, false);
            croppedImage = decoder.decodeRegion(rect, new BitmapFactory.Options());
        } catch (Throwable e) {
//            LogUtil.appError("照片处理失败", e);
        } finally {
            IOUtil.closeStream(is);
        }
        return croppedImage;
    }

    private float getImageRadio() {
        return Math.max((float) initWidth, (float) initHeight)
                / Math.min((float) initWidth, (float) initHeight);
    }

    private Bitmap inMemoryCrop(ImageViewTouch cropImage) {
        int width = initWidth > initHeight ? initHeight : initWidth;
        int screenWidth = App.getScreenWidth();
        System.gc();
        Bitmap croppedImage = null;
        try {
            croppedImage = Bitmap.createBitmap(width, width, Bitmap.Config.RGB_565);

            Canvas canvas = new Canvas(croppedImage);
            float scale = cropImage.getScale();
            RectF srcRect = cropImage.getBitmapRect();
            Matrix matrix = new Matrix();

            matrix.postScale(scale / getImageRadio(), scale / getImageRadio());
            matrix.postTranslate(srcRect.left * width / screenWidth, srcRect.top * width
                    / screenWidth);
            //matrix.mapRect(srcRect);
            canvas.drawBitmap(oriBitmap, matrix, null);
        } catch (OutOfMemoryError e) {
            Log.e("OOM cropping image: " + e.getMessage(), e.toString());
            System.gc();
        }
        return croppedImage;
    }


}
