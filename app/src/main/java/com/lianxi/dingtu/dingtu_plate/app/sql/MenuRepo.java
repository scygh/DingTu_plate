package com.lianxi.dingtu.dingtu_plate.app.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MenuRepo {
    private SQLiteDbHelper dbHelper;

    public MenuRepo(Context context) {
        dbHelper = new SQLiteDbHelper(context);
    }

    public int insert(EMGoodsTo.GoodsBean goodsBean) {
        //打开连接，写入数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EMGoodsTo.GoodsBean.KEY_GOODSNO, goodsBean.getGoodsNo());
        values.put(EMGoodsTo.GoodsBean.KEY_GOODSNAME, goodsBean.getGoodsName());
        values.put(EMGoodsTo.GoodsBean.KEY_PRICE, goodsBean.getPrice());
        values.put(EMGoodsTo.GoodsBean.KEY_COUNT, goodsBean.getCount());
        values.put(EMGoodsTo.GoodsBean.KEY_TIME, goodsBean.getTime());

        //
        long id = db.insert(EMGoodsTo.GoodsBean.TABLE_NAME, null, values);
        db.close();
        return (int) id;
    }

    public ArrayList<EMGoodsTo.GoodsBean> getGoodsBeanListByTime(String time) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + EMGoodsTo.GoodsBean.TABLE_NAME
                + " WHERE " +
                EMGoodsTo.GoodsBean.KEY_TIME + "=?";
        ArrayList<EMGoodsTo.GoodsBean> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{time});

        if (cursor.moveToFirst()) {
            do {
                EMGoodsTo.GoodsBean goodsBean = new EMGoodsTo.GoodsBean();
                goodsBean.setGoodsNo(cursor.getInt(cursor.getColumnIndex(EMGoodsTo.GoodsBean.KEY_GOODSNO)));
                goodsBean.setGoodsName(cursor.getString(cursor.getColumnIndex(EMGoodsTo.GoodsBean.KEY_GOODSNAME)));
                goodsBean.setPrice(cursor.getDouble(cursor.getColumnIndex(EMGoodsTo.GoodsBean.KEY_PRICE)));
                goodsBean.setCount(cursor.getInt(cursor.getColumnIndex(EMGoodsTo.GoodsBean.KEY_COUNT)));
                goodsBean.setTime(cursor.getString(cursor.getColumnIndex(EMGoodsTo.GoodsBean.KEY_TIME)));
                list.add(goodsBean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public Set<String> getGoodsBeanListAllTime() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + EMGoodsTo.GoodsBean.KEY_TIME + " FROM " + EMGoodsTo.GoodsBean.TABLE_NAME;
        Set<String> list = new HashSet<>();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(cursor.getColumnIndex(EMGoodsTo.GoodsBean.KEY_TIME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean delete(String time) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(EMGoodsTo.GoodsBean.TABLE_NAME, EMGoodsTo.GoodsBean.KEY_TIME + "=?", new String[]{time});
        db.close();
        return true;
    }
}
