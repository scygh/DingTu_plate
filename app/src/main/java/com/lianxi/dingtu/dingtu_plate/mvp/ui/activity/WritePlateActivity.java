package com.lianxi.dingtu.dingtu_plate.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.dingtu_plate.R;
import com.lianxi.dingtu.dingtu_plate.app.Utils.AudioUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTypeTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.PlateCardInfo;
import com.lianxi.dingtu.dingtu_plate.di.component.DaggerWritePlateComponent;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.WritePlateContract;
import com.lianxi.dingtu.dingtu_plate.mvp.presenter.WritePlatePresenter;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.adapter.MyExtendableListViewAdapter;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.adapter.PlateUidRvAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android_serialport_api.SerialPortApi;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>商品写盘界面
 * Created by MVPArmsTemplate on 10/31/2019 08:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class WritePlateActivity extends BaseActivity<WritePlatePresenter> implements WritePlateContract.View {

    @BindView(R.id.goods_name)
    TextView goodsName;
    @BindView(R.id.goods_num)
    TextView goodsNum;
    @BindView(R.id.goods_price)
    TextView goodsPrice;
    @BindView(R.id.clear)
    Button clear;
    @BindView(R.id.write)
    Button write;
    @BindView(R.id.read)
    Button read;
    @BindView(R.id.uid_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.goods_listView)
    ExpandableListView goodsListView;
    @BindView(R.id.goods_progressbar)
    ProgressBar progressBar;
    Set<String> uidSet = new HashSet<>();
    List<String> uidList = new ArrayList<>();
    PlateUidRvAdapter adapter;
    List<String> EMGoodsTypeToNameList = new ArrayList<>();
    List<List<EMGoodsTo>> EMGoodsTolist = new ArrayList<>();
    MyExtendableListViewAdapter lvadapter;
    List<EMGoodsTypeTo> myemGoodsTypeTo;
    private int pattern = 3;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    } else {
                        initRecyclerView();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onEmGoodsTypeTo(List<EMGoodsTypeTo> emGoodsTypeTo) {
        if (myemGoodsTypeTo != null) {
            myemGoodsTypeTo.clear();
        }
        myemGoodsTypeTo = emGoodsTypeTo;
        for (int i = 0; i < emGoodsTypeTo.size(); i++) {
            EMGoodsTolist.add(new ArrayList<>());//初始化要更新的容器，不然会造成数组越界
            EMGoodsTypeToNameList.add(emGoodsTypeTo.get(i).getName());
            mPresenter.getEMGoods(emGoodsTypeTo.get(i).getId());
        }
    }

    @Override
    public void onEmGoodsTo(List<EMGoodsTo> emGoodsTo) {
        //进行判断后再放入指定的位置，抱着有序
        if (emGoodsTo.size() > 0) {
            for (int j = 0; j < myemGoodsTypeTo.size(); j++) {
                if (myemGoodsTypeTo.get(j).getId().equals(emGoodsTo.get(0).getGoods().getGoodsType())) {
                    EMGoodsTolist.set(j, emGoodsTo);
                    break;
                }
            }
        }
        if (EMGoodsTolist.size() == EMGoodsTypeToNameList.size()) {
            List<EMGoodsTo[]> emGoodsTos = new ArrayList<>();
            for (int i = 0; i < EMGoodsTolist.size(); i++) {
                emGoodsTos.add(EMGoodsTolist.get(i).toArray(new EMGoodsTo[EMGoodsTolist.get(i).size()]));
            }
            progressBar.setVisibility(View.GONE);
            lvadapter = new MyExtendableListViewAdapter(this, EMGoodsTypeToNameList.toArray(new String[EMGoodsTypeToNameList.size()]), emGoodsTos.toArray(new EMGoodsTo[emGoodsTos.size()][]));
            goodsListView.setAdapter(lvadapter);
            goodsListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    return false;
                }
            });
            goodsListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    goodsName.setText(EMGoodsTolist.get(groupPosition).get(childPosition).getGoods().getGoodsName());
                    goodsNum.setText(EMGoodsTolist.get(groupPosition).get(childPosition).getGoods().getGoodsNo() + "");
                    goodsPrice.setText(EMGoodsTolist.get(groupPosition).get(childPosition).getGoods().getPrice() + "");
                    if (pattern == 2) {
                        writePlate();
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void onFailed() {
        ToastUtils.showShort("请求失败");
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerWritePlateComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_write_plate; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("商品写盘");
        progressBar.setVisibility(View.VISIBLE);
        SerialPortApi.initPort();
        SerialPortApi.getInstance().setResponse(new SerialPortApi.SerialPortResponse() {
            @Override
            public void onGetUpTo(String card, PlateCardInfo info) {
                ToastUtils.showShort(card);
                uidSet.add(card);
                uidList.clear();
                uidList.addAll(uidSet);
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onCheckFail() {
                ToastUtils.showShort(SerialPortApi.HINI_CHECK_FAIL);
            }

            @Override
            public void onOperatorSuccess(String msg) {
                if (msg.equals(SerialPortApi.HINI_WRITE_CARD_OK)) {
                    if (pattern == 1) {
                        ToastUtils.showShort("清盘模式");
                    } else if (pattern == 2) {
                        ToastUtils.showShort("写盘模式");
                    }
                } else {
                    ToastUtils.showShort(msg);
                }
                if (msg.equals(SerialPortApi.HINT_PX_PATTERN_OK)) {
                    SerialPortApi.gotoReadCardPattern();
                } else if (msg.equals(SerialPortApi.HINI_READCARD_PATTERN_OK)) {
                    SerialPortApi.openRF_field();
                } else if (msg.equals(SerialPortApi.HINT_OPENRF_FIELD_OK)) {
                    ToastUtils.showShort("请放置餐盘");
                }
            }
        });
        openReadPattern();
        mPresenter.getEmGoodsTypeTo("1");
    }

    private void openReadPattern() {
        SerialPortApi.clearSb();
        SerialPortApi.closeRF_field();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SerialPortApi.PxPattern();
            }
        }, 200);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WritePlateActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(WritePlateActivity.this, DividerItemDecoration.VERTICAL));
        adapter = new PlateUidRvAdapter(WritePlateActivity.this, uidList);
        recyclerView.setAdapter(adapter);
        adapter.setMyItemClickListener(new PlateUidRvAdapter.OnMyItemClickListener() {
            @Override
            public void onItemLongClick(int positon) {
                AlertDialog alertDialog = new AlertDialog.Builder(WritePlateActivity.this)
                        .setTitle("提示")
                        .setMessage("")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                alertDialog.show();
            }
        });
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


    @OnClick({R.id.clear, R.id.write, R.id.clear_list, R.id.read, R.id.back_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clear:
                pattern = 1;
                clearList();
                SerialPortApi.gotoClearCardPattern(0);
                changeStyle("clear");
                break;
            case R.id.clear_list:
                clearList();
                break;
            case R.id.write:
                pattern = 2;
                clearList();
                changeStyle("write");
                writePlate();
                break;
            case R.id.read:
                pattern = 3;
                clearList();
                changeStyle("read");
                openReadPattern();
                break;
            case R.id.back_iv:
                finish();
                break;
        }
    }

    private void writePlate() {
        int conpanyCode = (int) SpUtils.get(WritePlateActivity.this, AppConstant.Api.COMPANYCODE, 0);
        if (!TextUtils.isEmpty(goodsPrice.getText().toString()) && !TextUtils.isEmpty(goodsNum.getText().toString())) {
            double price = Double.parseDouble(goodsPrice.getText().toString());
            int centprice = (int) (price * 100);
            SimpleDateFormat format = new SimpleDateFormat("yyMMddHH");
            String currentDate = format.format(new Date(System.currentTimeMillis()));
            SerialPortApi.gotoWriteCardPattern(Integer.parseInt(goodsNum.getText().toString()), conpanyCode, centprice, currentDate);
        } else {
            AudioUtils.getInstance().speakText("请先选择商品");
        }

    }

    private void changeStyle(String style) {
        if (style.equals("clear")) {
            clear.setBackgroundColor(getResources().getColor(R.color.GREEN));
            clear.setTextColor(getResources().getColor(R.color.white));
            write.setBackground(getResources().getDrawable(R.drawable.progress_bg));
            write.setTextColor(getResources().getColor(R.color.GREEN));
            read.setBackground(getResources().getDrawable(R.drawable.progress_bg));
            read.setTextColor(getResources().getColor(R.color.GREEN));
        } else if (style.equals("write")) {
            write.setBackgroundColor(getResources().getColor(R.color.GREEN));
            write.setTextColor(getResources().getColor(R.color.white));
            clear.setBackground(getResources().getDrawable(R.drawable.progress_bg));
            clear.setTextColor(getResources().getColor(R.color.GREEN));
            read.setBackground(getResources().getDrawable(R.drawable.progress_bg));
            read.setTextColor(getResources().getColor(R.color.GREEN));
        } else if (style.equals("read")) {
            read.setBackgroundColor(getResources().getColor(R.color.GREEN));
            read.setTextColor(getResources().getColor(R.color.white));
            clear.setBackground(getResources().getDrawable(R.drawable.progress_bg));
            clear.setTextColor(getResources().getColor(R.color.GREEN));
            write.setBackground(getResources().getDrawable(R.drawable.progress_bg));
            write.setTextColor(getResources().getColor(R.color.GREEN));
        }
    }

    private void clearList() {
        uidSet.clear();
        uidList.clear();
        handler.sendEmptyMessage(1);
    }

    @Override
    protected void onDestroy() {
        SerialPortApi.closeRF_field();
        EMGoodsTypeToNameList.clear();
        EMGoodsTolist.clear();
        if (myemGoodsTypeTo != null) {
            myemGoodsTypeTo.clear();
        }
        super.onDestroy();
    }
}
