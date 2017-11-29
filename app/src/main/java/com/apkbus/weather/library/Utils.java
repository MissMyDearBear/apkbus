package com.apkbus.weather.library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.os.Build;

import java.lang.reflect.Method;

public class Utils {
    /**
     * 将不透明的Activity转换成半透明的{@link android.R.attr#windowIsTranslucent}
     * 调用这个方法使在此Activity之下的视图得以显示。但此Activity将被重绘。
     * 此法对非半透明Activity或有{@link android.R.attr#windowIsFloating}(浮动)属性之Activity无效.
     */
    public static void convertActivityToTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            convertActivityToTranslucentAfterL(activity);
        } else {
            convertActivityToTranslucentBeforeL(activity);
        }
    }

    /* AndroidL以下版本convertToTranslucent方法 */
    private static void convertActivityToTranslucentBeforeL(Activity activity) {
        try {
            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes)
                if (clazz.getSimpleName().contains("TranslucentConversionListener"))
                    translucentConversionListenerClazz = clazz;
            @SuppressLint("PrivateApi") Method method = Activity.class.getDeclaredMethod("convertToTranslucent", translucentConversionListenerClazz);
            method.setAccessible(true);
            method.invoke(activity, new Object[]{null});
        } catch (Throwable ignored) {
        }
    }

    /* 在AndroidL及以上版本引用convertToTranslucent方法 */
    private static void convertActivityToTranslucentAfterL(Activity activity) {
        try {
            @SuppressLint("PrivateApi") Method getActivityOptions = Activity.class.getDeclaredMethod("getActivityOptions");
            getActivityOptions.setAccessible(true);
            Object options = getActivityOptions.invoke(activity);

            Class<?>[] classes = Activity.class.getDeclaredClasses();
            Class<?> translucentConversionListenerClazz = null;
            for (Class clazz : classes)
                if (clazz.getSimpleName().contains("TranslucentConversionListener"))
                    translucentConversionListenerClazz = clazz;
            @SuppressLint("PrivateApi") Method convertToTranslucent = Activity.class.getDeclaredMethod("convertToTranslucent", translucentConversionListenerClazz, ActivityOptions.class);
            convertToTranslucent.setAccessible(true);
            convertToTranslucent.invoke(activity, null, options);
        } catch (Throwable ignored) {
        }
    }
}
