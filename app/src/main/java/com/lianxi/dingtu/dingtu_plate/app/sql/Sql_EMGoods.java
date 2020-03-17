package com.lianxi.dingtu.dingtu_plate.app.sql;

public class Sql_EMGoods {
    /**
     * CREATE TABLE [dbo].[T_EMGoods](
     * 	[GoodsNo] [int] NOT NULL,
     * 	[GoodsName] [nvarchar](50) NOT NULL,
     * 	[Price] [decimal](9, 2) NOT NULL,
     * 	[Description] [nvarchar](100) NULL,
     * )
     */

    //表名
    public static final String TABLE_NAME = "T_EMGoods";

    //表的各个字段名
    public static final String KEY_GOODSNO="GoodsNo";
    public static final String KEY_GOODSNAME="GoodsName";
    public static final String KEY_PRICE="Price";
    public static final String KEY_DESCRIPTION="Description";

    public int goodsNo;
    public String goodsName;
    public double price;
    public String description;

    public int getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(int goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
