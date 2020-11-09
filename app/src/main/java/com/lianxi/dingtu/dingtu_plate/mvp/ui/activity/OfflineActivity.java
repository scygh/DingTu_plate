package com.lianxi.dingtu.dingtu_plate.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.iflytek.cloud.thirdparty.S;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.lianxi.dingtu.dingtu_plate.app.Utils.AudioUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.GainschaUsbUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.Printer.DeviceConnFactoryManager;
import com.lianxi.dingtu.dingtu_plate.app.Utils.Printer.PrinterCommand;
import com.lianxi.dingtu.dingtu_plate.app.Utils.Printer.PrintingTicketsUtil;
import com.lianxi.dingtu.dingtu_plate.app.Utils.Printer.ThreadPool;
import com.lianxi.dingtu.dingtu_plate.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.USBHelper;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_plate.app.base.MainApplication;
import com.lianxi.dingtu.dingtu_plate.app.entity.CardInfoBean;
import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.GetFacePayAuthInfoParam;
import com.lianxi.dingtu.dingtu_plate.app.entity.PlateCardInfo;
import com.lianxi.dingtu.dingtu_plate.app.listening.USBListening;
import com.lianxi.dingtu.dingtu_plate.app.sql.EMGoodsPayDetailRepo;
import com.lianxi.dingtu.dingtu_plate.app.sql.EMGoodsRepo;
import com.lianxi.dingtu.dingtu_plate.app.sql.MenuRepo;
import com.lianxi.dingtu.dingtu_plate.app.sql.Sql_EMGoodsPayDetail;
import com.lianxi.dingtu.dingtu_plate.di.component.DaggerOfflineComponent;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.OfflineContract;
import com.lianxi.dingtu.dingtu_plate.mvp.presenter.OfflinePresenter;

import com.lianxi.dingtu.dingtu_plate.R;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.adapter.MenuAdapter;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.adapter.PayGoodsAdapter;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;


import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android_serialport_api.SerialPortApi;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.ByteUtils.bytesToHexString;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.Printer.Constant.CONN_STATE_DISCONN;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.StringUtils.sub;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.PARAMS_APPID_CONTENT;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.PARAMS_MCH_ID_CONTENT;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxfacepayUtil.isSuccessInfo;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxfacepayUtil.showToast;


/**
 * ================================================
 * Description:
 * <p>离线消费界面
 * Created by MVPArmsTemplate on 10/31/2019 08:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class OfflineActivity extends BaseActivity<OfflinePresenter> implements OfflineContract.View {
    @BindView(R.id.offline_num)
    TextView goods_num;
    @BindView(R.id.offline_price)
    TextView goods_price;
    @BindView(R.id.offline_pay_state_success)
    TextView offline_pay_state_success;
    @BindView(R.id.offline_pay_state_fail)
    TextView offline_pay_state_fail;
    @BindView(R.id.offline_pay_state_wait)
    TextView offline_pay_state_wait;
    @BindView(R.id.offline_pay_blance)
    TextView offline_pay_blance;
    @BindView(R.id.offline_recy)
    RecyclerView mRecyclerView;
    @BindView(R.id.offline_right)
    RelativeLayout offline_right;
    @BindView(R.id.offline_drawerLayout)
    DrawerLayout offline_drawerLayout;
    @BindView(R.id.offline_recy_menu)
    RecyclerView offline_recy_menu;
    @BindView(R.id.pay_operation)
    LinearLayout pay_operation;
    @BindView(R.id.tv_pay_operation)
    TextView tv_pay_operation;
    @BindView(R.id.offline_pay_btn)
    Button paybutton;
    private List<EMGoodsTo.RowsBean.GoodsBean> data = new ArrayList<>();
    private List<String> menu_data = new ArrayList<>();
    private PayGoodsAdapter adapter;
    private MenuAdapter menuAdapter;
    private int pattern;
    private HashMap<String, String> setPlate = new HashMap<>();
    private boolean havingCard = false;
    private int companyCode;
    private int number = 0;//卡内码
    private String code = "";//单位代码
    private boolean isAutopay;
    private double Balance = 0.0;

    private static final int UPDATE_MAIN_LISTADNPRICE = 1;
    private static final int UPDATE_MENU_DATA = 2;
    private static final int ISHAVINGCARD = 4;
    private static final int INIT_READCARD_PAY = 5;
    private static final int PAY_SUCCESS = 6;
    private static final int PAYBUTTON_ENABLE = 7;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_MAIN_LISTADNPRICE:
                    if (adapter != null) {
                        Log.e(TAG, "handleMessage: 更新");
                        adapter.notifyDataSetChanged();
                        int count = 0;
                        double price = 0.0;
                        for (EMGoodsTo.RowsBean.GoodsBean goods : data) {
                            count = count + goods.getCount();
                            price = price + goods.getPrice() * goods.getCount();
                        }
                        goods_price.setText(String.format("%.2f", price));
                        goods_num.setText(count + "");
                        if (isAutopay) {
                            waitStatus();
                        } else {
                            clearStatus();
                        }
                    }
                    break;
                case UPDATE_MENU_DATA:
                    if (menuAdapter != null) {
                        menu_data.clear();
                        menu_data.addAll(menuRepo.getGoodsBeanListAllTime());
                        menuAdapter.notifyDataSetChanged();
                    }
                    break;
                case ISHAVINGCARD:
                    if (havingCard) {
                        handler.sendEmptyMessageDelayed(ISHAVINGCARD, 500);
                    } else {
                        clear();
                        if (isAutopay) {
                            openReadCard();
                        }
                    }
                    break;
                case INIT_READCARD_PAY:
                    usbHelper.notBreakRead();
                    initReadCardPay();
                    break;
                case PAY_SUCCESS:
                    clearMytime();
                    paybutton.setEnabled(true);
                    break;
                case PAYBUTTON_ENABLE:
                    paybutton.setEnabled(true);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private USBHelper usbHelper;
    private Double mTotalPrice = 0.0;
    ;
    private int deviceID;
    private MenuRepo menuRepo = new MenuRepo(OfflineActivity.this);
    private String rawdata;
    private String mAuthInfo;
    private String menu_time = "";
    private String key = "";
    private boolean isPrint;
    private EMGoodsPayDetailRepo emGoodsPayDetailRepo = new EMGoodsPayDetailRepo(OfflineActivity.this);
    private MyTime myTime;
    private GainschaUsbUtils gainschaUsbUtils;
    private boolean isGainOpen = false;
    private ExecutorService singleExecutor;
    CardInfoBean cardInfoBean;

    class MyTime extends CountDownTimer {
        public MyTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            offline_pay_state_wait.setText("等待支付(" + millisUntilFinished / 1000 + ")");
        }

        @Override
        public void onFinish() {
            handler.sendEmptyMessage(6);
            clearStatus();
            handler.removeMessages(5);//防止重复点击按钮
        }
    }

    private void clearMytime() {
        if (myTime != null) {
            myTime.cancel();
            myTime = null;
        }
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOfflineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_offline; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("离线消费");
        isAutopay = (boolean) SpUtils.get(this, AppConstant.Receipt.PAYSTATE, false);
        isPrint = (boolean) SpUtils.get(this, AppConstant.Receipt.isPrint, false);

        companyCode = (int) SpUtils.get(this, AppConstant.Api.COMPANYCODE, 0);
        key = (String) SpUtils.get(this, AppConstant.Card.KEY, "");

        deviceID = Integer.parseInt((String) SpUtils.get(this, AppConstant.Receipt.MAC_NUMBER, "1"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OfflineActivity.this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PayGoodsAdapter(R.layout.item_goods, data);
        mRecyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(OfflineActivity.this, RecyclerView.VERTICAL, false);
        offline_recy_menu.setLayoutManager(linearLayoutManager2);
        offline_recy_menu.addItemDecoration(new DividerItemDecoration(OfflineActivity.this, DividerItemDecoration.VERTICAL));
        Set<String> set = menuRepo.getGoodsBeanListAllTime();
        menu_data = new ArrayList<>(set);
        menuAdapter = new MenuAdapter(R.layout.item_menu, menu_data);
        offline_recy_menu.setAdapter(menuAdapter);

        clearStatus();

        singleExecutor = Executors.newSingleThreadExecutor();

       /* gainschaUsbUtils = new GainschaUsbUtils(getApplicationContext());
        gainschaUsbUtils.setOnGainSchaUsbListener(new GainschaUsbUtils.OnGainSchaUsbListener() {
            @Override
            public void onConnect(String deviceConnect) {
                Log.d("jiabou", "onConnect: " + deviceConnect);
                if (deviceConnect.equals("已连接")) {
                    isGainOpen = true;
                    ToastUtils.showShort("打印机已连接");
                    handler.sendEmptyMessage(7);
                } else {
                    isGainOpen = false;
                }
            }

            @Override
            public void onStatus(String deviceStatus) {

            }

            @Override
            public void onCommandTypes(String deviceType) {

            }
        });
        singleExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    gainschaUsbUtils.openConnect();
                    Log.d("deviceConnect", "onConnect: 1");
                } catch (Exception e) {

                }
            }
        });*/
        //初始化音频
        AudioUtils.getInstance().init(getApplicationContext());

        //开启自动支付
        usbHelper = USBHelper.getInstance(this);
        if (isAutopay) {
            pay_operation.setVisibility(View.GONE);
            tv_pay_operation.setVisibility(View.GONE);
            pattern = 2;
            openReadCard();
        } else {
            pay_operation.setVisibility(View.VISIBLE);
            tv_pay_operation.setVisibility(View.VISIBLE);
            pattern = 1;
        }

        SerialPortApi.initPort();
        SerialPortApi.getInstance().setResponse(new SerialPortApi.SerialPortResponse() {

            @Override
            public void onGetUpTo(String card, PlateCardInfo info) {
                Log.e(TAG, "onGetUpTo: " + JSON.toJSONString(info));

                //判断盘子内是否有数据
                if (info.getCompanyCode() == null) return;

                if (info.getGoodsPrice() == 0.0 && info.getDate().equals("00000000")) {
                    AudioUtils.getInstance().speakText("无数据盘");
                    return;
                }

                //判断盘子是否在有效期内
                try {
                    if (!isDateQualified(info.getDate())) {
                        AudioUtils.getInstance().speakText("过期餐盘");
                        return;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //判断盘子是否重复
                if (!setPlate.containsKey(card)) {
                    setPlate.put(card, info.getGoodsNum());
                } else {
                    return;
                }
                EMGoodsTo.RowsBean.GoodsBean goodsBean = new EMGoodsTo.RowsBean.GoodsBean();
                goodsBean.setCount(1);
                goodsBean.setPrice(info.getGoodsPrice());
                goodsBean.setGoodsName(info.getGoodsPrice() + "元商品");
                goodsBean.setGoodsNo(Integer.parseInt(info.getGoodsNum()));


                int i = 0;
                if (data.size() == 0) {
                    goodsBean.setCount(1);
                    data.add(goodsBean);
                } else if (data.size() > 0) {
                    List<EMGoodsTo.RowsBean.GoodsBean> goodsBeanList = new ArrayList<>();
                    goodsBeanList.addAll(data);
                    data.clear();
                    for (EMGoodsTo.RowsBean.GoodsBean goods : goodsBeanList) {
                        if (goods.getGoodsNo() == goodsBean.getGoodsNo()) {
                            goods.setCount(goods.getCount() + 1);
                            i = 1;
                        }
                        data.add(goods);
                    }
                    if (i != 1) {
                        goodsBean.setCount(1);
                        data.add(goodsBean);
                    }
                }
                handler.sendEmptyMessage(1);
                Log.e(TAG, "getEMGoodsByNum: " + JSON.toJSONString(data));
            }

            @Override
            public void onCheckFail() {

            }

            @Override
            public void onOperatorSuccess(String msg) {
                if (msg.equals(SerialPortApi.HAVECARD)) {
                    havingCard = true;
                    SerialPortApi.openReadPattern(handler);
                    changeToSinglePattern(2000);
                }
                if (msg.equals(SerialPortApi.NOCARD)) {
                    havingCard = false;
                    changeToSinglePattern(1000);
                }
            }
        });
        SerialPortApi.singleResponsePattern(handler);

        //菜品数量加减
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_btn_plus:
                        data.get(position).setCount(data.get(position).getCount() + 1);
                        handler.sendEmptyMessage(UPDATE_MAIN_LISTADNPRICE);

                        break;
                    case R.id.item_btn_reduce:
                        if (data.get(position).getCount() == 1) {
                            AlertDialog dialog = new AlertDialog.Builder(OfflineActivity.this)
                                    .setIcon(R.mipmap.warning)
                                    .setTitle("系统提示")
                                    .setMessage("确定要删除该商品？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Collection<String> values = setPlate.values();
                                            while (values.contains(data.get(position).getGoodsNo() + "")) {
                                                values.remove(data.get(position).getGoodsNo() + "");
                                            }
                                            data.remove(position);
                                            handler.sendEmptyMessage(1);
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).create();
                            dialog.show();
                        } else {
                            data.get(position).setCount(data.get(position).getCount() - 1);
                            handler.sendEmptyMessage(UPDATE_MAIN_LISTADNPRICE);
                        }
                        break;
                }
            }
        });
        menuAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                menu_time = menu_data.get(position);
                data.clear();
                data.addAll(menuRepo.getGoodsBeanListByTime(menu_data.get(position)));
                handler.sendEmptyMessage(UPDATE_MAIN_LISTADNPRICE);
                offline_drawerLayout.closeDrawer(offline_right);
            }
        });
        menuAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.item_menu_btn) {
                    AlertDialog dialog = new AlertDialog.Builder(OfflineActivity.this)
                            .setIcon(R.mipmap.warning)
                            .setTitle("系统提示：")
                            .setMessage("确定要删除该订单？")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (menu_data.size() > 0) {
                                        menuRepo.delete(menu_data.get(position));
                                    }
                                    handler.sendEmptyMessageDelayed(UPDATE_MENU_DATA, 500);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create();
                    dialog.show();

                }
            }
        });

    }

    private boolean isDateQualified(String date) throws ParseException {
        int H = Integer.parseInt((String) SpUtils.get(OfflineActivity.this, AppConstant.Receipt.PLATE_INDATE, "0"));//小时

        //当有效期为0小时时=无有效期
        if (H == 0) return true;

        //餐盘内时间
        String plate_date = "20" + date;
        DateFormat df = new SimpleDateFormat("yyyyMMddHH");
        Date date_plate = df.parse(plate_date);

        //系统时间
        Date date_now = new Date(System.currentTimeMillis());
        //系统时间-有效期
        Date newDate2 = new Date(date_now.getTime() - (long) H * 60 * 60 * 1000);

        if (date_plate.getTime() < newDate2.getTime()) {
            return false;
        } else {
            return true;
        }
    }

    private void changeToSinglePattern(int i) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SerialPortApi.singleResponsePattern(handler);
            }
        }, i);
    }

    @OnClick({R.id.offline_plusone_btn, R.id.offline_pay_btn, R.id.offline_clear_btn, R.id.offline_save_btn, R.id.offline_take_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.offline_plusone_btn:
                break;
            case R.id.offline_pay_btn:
                paybutton.setEnabled(false);
                myTime = new MyTime(60000, 1000);
                myTime.start();
                waitStatus();
                openReadCard();
                break;
            case R.id.offline_clear_btn:
                AlertDialog dialog = new AlertDialog.Builder(OfflineActivity.this)
                        .setIcon(R.mipmap.warning)
                        .setTitle("系统提示：")
                        .setMessage("确定要清空商品列表？")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clear();
                                clearStatus();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
                break;
            case R.id.offline_save_btn:
                try {
                    if (data != null && data.size() > 0) {
                        Date date = new Date(System.currentTimeMillis());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = simpleDateFormat.format(date);
                        for (EMGoodsTo.RowsBean.GoodsBean goodsBean : data) {
                            goodsBean.setTime(time);
                            menuRepo.insert(goodsBean);
                        }
                        Toast.makeText(OfflineActivity.this, "已成功挂起订单：" + time, Toast.LENGTH_SHORT).show();
                        AudioUtils.getInstance().speakText("挂单成功");
                        handler.sendEmptyMessageDelayed(UPDATE_MENU_DATA, 500);
                        handler.sendEmptyMessage(ISHAVINGCARD);
                    } else {
                        Toast.makeText(OfflineActivity.this, "商品列表为空！", Toast.LENGTH_SHORT).show();
                        AudioUtils.getInstance().speakText("商品列表为空");
                    }

                } catch (Exception e) {
                    Toast.makeText(OfflineActivity.this, "挂单失败，请重试!", Toast.LENGTH_SHORT).show();
                    AudioUtils.getInstance().speakText("挂单失败，请重试");
                }

                break;
            case R.id.offline_take_btn:
                handler.sendEmptyMessageDelayed(UPDATE_MENU_DATA, 500);
                offline_drawerLayout.openDrawer(offline_right);
                break;
        }
    }

    /**
    * descirption: 开启刷卡入口
    */
    private void openReadCard() {
        handler.removeMessages(INIT_READCARD_PAY);//防止重复点击按钮
        usbHelper.notBreakRead();
        initReadCardPay();
    }

    /**
    * descirption: 开启刷卡
    */
    private void initReadCardPay() {
        singleExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTotalPrice = Double.parseDouble(goods_price.getText().toString());
                if (mTotalPrice <= 0.00) {//
                    ToastUtils.showShort("请识别餐盘");
                    handler.sendEmptyMessageDelayed(INIT_READCARD_PAY, 2000);
                    return;
                }
                cardInfoBean = usbHelper.read_card();
                if (cardInfoBean != null) {
                    Log.d("read_card5", "findRFCardListening: " + JSON.toJSONString(cardInfoBean));
                    if (cardInfoBean.getCash_account() == 3276.8 && cardInfoBean.getNum() == 327680 || cardInfoBean.getCode().equals("0000")) {//如果发现是这些数据表示没有放置卡片,或者错误则重新读卡
                        handler.sendEmptyMessageDelayed(INIT_READCARD_PAY, 500);
                        return;
                    }
                    usbHelper.PeakNoise();
                    if (cardInfoBean.getCash_account() > mTotalPrice) {
                        goToOfflinePay(cardInfoBean);
                    } else {
                        ToastUtils.showShort("余额不足，请充值");
                        handler.sendEmptyMessageDelayed(INIT_READCARD_PAY, 2000);
                    }
                } else {
                    ToastUtils.showShort("没读到卡，刷卡停止");
                }
            }
        });
    }

    /**
    * descirption: 去离线支付
    */
    private void goToOfflinePay(CardInfoBean cardInfoBean) {
        Double cash = sub(cardInfoBean.getCash_account(), mTotalPrice);
        cardInfoBean.setCash_account(cash);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String Spending_time = formatter.format(date);
        cardInfoBean.setSpending_time(Spending_time);
        cardInfoBean.setConsumption_num(cardInfoBean.getConsumption_num() + 1);
        Log.e(TAG, "findRFCardListening:支付 " + cash);
        Log.d("write_card5", "findRFCardListening: " + JSON.toJSONString(cardInfoBean));
        if (usbHelper.write_card5(cardInfoBean)) {
            Sql_EMGoodsPayDetail sql_emGoodsPayDetail = new Sql_EMGoodsPayDetail();
            sql_emGoodsPayDetail.setNumber(cardInfoBean.getNum());
            sql_emGoodsPayDetail.setDeviceid(deviceID);
            sql_emGoodsPayDetail.setAmount(mTotalPrice);
            sql_emGoodsPayDetail.setTradedatetime(Spending_time);
            sql_emGoodsPayDetail.setOfflinepaycount(cardInfoBean.getConsumption_num());
            sql_emGoodsPayDetail.setUploadsuccess(0);
            sql_emGoodsPayDetail.setPattern(pattern);
            emGoodsPayDetailRepo.insert(sql_emGoodsPayDetail);
            if (isPrint) Printing();
            successStatus();
            offline_pay_blance.setText("余额：" + cardInfoBean.getCash_account());
            Balance = cardInfoBean.getCash_account();
            if (menu_time != "") {
                menuRepo.delete(menu_time);
                handler.sendEmptyMessageDelayed(UPDATE_MENU_DATA, 500);
            }
            handler.sendEmptyMessageDelayed(ISHAVINGCARD, 3000);
            if (isAutopay) {
                handler.sendEmptyMessageDelayed(INIT_READCARD_PAY, 5000);//继续开启刷卡
            }
            AudioUtils.getInstance().speakText("支付成功");
            handler.sendEmptyMessageDelayed(PAY_SUCCESS, 3000);
        } else {
            if (isAutopay) {
                handler.sendEmptyMessageDelayed(INIT_READCARD_PAY, 5000);//继续开启刷卡
            }
            AudioUtils.getInstance().speakText("支付失败");
            failStatus();
        }
    }

    /**
     * descirption: 清空状态
     */
    private void clearStatus() {
        offline_pay_state_wait.setVisibility(View.GONE);
        offline_pay_state_fail.setVisibility(View.GONE);
        offline_pay_state_success.setVisibility(View.GONE);
        offline_pay_blance.setVisibility(View.GONE);
    }

    /**
     * descirption: 等待支付状态
     */
    private void waitStatus() {
        offline_pay_state_fail.setVisibility(View.GONE);
        offline_pay_state_success.setVisibility(View.GONE);
        offline_pay_blance.setVisibility(View.GONE);
        offline_pay_state_wait.setVisibility(View.VISIBLE);
    }

    /**
     * descirption: 支付成功状态
     */
    private void successStatus() {
        offline_pay_state_wait.setVisibility(View.GONE);
        offline_pay_state_fail.setVisibility(View.GONE);
        offline_pay_state_success.setVisibility(View.VISIBLE);
        offline_pay_blance.setVisibility(View.VISIBLE);
    }

    /**
     * descirption: 支付失败状态
     */
    private void failStatus() {
        offline_pay_state_wait.setVisibility(View.GONE);
        offline_pay_state_fail.setVisibility(View.VISIBLE);
        offline_pay_state_success.setVisibility(View.GONE);
        offline_pay_blance.setVisibility(View.GONE);
    }

    /**
     * descirption: 新的USB打印机打印方法
     */
    public void Printing() {
        /*if (isGainOpen) {
            sendMsg(0);
        } else {
            ToastUtils.showShort("打印机未打开成功,正在尝试重新打开...");
            gainschaUsbUtils.openConnect();
            sendMsg(3000);
        }*/
    }

    public void sendMsg(long time) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gainschaUsbUtils.sendReceipt(data, OfflineActivity.this);
            }
        }, time);
    }


    private void clear() {
        setPlate.clear();
        data.clear();
        handler.sendEmptyMessage(UPDATE_MAIN_LISTADNPRICE);
        offline_pay_state_success.setVisibility(View.GONE);
        offline_pay_blance.setVisibility(View.GONE);
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


    /*private ThreadPool threadPool;
    //小票打印是否显示余额


    //打印小票
    public void Printing() {
        Log.e(TAG, "onActivityResult: 开始连接打印机");
        //串口连接
        *//* 获取波特率 *//*
        int baudrate = 9600;
//        (int) SpUtils.get(OfflineActivity.this, AppConstant.Print.SERIALPORTBAUDRATE, 0);
        *//* 获取串口号 *//*
        String path = "/dev/ttyS3";
//        (String) SpUtils.get(OfflineActivity.this, AppConstant.Print.SERIALPORTPATH, "");

        Log.e(TAG, "onViewClicked: baudrate:" + baudrate + "  path:" + path);
        if (baudrate != 0 && !TextUtils.isEmpty(path)) {
            *//* 初始化DeviceConnFactoryManager *//*
            new DeviceConnFactoryManager.Build()
                    *//* 设置连接方式 *//*
                    .setConnMethod(DeviceConnFactoryManager.CONN_METHOD.SERIAL_PORT)
                    .setContext(OfflineActivity.this)
                    .setId(id)
                    *//* 设置波特率 *//*
                    .setBaudrate(baudrate)
                    *//* 设置串口号 *//*
                    .setSerialPort(path)
                    .build();
            *//* 打开端口 *//*
            DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].openPort();
        }
        threadPool = ThreadPool.getInstantiation();
        threadPool.addTask(new Runnable() {
            @Override
            public void run() {
                if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getCurrentPrinterCommand() == PrinterCommand.ESC) {
                    PrintingTicketsUtil.sendReceiptWithResponse(OfflineActivity.this, data, id, Balance);
                } else {
                    mHandler.obtainMessage(PRINTER_COMMAND_ERROR).sendToTarget();
                }
            }
        });
    }

    private static final int CONN_PRINTER = 0x12;
    */

    /**
     * 使用打印机指令错误
     *//*
    private static final int PRINTER_COMMAND_ERROR = 0x008;
    private static int id = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CONN_STATE_DISCONN:
                    if (DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id] != null || !DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].getConnState()) {
                        DeviceConnFactoryManager.getDeviceConnFactoryManagers()[id].closePort(id);
                        Toast.makeText(OfflineActivity.this, R.string.str_disconnect_success, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case PRINTER_COMMAND_ERROR:
                    Toast.makeText(OfflineActivity.this, R.string.str_choice_printer_command, Toast.LENGTH_SHORT).show();
                    break;
                case CONN_PRINTER:
                    Toast.makeText(OfflineActivity.this, R.string.str_cann_printer, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if (threadPool != null) {
            threadPool.stopThreadPool();
            threadPool = null;
        }*/
        singleExecutor.shutdown();
        handler.removeCallbacksAndMessages(null);
        SerialPortApi.getInstance().removeResponse();
        MainApplication.getSerialPortUtils().setOnDataReceiveListenerNull();
        if (data != null) {
            data.clear();
        }
        if (setPlate != null) {
            setPlate.clear();
        }
        if (menu_data != null) {
            menu_data.clear();
        }
        if (gainschaUsbUtils != null) {
            gainschaUsbUtils.closeConnect();
            gainschaUsbUtils.release();
        }
        if (usbHelper != null) {
            usbHelper.close();
        }
        clearMytime();
    }
}
