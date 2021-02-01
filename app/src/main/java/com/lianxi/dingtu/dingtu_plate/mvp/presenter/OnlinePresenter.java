package com.lianxi.dingtu.dingtu_plate.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.RxUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;
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
import com.lianxi.dingtu.dingtu_plate.app.entity.UserGetTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.WxExpenseParam;
import com.lianxi.dingtu.dingtu_plate.app.entity.WxExpenseTo;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.OnlineContract;
import com.tencent.wxpayface.WxPayFace;

import java.util.List;


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
public class OnlinePresenter extends BasePresenter<OnlineContract.Model, OnlineContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public OnlinePresenter(OnlineContract.Model model, OnlineContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void getEMGoodsByNum(int num) {
        mModel.getEMGoodsByNum(num)
                .observeOn(Schedulers.io())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<EMGoodsTo.RowsBean.GoodsBean>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<EMGoodsTo.RowsBean.GoodsBean> listBaseResponse) {
                        if (listBaseResponse.isSuccess()) {
                            mRootView.getEMGoodsByNum(listBaseResponse.getContent());
                        } else {
                            mRootView.showMessage(listBaseResponse.getMessage());
                            mRootView.showMessage("错误");
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage("错误");
                    }
                });
    }

    public void onByNumber(int number) {
        mModel.getByNumber(number)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<CardInfoTo>>(mErrorHandler) {

                    @Override
                    public void onNext(BaseResponse<CardInfoTo> cardInfoToBaseResponse) {
                        if (cardInfoToBaseResponse.isSuccess()) {
                            mRootView.onCardInfo(cardInfoToBaseResponse.getContent());
                        } else {
                            mRootView.showMessage(cardInfoToBaseResponse.getMessage());
                            mRootView.onPayFailure();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mRootView.onPayFailure();
                        Log.e(TAG, "onNext: 支付失败  " + t);
                    }
                });
    }

    public void userGetTo(int number) {
        mModel.userGetTo(number)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new Observer<BaseResponse<UserGetTo>>() {
                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<UserGetTo> readCardToBaseResponse) {
                        if (readCardToBaseResponse.getStatusCode() != 200) {
                            mRootView.showMessage(readCardToBaseResponse.getMessage());
                        } else {
                            if (readCardToBaseResponse.isSuccess())
                                if (readCardToBaseResponse.getContent() != null)
                                    mRootView.onUserGetTo(readCardToBaseResponse.getContent());
                        }
                    }

                });
    }

    public void getPaySgetPayKeySwitch2() {
        String _device = (String) SpUtils.get(mApplication, AppConstant.Receipt.MAC_NUMBER, "1");
        int id = Integer.valueOf(TextUtils.isEmpty(_device) ? "1" : _device);
        mModel.getEMDevice(id)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<KeySwitchTo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<KeySwitchTo> keySwitchToBaseResponse) {
                        if (keySwitchToBaseResponse.isSuccess()) {
                            if (keySwitchToBaseResponse.getContent() != null)
                                mRootView.creatBill(keySwitchToBaseResponse.getContent().isKeyEnabled());
                        } else {
                            mRootView.showMessage(keySwitchToBaseResponse.getMessage());
                            mRootView.onPayFailure();

                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mRootView.onPayFailure();
                        Log.e(TAG, "onNext: 支付失败  " + t);
                    }
                });
    }

    public void createSimpleExpense(SimpleExpenseParam param) {
        mModel.createSimpleExpense(param)
                .compose(RxUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<SimpleExpenseTo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<SimpleExpenseTo> simpleExpenseToBaseResponse) {
                        if (simpleExpenseToBaseResponse.isSuccess()) {
                            if (simpleExpenseToBaseResponse.getContent() != null)
                                mRootView.creatSimpleSuccess(simpleExpenseToBaseResponse.getContent());
                        } else {
                            mRootView.showMessage(simpleExpenseToBaseResponse.getMessage());
                            mRootView.onPayFailure();
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        mRootView.hideLoading();
                        mRootView.onPayFailure();
                    }
                });
    }

    public void onScanQR(String qRcode, Double amount, List<QRExpenseParam.ListGoodsBean> dataToQRExpense) {
        String deviceID = (String) SpUtils.get(mApplication, AppConstant.Receipt.MAC_NUMBER, "1");
        int id = Integer.valueOf(TextUtils.isEmpty(deviceID) ? "1" : deviceID);
        mModel.getQRRead(qRcode, id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<QRReadTo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<QRReadTo> qrReadToBaseResponse) {
                        if (qrReadToBaseResponse.isSuccess()) {
                            QRExpenseParam param = new QRExpenseParam();
                            param.setQRContent(qRcode);
                            param.setNumber(qrReadToBaseResponse.getContent().getNumber());
                            param.setAmount(amount);
                            param.setPattern(4);
                            param.setPayCount(qrReadToBaseResponse.getContent().getPayCount());
                            param.setDeviceID(id);
                            param.setDeviceType(2);
                            param.setQRType(qrReadToBaseResponse.getContent().getQRType());
                            param.setListGoods(dataToQRExpense);
                            onExpenseQR(param);
                        } else {
                            mRootView.showMessage(qrReadToBaseResponse.getMessage());
                            mRootView.onPayFailure();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mRootView.hideLoading();
                        mRootView.onPayFailure();
                    }
                });
    }

    private void onExpenseQR(QRExpenseParam param) {
        mModel.addQRExpense(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<QRExpenseTo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<QRExpenseTo> qrExpenseToBaseResponse) {

                        if (qrExpenseToBaseResponse.isSuccess()) {
                            mRootView.onQRPaySuccess(qrExpenseToBaseResponse.getContent());
                        } else {
                            mRootView.showMessage(qrExpenseToBaseResponse.getMessage());
                            mRootView.onPayFailure();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mRootView.onPayFailure();
                    }
                });
    }

    public void getFacePayAuthInfo(GetFacePayAuthInfoParam param) {
        mModel.getFacePayAuthInfo(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<GetFacePayAuthInfoTo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<GetFacePayAuthInfoTo> getFacePayAuthInfoToBaseResponse) {
                        if (getFacePayAuthInfoToBaseResponse.isSuccess()) {
                            if (!TextUtils.isEmpty(getFacePayAuthInfoToBaseResponse.getMessage())) {
                                SpUtils.put(mApplication, AppConstant.WxFacePay.AUTHINFO, getFacePayAuthInfoToBaseResponse.getContent().getAuthInfo());
                                Log.e(TAG, "onNext: AUTHINFO    " + getFacePayAuthInfoToBaseResponse.getContent().getAuthInfo());
                                mRootView.onWxfacePay();
                            }
                        } else {
                            mRootView.showMessage(getFacePayAuthInfoToBaseResponse.getMessage());
                            mRootView.onPayFailure();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mRootView.onPayFailure();
                        Log.e(TAG, "onNext: 支付失败  " + t);
                    }
                });
    }

    public void onExpenseWxfacepay(WxExpenseParam param) {
        mModel.addWxFaceExpense(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<WxExpenseTo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<WxExpenseTo> wxExpenseToBaseResponse) {
                        if (wxExpenseToBaseResponse.isSuccess()) {
                            mRootView.onWxfacePaySuccess(wxExpenseToBaseResponse.getContent());
                            Log.e(TAG, "onNext: " + JSON.toJSONString(wxExpenseToBaseResponse.getContent()));
                            Log.e(TAG, "onNext: 支付成功");
                        } else {
                            mRootView.showMessage(wxExpenseToBaseResponse.getMessage());
                            mRootView.onPayFailure();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        mRootView.onPayFailure();
                        Log.e(TAG, "onNext: 支付失败  " + t);
                    }
                });
    }
}
