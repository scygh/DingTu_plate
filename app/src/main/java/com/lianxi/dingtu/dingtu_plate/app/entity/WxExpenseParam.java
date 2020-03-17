package com.lianxi.dingtu.dingtu_plate.app.entity;

import java.io.Serializable;
import java.util.List;

public class WxExpenseParam implements Serializable {

    /**
     {
     "FaceCode": "string",
     "OpenId": "string",
     "ListGoods": [
     {
     "ID": "00000000-0000-0000-0000-000000000000",
     "EID": "00000000-0000-0000-0000-000000000000",
     "GoodsNo": 0,
     "OrderNo": 0,
     "GoodsName": "string",
     "Price": 0,
     "Amount": 0,
     "Count": 0,
     "CreateTime": "2019-08-15T02:27:49.755Z"
     }
     ],
     "Number": 0,
     "Amount": 0,
     "Pattern": 1,
     "PayCount": 0,
     "PayKey": "string",
     "DeviceID": 0,
     "DeviceType": 2
     }
     */

    private String FaceCode;
    private String TradeNo;
    private String OpenId;
    private int Number;
    private double Amount;
    private int Pattern;
    private int PayCount;
    private String PayKey;
    private int DeviceID;
    private int DeviceType;
    private List<ListGoodsBean> ListGoods;

    public String getTradeNo() {
        return TradeNo;
    }

    public void setTradeNo(String tradeNo) {
        TradeNo = tradeNo;
    }

    public String getOpenId() {
        return OpenId;
    }

    public void setOpenId(String OpenId) {
        this.OpenId = OpenId;
    }

    public String getFaceCode() {
        return FaceCode;
    }

    public void setFaceCode(String FaceCode) {
        this.FaceCode = FaceCode;
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
