package com.lianxi.dingtu.dingtu_plate.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lianxi.dingtu.dingtu_plate.R;

import java.util.List;

public class MenuAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MenuAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_menu_tv,item)
                .addOnClickListener(R.id.item_menu_btn);
    }
}
