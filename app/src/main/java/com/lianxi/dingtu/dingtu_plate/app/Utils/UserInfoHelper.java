package com.lianxi.dingtu.dingtu_plate.app.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.lianxi.dingtu.dingtu_plate.app.entity.UserInfoTo;

import static com.lianxi.dingtu.dingtu_plate.app.api.AppConstant.KeyValue.KEY_IS_LOGIN_INFO;
import static com.lianxi.dingtu.dingtu_plate.app.api.AppConstant.KeyValue.KEY_USER_INFO;

public class UserInfoHelper {

    protected static UserInfoHelper instance;
    private UserInfoTo userInfoTo;
    private Context mContext;

    private UserInfoHelper(Context context) {
        mContext = context;
        load(context);
    }


    public static UserInfoHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (UserInfoHelper.class) {
                if (instance == null) {
                    instance = new UserInfoHelper(context);
                }
            }
        }
        instance.mContext = context;
        return instance;
    }

    public UserInfoTo getUserInfoTo() {
        return userInfoTo;
    }

    public String getAccessToken() {
        return userInfoTo == null ? "" : userInfoTo.getAccessToken();
    }

    public int getCode() {
        return userInfoTo == null ? 0 : userInfoTo.getCompanyCode();
    }

    public void updateUser(UserInfoTo infoTo) {
        userInfoTo = infoTo;
        save();
    }

    private void save() {
        ConfigUtil.saveString(PreferenceManager.getDefaultSharedPreferences(mContext),
                KEY_USER_INFO, JSON.toJSONString(userInfoTo));
    }

    public void load(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String userJson = ConfigUtil.getString(sp, KEY_USER_INFO, "");
        Log.d("KeyValue-----", userJson);
        if (!TextUtils.isEmpty(userJson)) {
            userInfoTo = JSON.parseObject(userJson, UserInfoTo.class);
        }
    }

    public boolean isLogin() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        return ConfigUtil.getBoolean(sp, KEY_IS_LOGIN_INFO);
    }

    public void updateLogin(boolean flag) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        ConfigUtil.saveBoolean(sp, KEY_IS_LOGIN_INFO, flag);
    }

    public void clearAll() {
        ConfigUtil.saveString(PreferenceManager.getDefaultSharedPreferences(mContext),
                KEY_USER_INFO, "");
        updateLogin(false);
    }
}
