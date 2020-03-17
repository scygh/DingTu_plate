package com.lianxi.dingtu.dingtu_plate.app.entity;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2019/11/11 14:42
 */
public class PlateCardInfo {

    private String cardNum;
    private String goodsNum;
    private String companyCode;
    private double goodsPrice;
    private String date;

    public PlateCardInfo(String cardNum) {
        this.cardNum = cardNum;
    }

    public PlateCardInfo(String cardNum, String goodsNum, String companyCode, double goodsPrice, String date) {
        this.cardNum = cardNum;
        this.goodsNum = goodsNum;
        this.companyCode = companyCode;
        this.goodsPrice = goodsPrice;
        this.date = date;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
