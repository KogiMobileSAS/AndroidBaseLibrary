package com.kogimobile.android.baselibrary.rest.model.error;

import android.content.Context;

import com.kogimobile.android.baselibrary.R;

/**
 * @author Julian Cardona on 11/12/14.
 */
public class ErrorFactory {

    public static final int GENERAL_DEFAULT_ERROR = -1;

    public static String getErrorMessage(Context ctx, int code){
        try {
            String errorCodeMapping = String.valueOf(code);
            errorCodeMapping = errorCodeMapping.replace("-","_");
            int id = ctx.getResources().getIdentifier("error_code_" + errorCodeMapping, "string", ctx.getPackageName());
            return ctx.getString(id);
        }catch (Exception e){
            return ctx.getString(R.string.network_error_code_general);
        }
    }

}
