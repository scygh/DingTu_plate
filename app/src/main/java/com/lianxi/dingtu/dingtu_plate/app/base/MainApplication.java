package com.lianxi.dingtu.dingtu_plate.app.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.jess.arms.base.App;
import com.jess.arms.base.delegate.AppDelegate;
import com.jess.arms.base.delegate.AppLifecycles;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.Preconditions;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;

import android_serialport_api.SerialPortUtils;


public class MainApplication extends MultiDexApplication implements App {

    private AppLifecycles mAppDelegate;
    public static Context mContext;
    private static SerialPortUtils serialPortUtils;
    public static SerialPortUtils getSerialPortUtils(){return  serialPortUtils;};

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        if (mAppDelegate==null){
            this.mAppDelegate=new AppDelegate(base);
        }
        this.mAppDelegate.attachBaseContext(base);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this.getApplicationContext();
        if (mAppDelegate != null){
            this.mAppDelegate.onCreate(this);
        }
        //打开串口
        serialPortUtils = new SerialPortUtils();
        serialPortUtils.openSerialPort(AppConstant.SerialPort.MACHINE_PORT,AppConstant.SerialPort.MACHINE_BAUDRTE);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate!=null){
            this.mAppDelegate.onTerminate(this);
        }
        //关闭串口
        serialPortUtils.closeSerialPort();
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        com.jess.arms.utils.Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate.class.getName());
        Preconditions.checkState(mAppDelegate instanceof App, "%s must be implements %s", AppDelegate.class.getName(), App.class.getName());
        return ((App) mAppDelegate).getAppComponent();
    }


}
