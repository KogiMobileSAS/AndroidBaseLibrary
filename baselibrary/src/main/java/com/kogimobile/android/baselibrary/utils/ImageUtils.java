package com.kogimobile.android.baselibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import timber.log.Timber;

public class ImageUtils {

    private static final String TAG = ImageUtils.class.getSimpleName();

    public static Bitmap decodeSampledBitmapFromFile(String filePath, int requiredWidth,int requiredHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        float imageMaxFactor = 1.15f;
        options.inJustDecodeBounds = true;
        int sampleSize = 1;
        BitmapFactory.decodeFile(filePath, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;

        if (imageHeight > requiredHeight || imageWidth > requiredWidth) {
            final int halfHeight = imageHeight / 2;
            final int halfWidth = imageWidth / 2;
            while (((halfHeight / sampleSize) > requiredHeight*imageMaxFactor) || ((halfWidth / sampleSize) > requiredWidth*imageMaxFactor)) {
                sampleSize *= 2;
            }
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        Timber.d(TAG, "Decoding bitmap on path: " + filePath + " with sample size of: " + sampleSize + " Dimensions:" + imageWidth + "x" + imageHeight);
        return BitmapFactory.decodeFile(filePath, options);
    }


    public static Bitmap scaleBitmap(Bitmap bm, int maxSize) {
        float width = bm.getWidth();
        float height = bm.getHeight();

        if (width > height) {
            // landscape
            float ratio = width / maxSize;
            Timber.d(TAG, "Width > Height ---------->  Width: " + width + " Height: " + height + " Max Size: " + maxSize + " Radio : " + ratio);
            width = maxSize;
            height = height / ratio;
        } else if (height > width) {
            // portrait
            float ratio = height / maxSize;
            Timber.d(TAG, "Height > Height---------->  Width: " + width + " Height: " + height + " Max Size: " + maxSize + " Radio : " + ratio);
            height = maxSize;
            width = width / ratio;
        } else {
            // square
            height = maxSize;
            width = maxSize;
        }
        Timber.d(TAG, "after scaling Width and height are " + width + "--" + height);

        bm = Bitmap.createScaledBitmap(bm, (int)width, (int)height, true);
        return bm;
    }


}
