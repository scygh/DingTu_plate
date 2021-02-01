package com.lianxi.dingtu.dingtu_plate.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
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
import java.util.Locale;
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
public class WritePlateActivity extends BaseActivity<WritePlatePresenter> implements WritePlateContract.View, TextToSpeech.OnInitListener {

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
    List<List<EMGoodsTo.RowsBean>> EMGoodsTolist = new ArrayList<>();
    MyExtendableListViewAdapter lvadapter;
    List<EMGoodsTypeTo> myemGoodsTypeTo;
    private int pattern = 3;
    private TextToSpeech textToSpeech;
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

    /**
     * descirption: 获取菜品类别列表
     */
    @Override
    public void onEmGoodsTypeTo(List<EMGoodsTypeTo> emGoodsTypeTo) {
        if (myemGoodsTypeTo != null) {
            myemGoodsTypeTo.clear();
        }
        myemGoodsTypeTo = emGoodsTypeTo;
        for (int i = 0; i < emGoodsTypeTo.size(); i++) {
            EMGoodsTolist.add(new ArrayList<>());//初始化要更新的容器，不然会造成数组越界//放详细菜的列表的列表
            EMGoodsTypeToNameList.add(emGoodsTypeTo.get(i).getName());
            mPresenter.getEMGoods(emGoodsTypeTo.get(i).getId());
        }
    }

    /**
     * descirption: 获取菜品类别下面的详细菜品，再初始化二级列表
     */
    @Override
    public void onEmGoodsTo(EMGoodsTo emGoodsTo) {
        //进行判断后再放入指定的位置，保证有序
        if (emGoodsTo.getRows().size() > 0) {
            for (int j = 0; j < myemGoodsTypeTo.size(); j++) {
                if (myemGoodsTypeTo.get(j).getId().equals(emGoodsTo.getRows().get(0).getGoods().getGoodsType())) {
                    EMGoodsTolist.set(j, emGoodsTo.getRows());
                    break;
                }
            }
        }
        if (EMGoodsTolist.size() == EMGoodsTypeToNameList.size()) {
            List<EMGoodsTo.RowsBean[]> emGoodsTos = new ArrayList<>();
            for (int i = 0; i < EMGoodsTolist.size(); i++) {
                emGoodsTos.add(EMGoodsTolist.get(i).toArray(new EMGoodsTo.RowsBean[EMGoodsTolist.get(i).size()]));
            }
            progressBar.setVisibility(View.GONE);
            lvadapter = new MyExtendableListViewAdapter(this, EMGoodsTypeToNameList.toArray(new String[EMGoodsTypeToNameList.size()]), emGoodsTos.toArray(new EMGoodsTo.RowsBean[emGoodsTos.size()][]));
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
                        openSingleReadPattern();
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
        //初始化音频
        //AudioUtils.getInstance().init(getApplicationContext());
        textToSpeech = new TextToSpeech(this, this);
        //初始化串口
        SerialPortApi.initPort();
        SerialPortApi.getInstance().setResponse(new SerialPortApi.SerialPortResponse() {
            @Override
            public void onGetUpTo(String card, PlateCardInfo info) {
                //ToastUtils.showShort(card);
                if (pattern == 1) {//如果是清盘模式，读到就去清盘
                    if (info.getGoodsNum() != null) {
                        SerialPortApi.gotoClearCardPattern(0);
                    } else {
                        updateList(card);
                    }
                } else if (pattern == 2) {//如果是写卡模式，读到就去写盘
                    if (info.getGoodsNum() != null) {
                        writePlate();
                    } else {
                        updateList(card);
                    }
                } else if (pattern == 3) {//如果是读盘模式，读到就去刷新列表
                    updateList(card);
                } else if (pattern == 0) {//如果是初始化模式
                    if (card.equalsIgnoreCase("HAVECARD")) {
                        SerialPortApi.gotoClearCardPattern(0);
                    } else {
                        updateList(card);
                    }
                }
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
                if (msg.equals(SerialPortApi.HINT_SINGLE_RESPONSE_PATTERN_OK)) {
                    if (pattern == 0) {
                        SerialPortApi.gotoReadCardUIDPattern();
                    } else {
                        SerialPortApi.gotoReadCardPattern();
                    }
                } else if (msg.equals(SerialPortApi.HINI_READCARD_PATTERN_OK)) {
                    SerialPortApi.openRF_field();
                } else if (msg.equals(SerialPortApi.HINI_READCARDUID_PATTERN_OK)) {
                    SerialPortApi.openRF_field();
                } else if (msg.equals(SerialPortApi.HINT_OPENRF_FIELD_OK)) {
                    ToastUtils.showShort("请放置餐盘");
                }
            }
        });
        //获取菜品类别列表
        mPresenter.getEmGoodsTypeTo("1");
    }

    /**
     * descirption: 更新盘子列表
     */
    public void updateList(String card) {
        uidSet.add(card);
        uidList.clear();
        uidList.addAll(uidSet);
        handler.sendEmptyMessage(1);
    }

    /**
     * descirption: 初始化清空 开启盘询模式
     */
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


    @OnClick({R.id.initCard, R.id.clear, R.id.write, R.id.clear_list, R.id.read, R.id.back_ib})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.initCard:
                pattern = 0;
                clearList();
                openSingleReadPattern();
                break;
            case R.id.clear:
                pattern = 1;
                clearList();
                changeStyle("clear");
                openSingleReadPattern();
                break;
            case R.id.clear_list:
                clearList();
                break;
            case R.id.write:
                pattern = 2;
                clearList();
                changeStyle("write");
                openSingleReadPattern();
                break;
            case R.id.read:
                pattern = 3;
                clearList();
                changeStyle("read");
                openSingleReadPattern();
                break;
            case R.id.back_ib:
                finish();
                break;
        }
    }

    /**
     * descirption: 清空列表
     */
    private void clearList() {
        uidSet.clear();
        uidList.clear();
        handler.sendEmptyMessage(1);
    }

    /**
     * descirption: 初始化清空 开启单次读模式
     */
    private void openSingleReadPattern() {
        SerialPortApi.clearSb();
        SerialPortApi.closeRF_field();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SerialPortApi.singleResponsePattern();
            }
        }, 200);
    }

    /**
     * descirption: 初始化餐盘信息列表
     */
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

    private void writePlate() {
        int conpanyCode = (int) SpUtils.get(WritePlateActivity.this, AppConstant.Api.COMPANYCODE, 0);
        if (!TextUtils.isEmpty(goodsPrice.getText().toString()) && !TextUtils.isEmpty(goodsNum.getText().toString())) {
            double price = Double.parseDouble(goodsPrice.getText().toString());
            int centprice = (int) (price * 100);
            SimpleDateFormat format = new SimpleDateFormat("yyMMddHH");
            String currentDate = format.format(new Date(System.currentTimeMillis()));
            SerialPortApi.gotoWriteCardPattern(Integer.parseInt(goodsNum.getText().toString()), conpanyCode, centprice, currentDate);
        } else {
            //AudioUtils.getInstance().speakText("请先选择商品");
            speakChinese("请先选择商品，再点击写盘");
        }

    }

    private void changeStyle(String style) {
        if (style.equals("clear")) {
            clear.setBackground(getResources().getDrawable(R.drawable.progress_bg_select));
            clear.setTextColor(getResources().getColor(R.color.white));
            write.setBackground(getResources().getDrawable(R.drawable.progress_bg));
            write.setTextColor(getResources().getColor(R.color.colorAccent));
            read.setBackground(getResources().getDrawable(R.drawable.progress_bg));
            read.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (style.equals("write")) {
            write.setBackground(getResources().getDrawable(R.drawable.progress_bg_select));
            write.setTextColor(getResources().getColor(R.color.white));
            clear.setBackground(getResources().getDrawable(R.drawable.progress_bg));
            clear.setTextColor(getResources().getColor(R.color.colorAccent));
            read.setBackground(getResources().getDrawable(R.drawable.progress_bg));
            read.setTextColor(getResources().getColor(R.color.colorAccent));
        } else if (style.equals("read")) {
            read.setBackground(getResources().getDrawable(R.drawable.progress_bg_select));
            read.setTextColor(getResources().getColor(R.color.white));
            clear.setBackground(getResources().getDrawable(R.drawable.progress_bg));
            clear.setTextColor(getResources().getColor(R.color.colorAccent));
            write.setBackground(getResources().getDrawable(R.drawable.progress_bg));
            write.setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    private void speakChinese(String msg) {
        if (textToSpeech != null && !textToSpeech.isSpeaking()) {
            // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            textToSpeech.setPitch(1.0f);
            //设定语速 ，默认1.0正常语速
            textToSpeech.setSpeechRate(1.2f);
            //朗读，注意这里三个参数的added in API level 4   四个参数的added in API level 21
            textToSpeech.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        SerialPortApi.closeRF_field();
        if (EMGoodsTypeToNameList != null) {
            EMGoodsTypeToNameList.clear();
        }
        if (EMGoodsTolist != null) {
            EMGoodsTolist.clear();
        }
        if (myemGoodsTypeTo != null) {
            myemGoodsTypeTo.clear();
        }
        if (textToSpeech != null) {
            textToSpeech.shutdown();
            textToSpeech.stop();
            textToSpeech = null;
        }
        super.onDestroy();
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
}
