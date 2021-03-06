package com.lianxi.dingtu.dingtu_plate.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.lianxi.dingtu.dingtu_plate.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_plate.app.entity.OfflineExpenseParam;
import com.lianxi.dingtu.dingtu_plate.app.sql.Sql_EMGoodsPayDetail;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/31/2019 08:50
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface MainContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void OfflineExpenseResult(boolean iSuccess);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {

        Observable<BaseResponse<String>> getConfig(String key);

        Observable<BaseResponse> isOfflineExpenseSuccess(OfflineExpenseParam param);
    }
}
