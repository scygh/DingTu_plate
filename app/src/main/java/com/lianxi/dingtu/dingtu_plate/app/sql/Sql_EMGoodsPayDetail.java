package com.lianxi.dingtu.dingtu_plate.app.sql;

import java.util.List;

public class Sql_EMGoodsPayDetail {
    //表名
    public static final String TABLE_NAME = "T_ExpenseDetail";

    //表的各个字段名
    public static final String KEY_ID="id";
    public static final String KEY_NUMBER="number";
    public static final String KEY_DEVICEID="deviceid";
    public static final String KEY_AMOUNT="amount";
    public static final String KEY_TRADEDATETIME="tradedatetime";
    public static final String KEY_OFFLINEPAYCOUNT="offlinepaycount";
    public static final String KEY_UPLOADSUCCESS="uploadsuccess";
    public static final String KEY_PATTERN="pattern";

    public int id;
    public int number;
    public int deviceid;
    public double amount;
    public String tradedatetime;
    public int offlinepaycount;
    //0为fail 1为success
    public int uploadsuccess;
    private int pattern;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(int deviceid) {
        this.deviceid = deviceid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTradedatetime() {
        return tradedatetime;
    }

    public void setTradedatetime(String tradedatetime) {
        this.tradedatetime = tradedatetime;
    }

    public int getOfflinepaycount() {
        return offlinepaycount;
    }

    public void setOfflinepaycount(int offlinepaycount) {
        this.offlinepaycount = offlinepaycount;
    }

    public int getUploadsuccess() {
        return uploadsuccess;
    }

    public void setUploadsuccess(int uploadsuccess) {
        this.uploadsuccess = uploadsuccess;
    }

    public int getPattern() {
        return pattern;
    }

    public void setPattern(int pattern) {
        this.pattern = pattern;
    }
}
