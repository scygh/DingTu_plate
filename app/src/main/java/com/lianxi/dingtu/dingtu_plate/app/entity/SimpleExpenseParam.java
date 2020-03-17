package com.lianxi.dingtu.dingtu_plate.app.entity;

import java.io.Serializable;
import java.util.List;

public class SimpleExpenseParam implements Serializable {

    /**
     * Number : 101
     * Amount : 1.01
     * Pattern : 0
     * DeviceID : 1
     * PayCount : 1
     * PayKey : string
     */

    private int Number;
    private float Amount;
    private int Pattern;
    private int DeviceID;
    private int PayCount;
    private String PayKey;
    private int DeviceType;

    private List<ListGoodsBean> ListGoods;

    public List<ListGoodsBean> getListGoods() {
        return ListGoods;
    }

    public void setListGoods(List<ListGoodsBean> listGoods) {
        ListGoods = listGoods;
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

    public void setAmount(float Amount) {
        this.Amount = Amount;
    }

    public int getPattern() {
        return Pattern;
    }

    public void setPattern(int Pattern) {
        this.Pattern = Pattern;
    }

    public int getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(int DeviceID) {
        this.DeviceID = DeviceID;
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

    public int getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(int deviceType) {
        DeviceType = deviceType;
    }

    public static class ListGoodsBean {
        /**
         * "Id": "00000000-0000-0000-0000-000000000000",
         *         "Eid": "00000000-0000-0000-0000-000000000000",
         *         "GoodsNo": 0,
         *         "OrderNo": 0,
         *         "GoodsName": "string",
         *         "Price": 0,
         *         "Amount": 0,
         *         "Count": 0,
         *         "CreateTime": "2019-08-19T02:49:30.690Z"
         */

        private String Id;
        private String Eid;
        private int GoodsNo;
        private int OrderNo;
        private String GoodsName;
        private double Price;
        private double Amount;
        private int Count;
        private String CreateTime;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getEid() {
            return Eid;
        }

        public void setEid(String eid) {
            Eid = eid;
        }

        public int getGoodsNo() {
            return GoodsNo;
        }

        public void setGoodsNo(int goodsNo) {
            GoodsNo = goodsNo;
        }

        public int getOrderNo() {
            return OrderNo;
        }

        public void setOrderNo(int orderNo) {
            OrderNo = orderNo;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String goodsName) {
            GoodsName = goodsName;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double price) {
            Price = price;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double amount) {
            Amount = amount;
        }

        public int getCount() {
            return Count;
        }

        public void setCount(int count) {
            Count = count;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }
    }
}
