package com.lianxi.dingtu.dingtu_plate.app.Utils.wx;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.tencent.wxpayface.WxfacePayCommonCode;

import java.util.Map;

import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.RETURN_CODE;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.RETURN_MSG;
import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

public class WxfacepayUtil {

    public static boolean isSuccessInfo(Map info) {
        if (info == null) {
            showToast("isSuccessInfo: 调用返回为空, 请查看日志");
            new RuntimeException("调用返回为空").printStackTrace();
            return false;
        }
        String code = (String)info.get(RETURN_CODE);
        String msg = (String)info.get(RETURN_MSG);
        Log.e("WXface", "response | getWxpayfaceRawdata " + code + " | " + msg);
        if (code == null || !code.equals(WxfacePayCommonCode.VAL_RSP_PARAMS_SUCCESS)) {
            showToast("isSuccessInfo: 调用返回非成功信息, 请查看日志");
            new RuntimeException("调用返回非成功信息: " + msg).printStackTrace();
            return false;
        }
        Log.e("WXface", "调用返回成功");
        return true;
    }
    public static void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(IdentifysActivity.this, text, Toast.LENGTH_LONG).show();
                Log.e("WXface", "WX: "+text );
            }
        });
    }

    public static void showToast(final Activity context, final String msg) {
        if ("main".equals(Thread.currentThread().getName())) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } else {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
