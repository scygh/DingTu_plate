package com.lianxi.dingtu.dingtu_plate.app.Utils;

import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lianxi.dingtu.dingtu_plate.R;
import com.lianxi.dingtu.dingtu_plate.app.base.MainApplication;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2020/12/10 14:11
 */
public class ToastUtlis {

    public static Toast mToast = null;

    /**
     * 弹出Toast
     *
     * @param text 提示的文本
     */
    public static void showToast(String text) {
        try {
            View view = LayoutInflater.from(MainApplication.getMainContext()).inflate(R.layout.custom_toast, null, false);
            TextView textView = view.findViewById(R.id.toast_tv);
            textView.setText(text);
            if (mToast == null) {
                mToast = new Toast(MainApplication.getMainContext());
            } else {
                textView.setText(text);
            }
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setView(view);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        } catch (Exception e) {
            Looper.prepare();
            Toast.makeText(MainApplication.getMainContext(), text, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }

}
