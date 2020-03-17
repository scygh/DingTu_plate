package com.lianxi.dingtu.dingtu_plate.mvp.ui.widget;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class UpdateConfig {



    private static final String TAG = "Update";
    public static final String APK_PACKAGENAME ="com.lianxi.dingtu.newsnfc";

    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(APK_PACKAGENAME, 0).versionCode;
        } catch (NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return verCode;
    }

    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(APK_PACKAGENAME, 0).versionName;
        } catch (NameNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }
        return verName;

    }

}
