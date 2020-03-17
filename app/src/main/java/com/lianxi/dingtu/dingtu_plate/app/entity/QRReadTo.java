package com.lianxi.dingtu.dingtu_plate.app.entity;

import java.io.Serializable;

public class QRReadTo implements Serializable {

    /**
     * QRType : 3
     * State : 0
     * Number : 100
     * Balance : 1062.13
     * PayCount : 34
     * UserName : 鲁炳
     */

    private int QRType;
    private int State;
    private int Number;
    private double Balance;
    private int PayCount;
    private String UserName;

    public int getQRType() {
        return QRType;
    }

    public void setQRType(int QRType) {
        this.QRType = QRType;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int Number) {
        this.Number = Number;
    }

    public double getBalance() {
        return Balance;
    }

    public void setBalance(double Balance) {
        this.Balance = Balance;
    }

    public int getPayCount() {
        return PayCount;
    }

    public void setPayCount(int PayCount) {
        this.PayCount = PayCount;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
}
