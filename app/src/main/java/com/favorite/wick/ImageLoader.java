package com.favorite.wick;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by Weimin on 5/31/2016.
 */
public class ImageLoader {
    String url = "http://cdn.duitang.com/uploads/item/201603/02/20160302223503_jmdXT.thumb.224_0.jpeg";

    public static void loadImage(Context context, ImageView imageView, String url) {
        Picasso.with(context)
                .load(Uri.parse(url))
                .resize(100, 100)
                .centerCrop()
                .fit()
                .transform(new CropSquareTransformation())
                .into(imageView);


    }

    static class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return "square()";
        }
    }

}
