package com.lianxi.dingtu.dingtu_plate.app.base;

import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.jess.arms.base.App;
import com.jess.arms.base.delegate.AppDelegate;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.Preconditions;
import com.lianxi.dingtu.dingtu_plate.app.Utils.GainschaUsbUtils;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.activity.OnlineActivity;

import java.util.List;

import android_serialport_api.SerialPortUtils;


public class MainApplication extends MultiDexApplication implements App {

    private AppLifecycles mAppDelegate;
    private static Context mContext;
    private static Context mGainContext;
    private static SerialPortUtils serialPortUtils;

    public static SerialPortUtils getSerialPortUtils() {
        return serialPortUtils;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        if (mAppDelegate == null) {
            this.mAppDelegate = new AppDelegate(base);
        }
        this.mAppDelegate.attachBaseContext(base);
    }

    public static Context getMainContext() {
        return mContext;
    }

    public static Context getGainContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("isprocess", "onCreate: 1");
        if (isProcess()) {
            Log.d("isprocess", "onCreate: 2");
            if (mAppDelegate != null) {
                this.mAppDelegate.onCreate(this);
            }
            mContext = this.getApplicationContext();
            //打开串口
            serialPortUtils = new SerialPortUtils();
            serialPortUtils.openSerialPort(AppConstant.SerialPort.MACHINE_PORT, AppConstant.SerialPort.MACHINE_BAUDRTE);
        } else {
            mGainContext = this.getApplicationContext();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (isProcess()) {
            if (mAppDelegate != null) {
                this.mAppDelegate.onTerminate(this);
            }
            //关闭串口
            serialPortUtils.closeSerialPort();
        }
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        com.jess.arms.utils.Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate.class.getName());
        Preconditions.checkState(mAppDelegate instanceof App, "%s must be implements %s", AppDelegate.class.getName(), App.class.getName());
        return ((App) mAppDelegate).getAppComponent();
    }

    public boolean isProcess() {
        String processName = getProcessName(getApplicationContext(), android.os.Process.myPid());
        if (processName != null) {
            return processName.equals("com.lianxi.dingtu.dingtu_plate");
        }
        return false;
    }


    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

}
