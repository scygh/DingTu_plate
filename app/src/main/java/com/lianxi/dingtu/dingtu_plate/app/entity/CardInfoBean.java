package com.lianxi.dingtu.dingtu_plate.app.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/31.
 */

public class CardInfoBean implements Serializable {

    private String Error;//错误字典
    private String Code;//单位代码
    private int Num;//卡内码
    private int Type;//卡片类型
    private int Spending_limit;//消费限额
    private double Cash_account;//现金账户
    private String Card_validity;//卡片有效期
    private int Consumption_num;//消费次数
    private String Subsidies_time;//补贴年月
    private String Name;//名称
    private int Level;//补贴级别
    private int Guaranteed_amount;//保底额度
    private double Allowance_account;//补贴账户
    private String Spending_time;//消费时间
    private int Meal_times;//餐次次数
    private int Discount;//折扣率
    private boolean IsDiscount;//是否打折
    /*{"allowance_account":0.0,"card_validity":"20291118","cash_account":167566.34,"code":"0001","consumption_num":57,"discount":100,
    "guaranteed_amount":0,"isDiscount":false,"level":0,"meal_times":0,"name":"测试01\u0000\u0000\u0000","num":125,"spending_limit":0,"" +
    "spending_time":"2002f601","subsidies_time":"201912","type":1}*/

    public boolean getIsDiscount() {
        return IsDiscount;
    }

    public void setIsDiscount(boolean discount) {
        IsDiscount = discount;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }

    public double getCash_account() {
        return Cash_account;
    }

    public void setCash_account(double cash_account) {
        Cash_account = cash_account;
    }

    public double getAllowance_account() {
        return Allowance_account;
    }

    public void setAllowance_account(double allowance_account) {
        Allowance_account = allowance_account;
    }

    public String getCard_validity() {
        return Card_validity;
    }

    public void setCard_validity(String card_validity) {
        Card_validity = card_validity;
    }

    public String getSubsidies_time() {
        return Subsidies_time;
    }

    public void setSubsidies_time(String subsidies_time) {
        Subsidies_time = subsidies_time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public int getSpending_limit() {
        return Spending_limit;
    }

    public void setSpending_limit(int spending_limit) {
        Spending_limit = spending_limit;
    }

    public int getGuaranteed_amount() {
        return Guaranteed_amount;
    }

    public void setGuaranteed_amount(int guaranteed_amount) {
        Guaranteed_amount = guaranteed_amount;
    }

    public String getSpending_time() {
        return Spending_time;
    }

    public void setSpending_time(String spending_time) {
        Spending_time = spending_time;
    }

    public int getConsumption_num() {
        return Consumption_num;
    }

    public void setConsumption_num(int consumption_num) {
        Consumption_num = consumption_num;
    }

    public int getMeal_times() {
        return Meal_times;
    }

    public void setMeal_times(int meal_times) {
        Meal_times = meal_times;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }
}
