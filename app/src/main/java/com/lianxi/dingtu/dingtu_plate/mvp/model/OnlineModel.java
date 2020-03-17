package com.lianxi.dingtu.dingtu_plate.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lianxi.dingtu.dingtu_plate.app.api.UserService;
import com.lianxi.dingtu.dingtu_plate.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_plate.app.entity.CardInfoTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.GetFacePayAuthInfoParam;
import com.lianxi.dingtu.dingtu_plate.app.entity.GetFacePayAuthInfoTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.KeySwitchTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.QRExpenseParam;
import com.lianxi.dingtu.dingtu_plate.app.entity.QRExpenseTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.QRReadTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.SimpleExpenseParam;
import com.lianxi.dingtu.dingtu_plate.app.entity.SimpleExpenseTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.WxExpenseParam;
import com.lianxi.dingtu.dingtu_plate.app.entity.WxExpenseTo;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.OnlineContract;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/31/2019 08:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class OnlineModel extends BaseModel implements OnlineContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OnlineModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
    @Override
    public Observable<BaseResponse<EMGoodsTo.GoodsBean>> getEMGoodsByNum(int num) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getEMGoodsByNum(num);
    }

    @Override
    public Observable<BaseResponse<CardInfoTo>> getByNumber(int number) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getByNumber(number);
    }

    @Override
    public Observable<BaseResponse<KeySwitchTo>> getEMDevice(int id) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getEMDevice(id);
    }

    @Override public Observable<BaseResponse<SimpleExpenseTo>> createSimpleExpense(SimpleExpenseParam param) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).createSimpleExpense(param);
    }
    @Override public Observable<BaseResponse<QRReadTo>> getQRRead(String qrCodeContent, int deviceID) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getQRRead(qrCodeContent, deviceID);
    }

    @Override public Observable<BaseResponse<QRExpenseTo>> addQRExpense(QRExpenseParam param) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).addQRExpense(param);
    }

    @Override public Observable<BaseResponse<WxExpenseTo>> addWxFaceExpense(WxExpenseParam param) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).addWxFacePayExpense(param);
    }

    @Override
    public Observable<BaseResponse<GetFacePayAuthInfoTo>> getFacePayAuthInfo(GetFacePayAuthInfoParam param) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getFacePayAuthInfo(param);
    }


}