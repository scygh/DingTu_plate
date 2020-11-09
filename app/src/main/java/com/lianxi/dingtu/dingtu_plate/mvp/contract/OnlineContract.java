package com.lianxi.dingtu.dingtu_plate.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
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

import io.reactivex.Completable;
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
public interface OnlineContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void getEMGoodsByNum(EMGoodsTo.RowsBean.GoodsBean goodsBean);

        void onCardInfo(CardInfoTo cardInfoTo);

        void creatBill(boolean keyEnabled);

        void creatSimpleSuccess(SimpleExpenseTo simpleExpenseTo);

        void onPayFailure();

        void onQRPaySuccess(QRExpenseTo qrExpenseTo);

        void onWxfacePay();

        void onWxfacePaySuccess(WxExpenseTo wxExpenseTo);

    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<EMGoodsTo.RowsBean.GoodsBean>> getEMGoodsByNum(int num);

        Observable<BaseResponse<CardInfoTo>> getByNumber(int number);

        Observable<BaseResponse<KeySwitchTo>> getEMDevice(int id);

        Observable<BaseResponse<SimpleExpenseTo>> createSimpleExpense(SimpleExpenseParam param);

        Observable<BaseResponse<QRReadTo>> getQRRead(String qrCodeContent, int deviceID);

        Observable<BaseResponse<QRExpenseTo>> addQRExpense(QRExpenseParam param);

        Observable<BaseResponse<WxExpenseTo>> addWxFaceExpense(WxExpenseParam param);

        Observable<BaseResponse<GetFacePayAuthInfoTo>> getFacePayAuthInfo(GetFacePayAuthInfoParam param);

    }
}
