package com.lianxi.dingtu.dingtu_plate.app.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

public class EMGoodsPayDetailRepo {
    private SQLiteDbHelper dbHelper;

    public EMGoodsPayDetailRepo(Context context) {
        dbHelper=new SQLiteDbHelper(context);
    }
    public int insert(Sql_EMGoodsPayDetail emGoods){
        //打开连接，写入数据
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Sql_EMGoodsPayDetail.KEY_NUMBER,emGoods.getNumber());
        values.put(Sql_EMGoodsPayDetail.KEY_DEVICEID,emGoods.getDeviceid());
        values.put(Sql_EMGoodsPayDetail.KEY_PATTERN,emGoods.getPattern());
        values.put(Sql_EMGoodsPayDetail.KEY_AMOUNT,emGoods.getAmount());
        values.put(Sql_EMGoodsPayDetail.KEY_TRADEDATETIME,emGoods.getTradedatetime());
        values.put(Sql_EMGoodsPayDetail.KEY_OFFLINEPAYCOUNT,emGoods.getOfflinepaycount());
        values.put(Sql_EMGoodsPayDetail.KEY_UPLOADSUCCESS,emGoods.getUploadsuccess());
        //
        long id =db.insert(Sql_EMGoodsPayDetail.TABLE_NAME,null,values);
        db.close();
        return (int)id;
    }

    public ArrayList<Sql_EMGoodsPayDetail> getEMGoodsListByUPLOADSUCCESS(int isUploadSuccess){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String selectQuery="SELECT * FROM "+ Sql_EMGoodsPayDetail.TABLE_NAME +
                " WHERE "+Sql_EMGoodsPayDetail.KEY_UPLOADSUCCESS+"=?";
        ArrayList<Sql_EMGoodsPayDetail> list=new ArrayList<>();
        Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(isUploadSuccess)});

        if(cursor.moveToFirst()){
            do{
                Sql_EMGoodsPayDetail emGoods = new Sql_EMGoodsPayDetail();
                emGoods.setId(cursor.getInt(cursor.getColumnIndex(Sql_EMGoodsPayDetail.KEY_ID)));
                emGoods.setDeviceid(cursor.getInt(cursor.getColumnIndex(Sql_EMGoodsPayDetail.KEY_DEVICEID)));
                emGoods.setPattern(cursor.getInt(cursor.getColumnIndex(Sql_EMGoodsPayDetail.KEY_PATTERN)));
                emGoods.setAmount(cursor.getDouble(cursor.getColumnIndex(Sql_EMGoodsPayDetail.KEY_AMOUNT)));
                emGoods.setTradedatetime(cursor.getString(cursor.getColumnIndex(Sql_EMGoodsPayDetail.KEY_TRADEDATETIME)));
                emGoods.setOfflinepaycount(cursor.getInt(cursor.getColumnIndex(Sql_EMGoodsPayDetail.KEY_OFFLINEPAYCOUNT)));
                emGoods.setNumber(cursor.getInt(cursor.getColumnIndex(Sql_EMGoodsPayDetail.KEY_NUMBER)));
                list.add(emGoods);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
    public void update(Sql_EMGoodsPayDetail emGoodsPayDetail) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();

        int id = emGoodsPayDetail.getId();
        Log.e("update", "update:id "+id );
        values.put(Sql_EMGoodsPayDetail.KEY_DEVICEID,emGoodsPayDetail.getDeviceid());
        values.put(Sql_EMGoodsPayDetail.KEY_NUMBER,emGoodsPayDetail.getNumber());
        values.put(Sql_EMGoodsPayDetail.KEY_AMOUNT,emGoodsPayDetail.getAmount());
        values.put(Sql_EMGoodsPayDetail.KEY_TRADEDATETIME,emGoodsPayDetail.getTradedatetime());
        values.put(Sql_EMGoodsPayDetail.KEY_OFFLINEPAYCOUNT,emGoodsPayDetail.getOfflinepaycount());
        values.put(Sql_EMGoodsPayDetail.KEY_UPLOADSUCCESS,1);
        values.put(Sql_EMGoodsPayDetail.KEY_PATTERN,emGoodsPayDetail.getPattern());

        int a = db.update(Sql_EMGoodsPayDetail.TABLE_NAME,values,Sql_EMGoodsPayDetail.KEY_ID+"=?",new String[]{String.valueOf(id)});
        Log.e("update", "update: "+ JSON.toJSONString(emGoodsPayDetail)+"  "+a );
        db.close();
    }

}
