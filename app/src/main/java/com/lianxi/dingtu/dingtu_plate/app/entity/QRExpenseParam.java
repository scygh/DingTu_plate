package com.lianxi.dingtu.dingtu_plate.app.entity;

import java.io.Serializable;
import java.util.List;

public class QRExpenseParam implements Serializable {

    /**
     * QRContent : 134782713409480847
     * ListGoods : [{"GoodsNo":0,"Count":0}]
     * Number : 0
     * Amount : 0.01
     * Pattern : 1
     * PayCount : 0
     * PayKey : string
     * DeviceID : 1
     * DeviceType : 2
     * QRType : 1
     */

    private String QRContent;
    private int Number;
    private double Amount;
    private int Pattern;
    private int PayCount;
    private String PayKey;
    private int DeviceID;
    private int DeviceType;
    private int QRType;
    private List<ListGoodsBean> ListGoods;

    public String getQRContent() {
        return QRContent;
    }

    public void setQRContent(String QRContent) {
        this.QRContent = QRContent;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }

    public int getPattern() {
        return Pattern;
    }

    public void setPattern(int Pattern) {
        this.Pattern = Pattern;
    }

    public int getPayCount() {
        return PayCount;
    }

    public void setPayCount(int PayCount) {
        this.PayCount = PayCount;
    }

    public String getPayKey() {
        return PayKey;
    }

    public void setPayKey(String PayKey) {
        this.PayKey = PayKey;
    }

    public int getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(int DeviceID) {
        this.DeviceID = DeviceID;
    }

    public int getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(int DeviceType) {
        this.DeviceType = DeviceType;
    }

    public int getQRType() {
        return QRType;
    }

    public void setQRType(int QRType) {
        this.QRType = QRType;
    }

    public List<ListGoodsBean> getListGoods() {
        return ListGoods;
    }

    public void setListGoods(List<ListGoodsBean> ListGoods) {
        this.ListGoods = ListGoods;
    }

    public static class ListGoodsBean {
        /**
         * GoodsNo : 0
         * Count : 0
         */

        private int GoodsNo;
        private int Count;

        public int getGoodsNo() {
            return GoodsNo;
        }

        public void setGoodsNo(int GoodsNo) {
            this.GoodsNo = GoodsNo;
        }

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }
    }
}
