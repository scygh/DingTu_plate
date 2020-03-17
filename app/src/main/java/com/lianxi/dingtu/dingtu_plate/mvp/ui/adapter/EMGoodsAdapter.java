package com.lianxi.dingtu.dingtu_plate.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lianxi.dingtu.dingtu_plate.R;
import com.lianxi.dingtu.dingtu_plate.app.sql.Sql_EMGoods;

import java.util.List;

public class EMGoodsAdapter extends BaseQuickAdapter<Sql_EMGoods, BaseViewHolder> {
    public EMGoodsAdapter(int layoutResId, @Nullable List<Sql_EMGoods> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Sql_EMGoods item) {
        helper.setText(R.id.syn_item_num,item.getGoodsNo()+"")
                .setText(R.id.syn_item_name,item.getGoodsName()+"")
                .setText(R.id.syn_item_price,item.getPrice()+"");

    }
}
