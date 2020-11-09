package com.lianxi.dingtu.dingtu_plate.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.lianxi.dingtu.dingtu_plate.app.api.UserService;
import com.lianxi.dingtu.dingtu_plate.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTypeTo;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.SynContract;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/31/2019 08:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SynModel extends BaseModel implements SynContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SynModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }
    @Override public Observable<BaseResponse<List<EMGoodsTo.RowsBean.GoodsBean>>> getEMGoods(int state) {
        return mRepositoryManager.obtainRetrofitService(UserService.class).getEMGoods(state);
    }
}