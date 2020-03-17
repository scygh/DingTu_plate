package com.lianxi.dingtu.dingtu_plate.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lianxi.dingtu.dingtu_plate.R;
import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTo;

import java.util.List;

public class PayGoodsAdapter extends BaseItemDraggableAdapter<EMGoodsTo.GoodsBean, BaseViewHolder> {
    public PayGoodsAdapter(int id,@Nullable List<EMGoodsTo.GoodsBean> data) {
        super(id,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EMGoodsTo.GoodsBean item) {
        helper.setText(R.id.item_goods_num,item.getGoodsNo()+"")
        .setText(R.id.item_goods_name,item.getGoodsName()+"")
        .setText(R.id.item_goods_price,item.getPrice()+"元")
        .setText(R.id.item_goods_count,item.getCount()+"")
        .setText(R.id.item_goods_allprice,item.getCount()*item.getPrice()+"元")
        .addOnClickListener(R.id.item_btn_plus)
        .addOnClickListener(R.id.item_btn_reduce);


    }
}
