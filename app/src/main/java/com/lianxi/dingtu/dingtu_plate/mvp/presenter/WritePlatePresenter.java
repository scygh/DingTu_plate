package com.lianxi.dingtu.dingtu_plate.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.lianxi.dingtu.dingtu_plate.app.Utils.RxUtils;
import com.lianxi.dingtu.dingtu_plate.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTypeTo;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.WritePlateContract;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/31/2019 08:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class WritePlatePresenter extends BasePresenter<WritePlateContract.Model, WritePlateContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public WritePlatePresenter(WritePlateContract.Model model, WritePlateContract.View rootView) {
        super(model, rootView);
    }

    public void getEmGoodsTypeTo(String state) {
        mModel.onEmGoodsTypeTo(state)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<EMGoodsTypeTo>>>(mErrorHandler) {
                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onFailed();
                    }

                    @Override
                    public void onNext(BaseResponse<List<EMGoodsTypeTo>> getEMGoods) {
                        if (getEMGoods != null && getEMGoods.getStatusCode() == 200) {
                            mRootView.onEmGoodsTypeTo(getEMGoods.getContent());
                        }
                    }
                });
    }

    public void getEMGoods(String type) {
        mModel.onEmGoodsTo(1,50,type)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<EMGoodsTo>>(mErrorHandler) {
                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.onFailed();
                    }

                    @Override
                    public void onNext(BaseResponse<EMGoodsTo> getDetailList) {
                        if (getDetailList != null && getDetailList.getStatusCode() == 200) {
                            mRootView.onEmGoodsTo(getDetailList.getContent());
                        }
                    }
                });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}
