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
import android.speech.tts.TextToSpeech;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.dingtu_plate.R;
import com.lianxi.dingtu.dingtu_plate.app.Utils.AudioUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.GainschaUsbUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.Scanner;
import com.lianxi.dingtu.dingtu_plate.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.StringUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.USBHelper;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_plate.app.base.MainApplication;
import com.lianxi.dingtu.dingtu_plate.app.entity.CardInfoBean;
import com.lianxi.dingtu.dingtu_plate.app.entity.CardInfoTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.GetFacePayAuthInfoParam;
import com.lianxi.dingtu.dingtu_plate.app.entity.PlateCardInfo;
import com.lianxi.dingtu.dingtu_plate.app.entity.QRExpenseParam;
import com.lianxi.dingtu.dingtu_plate.app.entity.QRExpenseTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.SimpleExpenseParam;
import com.lianxi.dingtu.dingtu_plate.app.entity.SimpleExpenseTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.UserGetTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.WxExpenseParam;
import com.lianxi.dingtu.dingtu_plate.app.entity.WxExpenseTo;
import com.lianxi.dingtu.dingtu_plate.app.sql.MenuRepo;
import com.lianxi.dingtu.dingtu_plate.di.component.DaggerOnlineComponent;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.OnlineContract;
import com.lianxi.dingtu.dingtu_plate.mvp.presenter.OnlinePresenter;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.adapter.MenuAdapter;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.adapter.PayGoodsAdapter;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.widget.PayDialog;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android_serialport_api.SerialPortApi;
import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.StringUtils.sub;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.ASK_FACE_PERMIT;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.ASK_FACE_PERMIT_CONTENT;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.ASK_RET_PAGE;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.ASK_RET_PAGE_CONTENT;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.FACE_CODE;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.OPENID;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.PARAMS_APPID;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.PARAMS_APPID_CONTENT;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.PARAMS_AUTHINFO;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.PARAMS_FACE_AUTHTYPE;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.PARAMS_FACE_AUTHTYPE_CONTENT;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.PARAMS_MCH_ID;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.PARAMS_MCH_ID_CONTENT;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.PARAMS_OUT_TRADE_NO;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.PARAMS_REPORT_SUB_MCH_ID;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.PARAMS_STORE_ID;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.PARAMS_TOTAL_FEE;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxChatFacePay.SUB_OPENID;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxfacepayUtil.isSuccessInfo;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxfacepayUtil.showToast;


/**
 * ================================================
 * Description:
 * <p>在线消费界面
 * Created by MVPArmsTemplate on 10/31/2019 08:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class OnlineActivity extends BaseActivity<OnlinePresenter> implements OnlineContract.View, TextToSpeech.OnInitListener {

    @BindView(R.id.online_num)
    TextView goods_num;
    @BindView(R.id.online_price)
    TextView goods_price;
    @BindView(R.id.online_pay_state_success)
    TextView online_pay_state_success;
    @BindView(R.id.online_pay_state_fail)
    TextView online_pay_state_fail;
    @BindView(R.id.online_pay_state_wait)
    TextView online_pay_state_wait;
    @BindView(R.id.online_pay_blance)
    TextView online_pay_blance;
    @BindView(R.id.online_recy)
    RecyclerView mRecyclerView;
    @BindView(R.id.online_right)
    RelativeLayout online_right;
    @BindView(R.id.online_drawerLayout)
    DrawerLayout online_drawerLayout;
    @BindView(R.id.online_recy_menu)
    RecyclerView online_recy_menu;
    @BindView(R.id.pay_operation)
    LinearLayout pay_operation;
    @BindView(R.id.tv_pay_operation)
    TextView tv_pay_operation;
    @BindView(R.id.et_qr)
    EditText et;
    @BindView(R.id.online_pay_btn)
    Button paybutton;
    @BindView(R.id.order_information)
    LinearLayout menuLl;
    private List<EMGoodsTo.RowsBean.GoodsBean> data = new ArrayList<>();//商品列表
    private List<String> menu_data = new ArrayList<>();//挂单列表
    private String menu_time = "";
    private PayGoodsAdapter adapter;
    private MenuAdapter menuAdapter;
    private HashMap<String, String> setPlate = new HashMap<>();//装卡信息的集合
    private boolean havingCard = false;
    private int companyCode;
    private int number = 0;//卡内码
    private String code = "";//单位代码
    private MyTime myTime;//计时器
    private GainschaUsbUtils gainschaUsbUtils;//打印机
    private boolean isGainOpen = false;//打印机是否已经连接

    private USBHelper usbHelper;//usb刷卡
    private Scanner scanner;//usb扫码

    private Double mTotalPrice = 0.0;//总价
    private int deviceID;//设备号
    private MenuRepo menuRepo = new MenuRepo(OnlineActivity.this);//挂单数据库
    //微信人脸的数据
    private String rawdata;
    private String mAuthInfo;
    private boolean isAutopay;//是否开启自动消费
    private boolean isPrint;//是否开启打印

    private int payCount;//支付次数
    SimpleExpenseParam param = new SimpleExpenseParam();//简单消费实体
    private String pwd = "";//支付密码
    private ExecutorService singleExecutor;//线程池

    private static final int UPDATE_MAIN_LISTADNPRICE = 1;
    private static final int UPDATE_MENU_DATA = 2;
    private static final int ISHAVINGCARD = 4;
    private static final int INIT_READCARD_PAY = 5;
    private static final int PAY_SUCCESS = 6;
    private static final int PAYBUTTON_ENABLE = 7;
    private TextToSpeech textToSpeech;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_MAIN_LISTADNPRICE:
                    if (adapter != null) {
                        //更新主列表 更新价格数量
                        adapter.notifyDataSetChanged();
                        int count = 0;
                        double price = 0.0;
                        for (EMGoodsTo.RowsBean.GoodsBean goods : data) {
                            count = count + goods.getCount();
                            price = price + goods.getPrice() * goods.getCount();
                        }
                        goods_num.setText(count + "");
                        goods_price.setText(String.format("%.2f", price));
                        if (isAutopay) {
                            changeToWaitStatus();
                        } else {
                            clearStatus();
                        }
                    }
                    break;
                case UPDATE_MENU_DATA://更新挂单
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
                            openReadCardAndScanQRCode();
                        }
                    }
                    break;
                case INIT_READCARD_PAY:
                    usbHelper.notBreakRead();
                    initReadCardPay();
                    break;
                case PAY_SUCCESS:
                    if (isAutopay) {
                        et.setText("");
                    } else {
                        et.setText("");
                        clearMytime();
                        paybutton.setEnabled(true);
                    }
                    break;
                case PAYBUTTON_ENABLE:
                    String a = msg.getData().getString("deviceConnect");
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private class MyTime extends CountDownTimer {
        public MyTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            online_pay_state_wait.setText("等待支付(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            clearStatus();
            scanner.clearScan();
            handler.sendEmptyMessage(PAY_SUCCESS);
            handler.removeMessages(INIT_READCARD_PAY);//防止重复点击按钮
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOnlineComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_online; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        this.setTitle("在线消费");
        //获取读盘模式 0读取商品 1读取价格
        int plate_read_pattern = (int) SpUtils.get(this, AppConstant.Receipt.PLATE_READ_PATTERN, 0);
        //消费模式 0手动消费 1自动消费
        isAutopay = (boolean) SpUtils.get(this, AppConstant.Receipt.PAYSTATE, false);
        //是否开启打印
        isPrint = (boolean) SpUtils.get(this, AppConstant.Receipt.isPrint, false);
        //1001
        companyCode = (int) SpUtils.get(this, AppConstant.Api.COMPANYCODE, 0);
        //1
        deviceID = Integer.parseInt((String) SpUtils.get(this, AppConstant.Receipt.MAC_NUMBER, "1"));
        //商品列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OnlineActivity.this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PayGoodsAdapter(R.layout.item_goods, data);
        mRecyclerView.setAdapter(adapter);
        //挂单列表
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(OnlineActivity.this, RecyclerView.VERTICAL, false);
        online_recy_menu.setLayoutManager(linearLayoutManager2);
        online_recy_menu.addItemDecoration(new DividerItemDecoration(OnlineActivity.this, DividerItemDecoration.VERTICAL));
        //从数据库获取挂单的集合
        Set<String> set = menuRepo.getGoodsBeanListAllTime();
        menu_data = new ArrayList<>(set);
        menuAdapter = new MenuAdapter(R.layout.item_menu, menu_data);
        online_recy_menu.setAdapter(menuAdapter);
        //菜品数量加减
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.item_btn_plus:
                        data.get(position).setCount(data.get(position).getCount() + 1);
                        handler.sendEmptyMessage(1);
                        break;
                    case R.id.item_btn_reduce:
                        if (data.get(position).getCount() == 1) {
                            AlertDialog dialog = new AlertDialog.Builder(OnlineActivity.this)
                                    .setIcon(R.mipmap.warning)
                                    .setTitle("系统提示")
                                    .setMessage("确定要删除该商品？")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Log.d("SPAA", "onClick: " + setPlate.size());
                                            //移除判断的是否存在的集合
                                            Collection<String> values = setPlate.values();
                                            while (values.contains(data.get(position).getGoodsNo() + "")) {
                                                values.remove(data.get(position).getGoodsNo() + "");
                                            }
                                            Log.d("SPAA", "onClick: " + setPlate.size());
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
                            handler.sendEmptyMessage(1);
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
                handler.sendEmptyMessage(1);
                online_drawerLayout.closeDrawer(online_right);
            }
        });
        menuAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.item_menu_btn) {
                    AlertDialog dialog = new AlertDialog.Builder(OnlineActivity.this)
                            .setIcon(R.mipmap.warning)
                            .setTitle("系统提示：")
                            .setMessage("确定要删除该订单？")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (menu_data.size() > 0) {
                                        menuRepo.delete(menu_data.get(position));
                                    }
                                    handler.sendEmptyMessageDelayed(2, 500);
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

        //初始化线程池，有三个核心线程的线程池
        singleExecutor = Executors.newFixedThreadPool(3);

        //初始化打印机
        /*if (isPrint) {
            gainschaUsbUtils = new GainschaUsbUtils(MainApplication.getGainContext());
            gainschaUsbUtils.setOnGainSchaUsbListener(new GainschaUsbUtils.OnGainSchaUsbListener() {
                @Override
                public void onConnect(String deviceConnect) {
                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("deviceConnect", deviceConnect);
                    message.setData(bundle);
                    message.what = PAYBUTTON_ENABLE;
                    handler.sendMessage(message);
                    if (deviceConnect.equals("已连接")) {
                        isGainOpen = true;
                    } else {
                        isGainOpen = false;
                    }
                }

                @Override
                public void onStatus(String deviceStatus) {
                    Log.d("deviceStatus", "onStatus: " + deviceStatus);
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
                        while (!isGainOpen) {
                            gainschaUsbUtils.openConnect();
                            Thread.sleep(3000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
        }*/


        //初始化音频
        //AudioUtils.getInstance().init(MainApplication.getMainContext());
        textToSpeech = new TextToSpeech(this, this);
        //初始化usb刷卡
        usbHelper = USBHelper.getInstance(MainApplication.getMainContext());
        //初始化串口循环
        initPort(plate_read_pattern);

        //MainActivity中先初始化获取子商户号
        /*WxPayFace.getInstance().getWxpayfaceRawdata(new IWxPayfaceCallback() {
            @Override
            public void response(Map info) throws RemoteException {
                if (!isSuccessInfo(info)) {
                    return;
                }
                showToast("response | getWxpayfaceRawdata");
                rawdata = info.get("rawdata").toString();
            }
        });*/

        //初始化支付状态
        clearStatus();
        //根据消费模式判断支付操作的显示
        if (isAutopay) {
            pay_operation.setVisibility(View.GONE);
            tv_pay_operation.setVisibility(View.GONE);
            openReadCardAndScanQRCode();
        } else {
            pay_operation.setVisibility(View.VISIBLE);
            tv_pay_operation.setVisibility(View.VISIBLE);
        }
        menuLl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    if (data != null && data.size() > 0) {
                        Date date = new Date(System.currentTimeMillis());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = simpleDateFormat.format(date);
                        for (EMGoodsTo.RowsBean.GoodsBean goodsBean : data) {
                            goodsBean.setTime(time);
                            menuRepo.insert(goodsBean);
                        }
                        Toast.makeText(OnlineActivity.this, "订单已挂起：" + time, Toast.LENGTH_SHORT).show();
                        //AudioUtils.getInstance().speakText("挂单成功");
                        speakChinese("挂单成功");
                        handler.sendEmptyMessageDelayed(UPDATE_MENU_DATA, 500);
                        handler.sendEmptyMessage(ISHAVINGCARD);
                    } else {
                        Toast.makeText(OnlineActivity.this, "商品列表为空！", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(OnlineActivity.this, "挂单失败，请重试!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        menuLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(OnlineActivity.this)
                        .setIcon(R.mipmap.warning)
                        .setTitle("系统提示：")
                        .setMessage("确定要清空商品列表？")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                clear();//清理更新列表数据
                                clearStatus();//清空状态
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
        });
    }

    /**
     * descirption: 初始化串口
     */
    public void initPort(int plate_read_pattern) {
        SerialPortApi.initPort();
        SerialPortApi.getInstance().setResponse(new SerialPortApi.SerialPortResponse() {

            @Override
            public void onGetUpTo(String card, PlateCardInfo info) {
                //判断盘子内是否有数据
                card = info.getCardNum();
                if (info.getCompanyCode() == null) return;
                if (info.getGoodsPrice() == 0.0 && info.getDate().equals("00000000")) {
                    //AudioUtils.getInstance().speakText("无数据盘");
                    speakChinese("无数据盘");
                    return;
                }
                //判断盘子是否在有效期内
                try {
                    if (!isDateQualified(info.getDate())) {
                        //AudioUtils.getInstance().speakText("过期餐盘");
                        speakChinese("过期餐盘");
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
                //判断是否是商品模式（0）or价格模式（1）
                if (plate_read_pattern == 0) {
                    //商品模式 询问接口获取
                    mPresenter.getEMGoodsByNum(Integer.parseInt(info.getGoodsNum()));
                } else if (plate_read_pattern == 1) {
                    //价格模式 直接调用方法传入
                    EMGoodsTo.RowsBean.GoodsBean goodsBean = new EMGoodsTo.RowsBean.GoodsBean();
                    goodsBean.setCount(1);
                    goodsBean.setPrice(info.getGoodsPrice());
                    goodsBean.setGoodsName(info.getGoodsPrice() + "元商品");
                    goodsBean.setGoodsNo(Integer.parseInt(info.getGoodsNum()));
                    getEMGoodsByNum(goodsBean);
                }

            }

            @Override
            public void onCheckFail() {

            }

            @Override
            public void onOperatorSuccess(String msg) {
                if (msg.equals(SerialPortApi.HAVECARD)) {//如果读uid模式发现有卡就走大循环
                    havingCard = true;
                    SerialPortApi.openReadPattern(handler);//这个模式就会有卡信息返回
                    changeToSinglePattern(2000);
                }
                if (msg.equals(SerialPortApi.NOCARD)) {//如果读uid模式发现没卡就走小循环
                    havingCard = false;
                    changeToSinglePattern(1000);
                }
            }
        });
        SerialPortApi.singleResponsePattern(handler);//开启循环入口
    }

    /**
     * descirption: 继续循环
     */
    private void changeToSinglePattern(int i) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SerialPortApi.singleResponsePattern(handler);
            }
        }, i);
    }

    /**
     * descirption: 餐盘有效期
     */
    private boolean isDateQualified(String date) throws ParseException {
        int H = Integer.parseInt((String) SpUtils.get(OnlineActivity.this, AppConstant.Receipt.PLATE_INDATE, "0"));//小时
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

    /**
     * descirption:查询后回调，更新列表
     */
    @Override
    public void getEMGoodsByNum(EMGoodsTo.RowsBean.GoodsBean goodsBean) {
        if (goodsBean == null) {
            return;
        }
        int i = 0;
        if (data.size() == 0) {//如果是空的就直接放进去
            goodsBean.setCount(1);
            data.add(goodsBean);
        } else if (data.size() > 0) {//如果不为空就判断是否是已经存在的数量需要增加
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
            if (i != 1) {//如果不重叠 那就直接放入
                goodsBean.setCount(1);
                data.add(goodsBean);
            }
        }
        handler.sendEmptyMessage(UPDATE_MAIN_LISTADNPRICE); //更新主列表 更新价格数量
    }

    @OnClick({R.id.online_pay_btn, R.id.online_wxpay_btn, R.id.online_clear_btn, R.id.online_save_btn, R.id.online_take_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.online_pay_btn:
                if (Double.parseDouble(goods_price.getText().toString()) > 0) {
                    paybutton.setEnabled(false);
                    myTime = new MyTime(30000, 1000);
                    myTime.start();
                    changeToWaitStatus();
                    openReadCardAndScanQRCode();
                } else {
                    speakChinese("请先识别餐盘");
                }
                break;
            case R.id.online_wxpay_btn:
                scanner.clearScan();
                handler.removeMessages(INIT_READCARD_PAY);//防止重复点击按钮
                handler.sendEmptyMessage(6);
                changeToWaitStatus();
                GetFacePayAuthInfoParam param = new GetFacePayAuthInfoParam();
                param.setDeviceID(deviceID);
                param.setStoreId((String) SpUtils.get(OnlineActivity.this, AppConstant.Api.ACCOUNT, ""));
                param.setStoreName((String) SpUtils.get(OnlineActivity.this, AppConstant.Api.ACCOUNT, ""));
                param.setRawData(rawdata);//sdk调用凭证
                param.setAppId(PARAMS_APPID_CONTENT);
                param.setMchId(PARAMS_MCH_ID_CONTENT);
                param.setSubMchId((String) SpUtils.get(OnlineActivity.this, AppConstant.WxFacePay.PARAMS_REPORT_SUT_MCH_ID, ""));//子商户号
                //获取用户信息
                mPresenter.getFacePayAuthInfo(param);
                Log.e(TAG, "response: GetFacePayAuthInfoParam:" + JSON.toJSONString(param));
                break;
            case R.id.online_clear_btn:
                break;
            case R.id.online_save_btn:
                break;
            case R.id.online_take_btn:
                handler.sendEmptyMessageDelayed(UPDATE_MENU_DATA, 500);
                online_drawerLayout.openDrawer(online_right);
                break;
        }
    }

    /**
     * descirption: 清空支付状态和金额
     */
    private void clearStatus() {
        online_pay_state_wait.setVisibility(View.GONE);
        online_pay_state_fail.setVisibility(View.GONE);
        online_pay_state_success.setVisibility(View.GONE);
        online_pay_blance.setVisibility(View.GONE);
    }

    /**
     * descirption: 等待支付状态
     */
    private void changeToWaitStatus() {
        online_pay_state_fail.setVisibility(View.GONE);
        online_pay_state_success.setVisibility(View.GONE);
        online_pay_blance.setVisibility(View.GONE);
        online_pay_state_wait.setVisibility(View.VISIBLE);
    }

    /**
     * descirption: 切换到二维码支付成功状态
     */
    public void changeToSuccessStatus() {
        online_pay_state_wait.setVisibility(View.GONE);
        online_pay_state_fail.setVisibility(View.GONE);
        online_pay_state_success.setVisibility(View.VISIBLE);
        online_pay_blance.setVisibility(View.GONE);
    }

    /**
     * descirption: 切换到支付失败状态
     */
    private void changeTopayFailStatus() {
        online_pay_state_wait.setVisibility(View.GONE);
        online_pay_state_fail.setVisibility(View.VISIBLE);
        online_pay_state_success.setVisibility(View.GONE);
        online_pay_blance.setVisibility(View.GONE);
    }

    /**
     * descirption: 切换到刷卡支付成功状态
     */
    public void changeToPaySuccess(SimpleExpenseTo simpleExpenseTo) {
        online_pay_state_wait.setVisibility(View.GONE);
        online_pay_state_fail.setVisibility(View.GONE);
        online_pay_state_success.setVisibility(View.VISIBLE);
        online_pay_blance.setVisibility(View.VISIBLE);
        online_pay_blance.setText("余额：" + simpleExpenseTo.getExpenseDetail().getBalance());
    }

    /**
     * descirption: 打开usb监听卡和二维码
     */
    private void openReadCardAndScanQRCode() {
        handler.removeMessages(INIT_READCARD_PAY);//防止重复调用initReadCardPay
        initQrPay();
        usbHelper.notBreakRead();
        initReadCardPay();
    }


    /**
     * descirption: 开启usb刷卡
     */
    private void initReadCardPay() {
        singleExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mTotalPrice = Double.parseDouble(goods_price.getText().toString());
                if (mTotalPrice <= 0.00) {//
                    //ToastUtils.showShort("请识别餐盘");
                    handler.sendEmptyMessageDelayed(INIT_READCARD_PAY, 2000);
                    return;
                }
                CardInfoBean cardInfoBean = null;
                cardInfoBean = usbHelper.read_card();
                if (cardInfoBean != null) {
                    Log.d("read_card5", "findRFCardListening: " + JSON.toJSONString(cardInfoBean));
                    if (cardInfoBean.getCash_account() == 3276.8 && cardInfoBean.getNum() == 327680 || cardInfoBean.getCode().equals("0000")) {//如果发现是这些数据表示没有放置卡片
                        handler.sendEmptyMessageDelayed(INIT_READCARD_PAY, 500);
                        return;
                    }
                    usbHelper.PeakNoise();
                    if (cardInfoBean.getCash_account() > mTotalPrice) {
                        Double cash = sub(cardInfoBean.getCash_account(), mTotalPrice);
                        cardInfoBean.setCash_account(cash);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                        cardInfoBean.setSpending_time(formatter.format(new Date(System.currentTimeMillis())));
                        cardInfoBean.setConsumption_num(cardInfoBean.getConsumption_num() + 1);
                        number = cardInfoBean.getNum();
                        Log.d("write_card5", "findRFCardListening: " + JSON.toJSONString(cardInfoBean));
                        if (usbHelper.write_card5(cardInfoBean)) {
                            mPresenter.userGetTo(number);
                        } else {
                            onPayFailure();
                        }
                    } else {
                        speakChinese("余额不足，请充值");
                        onPayFailure();
                    }
                } else {
                    ToastUtils.showShort("没读到卡，刷卡停止");
                }
            }
        });
    }

    /**
     * descirption: 开启usb扫码
     */
    private void initQrPay() {
        scanner = new Scanner(OnlineActivity.this);
        scanner.setOnScanResultCallBack(new Scanner.OnScanResultCallBack() {
            @Override
            public void OnScanSucccess(String result) {
                Log.d("Scanresult", "OnScanSucccess: " + result);
                mTotalPrice = Double.parseDouble(goods_price.getText().toString());
                if (result.length() > 0 && result != null && result != "" && mTotalPrice > 0.00) {
                    mPresenter.onScanQR(result, Double.valueOf(goods_price.getText().toString()), dataToQRExpense(data));
                } else {
                    speakChinese("请先识别餐盘");
                }
            }

            @Override
            public void OnScanFail(String errorMsg) {
                speakChinese("扫码失败");
            }
        });
        scanner.scan(et);
    }


    @Override
    public void onUserGetTo(UserGetTo content) {
        payCount = content.getCard().getPayCount();
        mPresenter.getPaySgetPayKeySwitch2();
    }

    /**
     * descirption: 识别到卡查询后回调返回卡信息
     */
    @Override
    public void onCardInfo(CardInfoTo cardInfoTo) {

    }

    /**
     * descirption: 是否有卡密码
     */
    @Override
    public void creatBill(boolean keyEnabled) {
        if (payCount == -1) {
            //AudioUtils.getInstance().speakText("重新放置卡片");
            speakChinese("请重新放置卡片");
            return;
        }
        if (keyEnabled) {
            createPayDialog();
        } else {
            param.setNumber(number);
            param.setAmount(Float.parseFloat(mTotalPrice + ""));
            param.setDeviceID(deviceID);
            param.setPayCount(payCount + 1);
            param.setPayKey(pwd);
            param.setPattern(4);
            param.setDeviceType(2);
            param.setListGoods(dataToSimpleExpense(data));
            mPresenter.createSimpleExpense(param);
        }
    }

    /**
     * descirption: 消费成功
     */
    @Override
    public void creatSimpleSuccess(SimpleExpenseTo simpleExpenseTo) {
        speakChinese("支付成功");
        changeToPaySuccess(simpleExpenseTo);
        isMenuToDeleteAndUpdate();
        //不管是自动消费还是手动消费，支付成功后扫码都需要关闭在开启，防止刷两次。刷卡成功后自动暂停。
        scanner.clearScan();//暂停扫码
        scanner = null;
        handler.sendEmptyMessage(PAY_SUCCESS);//按模式更新自动手动消费的界面
        handler.sendEmptyMessageDelayed(ISHAVINGCARD, 1000);//当盘子被拿走时自动清空状态和列表，是自动消费则还要重新开启刷卡扫码。
    }

    @Override
    public void onPayFailure() {
        changeTopayFailStatus();
        //不管是自动消费还是手动消费，支付成功后都需要关闭在开启，防止刷两次。
        handler.removeMessages(INIT_READCARD_PAY);
        usbHelper.breakRead();//暂停刷卡
        scanner.clearScan();//暂停扫码
        scanner = null;
        handler.sendEmptyMessage(PAY_SUCCESS);//按模式更新自动手动消费的界面
        handler.sendEmptyMessageDelayed(ISHAVINGCARD, 1000);//当盘子被拿走时自动清空状态和列表，是自动消费则还要重新开启刷卡扫码。
    }

    @Override
    public void onQRPaySuccess(QRExpenseTo qrExpenseTo) {
        speakChinese("支付成功");
        changeToSuccessStatus();
        isMenuToDeleteAndUpdate();
        //不管是自动消费还是手动消费，支付成功后都需要关闭在开启，防止刷两次。
        handler.removeMessages(INIT_READCARD_PAY);
        usbHelper.breakRead();//暂停刷卡
        scanner.clearScan();//暂停扫码
        scanner = null;
        handler.sendEmptyMessage(PAY_SUCCESS);//按模式更新自动手动消费的界面
        handler.sendEmptyMessageDelayed(ISHAVINGCARD, 1000);//当盘子被拿走时自动清空状态和列表，是自动消费则还要重新开启刷卡扫码。
    }

    /**
     * descirption: 如果消费了是挂单，则更新挂单
     */
    public void isMenuToDeleteAndUpdate() {
        if (menu_time != "") {
            menuRepo.delete(menu_time);
            handler.sendEmptyMessageDelayed(UPDATE_MENU_DATA, 500);//更新挂单
        }
    }

    /**
     * descirption: 取消回收计时
     */
    private void clearMytime() {
        if (myTime != null) {
            myTime.cancel();
            myTime = null;
        }
    }

    /**
     * descirption: 获取凭证成功后调用 开始微信人脸识别 识别成功开始微信支付
     */
    @Override
    public void onWxfacePay() {
        showToast("onClick | code ");
        mAuthInfo = (String) SpUtils.get(OnlineActivity.this, AppConstant.WxFacePay.AUTHINFO, "");
        Log.e(TAG, "wxfacepay: mAuthInfo:" + mAuthInfo);
        HashMap params = new HashMap();
        //公众号, 须与调用支付接口时字段一致
        params.put(PARAMS_APPID, PARAMS_APPID_CONTENT);
        //商户号, 须与调用支付接口时字段一致
        params.put(PARAMS_MCH_ID, PARAMS_MCH_ID_CONTENT);
        //子商户号
        String sub_mch_id = (String) SpUtils.get(this, AppConstant.WxFacePay.PARAMS_REPORT_SUT_MCH_ID, "");
        params.put(PARAMS_REPORT_SUB_MCH_ID, sub_mch_id);
        params.put(ASK_RET_PAGE, ASK_RET_PAGE_CONTENT);
        //门店编号
        params.put(PARAMS_STORE_ID, SpUtils.get(OnlineActivity.this, AppConstant.Api.ACCOUNT, ""));
        long timeMillis = System.currentTimeMillis();
        Date date = new Date(timeMillis);
        String time = StringUtils.ConverToString(date);
        //商户订单号，须与调用支付接口时字段一致
        String OUT_TRADE_NO = companyCode + "_" + time + "_" + deviceID;
        params.put(PARAMS_OUT_TRADE_NO, OUT_TRADE_NO);
        //订单金额(数字), 单位分.
//        int price = (int) (mTotalPrice*100);
        int price = 1;

        params.put(PARAMS_TOTAL_FEE, String.valueOf(price));
        showToast(price + "分");
        /**
         * 可选值：
         *  FACEPAY: 人脸凭证，常用于人脸支付
         *  FACEPAY_DELAY: 延迟支付(需联系微信支付开通权限)
         */
        params.put(PARAMS_FACE_AUTHTYPE, PARAMS_FACE_AUTHTYPE_CONTENT);

        params.put(PARAMS_AUTHINFO, mAuthInfo);

        params.put(ASK_FACE_PERMIT, ASK_FACE_PERMIT_CONTENT);
        WxPayFace.getInstance().getWxpayfaceCode(params, new IWxPayfaceCallback() {
            @Override
            public void response(Map info) throws RemoteException {
                if (!isSuccessInfo(info)) {
                    return;
                }
                Log.e("WXface", "response | getWxpayfaceCode");
                final String code = (String) info.get(FACE_CODE);
                final String openid = (String) info.get(OPENID);
                final String sub_openid = (String) info.get(SUB_OPENID);
                showToast("code：" + code);
                showToast("openid：" + openid);
                showToast("sub_openid：" + sub_openid);
                showToast("sub_openid：" + sub_openid);
                showToast("sub_mch_id：" + sub_mch_id);
                showToast("OUT_TRADE_NO：" + OUT_TRADE_NO);
                showToast("getWxpayfaceCode: " + JSON.toJSONString(params));
                WxExpenseParam param = new WxExpenseParam();
                param.setFaceCode(code);
                param.setOpenId(openid);
                param.setTradeNo(OUT_TRADE_NO);
                param.setListGoods(dataToWxExpense(data));
//                param.setAmount(Double.valueOf(tv_total_price.getText().toString()));
                param.setAmount(0.01);
                param.setPattern(4);
                param.setDeviceID(deviceID);
                param.setDeviceType(2);
                showToast("Api/Expense/WeChatFacePay" + JSON.toJSONString(param));
                mPresenter.onExpenseWxfacepay(param);
            }
        });
    }

    /**
     * descirption: 微信人脸支付成功后打印信息 释放 刷新
     */
    @Override
    public void onWxfacePaySuccess(WxExpenseTo wxExpenseTo) {
        //AudioUtils.getInstance().speakText("支付成功");
        WxPayFace.getInstance().releaseWxpayface(OnlineActivity.this);
        changeToSuccessStatus();
        isMenuToDeleteAndUpdate();
        /*if (isPrint){
            Printing();
        }*/
        handler.sendEmptyMessageDelayed(ISHAVINGCARD, 3000);
    }

    /**
     * descirption: 密码框
     */
    PayDialog payDialog = null;

    void createPayDialog() {
        if (payDialog == null) {
            payDialog = new PayDialog(this);
            payDialog.setPasswordCallback(new PayDialog.PasswordCallback() {
                @Override
                public void callback(String password) {
                    pwd = password;
                    payDialog.dismiss();
                    param.setNumber(number);
                    param.setAmount(Float.parseFloat(mTotalPrice + ""));
                    param.setDeviceID(deviceID);
                    param.setPayCount(payCount + 1);
                    param.setPayKey(pwd);
                    param.setPattern(4);
                    param.setDeviceType(2);
                    param.setListGoods(dataToSimpleExpense(data));
                    mPresenter.createSimpleExpense(param);
                }
            });
        }
        payDialog.clearPasswordText();
        payDialog.setMoney(goods_price.getText().toString().trim());
        payDialog.show();
        payDialog.setCancelable(false);
        payDialog.setCanceledOnTouchOutside(false);
    }

    private List<WxExpenseParam.ListGoodsBean> dataToWxExpense(List<EMGoodsTo.RowsBean.GoodsBean> data) {
        List<WxExpenseParam.ListGoodsBean> listGoodsBeans = new ArrayList<>();
        for (EMGoodsTo.RowsBean.GoodsBean goodsBean : data) {
            WxExpenseParam.ListGoodsBean listGoodsBean = new WxExpenseParam.ListGoodsBean();
            listGoodsBean.setGoodsNo(goodsBean.getGoodsNo());
            listGoodsBean.setCount(goodsBean.getCount());
            listGoodsBeans.add(listGoodsBean);
        }
        return listGoodsBeans;
    }

    private List<SimpleExpenseParam.ListGoodsBean> dataToSimpleExpense(List<EMGoodsTo.RowsBean.GoodsBean> data) {
        List<SimpleExpenseParam.ListGoodsBean> listGoodsBeans = new ArrayList<>();
        for (EMGoodsTo.RowsBean.GoodsBean goodsBean : data) {
            SimpleExpenseParam.ListGoodsBean listGoodsBean = new SimpleExpenseParam.ListGoodsBean();
            listGoodsBean.setGoodsNo(goodsBean.getGoodsNo());
            listGoodsBean.setCount(goodsBean.getCount());
            listGoodsBeans.add(listGoodsBean);
        }
        return listGoodsBeans;
    }

    private List<QRExpenseParam.ListGoodsBean> dataToQRExpense(List<EMGoodsTo.RowsBean.GoodsBean> data) {
        List<QRExpenseParam.ListGoodsBean> listGoodsBeans = new ArrayList<>();
        for (EMGoodsTo.RowsBean.GoodsBean goodsBean : data) {
            QRExpenseParam.ListGoodsBean listGoodsBean = new QRExpenseParam.ListGoodsBean();
            listGoodsBean.setGoodsNo(goodsBean.getGoodsNo());
            listGoodsBean.setCount(goodsBean.getCount());
            listGoodsBeans.add(listGoodsBean);
        }
        return listGoodsBeans;
    }

    /**
     * descirption: 新的USB打印机打印方法
     */
    public void Printing() {
        if (isGainOpen) {
            sendMsg(0);
        } else {
            ToastUtils.showShort("打印机未连接");
        }
    }

    public void sendMsg(long time) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gainschaUsbUtils.sendReceipt(data, OnlineActivity.this);
            }
        }, time);
    }

    /**
     * descirption: 清空卡信息、列表对象 刷新
     */
    private void clear() {
        setPlate.clear();
        data.clear();
        handler.sendEmptyMessage(UPDATE_MAIN_LISTADNPRICE);
        online_pay_state_success.setVisibility(View.GONE);
        online_pay_blance.setVisibility(View.GONE);
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

    private void speakChinese(String msg) {
        if (textToSpeech != null && !textToSpeech.isSpeaking()) {
            // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            textToSpeech.setPitch(0.5f);
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
        super.onDestroy();
        if (data != null) {
            data.clear();
        }
        if (setPlate != null) {
            setPlate.clear();
        }
        if (menu_data != null) {
            menu_data.clear();
        }
        /*if (threadPool != null) {
            threadPool.stopThreadPool();
            threadPool = null;
        }*/
        if (textToSpeech != null) {
            textToSpeech.shutdown();
            textToSpeech.stop();
            textToSpeech = null;
        }
        if (gainschaUsbUtils != null) {
            gainschaUsbUtils.release();
        }
        if (scanner != null) {
            scanner.clearScan();
            scanner = null;
        }
        if (usbHelper != null) {
            usbHelper.breakRead();
            usbHelper = null;
        }
        clearMytime();
        singleExecutor.shutdown();
        handler.removeCallbacksAndMessages(null);
        SerialPortApi.getInstance().removeResponse();
        MainApplication.getSerialPortUtils().setOnDataReceiveListenerNull();
    }

   /*private ThreadPool threadPool;

   //打印小票
    public void Printing() {
        Log.e(TAG, "onActivityResult: 开始连接打印机");
        //串口连接
        *//* 获取波特率 *//*
        int baudrate = 9600;
        *//* 获取串口号 *//*
        String path = "/dev/ttyS3";

        Log.e(TAG, "onViewClicked: baudrate:" + baudrate + "  path:" + path);
        if (baudrate != 0 && !TextUtils.isEmpty(path)) {
            *//* 初始化DeviceConnFactoryManager *//*
            new DeviceConnFactoryManager.Build()
                    *//* 设置连接方式 *//*
                    .setConnMethod(DeviceConnFactoryManager.CONN_METHOD.SERIAL_PORT)
                    .setContext(OnlineActivity.this)
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
                    PrintingTicketsUtil.sendReceiptWithResponse(OnlineActivity.this, data, id, Balance);
                } else {
                    mHandler.obtainMessage(PRINTER_COMMAND_ERROR).sendToTarget();
                }
            }
        });
    }

    private static final int CONN_PRINTER = 0x12;
    *//**
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
                        Toast.makeText(OnlineActivity.this, R.string.str_disconnect_success, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case PRINTER_COMMAND_ERROR:
                    Toast.makeText(OnlineActivity.this, R.string.str_choice_printer_command, Toast.LENGTH_SHORT).show();
                    break;
                case CONN_PRINTER:
                    Toast.makeText(OnlineActivity.this, R.string.str_cann_printer, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };*/

}
