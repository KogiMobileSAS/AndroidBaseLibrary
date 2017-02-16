package com.kogimobile.android.baselibrary.rest.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * @author Julian Cardona on 5/21/15.
 */
public class LongTypedAdapter implements JsonSerializer<Long> {

    @Override
    public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null || src == -1) // exclusion is made here
            return null;

        return new JsonPrimitive(src);
    }
}