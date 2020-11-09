package com.lianxi.dingtu.dingtu_plate.app.Utils;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_plate.app.entity.CardInfoBean;
import com.lianxi.dingtu.dingtu_plate.app.listening.USBListening;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android_serialport_api.ChangeTool;

import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_ATTACHED;
import static android.hardware.usb.UsbManager.ACTION_USB_DEVICE_DETACHED;
import static android_serialport_api.SerialPortApi.getXor;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.ByteUtils.SumCheck;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.ByteUtils.bytesToHexString;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.ByteUtils.concatAll;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.ByteUtils.getString;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.ByteUtils.hexStringToBytes;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.ByteUtils.int2byte;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.ByteUtils.intToByte3;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.ByteUtils.str2Bcd;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.ByteUtils.ubyteTo2Bytes;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.ByteUtils.ubyteToBytes;

/**
 * Created by YuanYe on 18/6/21.
 * Description: 封装Usb接口通信的工具类
 * <p>
 * 使用USB设备：
 * 1.添加权限：
 * <uses-feature  android:name="android.hardware.usb.host" android:required="true">
 * </uses-feature>
 * 2.Manifest中添加以下<intent-filter>，获取USB操作的通知：
 * <action android:name="android.hardware.usb.action.USB_DEVICE_ATTACHED" />
 * 3.添加设备过滤信息，气筒usb_xml可以自由修改：
 * <meta-data  android:name="android.hardware.usb.action.USB_DEVICE_ATTACHED"
 * android:resource="@xml/usb_xml"></meta-data>
 * 4.根据目标设备的vendorId和productId过滤USB设备,拿到UsbDevice操作对象
 * 5.获取设备通讯通道
 * 6.连接
 */
public class USBHelper {

    private static final String TAG = "USBDeviceUtil";
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private static USBHelper util;
    private static Context context;
    private UsbDevice usbDevice; //目标USB设备
    private UsbManager usbManager;
    /**
     * 块输出端点
     */
    private UsbEndpoint epBulkOut;
    private UsbEndpoint epBulkIn;
    /**
     * 控制端点
     */
    private UsbEndpoint epControl;
    /**
     * 中断端点
     */
    private UsbEndpoint epIntEndpointOut;
    private UsbEndpoint epIntEndpointIn;
    private PendingIntent intent; //意图
    private UsbDeviceConnection mDeviceConnection = null;
    private int statue = USBInterface.usb_ok;
    UsbInterface mInterface;
    private static volatile USBHelper instance;

    public static final int MSG_FOUND_CARD = 1;
    public static final int MSG_NOFOUND_UID = 2;
    public static final int MSG_FOUND_QR = 3;

    Timer timer;
    TimerTask task;

    private ByteBuffer buffer;
    private static byte[] Receiveytes; // 接收信息字节
    private static int ret = -100;

    public USBListening usbListening = null;

    public void setUsbListening(USBListening usbListening) {
        this.usbListening = usbListening;
    }

    /**
     * descirption: USBHelper 单例对象
     */
    public static USBHelper getInstance(Context _context) {
        if (instance == null) {
            synchronized (USBHelper.class) {
                if (instance == null) {
                    context = _context;
                    instance = new USBHelper();
                }
            }
        }
        return instance;
    }

    /**
     * descirption: USBHelper 在建立之前的初始化操作 请求权限 连接
     */
    public USBHelper() {
        intent = PendingIntent.getBroadcast(context, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(ACTION_USB_DEVICE_DETACHED);
        context.registerReceiver(broadcastReceiver, filter);
        connection();
    }

    /**
     * 根据指定的vendorId和productId连接USB设备
     * <p>
     * vendorId  产商id
     * productId 产品id
     */
    public boolean connection() {
        usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> map = usbManager.getDeviceList();
        // 没有连接设备
        if (map.isEmpty()) {
            Toast.makeText(context, "未找到任何连接设备", Toast.LENGTH_LONG).show();
            return false;
        }
        // 遍历集合取指定的USB设备
        UsbDevice usbDevice = null;
        for (UsbDevice device : map.values()) {
            //不同厂商ID不同 不知道的不要乱填
            int VendorID = device.getVendorId();
            int ProductID = device.getProductId();
            Log.e("usbUtils: ", "VendorID" + VendorID + "==ProductID" + ProductID);
            if (VendorID == 1796 && ProductID == 8213) {
                usbDevice = device;
                break;
            }
        }
        // 没有找到设备
        if (usbDevice == null) {
            Toast.makeText(context, "未找到指定读卡设备", Toast.LENGTH_LONG).show();
            return false;
        }
        Log.e("usbPrint: ", JSON.toJSONString(usbDevice));
        // 程序是否有操作设备的权限
        if (!usbManager.hasPermission(usbDevice)) {
            usbManager.requestPermission(usbDevice, intent);
            return false;
        }
        // 设备接口, 注意设备不同接口不同
        for (int i = 0; i < usbDevice.getInterfaceCount(); ) {
            /**
             * 获取设备接口，一般都是一个接口，你可以打印getInterfaceCount()方法查看接
             * 口的个数，在这个接口上有两个端点，OUT 和 IN
             */
            UsbInterface usbInterface = usbDevice.getInterface(i);
            mInterface = usbInterface;
            break;
        }
        // 打开设备建立连接
        mDeviceConnection = usbManager.openDevice(usbDevice);
        if (mDeviceConnection.claimInterface(mInterface, true)) {
            // 分配端点, 注意设备不同端点不同
            epBulkOut = mInterface.getEndpoint(1);
            epBulkIn = mInterface.getEndpoint(0);
        } else {
            mDeviceConnection.close();
        }
        return true;
    }

    /**
     * descirption: 开启循环去接收信息,处理接收的 卡简单信息，二维码
     */
    public void run() {
        task = new TimerTask() {
            @Override
            public void run() {
                byte[] data = read();
                Log.d("USBReceivedData", "Timer读取到了数据" + bytesToHexString(data));
                if (data[0] == 0x05 && data[1] == 0x06) {
                    if (data[2] == 0x11) {//上报卡信息
                        Message msg = handler.obtainMessage(MSG_FOUND_CARD);
                        msg.obj = data;
                        handler.sendMessage(msg);
                    } else if (data[2] == 0x12) {
                        Message msg = handler.obtainMessage(MSG_FOUND_QR);
                        msg.obj = data;
                        handler.sendMessage(msg);
                    } else if (data[2] != 0x11) {
                        handler.sendEmptyMessage(MSG_NOFOUND_UID);
                    }
                }
            }
        };
        timer = new Timer();
        timer.schedule(task, 0, 100);
    }

    /**
     * 通过USB读取数据
     *
     * @return 返回读取内容
     */
    public byte[] read() {
        buffer = ByteBuffer.allocate(64);
        byte[] data = buffer.array();
        if (mDeviceConnection != null && epBulkIn != null) {
            mDeviceConnection.bulkTransfer(epBulkIn, data, 64, 100);
        }
        return data;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FOUND_CARD:
                    if (usbListening != null) {
                        byte[] data = (byte[]) msg.obj;
                        byte[] bytes = new byte[2];
                        bytes[0] = data[4];
                        bytes[1] = data[5];
                        String code = String.valueOf(Integer.parseInt(bytesToHexString(bytes), 16));//单位代码
                        byte[] byte_number = new byte[3];
                        byte_number[0] = data[6];
                        byte_number[1] = data[7];
                        byte_number[2] = data[8];
                        int number = Integer.parseInt(bytesToHexString(byte_number), 16);//卡内码
                        usbListening.findRFCardListening(code, number);
                    }
                    break;
                case MSG_FOUND_QR:
                    if (usbListening != null) {
                        byte[] data = (byte[]) msg.obj;
                        byte[] bytes = new byte[18];
                        for (int i = 0; i < bytes.length; i++) {
                            bytes[i] = data[i + 4];
                        }
                        String QRcode = new String(bytes);
                        usbListening.findQRListening(QRcode);
                    }
                    break;
            }
        }
    };

    /**
     * descirption: 关闭Timer循环
     */
    public void close_read() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 关闭USB连接，释放资源
     */
    public void close() {
        if (mDeviceConnection != null) { //关闭USB设备
            mDeviceConnection.close();
            mDeviceConnection = null;
        }
        if (context != null && broadcastReceiver != null) {
            context.unregisterReceiver(broadcastReceiver);
        }
        instance = null;
    }

    private boolean breadRead = false;

    public void breakRead() {
        breadRead = true;
    }

    public void notBreakRead() {
        breadRead = false;
    }

    /**
     * descirption: 读456区块数据并封装到对象中
     */
    public CardInfoBean read_card() {
        if (mDeviceConnection == null) return null;
        CardInfoBean cardInfoBean = new CardInfoBean();
        byte[] bt4 = new byte[]{
                (byte) 0x66, (byte) 0x99, (byte) 0x04, (byte) 0x00,
                (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x08
        };
        if (null != mDeviceConnection)
            ret = mDeviceConnection.bulkTransfer(epBulkOut, bt4,
                    bt4.length, 1000);
        if (ret == -1) {
            Log.e("CaptureListening", "通讯失败");
        }
        do {
            Receiveytes = null;
            Receiveytes = new byte[64];
            if (breadRead == true) break;
            ret = mDeviceConnection.bulkTransfer(epBulkIn, Receiveytes,
                    Receiveytes.length, 1000);
            Log.e("USBReceivedData", "循环读四区块 取到的数据: " + bytesToHexString(Receiveytes));
        } while (Receiveytes[0] != 0x05 || Receiveytes[1] != 0x06 || Receiveytes[2] != 0x04);
        Log.d("weizhi", "read_card: 1");
        if (ret == 64) {
            String num = bytesToHexString(Receiveytes).substring(8, 14);
            cardInfoBean.setNum(Integer.parseInt(num, 16)); //卡内码
            cardInfoBean.setName(getString(hexStringToBytes(bytesToHexString(Receiveytes).substring(16, 34))));//名称
            String code = bytesToHexString(Receiveytes).substring(34, 38);
            cardInfoBean.setCode(code);//单位代码
            String level_type = bytesToHexString(Receiveytes).substring(38, 40);
            cardInfoBean.setType(Integer.parseInt(level_type.substring(1), 16));//卡片类型
            cardInfoBean.setLevel(Integer.parseInt(level_type.substring(0, 1), 16));//补贴级别
        }

        byte[] bt5 = new byte[]{
                (byte) 0x66, (byte) 0x99, (byte) 0x04, (byte) 0x00,
                (byte) 0x05, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x09
        };
        ret = mDeviceConnection.bulkTransfer(epBulkOut, bt5,
                bt5.length, 1000);
        do {
            Receiveytes = null;
            Receiveytes = new byte[64];
            if (breadRead == true) break;
            ret = mDeviceConnection.bulkTransfer(epBulkIn, Receiveytes,
                    Receiveytes.length, 1000);
            Log.e("USBReceivedData", "循环读五区块 取到的数据: " + bytesToHexString(Receiveytes));
        } while (Receiveytes[0] != 0x05 || Receiveytes[1] != 0x06 || Receiveytes[2] != 0x04);
        Log.d("weizhi", "read_card: 2");
        if (ret == 64) {
            String cash_account = bytesToHexString(Receiveytes).substring(8, 14);
            int int_cash_account = Integer.parseInt(cash_account, 16);
            Double double_cash_account = (double) int_cash_account / 100;
            Log.e("CaptureListening", "double_cash_account: " + int_cash_account);

            cardInfoBean.setCash_account(double_cash_account);//现金账户

            String allowance_account = bytesToHexString(Receiveytes).substring(16, 22);
            int int_allowance_account = Integer.parseInt(allowance_account, 16);
            Double double_allowance_account = (double) int_allowance_account / 100;
            cardInfoBean.setAllowance_account(double_allowance_account);//补贴账户

            String meal_times = bytesToHexString(Receiveytes).substring(30, 32);
            cardInfoBean.setMeal_times(Integer.parseInt(meal_times, 16));//餐次次数

            String consumption_num = bytesToHexString(Receiveytes).substring(34, 38);
            cardInfoBean.setConsumption_num(Integer.parseInt(consumption_num, 16));//消费次数

            String spending_time = bytesToHexString(Receiveytes).substring(24, 30);
            spending_time = "20" + spending_time;
            cardInfoBean.setSpending_time(spending_time);//消费时间
        }


        byte[] bt6 = new byte[]{
                (byte) 0x66, (byte) 0x99, (byte) 0x04, (byte) 0x00,
                (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x0a
        };
        ret = mDeviceConnection.bulkTransfer(epBulkOut, bt6,
                bt6.length, 1000);
        do {
            Receiveytes = null;
            Receiveytes = new byte[64];
            if (breadRead == true) break;
            ret = mDeviceConnection.bulkTransfer(epBulkIn, Receiveytes,
                    Receiveytes.length, 1000);
            Log.e("USBReceivedData", "循环读六区块 取到的数据 " + bytesToHexString(Receiveytes));
        } while (Receiveytes[0] != 0x05 || Receiveytes[1] != 0x06 || Receiveytes[2] != 0x04);
        Log.d("weizhi", "read_card: 3");
        if (ret == 64) {
            String spending_limit = bytesToHexString(Receiveytes).substring(8, 14);
            cardInfoBean.setSpending_limit(Integer.parseInt(spending_limit, 16) / 100);//消费限额
            String guaranteed_amount = bytesToHexString(Receiveytes).substring(14, 18);
            cardInfoBean.setGuaranteed_amount(Integer.parseInt(guaranteed_amount, 16));//保底额度
            String card_validity = bytesToHexString(Receiveytes).substring(18, 24);
            card_validity = "20" + card_validity;
            cardInfoBean.setCard_validity(card_validity);//卡片有效期
            String subsidies_time = bytesToHexString(Receiveytes).substring(24, 28);
            subsidies_time = "20" + subsidies_time;
            cardInfoBean.setSubsidies_time(subsidies_time);//补贴年月
            String isdiscount = bytesToHexString(Receiveytes).substring(28, 30);
            byte a = (byte) Integer.parseInt(isdiscount, 16);
            boolean b;
            b = (a == 0x00) ? false : true;
            cardInfoBean.setIsDiscount(b);//是否打折
            String discount = bytesToHexString(Receiveytes).substring(30, 32);
            cardInfoBean.setDiscount(Integer.parseInt(discount, 16));//折扣率
        }
        if (breadRead) return null;
        return cardInfoBean;
    }

    private short SECOND = 0x00;
    private short THRID = 0x00;
    private short FOURTH = 0x00;
    private short FIFTH = 0x00;
    private short SIXTH = 0x00;
    private short SEVENTH = 0x00;
    private short EIGHTH = 0x00;
    private short NINTH = 0x00;
    private short TENTH = 0x00;
    private short ELEVENTH = 0x00;
    private short TWELFTH = 0x00;
    private short THIRTEENTH = 0x00;
    private short FOURTEENTH = 0x00;
    private short FIFTEENTH = 0x00;
    private short SIXTEENTH = 0x00;
    private short SEVENTEENTH = 0x00;
    private short EIGHTEENTH = 0x00;
    private short NIGHTEENTH = 0x00;
    private short TWENTY = 0x00;

    /**
     * 通过USB发送数据
     */
    public boolean send() {
        if (mDeviceConnection == null || epBulkOut == null) return false;
        byte[] sbyte = {
                (byte) SECOND, (byte) THRID, (byte) FOURTH, (byte) FIFTH,
                (byte) SIXTH, (byte) SEVENTH, (byte) EIGHTH, (byte) NINTH,
                (byte) TENTH, (byte) ELEVENTH, (byte) TWELFTH,
                (byte) THIRTEENTH, (byte) FOURTEENTH, (byte) FIFTEENTH, (byte) SIXTEENTH,
                (byte) SEVENTEENTH, (byte) EIGHTEENTH, (byte) NIGHTEENTH, (byte) TWENTY
        };
        byte[] bt = concatAll(new byte[]{(byte) 0x66, (byte) 0x99}, sbyte, SumCheck(sbyte, 1));
        Log.d("异或校验待发送", "send: " + ChangeTool.ByteArrToHex(bt));
        if (mDeviceConnection.bulkTransfer(epBulkOut, bt, bt.length, 1000) >= 0) {
            //0 或者正数表示成功
            Log.e("异或校验发送完毕", "send: " + bytesToHexString(bt));
            return true;
        } else {
            Log.i(TAG, "发送失败的");
            return false;
        }
    }

    /**
     * 开启主动上报卡信息
     */
    public void AgreementOpen() {
        String nfc_key = (String) SpUtils.get(context, AppConstant.Card.KEY, "");
        byte[] content = hexStringToBytes(nfc_key);
        SECOND = 0x10;
        THRID = 0x00;//密码类型
        FOURTH = 0x01;//扇区
        FIFTH = content[0];
        SIXTH = content[1];
        SEVENTH = content[2];
        EIGHTH = content[3];
        NINTH = content[4];
        TENTH = content[5];
        ELEVENTH = 0x01;
        TWELFTH = 0x00;
        THIRTEENTH = 0x00;
        FOURTEENTH = 0x00;
        FIFTEENTH = 0x00;
        SIXTEENTH = 0x00;
        SEVENTEENTH = 0x00;
        EIGHTEENTH = 0x00;
        NIGHTEENTH = 0x00;
        TWENTY = 0x00;
        send();
    }

    /**
     * 关闭主动上报卡信息
     */
    public void AgreementClose() {
        String nfc_key = (String) SpUtils.get(context, AppConstant.Card.KEY, "");
        byte[] content = hexStringToBytes(nfc_key);
        SECOND = 0x10;
        THRID = 0x00;//密码类型
        FOURTH = 0x01;//扇区
        FIFTH = content[0];
        SIXTH = content[1];
        SEVENTH = content[2];
        EIGHTH = content[3];
        NINTH = content[4];
        TENTH = content[5];
        ELEVENTH = 0x00;
        TWELFTH = 0x00;
        THIRTEENTH = 0x00;
        FOURTEENTH = 0x00;
        FIFTEENTH = 0x00;
        SIXTEENTH = 0x00;
        SEVENTEENTH = 0x00;
        EIGHTEENTH = 0x00;
        NIGHTEENTH = 0x00;
        TWENTY = 0x00;
        send();

    }

    /**
     * descirption: 写回到卡
     */
    public boolean write_card5(CardInfoBean cardInfoBean) {
        int cash = (int) (cardInfoBean.getCash_account() * 100);
        byte[] byte_Cash = intToByte3(cash);
        byte[] head_check_a = SumCheck(byte_Cash, 1);
        byte[] byte_Allowance = intToByte3((int) cardInfoBean.getAllowance_account() * 100);
        byte[] head_check_b = SumCheck(byte_Allowance, 1);
        String Spending_time = cardInfoBean.getSpending_time();
        byte[] byte_Spending_time = str2Bcd(Spending_time.substring(2));
        byte[] byte_d = ubyteToBytes(cardInfoBean.getMeal_times());
        byte[] byte_xfcs = ubyteTo2Bytes(cardInfoBean.getConsumption_num());
        SECOND = 0x05;//写数据
        THRID = 0x00;//密码类型
        FOURTH = 0x05;//扇区
        FIFTH = byte_Cash[0];
        SIXTH = byte_Cash[1];
        SEVENTH = byte_Cash[2];
        EIGHTH = head_check_a[0];
        NINTH = byte_Allowance[0];
        TENTH = byte_Allowance[1];
        ELEVENTH = byte_Allowance[2];
        TWELFTH = head_check_b[0];
        THIRTEENTH = byte_Spending_time[0];
        FOURTEENTH = byte_Spending_time[1];
        FIFTEENTH = byte_Spending_time[2];
        SIXTEENTH = byte_d[0];
        SEVENTEENTH = 0x00;
        EIGHTEENTH = byte_xfcs[0];
        NIGHTEENTH = byte_xfcs[1];
        TWENTY = getXor(int2byte(cardInfoBean.getConsumption_num()));
        //TWENTY = 0x00;
        Log.d("getConsumption_num", "write_card5: " + TWENTY);
        return send();
    }

    /**
     * 蜂鸣指令
     *
     * @return
     */
    public void PeakNoise() {
        SECOND = (byte) 0x02;
        THRID = 0x00;
        FOURTH = 0x00;
        FIFTH = 0x00;
        SIXTH = 0x00;
        SEVENTH = 0x00;
        EIGHTH = 0x00;
        NINTH = 0x00;
        TENTH = 0x00;
        ELEVENTH = 0x00;
        TWELFTH = 0x00;
        THIRTEENTH = 0x00;
        FOURTEENTH = 0x00;
        FIFTEENTH = 0x00;
        SIXTEENTH = 0x00;
        SEVENTEENTH = 0x00;
        EIGHTEENTH = 0x00;
        NIGHTEENTH = 0x00;
        TWENTY = 0x00;
        send();
    }

    public void writeTest() {
        SECOND = (byte) 0x05;
        THRID = 0x00;
        FOURTH = 0x05;
        FIFTH = 0x00;
        SIXTH = 0x30;
        SEVENTH = 0x39;
        EIGHTH = 0x69;
        NINTH = 0x00;
        TENTH = 0x00;
        ELEVENTH = 0x00;
        TWELFTH = 0x00;
        THIRTEENTH = 0x01;
        FOURTEENTH = 0x01;
        FIFTEENTH = 0x01;
        SIXTEENTH = 0x00;
        SEVENTEENTH = 0x00;
        EIGHTEENTH = 0x00;
        NIGHTEENTH = 0x00;
        TWENTY = 0x00;
        send();
    }

    /**
     * descirption: usb权限
     */
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            //call method to set up device communication
                            // 设备接口, 注意设备不同接口不同
                            for (int i = 0; i < device.getInterfaceCount(); ) {
                                /**
                                 * 获取设备接口，一般都是一个接口，你可以打印getInterfaceCount()方法查看接
                                 * 口的个数，在这个接口上有两个端点，OUT 和 IN
                                 */
                                UsbInterface usbInterface = device.getInterface(i);
                                mInterface = usbInterface;
                                break;
                            }
                            // 打开设备建立连接
                            mDeviceConnection = usbManager.openDevice(device);
                            if (mDeviceConnection.claimInterface(mInterface, true)) {
                                // 分配端点, 注意设备不同端点不同
                                epBulkOut = mInterface.getEndpoint(1);
                                epBulkIn = mInterface.getEndpoint(0);
                            } else {
                                mDeviceConnection.close();
                            }
//                            hacking();
                        }
                    }
                }
                statue = USBInterface.usb_permission_ok;
            } else {
                statue = USBInterface.usb_permission_fail;
            }
        }
    };
}
