# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Applications/Android-SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:

#General
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod

# Timber
-dontwarn org.jetbrains.annotations.**

# Joda Time
-dontwarn org.joda.convert.FromString
-dontwarn org.joda.convert.ToString

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

#Event Bus
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

#BaseLibrary
-keepnames class com.kogimobile.android.baselibrary.**
-keep class com.kogimobile.android.baselibrary.**{
    public <methods>;
    protected <methods>;
    public static <methods>;
}