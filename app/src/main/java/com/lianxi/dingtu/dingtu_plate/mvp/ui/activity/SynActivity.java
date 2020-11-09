package com.lianxi.dingtu.dingtu_plate.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.dingtu_plate.R;
import com.lianxi.dingtu.dingtu_plate.app.Utils.AntiShake;
import com.lianxi.dingtu.dingtu_plate.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTo;
import com.lianxi.dingtu.dingtu_plate.app.sql.EMGoodsRepo;
import com.lianxi.dingtu.dingtu_plate.app.sql.Sql_EMGoods;
import com.lianxi.dingtu.dingtu_plate.di.component.DaggerSynComponent;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.SynContract;
import com.lianxi.dingtu.dingtu_plate.mvp.presenter.SynPresenter;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.adapter.EMGoodsAdapter;

import org.jetbrains.annotations.Async;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>商品同步界面
 * Created by MVPArmsTemplate on 10/31/2019 08:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SynActivity extends BaseActivity<SynPresenter> implements SynContract.View {
    @BindView(R.id.syn_recy_goods)
    RecyclerView mRecyclerView;
    @BindView(R.id.syn_pro)
    ProgressBar progressBar;
    private EMGoodsAdapter adapter;
    private List<Sql_EMGoods> data = new ArrayList<>();
    ;

    private EMGoodsRepo emGoodsRepo = new EMGoodsRepo(this);
    private MyTask task;
    private List<EMGoodsTo.RowsBean.GoodsBean> emGoodsToList = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSynComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_syn; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("商品同步");
        data = emGoodsRepo.getEMGoodsList();
//        Log.e(TAG, "initData: " + JSON.toJSONString(data));
        initRecyclerView();
        progressBar.setProgress(0);
    }

    private void initRecyclerView() {
        // 定义一个线性布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(SynActivity.this, RecyclerView.VERTICAL, false);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(manager);
        // 设置adapter
        adapter = new EMGoodsAdapter(R.layout.item_syn_goods, data);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void onPagers(List<EMGoodsTo.RowsBean.GoodsBean> emGoodsToList) {
        this.emGoodsToList = emGoodsToList;
        task = new MyTask();
        task.execute();
    }

    AntiShake util = new AntiShake();

    @OnClick({R.id.syn_btn_syn, R.id.syn_btn_clear})
    public void OnViewClicked(View view) {
        switch (view.getId()) {
            case R.id.syn_btn_syn:
                if (util.check()) return;
                if (emGoodsRepo.delete()) {
                    data.clear();
                    mPresenter.setList();
                }
                Log.e(TAG, "OnViewClicked: 同步");
                break;
            case R.id.syn_btn_clear:
                if (util.check()) return;
                if (emGoodsRepo.delete()) {
                    data.clear();
                    adapter.notifyDataSetChanged();
                    progressBar.setProgress(0);
                }
                break;

        }
    }

    public class MyTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            progressBar.setMax(emGoodsToList.size());
        }

        @Override
        protected String doInBackground(String... s) {
            try {
                int count = 0;
                for (EMGoodsTo.RowsBean.GoodsBean emGoodsTo : emGoodsToList) {
                    Sql_EMGoods sqlEmGoods = new Sql_EMGoods();
                    sqlEmGoods.setGoodsNo(emGoodsTo.getGoodsNo());
                    sqlEmGoods.setGoodsName(emGoodsTo.getGoodsName());
                    sqlEmGoods.setPrice(emGoodsTo.getPrice());
                    sqlEmGoods.setDescription(emGoodsTo.getDescription());
                    emGoodsRepo.insert(sqlEmGoods);
                    data.add(sqlEmGoods);
                    count += 1;
                    publishProgress(count);
                }
                Log.e(TAG, "doInBackground: " + JSON.toJSONString(data));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            adapter.notifyDataSetChanged();
            progressBar.setProgress(values[0]);


        }
    }

    ;
}
