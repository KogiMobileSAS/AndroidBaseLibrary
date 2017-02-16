package com.kogimobile.android.baselibrary.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

/**
 * @author Julian Cardona on 5/26/16.
 */
public class DrawableUtils {

    public static void tintImageDrawable(ImageView view,int resource,int color){
        Drawable bg = getDrawableFromResource(view.getContext(),resource);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            view.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        }
        else {
            DrawableCompat.setTint(bg,color);
        }
        view.setImageDrawable(bg);
    }

    public static Drawable getDrawableFromResource(Context context, int resource){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return  VectorDrawableCompat.create(context.getResources(),resource, null);
        }else{
            return ContextCompat.getDrawable(context,resource);
        }
    }

    public static Bitmap getBitmapFromDrawable(Context context, int drawableId) {
        Drawable drawable = getDrawableFromResource(context, drawableId);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawableCompat) {
            return getBitmap((VectorDrawableCompat) drawable);
        } else if (drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable);
        }else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Bitmap getBitmap(VectorDrawableCompat vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

}
