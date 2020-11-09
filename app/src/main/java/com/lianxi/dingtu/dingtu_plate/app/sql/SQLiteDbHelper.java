package com.lianxi.dingtu.dingtu_plate.app.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTo;

import static com.lianxi.dingtu.dingtu_plate.app.sql.Sql_EMGoodsPayDetail.TABLE_NAME;

public class SQLiteDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "dingtu_plate.db";

    public static final int DB_VERSION = 1;


    //创建 CommodityGroup 表的 sql 语句
    private static final String STUDENTS_CREATE_TABLE_EMGODDSPAYDETAIL_SQL = "create table " + Sql_EMGoodsPayDetail.TABLE_NAME + "("
            + Sql_EMGoodsPayDetail.KEY_ID + " INTEGER PRIMARY KEY autoincrement,"
            + Sql_EMGoodsPayDetail.KEY_NUMBER + " INTEGER not null,"
            + Sql_EMGoodsPayDetail.KEY_DEVICEID + " INTEGER not null,"
            + Sql_EMGoodsPayDetail.KEY_PATTERN + " INTEGER not null,"
            + Sql_EMGoodsPayDetail.KEY_AMOUNT + " REAL not null,"
            + Sql_EMGoodsPayDetail.KEY_TRADEDATETIME + " VARCHAR(20) not null,"
            + Sql_EMGoodsPayDetail.KEY_OFFLINEPAYCOUNT + " INTEGER not null,"
            + Sql_EMGoodsPayDetail.KEY_UPLOADSUCCESS + " INTEGER not null"
            + ");";
    private static final String STUDENTS_CREATE_TABLE_EMGODDS_SQL = "create table " + Sql_EMGoods.TABLE_NAME + "("
            + Sql_EMGoods.KEY_GOODSNO + " INTEGER not null,"
            + Sql_EMGoods.KEY_GOODSNAME + " VARCHAR(256) not null,"
            + Sql_EMGoods.KEY_PRICE + " REAL not null,"
            + Sql_EMGoods.KEY_DESCRIPTION + " VARCHAR(256) "
            + ");";
    private static final String STUDENTS_CREATE_TABLE_GOODSBEAN_SQL = "create table " + EMGoodsTo.RowsBean.GoodsBean.TABLE_NAME + "("
            + EMGoodsTo.RowsBean.GoodsBean.KEY_TIME + " VARCHAR(20) not null,"
            + EMGoodsTo.RowsBean.GoodsBean.KEY_GOODSNO + " INTEGER not null,"
            + EMGoodsTo.RowsBean.GoodsBean.KEY_GOODSNAME + " VARCHAR(256) not null,"
            + EMGoodsTo.RowsBean.GoodsBean.KEY_PRICE + " REAL not null,"
            + EMGoodsTo.RowsBean.GoodsBean.KEY_COUNT + " INTEGER not null"
            + ");";

    public SQLiteDbHelper(Context context) {
        // 传递数据库名与版本号给父类
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // 在这里通过 db.execSQL 函数执行 SQL 语句创建所需要的表
        db.execSQL(STUDENTS_CREATE_TABLE_EMGODDSPAYDETAIL_SQL);
        db.execSQL(STUDENTS_CREATE_TABLE_EMGODDS_SQL);
        db.execSQL(STUDENTS_CREATE_TABLE_GOODSBEAN_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // 数据库版本号变更会调用 onUpgrade 函数，在这根据版本号进行升级数据库
        switch (oldVersion) {
            case 1:
                // do something
                break;

            default:
                break;
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // 启动外键
            db.execSQL("PRAGMA foreign_keys = 1;");

            //或者这样写
            String query = String.format("PRAGMA foreign_keys = %s", "ON");
            db.execSQL(query);
        }
    }
}
