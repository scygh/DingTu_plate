package com.lianxi.dingtu.dingtu_plate.app.Utils;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2020/3/17 14:47
 */
public class Scanner {

    private Activity activity;

    public Scanner(Activity activity) {
        this.activity = activity;
    }

    /**
     * 显示的/隐藏的 EditText 获得光标，准备扫码
     */
    public void scan(EditText editText) {
        //获得光标
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();

        //关闭软键盘：防止顺序乱码
        InputMethodManager manager = null;
        manager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //View focus = act.getCurrentFocus();
        manager.hideSoftInputFromWindow(
                //focus == null ? null : focus.getWindowToken(),
                editText == null ? null : editText.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        //增加软键盘监听，扫出来内容会自己填充到editText中去的
        editText.setOnKeyListener(null);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d("canresult", "onKey: " + keyCode + event.getAction());
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (editText.getText().toString().trim().length() == 18) {
                        if (onScanResultCallBack != null) {
                            onScanResultCallBack.OnScanSucccess(editText.getText().toString().trim());//返回结果值，看需要使用了
                        } else {
                            ToastUtils.showShort("请先点击支付");
                        }
                    } else {
                        ToastUtils.showShort("无效二维码");
                    }
                    editText.post(new Runnable() {
                        @Override
                        public void run() {
                            editText.setText("");
                        }
                    });
                    return true;
                }
                return false;
            }
        });
        /*editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("textchange", "afterTextChanged: " + s.toString());
                if (s.toString().length() == 18) {
                    if (onScanResultCallBack != null) {
                        onScanResultCallBack.OnScanSucccess(s.toString());//返回结果值，看需要使用了
                    } else {
                        ToastUtils.showShort("请先点击支付");
                    }
                    editText.post(new Runnable() {
                        @Override
                        public void run() {
                            editText.setText("");
                        }
                    });
                }
            }
        });*/
    }


    /**
     * 扫码枪接口：有timeout设置，当然光标移除的时候关闭计时
     * 成功 返回扫码结果
     * 失败 返回错误信息
     */
    public interface OnScanResultCallBack {
        public void OnScanSucccess(String result);

        public void OnScanFail(String errorMsg);

    }

    public void clearScan() {
        onScanResultCallBack = null;
    }


    private OnScanResultCallBack onScanResultCallBack;

    /**
     * 子类实现回调函数
     */
    public void setOnScanResultCallBack(Scanner.OnScanResultCallBack onScanResultCallBack) {
        if (onScanResultCallBack == null)
            throw new IllegalArgumentException("empty onScanResultCallBack");
        this.onScanResultCallBack = onScanResultCallBack;
    }

}