package com.lianxi.dingtu.dingtu_plate.app.entity;

import java.io.Serializable;
import java.util.Date;

public class EMGoodsTo implements Serializable {


    /**
     * Goods : {"GoodsNo":5,"GoodsType":"494fd948-743b-4e51-8ee3-d04e32631757","GoodsName":"豆浆","Price":2,"Total":9999,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":"","GoodsLetter":"Dou Jiang "}
     * ImgCount : 0
     */

    private GoodsBean Goods;
    private int ImgCount;

    public GoodsBean getGoods() {
        return Goods;
    }

    public void setGoods(GoodsBean Goods) {
        this.Goods = Goods;
    }

    public int getImgCount() {
        return ImgCount;
    }

    public void setImgCount(int ImgCount) {
        this.ImgCount = ImgCount;
    }

    public static class GoodsBean {
        /**
         * GoodsNo : 5
         * GoodsType : 494fd948-743b-4e51-8ee3-d04e32631757
         * GoodsName : 豆浆
         * Price : 2
         * Total : 9999
         * GoodsNature : 0
         * PackageDetails : null
         * State : 1
         * Description :
         * GoodsLetter : Dou Jiang
         */
        public static final String TABLE_NAME = "T_GoodsBean";

        //表的各个字段名
        public static final String KEY_GOODSNO="GoodsNo";
        public static final String KEY_GOODSNAME="GoodsName";
        public static final String KEY_PRICE="Price";
        public static final String KEY_COUNT="count";
        public static final String KEY_TIME="time";

        private int GoodsNo;
        private String GoodsType;
        private String GoodsName;
        private double Price;
        private int Total;
        private int GoodsNature;
        private Object PackageDetails;
        private int State;
        private String Description;
        private String GoodsLetter;
        private int count;
        private String time;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getGoodsNo() {
            return GoodsNo;
        }

        public void setGoodsNo(int GoodsNo) {
            this.GoodsNo = GoodsNo;
        }

        public String getGoodsType() {
            return GoodsType;
        }

        public void setGoodsType(String GoodsType) {
            this.GoodsType = GoodsType;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String GoodsName) {
            this.GoodsName = GoodsName;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public int getTotal() {
            return Total;
        }

        public void setTotal(int Total) {
            this.Total = Total;
        }

        public int getGoodsNature() {
            return GoodsNature;
        }

        public void setGoodsNature(int GoodsNature) {
            this.GoodsNature = GoodsNature;
        }

        public Object getPackageDetails() {
            return PackageDetails;
        }

        public void setPackageDetails(Object PackageDetails) {
            this.PackageDetails = PackageDetails;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getGoodsLetter() {
            return GoodsLetter;
        }

        public void setGoodsLetter(String GoodsLetter) {
            this.GoodsLetter = GoodsLetter;
        }
    }
}
