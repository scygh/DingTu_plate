package android_serialport_api;


import android.os.Handler;
import android.util.Log;

import com.lianxi.dingtu.dingtu_plate.app.base.MainApplication;
import com.lianxi.dingtu.dingtu_plate.app.entity.PlateCardInfo;


/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2019/10/31 16:39
 */
public class SerialPortApi {

    public static final String TAG = "SerialPortApi";
    public static final String OPENRF_FIELD = "02 05 01 aa 03";
    public static final String OPENRF_FIELD_OK = "0205010603";
    public static final String HINT_OPENRF_FIELD_OK = "射频场已开启";
    public static final String CLOSERF_FIELD = "02 05 02 aa 03";
    public static final String CLOSERF_FIELD_OK = "0205020503";
    public static final String HINT_CLOSERF_FIELD_OK = "射频场已关闭";
    public static final String READCARD_PATTERN = "02 06 03 00 aa 03";
    public static final String READCARD_PATTERN_OK = "0205030403";
    public static final String HINI_READCARD_PATTERN_OK = "进入连续读三块读卡模式";
    public static final String READCARDUID_PATTERN = "02 05 05 aa 03";
    public static final String READCARDUID_PATTERN_OK = "0205050203";
    public static final String HINI_READCARDUID_PATTERN_OK = "进入读UID模式";
    public static final String PX_PATTERN = "02 05 10 aa 03";
    public static final String PX_PATTERN_OK = "0205101703";
    public static final String HINT_PX_PATTERN_OK = "进入盘询模式";
    public static final String SINGLE_RESPONSE_PATTERN = "02 05 11 aa 03";
    public static final String SINGLE_RESPONSE_PATTERN_OK = "0205111603";
    public static final String HINT_SINGLE_RESPONSE_PATTERN_OK = "进入单次响应模式";
    public static final String WRITE_CARD_OK = "0205040303";
    public static final String HINI_WRITE_CARD_OK = "写盘模式";
    public static final String HINI_CHECK_FAIL = "校验失败";
    public static final String HAVECARD = "有卡";
    public static final String NOCARD = "无卡";
    public static StringBuilder stringBuilder = new StringBuilder();
    public static SerialPortApi serialPortApi;
    public static SerialPortResponse response;
    private static PlateCardInfo cardInfo;

    public void setResponse(SerialPortResponse response) {
        this.response = response;
    }

    public void removeResponse() {
        this.response = null;
    }

    public interface SerialPortResponse {
        void onGetUpTo(String card, PlateCardInfo info);//读到卡片

        void onCheckFail();//校验失败

        void onOperatorSuccess(String msg);//操作成功

    }

    public static SerialPortApi getInstance() {
        serialPortApi = new SerialPortApi();
        return serialPortApi;
    }

    /**
     * descirption: 监听串口接收
     */
    public static void initPort() {
        MainApplication.getSerialPortUtils().setOnDataReceiveListener(new SerialPortUtils.OnDataReceiveListener() {
            @Override
            public void onDataReceive(String hexStr) {
                stringBuilder.append(hexStr);
                if (stringBuilder.toString().contains("020E0400") || stringBuilder.toString().contains("020E0500")) {//写盘
                    if (stringBuilder.length() == 28) {//直到读全开始校验
                        if (getXor(ChangeTool.HexToByteArr(stringBuilder.toString())) == 0) {
                            Log.d(TAG, "卡片" + stringBuilder.toString().substring(8, 24));
                            if (response != null) {
                                cardInfo = new PlateCardInfo(stringBuilder.toString().substring(8, 24));
                                response.onGetUpTo(stringBuilder.toString().substring(8, 24), cardInfo);
                                if (stringBuilder.toString().contains("020E0500")) {
                                    response.onOperatorSuccess(HAVECARD);
                                }
                            }
                        } else {
                            Log.d(TAG, HINI_CHECK_FAIL);
                            if (response != null) {
                                response.onCheckFail();
                            }
                        }
                        stringBuilder.delete(0, stringBuilder.length());
                    }
                } else if (stringBuilder.toString().contains("021A0300")) {//读盘
                    if (stringBuilder.length() == 52) {//直到读全开始校验
                        if (getXor(ChangeTool.HexToByteArr(stringBuilder.toString())) == 0) {
                            String hs = stringBuilder.toString().substring(8, 46);
                            Log.d(TAG, "发现卡片" + hs);
                            if (response != null) {
                                cardInfo = new PlateCardInfo(hs.substring(0, 16), ChangeTool.HexToInt(hs.substring(16, 20)) + ""
                                        , ChangeTool.HexToInt(hs.substring(20, 24)) + "", ChangeTool.HexToInt(hs.substring(24, 30)) / 100.0, hs.substring(30, 38));
                                response.onGetUpTo(stringBuilder.toString().substring(8, 46), cardInfo);
                            }
                        } else {
                            Log.d(TAG, HINI_CHECK_FAIL);
                            if (response != null) {
                                response.onCheckFail();
                            }
                        }
                        stringBuilder.delete(0, stringBuilder.length());
                    }
                } else if (stringBuilder.toString().contains("020605")) {//无卡
                    if (stringBuilder.length() == 12) {//直到读全开始校验
                        if (getXor(ChangeTool.HexToByteArr(stringBuilder.toString())) == 0) {
                            if (response != null) {
                                response.onOperatorSuccess(NOCARD);
                            }
                        } else {
                            Log.d(TAG, HINI_CHECK_FAIL);
                            if (response != null) {
                                response.onCheckFail();
                            }
                        }
                        stringBuilder.delete(0, stringBuilder.length());
                    }
                } else {
                    if (stringBuilder.length() == 10) {//直到读全开始校验
                        if (getXor(ChangeTool.HexToByteArr(stringBuilder.toString())) == 0) {
                            if (stringBuilder.toString().equals(OPENRF_FIELD_OK)) {
                                Log.d(TAG, HINT_OPENRF_FIELD_OK);
                                if (response != null) {
                                    response.onOperatorSuccess(HINT_OPENRF_FIELD_OK);
                                }
                            } else if (stringBuilder.toString().equals(CLOSERF_FIELD_OK)) {
                                Log.d(TAG, HINT_CLOSERF_FIELD_OK);
                                if (response != null) {
                                    response.onOperatorSuccess(HINT_CLOSERF_FIELD_OK);
                                }
                            } else if (stringBuilder.toString().equals(READCARD_PATTERN_OK)) {
                                Log.d(TAG, HINI_READCARD_PATTERN_OK);
                                if (response != null) {
                                    response.onOperatorSuccess(HINI_READCARD_PATTERN_OK);
                                }
                            } else if (stringBuilder.toString().equals(PX_PATTERN_OK)) {
                                Log.d(TAG, HINT_PX_PATTERN_OK);
                                if (response != null) {
                                    response.onOperatorSuccess(HINT_PX_PATTERN_OK);
                                }
                            } else if (stringBuilder.toString().equals(WRITE_CARD_OK)) {
                                Log.d(TAG, HINI_WRITE_CARD_OK);
                                if (response != null) {
                                    response.onOperatorSuccess(HINI_WRITE_CARD_OK);
                                }
                            } else if (stringBuilder.toString().equals(SINGLE_RESPONSE_PATTERN_OK)) {
                                Log.d(TAG, HINT_SINGLE_RESPONSE_PATTERN_OK);
                                if (response != null) {
                                    response.onOperatorSuccess(HINT_SINGLE_RESPONSE_PATTERN_OK);
                                }
                            } else if (stringBuilder.toString().equals(READCARDUID_PATTERN_OK)) {
                                Log.d(TAG, HINI_READCARDUID_PATTERN_OK);
                                if (response != null) {
                                    response.onOperatorSuccess(HINI_READCARDUID_PATTERN_OK);
                                }
                            }
                        } else {
                            Log.d(TAG, HINI_CHECK_FAIL);
                            if (response != null) {
                                response.onCheckFail();
                            }
                        }
                        stringBuilder.delete(0, stringBuilder.length());
                    }
                }
            }
        });
    }

    public static void clearSb() {
        stringBuilder.delete(0, stringBuilder.length());
    }

    /**
     * descirption: 打开射频场命令
     */
    public static void openRF_field() {
        MainApplication.getSerialPortUtils().sendSerialPort(OPENRF_FIELD);
    }

    /**
     * descirption: 关闭射频场命令
     */
    public static void closeRF_field() {
        MainApplication.getSerialPortUtils().sendSerialPort(CLOSERF_FIELD);
    }

    /**
     * descirption: 切换成盘询模式
     */
    public static void PxPattern() {
        MainApplication.getSerialPortUtils().sendSerialPort(PX_PATTERN);
    }

    /**
     * descirption: 进入连续读三个块命令读卡模式
     */
    public static void gotoReadCardPattern() {
        MainApplication.getSerialPortUtils().sendSerialPort(READCARD_PATTERN);
    }

    /**
     * descirption: 进入读UID模式
     */
    public static void gotoReadCardUIDPattern() {
        MainApplication.getSerialPortUtils().sendSerialPort(READCARDUID_PATTERN);
    }

    /**
     * descirption: 进入连续写三个块命令写卡
     * num 商品编号
     */
    public static void gotoClearCardPattern(int num) {
        MainApplication.getSerialPortUtils().sendSerialPort("02120400" + ChangeTool.numToHex2(num) + "00000000000000000000 0003");
    }

    /**
     * descirption: 进入连续写三个块命令写卡
     * num 商品编号
     */
    public static void gotoWriteCardPattern(int num, int companyCode, int price, String date) {
        MainApplication.getSerialPortUtils().sendSerialPort("02120400" + ChangeTool.numToHex2(num)
                + ChangeTool.numToHex2(companyCode) + ChangeTool.numToHex3(price) + date + "000003");
    }

    /**
     * descirption: 校验
     */
    public static byte getXor(byte[] datas) {
        byte temp = datas[0];
        for (int i = 1; i < datas.length - 1; i++) {
            temp ^= datas[i];
        }
        return temp;
    }

    /**
     * descirption: 切换成单次响应模式
     */
    public static void singleResponsePattern() {
        MainApplication.getSerialPortUtils().sendSerialPort(SINGLE_RESPONSE_PATTERN);
    }

    /**
     * descirption: 切换至单次读模式，再开启读UID
     */
    public static void singleResponsePattern(Handler handler) {
        closeRF_field();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                singleResponsePattern();
            }
        }, 300);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoReadCardUIDPattern();
            }
        }, 600);
    }

    /**
    * descirption: 切换至盘询模式再开启读三块盘
    */
    public static void openReadPattern(Handler handler) {
       closeRF_field();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               PxPattern();
            }
        }, 300);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoReadCardPattern();
            }
        }, 600);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openRF_field();
            }
        }, 900);
    }

}
