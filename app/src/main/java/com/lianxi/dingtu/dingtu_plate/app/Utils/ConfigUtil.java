package com.lianxi.dingtu.dingtu_plate.app.Utils;

import android.content.SharedPreferences;

public class ConfigUtil {

    public static SharedPreferences mPref;

    public static void saveString(SharedPreferences pref, String key, String value) {
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(key, value);
        edit.commit();
    }

    /* String类型 */
    public static void saveString(String key, String value) {
        saveString(mPref, key, value);
    }

    public static String getString(String key) {
        return getString(mPref, key, "");
    }

    public static String getString(String key, String strDefault) {
        return getString(mPref, key, strDefault);
    }

    public static String getString(SharedPreferences pref, String key, String strDefault) {
        return pref.getString(key, strDefault);
    }

	/* boolean类型 */

    public static void saveBoolean(SharedPreferences pref, String key, boolean value) {
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static void saveBoolean(String key, boolean value) {
        saveBoolean(mPref, key, value);
    }

    public static boolean getBoolean(SharedPreferences pref, String key) {
        return getBoolean(pref, key, false);
    }


    public static boolean getBoolean(SharedPreferences pref, String key, boolean strDefault) {
        return pref.getBoolean(key, strDefault);
    }

	/* int类型 */

    public static void saveInt(SharedPreferences pref, String key, int value) {
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static void saveInt(String key, int value) {
        saveInt(mPref, key, value);
    }

    public static int getInt(String key) {
        return getInt(mPref, key, 0);
    }

    public static int getInt(String key, int strDefault) {
        return getInt(mPref, key, strDefault);
    }

    public static int getInt(SharedPreferences pref, String key, int strDefault) {
        return pref.getInt(key, strDefault);
    }

}
