package com.kogimobile.android.baselibrary.utils;

/**
 * @author Julian Cardona on 11/12/14.
 */
public class StringUtils {

    public static boolean isBlank(CharSequence string){
        return (string == null || string.toString().trim().length() == 0);
    }

    public static String ifBlankThenReturn(CharSequence string, CharSequence blankDefault){
        if(string == null || string.toString().trim().length() == 0 || string.toString().equalsIgnoreCase("null"))
            return  blankDefault.toString();
        return string.toString();
    }

    public static String ifNotBlankThenPrefix(CharSequence string,CharSequence prefix,CharSequence blankDefault){
        if(string == null || string.toString().trim().length() == 0 || string.toString().equalsIgnoreCase("null"))
            return  blankDefault.toString();
        return prefix.toString().concat(string.toString());
    }

    public static String ifNotBlankThenSuffix(CharSequence string,CharSequence suffix,CharSequence blankDefault){
        if(string == null || string.toString().trim().length() == 0 || string.toString().equalsIgnoreCase("null"))
            return  blankDefault.toString();
        return string.toString().concat(suffix.toString());
    }

    public static String ifNotBlankThenPrefixAndSuffix(CharSequence string,CharSequence prefix,CharSequence suffix,CharSequence blankDefault){
        if(string == null || string.toString().trim().length() == 0 || string.toString().equalsIgnoreCase("null"))
            return  blankDefault.toString();
        return prefix.toString().concat(string.toString().concat(suffix.toString()));
    }
}
