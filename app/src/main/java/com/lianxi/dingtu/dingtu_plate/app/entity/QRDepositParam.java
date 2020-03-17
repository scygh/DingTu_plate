package com.lianxi.dingtu.dingtu_plate.app.entity;

import java.io.Serializable;

public class QRDepositParam implements Serializable {

    /**
     * QRContent : 134572198865090444
     * Number : 148
     * Amount : 0.01
     */

    private String QRContent;
    private int Number;
    private double Amount;

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
}
