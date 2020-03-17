package com.lianxi.dingtu.dingtu_plate.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.jess.arms.utils.RxLifecycleUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_plate.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_plate.app.entity.UserInfoTo;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.LoginContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/30/2019 15:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
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

    public void login(String name, String password) {
        mModel.login(name, password, "")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserInfoTo>>(mErrorHandler) {
                    @Override public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.hideLoading();
                    }

                    @Override public void onNext(BaseResponse<UserInfoTo> userInfoToBaseResponse) {
                        if (userInfoToBaseResponse.isSuccess()) {
                            if (userInfoToBaseResponse.getContent()!=null) {
                                SpUtils.put(mApplication, AppConstant.Api.TOKEN, userInfoToBaseResponse.getContent().getAccessToken());
                                SpUtils.put(mApplication, AppConstant.Api.COMPANYCODE, userInfoToBaseResponse.getContent().getCompanyCode());
                                SpUtils.put(mApplication, AppConstant.Api.ACCOUNT, userInfoToBaseResponse.getContent().getAccount());
                                getCardPwd(userInfoToBaseResponse.getContent());
                            }
                        }
                    }
                });
    }

    private void getCardPwd(UserInfoTo userInfoTo) {
        mModel.getCardPassword()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(mErrorHandler) {
                    @Override public void onNext(BaseResponse<String> stringBaseResponse) {
                        if (stringBaseResponse.isSuccess()) {
                            if(!TextUtils.isEmpty(stringBaseResponse.getMessage())){
                                SpUtils.put(mApplication, AppConstant.Card.KEY, stringBaseResponse.getContent());
                                Log.e(TAG, "onNext: "+ stringBaseResponse.getContent());
                                mRootView.onLogin(userInfoTo);
                            }

                        }
                    }
                });
    }

}
