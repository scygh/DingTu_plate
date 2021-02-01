package com.lianxi.dingtu.dingtu_plate.app.Utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.jb.sdk.aidl.JBPrinterConnectCallback;
import com.jb.sdk.aidl.JBPrinterRealStatusCallback;
import com.jb.sdk.aidl.JBService;
import com.jb.sdk.command.ReceiptCommand;
import com.jb.sdk.io.GpDevice;
import com.jb.sdk.io.GpPort;
import com.jb.sdk.io.PortParameter;
import com.jb.sdk.service.JbPrintService;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import static android.content.Context.BIND_AUTO_CREATE;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxfacepayUtil.showToast;

/**
 * description ：USB打印机工具类
 * * author : scy
 * email : 1797484636@qq.com
 * date : 2020/5/15 09:23
 */
public class GainschaUsbUtils {

    private Context context;
    private String deviceName;
    private static final String TAG = "ServiceConnection";
    private JBService mService;
    private int mPrinterId = 1;
    private static final int REQUEST_TOAST_PRINTER_STATUS = 1;
    private OnGainSchaUsbListener onGainSchaUsbListener;

    public interface OnGainSchaUsbListener {
        void onConnect(String deviceConnect);//连接状态

        void onStatus(String deviceStatus);//设备状态

        void onCommandTypes(String deviceType);//命令模式
    }

    public void setOnGainSchaUsbListener(OnGainSchaUsbListener onGainSchaUsbListener) {
        this.onGainSchaUsbListener = onGainSchaUsbListener;
    }


    public GainschaUsbUtils(Context context) {
        this.context = context;
        deviceName = getUsbDevices();
        /* 绑定服务，绑定成功后调用ServiceConnection中的onServiceConnected方法 */
        Intent intent = new Intent(context, JbPrintService.class);
        context.bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    /**
     * 获取USB打印机的名字
     *
     * @return 返回的打印设备的名字， noDevices：没有获取到任何打印设备
     */
    private String getUsbDevices() {
        String usbname = "";
        UsbManager manager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> devices = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = devices.values().iterator();
        int count = devices.size();
        Log.d(TAG, "usb设备数: " + count);
        if (count > 0) {
            while (deviceIterator.hasNext()) {
                UsbDevice device = deviceIterator.next();
                String devicename = device.getDeviceName();
                Log.d(TAG, "usb设备: " + devicename);
                if (checkUsbDevicePidVid(device)) {
                    usbname = devicename;
                    Log.d(TAG, "找到usb打印设备: " + usbname);
                }
            }
        } else {
            usbname = "noDevices";
        }
        return usbname;
    }

    /**
     * 判断是否是USB打印机
     *
     * @param dev
     * @return
     */
    private boolean checkUsbDevicePidVid(UsbDevice dev) {
        int pid = dev.getProductId();
        int vid = dev.getVendorId();
        boolean rel = false;
        if ((vid == 34918 && pid == 256) || (vid == 1137 && pid == 85) || (vid == 6790 && pid == 30084) || (vid == 26728 && pid == 256) || (vid == 26728 && pid == 512) || (vid == 26728 && pid == 256) || (vid == 26728 && pid == 768) || (vid == 26728 && pid == 1024) || (vid == 26728 && pid == 1280) || (vid == 26728 && pid == 1536)) {
            rel = true;
        }
        return rel;
    }

    /**
     * descirption: 获取AIDL接口类型对象，注册接口返回
     */
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "========onServiceConnected=======");
            mService = JBService.Stub.asInterface(service);
            try {
                mService.registerConnectCallback(new GainschaUsbUtils.ConnectCallback());
                mService.registerPrinterStatusCallback(new GainschaUsbUtils.QueryPrinterRealStatus());
                Log.d(TAG, "绑定服务成功了");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "========onServiceDisconnected=======");
            mService = null;
        }
    };

    /**
     * 打印机连接回调
     */
    public class ConnectCallback extends JBPrinterConnectCallback.Stub {

        @Override
        public void onConnecting(final int mId) throws RemoteException {
            //正在连接
            if (onGainSchaUsbListener != null) {
                onGainSchaUsbListener.onConnect(getStatus(mId));
            }
        }

        @Override
        public void onDisconnect(final int mId) throws RemoteException {
            //已断开
            if (onGainSchaUsbListener != null) {
                onGainSchaUsbListener.onConnect(getStatus(mId));
            }
        }

        @Override
        public void onConnected(final int mId) throws RemoteException {
            //已连接
            if (onGainSchaUsbListener != null) {
                onGainSchaUsbListener.onConnect(getStatus(mId));
            }
        }

//        @Override
//        public IBinder asBinder() {
//            return null;
//        }
    }

    /**
     * 打印机实时状态查询回调
     */
    public class QueryPrinterRealStatus extends JBPrinterRealStatusCallback.Stub {

        @Override
        public void onPrinterRealStatus(int mId, int status, int requestCode) throws RemoteException {
            switch (requestCode) {//这个回调方法应该是在打印的时候判断打印机的状态的时候回调
                case REQUEST_TOAST_PRINTER_STATUS:
                    showPrinterStatus(mId, status);
                    break;
            }
        }

//        @Override
//        public IBinder asBinder() {
//            return null;
//        }
    }

    /**
     * 显示打印机的实时状态
     *
     * @param id
     * @param status
     */
    private void showPrinterStatus(final int id, int status) {
        String str;
        if (status == GpDevice.STATE_NO_ERR) {
            str = "打印机正常";
        } else {
            str = "打印机 ";
            if ((byte) (status & GpDevice.STATE_OFFLINE) > 0) {
                str += "脱机";
            }
            if ((byte) (status & GpDevice.STATE_PAPER_ERR) > 0) {
                str += "缺纸";
            }
            if ((byte) (status & GpDevice.STATE_COVER_OPEN) > 0) {
                str += "打印机开盖";
            }
            if ((byte) (status & GpDevice.STATE_ERR_OCCURS) > 0) {
                str += "打印机出错";
            }
            if ((byte) (status & GpDevice.STATE_TIMES_OUT) > 0) {
                str += "查询超时";
            }
        }
        final String statusStr = str;
        if (onGainSchaUsbListener != null) {
            onGainSchaUsbListener.onStatus(statusStr);
        }
    }

    /**
     * descirption: 根据id获取打印机连接状态
     */
    private String getStatus(int id) {
        if (mService == null) {
            return "未连接";
        }
        int state = GpPort.STATE_NONE;
        try {
            state = mService.getPrinterConnectStatus(id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        String str;
        if (state == GpPort.STATE_CONNECTED) {
            str = "已连接";
        } else if (state == GpPort.STATE_CONNECTING) {
            str = "链接中";
        } else {
            str = "未连接";
        }
        return str;
    }

    /**
     * 获取连接状态,Toast显示
     */
    public void getPrinterStatus() {
        try {
            int connectStatus = mService.getPrinterConnectStatus(mPrinterId);

            if (connectStatus == GpPort.STATE_CONNECTED) {
                Log.d(TAG, "已连接");
                msg("已连接");
            } else if (connectStatus == GpPort.STATE_CONNECTING) {
                Log.d(TAG, "连接中");
                msg("连接中");
            } else if (connectStatus == GpPort.STATE_NONE) {
                Log.d(TAG, "未连接");
                msg("未连接");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印toast信息
     *
     * @param m
     */
    private void msg(String m) {
        Toast.makeText(context, m, Toast.LENGTH_SHORT).show();
    }

    /**
     * 打开连接
     */
    public void openConnect() {
        //id为打印服务操作的打印机的id，最大可以操作3台
        try {
            Log.d(TAG, "openConnect: " + deviceName);
            mService.openPort(mPrinterId, PortParameter.USB, deviceName, 0);//打开连接后ConnectCallback会调用回调方法
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印测试页
     */
    public void printTestPaper() {
        try {
            mService.printeTestPage(mPrinterId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印
     */
    public void sendReceipt(List<EMGoodsTo.RowsBean.GoodsBean> data, Context context) {
        String pName = SpUtils.get(context, AppConstant.Receipt.NAME, "").toString();
        String pAddress = SpUtils.get(context, AppConstant.Receipt.ADDRESS, "").toString();
        String phone = SpUtils.get(context, AppConstant.Receipt.PHONE, "").toString();
        if (pName == null || pAddress == null || phone == null || pName == "" || pAddress == "" || phone == "") {
            showToast("小票信息不完整，请到设置中完善");
        }
        ReceiptCommand esc = new ReceiptCommand();
        esc.addInitializePrinter();
        esc.addPrintAndFeedLines((byte) 3);
        esc.addSelectJustification(ReceiptCommand.JUSTIFICATION.CENTER);// 设置打印居中
        esc.addSelectPrintModes(ReceiptCommand.FONT.FONTA, ReceiptCommand.ENABLE.OFF, ReceiptCommand.ENABLE.ON, ReceiptCommand.ENABLE.ON, ReceiptCommand.ENABLE.OFF);// 设置为倍高倍宽
        esc.addText(pName + "\n"); // 打印文字
        esc.addPrintAndLineFeed();

        /* 打印文字 */
        esc.addSelectPrintModes(ReceiptCommand.FONT.FONTA, ReceiptCommand.ENABLE.OFF, ReceiptCommand.ENABLE.OFF, ReceiptCommand.ENABLE.OFF, ReceiptCommand.ENABLE.OFF);// 取消倍高倍宽
        esc.addSelectJustification(ReceiptCommand.JUSTIFICATION.LEFT);// 设置打印左对齐
        esc.addText("欢迎光临\n"); // 打印文字
        esc.addText("Welcome to our shop!\n"); // 打印文字
        esc.addPrintAndLineFeed();

        /* *//* 打印繁体中文 需要打印机支持繁体字库 *//*
        String message = "佳博智匯票據打印機\n";
        // esc.addText(message,"BIG5");
        esc.addText(message, "GB2312");
        esc.addPrintAndLineFeed();*/
        esc.addText("名称");
        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
        esc.addSetAbsolutePrintPosition((short) 6);
        esc.addText("价格");
        esc.addSetAbsolutePrintPosition((short) 10);
        esc.addText("数量\n");
        esc.addText("-------------------------------\n");

        double sum = 0.0;

        /* 绝对位置 具体详细信息请查看GP58编程手册 */
        for (int i = 0; i < data.size(); i++) {
            esc.addText(data.get(i).getGoodsName());
            esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
            esc.addSetAbsolutePrintPosition((short) 6);
            esc.addText(data.get(i).getPrice() + "元");
            esc.addSetAbsolutePrintPosition((short) 10);
            esc.addText("x" + data.get(i).getCount() + "\n");
            sum += data.get(i).getPrice() * data.get(i).getCount();
        }

        esc.addText("-------------------------------\n");
        esc.addPrintAndLineFeed();

        esc.addText("小计");
        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
        esc.addSetAbsolutePrintPosition((short) 6);
        esc.addText(sum + "元");

        esc.addPrintAndLineFeed();
        /* 打印一维条码 */
        //esc.addText("Print code128\n"); // 打印文字
        esc.addSelectPrintingPositionForHRICharacters(ReceiptCommand.HRI_POSITION.BELOW);//
        // 设置条码可识别字符位置在条码下方
        esc.addSetBarcodeHeight((byte) 60); // 设置条码高度为60点
        esc.addSetBarcodeWidth((byte) 1); // 设置条码单元宽度为1
        esc.addCODE128(esc.genCodeB("123456")); // 打印Code128码
        esc.addPrintAndLineFeed();

        /* 打印文字 */
        esc.addSelectPrintModes(ReceiptCommand.FONT.FONTA, ReceiptCommand.ENABLE.OFF, ReceiptCommand.ENABLE.OFF, ReceiptCommand.ENABLE.OFF, ReceiptCommand.ENABLE.OFF);// 取消倍高倍宽
        esc.addSelectJustification(ReceiptCommand.JUSTIFICATION.LEFT);// 设置打印左对齐
        esc.addText("地址：" + pAddress + "\n"); // 打印文字
        esc.addText("电话：" + phone + "\n"); // 打印文字
        esc.addPrintAndLineFeed();

        /*
         * QRCode命令打印 此命令只在支持QRCode命令打印的机型才能使用。 在不支持二维码指令打印的机型上，则需要发送二维条码图片
         */
        /*esc.addText("Print QRcode\n"); // 打印文字
        esc.addSelectErrorCorrectionLevelForQRCode((byte) 0x31); // 设置纠错等级
        esc.addSelectSizeOfModuleForQRCode((byte) 3);// 设置qrcode模块大小
        esc.addStoreQRCodeData("www.smarnet.cc");// 设置qrcode内容
        esc.addPrintQRCode();// 打印QRCode
        esc.addPrintAndLineFeed();*/

        /* 打印文字 */
        //esc.addSelectJustification(ReceiptCommand.JUSTIFICATION.CENTER);// 设置打印左对齐
        //esc.addText("Completed!\r\n"); // 打印结束
        // 开钱箱
        esc.addGeneratePlus(ReceiptCommand.FOOT.F5, (byte) 255, (byte) 255);
        esc.addPrintAndFeedLines((byte) 8);

        Vector<Byte> datas = esc.getCommand(); // 发送数据
        byte[] bytes = toPrimitives(datas.toArray(new Byte[datas.size()]));
        String sss = Base64.encodeToString(bytes, Base64.DEFAULT);
        try {
            mService.sendReceiptCommand(mPrinterId, bytes);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private byte[] toPrimitives(Byte[] oBytes) {
        byte[] bytes = new byte[oBytes.length];

        for (int i = 0; i < oBytes.length; i++) {
            bytes[i] = oBytes[i];
        }
        return bytes;
    }

    /**
     * 关闭连接
     */
    public void closeConnect() {
        try {
            mService.closePort(mPrinterId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取命令类型
     */
    public void getCommandTypes() {
        try {
            final int type = mService.getPrinterCommandType(mPrinterId);
            if (onGainSchaUsbListener != null) {
                onGainSchaUsbListener.onCommandTypes(type == GpDevice.UNKNOWN_COMMAND ? "未知类型" :
                        (type == GpDevice.RECEIPT_COMMAND ? "票据模式" :
                                "标签模式"));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * descirption: 释放资源
     */
    public void release() {
        if (mService != null) {
            closeConnect();
        }
        context.unbindService(mServiceConnection);//解绑服务
    }

}
