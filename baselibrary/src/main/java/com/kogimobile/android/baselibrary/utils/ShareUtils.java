package com.kogimobile.android.baselibrary.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Julian Cardona on 10/30/14.
 */
public class ShareUtils {

    public static void shareString(Context ctx,String text){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
        //TODO change share title
        ctx.startActivity(Intent.createChooser(sharingIntent,"Share Text"));
    }
}
