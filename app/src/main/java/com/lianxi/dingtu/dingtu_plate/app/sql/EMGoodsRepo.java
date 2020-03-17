package com.lianxi.dingtu.dingtu_plate.app.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTo;

import java.util.ArrayList;

public class EMGoodsRepo {
    private SQLiteDbHelper dbHelper;

    public EMGoodsRepo(Context context) {
        dbHelper=new SQLiteDbHelper(context);
    }

    public int insert(Sql_EMGoods emGoods){
        //打开连接，写入数据
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Sql_EMGoods.KEY_GOODSNO,emGoods.getGoodsNo());
        values.put(Sql_EMGoods.KEY_GOODSNAME,emGoods.getGoodsName());
        values.put(Sql_EMGoods.KEY_PRICE,emGoods.getPrice());
        values.put(Sql_EMGoods.KEY_DESCRIPTION,emGoods.getDescription());
       
        //
        long id =db.insert(Sql_EMGoods.TABLE_NAME,null,values);
//        db.close();
        return (int)id;
    }

    public ArrayList<Sql_EMGoods> getEMGoodsList(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT "+
                Sql_EMGoods.KEY_GOODSNO+","+
                Sql_EMGoods.KEY_GOODSNAME+","+
                Sql_EMGoods.KEY_PRICE+","+
                Sql_EMGoods.KEY_DESCRIPTION+" FROM "+ Sql_EMGoods.TABLE_NAME;
        ArrayList<Sql_EMGoods> list=new ArrayList<>();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            do{
                Sql_EMGoods emGoods = new Sql_EMGoods();
                emGoods.setGoodsNo(cursor.getInt(cursor.getColumnIndex(Sql_EMGoods.KEY_GOODSNO)));
                emGoods.setGoodsName(cursor.getString(cursor.getColumnIndex(Sql_EMGoods.KEY_GOODSNAME)));
                emGoods.setPrice(cursor.getDouble(cursor.getColumnIndex(Sql_EMGoods.KEY_PRICE)));
                list.add(emGoods);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    public ArrayList<EMGoodsTo.GoodsBean> getEMGoodsListByNum(int num){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT * FROM "+ Sql_EMGoods.TABLE_NAME+
                " WHERE "+
                EMGoodsTo.GoodsBean.KEY_GOODSNO + "=?";

        ArrayList<EMGoodsTo.GoodsBean> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(num)});

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
    public boolean delete() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "DELETE FROM " + Sql_EMGoods.TABLE_NAME + ";";
        db.execSQL(sql);
        db.close();
        return true;
    }

}
