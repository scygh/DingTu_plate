package com.lianxi.dingtu.dingtu_plate.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.lianxi.dingtu.dingtu_plate.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.UserInfoHelper;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_plate.app.entity.OfflineExpenseParam;
import com.lianxi.dingtu.dingtu_plate.app.sql.EMGoodsPayDetailRepo;
import com.lianxi.dingtu.dingtu_plate.app.sql.Sql_EMGoodsPayDetail;
import com.lianxi.dingtu.dingtu_plate.di.component.DaggerMainComponent;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.MainContract;
import com.lianxi.dingtu.dingtu_plate.mvp.presenter.MainPresenter;
import com.lianxi.dingtu.dingtu_plate.R;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
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

    private Intent intent;

    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.companycode)
    TextView companyCode;
    private ArrayList<Sql_EMGoodsPayDetail> payDetailList;
    private EMGoodsPayDetailRepo emGoodsPayDetailRepo;

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
        companyCode.setText(String.format("单位代码 %s", code));

        Map<String, String> m1 = new HashMap<>();
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
        }

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

    @OnClick({R.id.online, R.id.offline, R.id.setup, R.id.writeplate, R.id.syn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.online:
                intent = new Intent(this, OnlineActivity.class);
                startActivity(intent);
                break;
            case R.id.offline:
                intent = new Intent(this, OfflineActivity.class);
                startActivity(intent);
                break;
            case R.id.setup:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.writeplate:
                intent = new Intent(this, WritePlateActivity.class);
                startActivity(intent);
                break;
            case R.id.syn:
                intent = new Intent(this, SynActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    protected void onResume() {
        if (isConnected()) {
            emGoodsPayDetailRepo = new EMGoodsPayDetailRepo(MainActivity.this);
            payDetailList = emGoodsPayDetailRepo.getEMGoodsListByUPLOADSUCCESS(0);
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
                }
            }
        }
        super.onResume();
    }

    @Override
    public void OfflineExpenseResult(boolean iSuccess) {
        ToastUtils.showShort("同步消费记录成功");
        if (iSuccess) {
            for (Sql_EMGoodsPayDetail emGoodsPayDetail : payDetailList) {
                emGoodsPayDetailRepo.update(emGoodsPayDetail);
            }
        }
    }
}
