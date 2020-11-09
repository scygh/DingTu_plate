package com.lianxi.dingtu.dingtu_plate.app.Utils.Printer;

import android.content.Context;

import com.lianxi.dingtu.dingtu_plate.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTo;
import com.printer.command.EscCommand;
import com.printer.command.LabelCommand;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxfacepayUtil.showToast;

public class PrintingTicketsUtil {
    /**
     * 发送票据
     */
    public static void sendReceiptWithResponse(Context context, List<EMGoodsTo.RowsBean.GoodsBean> goodsBeanList, int id, double Balance) {
        String pName = SpUtils.get(context, AppConstant.Receipt.NAME, "").toString();
        String pAddress = SpUtils.get(context, AppConstant.Receipt.ADDRESS, "").toString();
        String phone = SpUtils.get(context, AppConstant.Receipt.PHONE, "").toString();
        if (pName == null || pAddress == null || phone == null || pName == "" || pAddress == "" || phone == "") {
            showToast("小票信息不完整，请到设置中完善");
        }
        EscCommand esc = new EscCommand();
        esc.addInitializePrinter();
        esc.addPrintAndFeedLines((byte) 3);
        /* 设置打印居中 */
        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
        /* 设置为倍高倍宽 */
        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
        /* 打印文字 */
        esc.addText(pName + "\n");
        esc.addPrintAndLineFeed();

        /* 打印文字 */
        /* 取消倍高倍宽 */
        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF, EscCommand.ENABLE.OFF);
        /* 设置打印左对齐 */
        esc.addSelectJustification(EscCommand.JUSTIFICATION.LEFT);
//        /* 打印文字 */
//        esc.addText( "Print text\n" );
//        /* 打印文字 */
//        esc.addText( "Welcome to use our printer!\n" );
//
//        /* 打印繁体中文 需要打印机支持繁体字库 */
//        String message = "票據打印機繁体\n";
//        esc.addText( message, "GB2312" );
//        esc.addPrintAndLineFeed();

        /* 绝对位置 具体详细信息请查看编程手册 */
        esc.addText("名称" + "         " + "数量" + "         " + "单价");
//        esc.addSetHorAndVerMotionUnits((byte) 7, (byte) 0);
//        esc.addSetAbsolutePrintPosition((short) 6);
//        esc.addText("数量");
//        esc.addSetAbsolutePrintPosition((short) 10);
//        esc.addText("单价");
        esc.addPrintAndLineFeed();

        int c = 0;
        Double p = 0.0;
        for (EMGoodsTo.RowsBean.GoodsBean goodsBean : goodsBeanList) {
            String name = goodsBean.getGoodsName();
            String count = goodsBean.getCount() + "";
            for (int i = 0; i < 15 - name.length(); i++) {
                name = name + " ";
            }
            for (int i = 0; i < 23 - count.length(); i++) {
                count = count + " ";
            }
            esc.addText(name + count + goodsBean.getPrice());

            esc.addPrintAndLineFeed();
            p = goodsBean.getPrice() * goodsBean.getCount() + p;
            c = goodsBean.getCount() + c;
        }
        /* 打印图片 */
        /* 打印文字 */
//        esc.addText( "Print bitmap!\n" );
//        Bitmap b = BitmapFactory.decodeResource( getResources(),
//                R.drawable.printer);
//        /* 打印图片 */
//        esc.addRastBitImage( b, 380, 0 );

        /* 打印一维条码 */
        /* 打印文字 */
        esc.addText("--------------------------------\n");

        esc.addText("合计" + "          " + c + "            " + p);

        esc.addPrintAndLineFeed();

        esc.addText("余额：" + Balance + "\n");
        esc.addText("收银员：" + SpUtils.get(context, AppConstant.Api.ACCOUNT, "") + "\n");
        esc.addText("地址：" + pAddress + "\n");
        esc.addText("电话：" + phone + "\n");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        esc.addText("日期：" + simpleDateFormat.format(date) + "\n");
        esc.addPrintAndLineFeed();
//        esc.addSelectPrintingPositionForHRICharacters( EscCommand.HRI_POSITION.BELOW );
        /*
         * 设置条码可识别字符位置在条码下方
         * 设置条码高度为60点
         */
//        esc.addSetBarcodeHeight((byte) 60);
        /* 设置条码单元宽度为1 */
//        esc.addSetBarcodeWidth((byte) 1);
        /* 打印Code128码 */
//        esc.addCODE128(esc.genCodeB("SMARNET"));
//        esc.addPrintAndLineFeed();


        /*
         * QRCode命令打印 此命令只在支持QRCode命令打印的机型才能使用。 在不支持二维码指令打印的机型上，则需要发送二维条码图片
         */
        /* 打印文字 */
//        esc.addText("Print QRcode\n");
//        /* 设置纠错等级 */
//        esc.addSelectErrorCorrectionLevelForQRCode((byte) 0x31);
//        /* 设置qrcode模块大小 */
//        esc.addSelectSizeOfModuleForQRCode((byte) 3);
//        /* 设置qrcode内容 */
//        esc.addStoreQRCodeData("Printer");
//        esc.addPrintQRCode(); /* 打印QRCode */
//        esc.addPrintAndLineFeed();
//
//        /* 设置打印左对齐 */
//        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
//        /* 打印文字 */
//        esc.addText("Completed!\r\n");
//
//        /* 开钱箱 */
        esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
        esc.addPrintAndFeedLines((byte) 8);
        /* 加入查询打印机状态，用于连续打印 */
        byte[] bytes = {29, 114, 1};
        esc.addUserCommand(bytes);
        Vector<Byte> datas = esc.getCommand();
        /* 发送数据 */
        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].sendDataImmediately(datas);
    }

    public static void sendReceiptWithResponse(Context context, String text, int id) {

        EscCommand esc = new EscCommand();
        esc.addInitializePrinter();
        esc.addPrintAndFeedLines((byte) 3);
        /* 设置打印居中 */
        esc.addSelectJustification(EscCommand.JUSTIFICATION.CENTER);
        /* 设置为倍高倍宽 */
        esc.addSelectPrintModes(EscCommand.FONT.FONTA, EscCommand.ENABLE.OFF, EscCommand.ENABLE.ON, EscCommand.ENABLE.ON, EscCommand.ENABLE.OFF);
        /* 打印文字 */
        esc.addText(text + "\n");
        esc.addPrintAndLineFeed();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        esc.addText("日期：" + simpleDateFormat.format(date) + "\n");
        esc.addPrintAndLineFeed();
        esc.addGeneratePlus(LabelCommand.FOOT.F5, (byte) 255, (byte) 255);
        esc.addPrintAndFeedLines((byte) 8);
        /* 加入查询打印机状态，用于连续打印 */
        byte[] bytes = {29, 114, 1};
        esc.addUserCommand(bytes);
        Vector<Byte> datas = esc.getCommand();
        /* 发送数据 */
        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].sendDataImmediately(datas);
    }
}
