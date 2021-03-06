package com.lianxi.dingtu.dingtu_plate.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.lianxi.dingtu.dingtu_plate.mvp.contract.WritePlateContract;
import com.lianxi.dingtu.dingtu_plate.mvp.model.WritePlateModel;


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
@Module
public abstract class WritePlateModule {

    @Binds
    abstract WritePlateContract.Model bindWritePlateModel(WritePlateModel model);
}