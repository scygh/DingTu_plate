package com.lianxi.dingtu.dingtu_plate.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lianxi.dingtu.dingtu_plate.R;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.activity.OfflineActivity;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.activity.OnlineActivity;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.activity.SettingsActivity;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.activity.SynActivity;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.activity.WritePlateActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class MainFragment extends BaseFragment {

    @BindView(R.id.online)
    LinearLayout online;
    @BindView(R.id.offline)
    LinearLayout offline;
    @BindView(R.id.setup)
    LinearLayout setup;
    @BindView(R.id.writeplate)
    LinearLayout writeplate;
    @BindView(R.id.syn)
    LinearLayout syn;
    private Intent intent;

    @Override
    public int initLayout() {
        return R.layout.fragment_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.online, R.id.offline, R.id.writeplate, R.id.syn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.online:
                intent = new Intent(getActivity(), OnlineActivity.class);
                startActivity(intent);
                break;
            case R.id.offline:
                intent = new Intent(getActivity(), OfflineActivity.class);
                startActivity(intent);
                break;
            case R.id.writeplate:
                intent = new Intent(getActivity(), WritePlateActivity.class);
                startActivity(intent);
                break;
            case R.id.syn:
                intent = new Intent(getActivity(), SynActivity.class);
                startActivity(intent);
                break;
        }
    }
}
