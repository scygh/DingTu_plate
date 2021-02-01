package com.lianxi.dingtu.dingtu_plate.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.dingtu_plate.R;
import com.lianxi.dingtu.dingtu_plate.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_plate.app.entity.OfflineExpenseParam;
import com.lianxi.dingtu.dingtu_plate.app.sql.EMGoodsPayDetailRepo;
import com.lianxi.dingtu.dingtu_plate.app.sql.Sql_EMGoodsPayDetail;
import com.lianxi.dingtu.dingtu_plate.app.task.MainTask;
import com.lianxi.dingtu.dingtu_plate.app.task.TaskParams;
import com.lianxi.dingtu.dingtu_plate.di.component.DaggerMainComponent;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.MainContract;
import com.lianxi.dingtu.dingtu_plate.mvp.presenter.MainPresenter;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.adapter.MainPagerAdapter;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.fragment.BaseFragment;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.fragment.GalleryTransformer;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.fragment.MainFragment;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.fragment.SettingFragment;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.blankj.utilcode.util.NetworkUtils.isConnected;
import static com.jess.arms.utils.Preconditions.checkNotNull;
import static com.lianxi.dingtu.dingtu_plate.app.Utils.wx.WxfacepayUtil.isSuccessInfo;


/**
 * ================================================
 * Description:
 * <p>主界面
 * Created by MVPArmsTemplate on 10/31/2019 08:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.offlinepay_record)
    TextView offlinepayRecord;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.companycode)
    TextView companyCode;
    private ArrayList<Sql_EMGoodsPayDetail> payDetailList;
    private EMGoodsPayDetailRepo emGoodsPayDetailRepo;
    ProgressDialog pd1;
    @BindView(R.id.main_vp)
    ViewPager mainViewPager;
    List<BaseFragment> baseFragments;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String a = (String) SpUtils.get(this, AppConstant.Api.ACCOUNT, "");
        account.setText(String.format(" %s", a));
        int code = (int) SpUtils.get(this, AppConstant.Api.COMPANYCODE, 0);
        companyCode.setText(String.format(" %s", code));
        /*Map<String, String> m1 = new HashMap<>();
        try {
            WxPayFace.getInstance().initWxpayface(MainActivity.this, m1, new IWxPayfaceCallback() {
                @Override
                public void response(Map info) throws RemoteException {
                    if (!isSuccessInfo(info)) {
                        return;
                    }
                    //获取子商户号
                    mPresenter.getSubMchId();
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "微信人脸支付初始化异常", Toast.LENGTH_SHORT).show();
        }*/
        baseFragments = new ArrayList<>();
        baseFragments.add(new MainFragment());
        baseFragments.add(new SettingFragment());
        mainViewPager.setOffscreenPageLimit(3);
        mainViewPager.setPageMargin(80);
        mainViewPager.setPageTransformer(true,new GalleryTransformer());
        mainViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), baseFragments));
        updateApk();
    }

    private void updateApk() {
        TaskParams params = new TaskParams();
        MainTask.UpdateTask dbTask = new MainTask.UpdateTask(this, false);
        dbTask.execute(params);
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
    protected void onResume() {
        emGoodsPayDetailRepo = new EMGoodsPayDetailRepo(MainActivity.this);
        payDetailList = emGoodsPayDetailRepo.getEMGoodsListByUPLOADSUCCESS(0);
        offlinepayRecord.setText(payDetailList.size() + " 条");
        if (isConnected()) {
            Log.e(TAG, "scy2" + JSON.toJSONString(payDetailList));
            List<OfflineExpenseParam.OfflineExpenseBean> offlineExpenseBeanList = new ArrayList<>();
            if (payDetailList.size() > 0) {
                for (Sql_EMGoodsPayDetail detail : payDetailList) {
                    OfflineExpenseParam.OfflineExpenseBean offlineExpenseBean = new OfflineExpenseParam.OfflineExpenseBean();
                    offlineExpenseBean.setDeviceID(detail.getDeviceid());
                    offlineExpenseBean.setAmount(detail.getAmount());
                    offlineExpenseBean.setTradeDateTime(detail.getTradedatetime());
                    offlineExpenseBean.setOfflinePayCount(detail.getOfflinepaycount());
                    offlineExpenseBean.setNumber(detail.getNumber());
                    offlineExpenseBean.setPattern(detail.getPattern());
                    offlineExpenseBeanList.add(offlineExpenseBean);
                }
                if (offlineExpenseBeanList.size() > 0) {
                    OfflineExpenseParam param = new OfflineExpenseParam();
                    param.setListOfflineExpense(offlineExpenseBeanList);
                    mPresenter.postPayDetail(param);
                    pd1 = new ProgressDialog(this);
                    pd1.setTitle("提示");
                    pd1.setMessage("上传消费记录中...,请等待");
                    pd1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pd1.setCancelable(true);
                    pd1.setMax(offlineExpenseBeanList.size());
                    pd1.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int i = 0;
                            while (i < offlineExpenseBeanList.size()) {
                                try {
                                    Thread.sleep(1000);
                                    pd1.incrementProgressBy(i);
                                    i++;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            handler.sendEmptyMessage(1);
                        }
                    }).start();
                }
            }
        } else {
            ToastUtils.showShort("无网络连接");
        }
        super.onResume();
    }

    @Override
    public void OfflineExpenseResult(boolean iSuccess) {
        if (iSuccess) {
            for (Sql_EMGoodsPayDetail emGoodsPayDetail : payDetailList) {
                emGoodsPayDetailRepo.delete(emGoodsPayDetail);
            }
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    offlinepayRecord.setText("0 条");
                    pd1.dismiss();
                    ToastUtils.showShort("同步" + payDetailList.size() + "条消费记录成功");
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
