package com.lianxi.dingtu.dingtu_plate.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lianxi.dingtu.dingtu_plate.di.module.OnlineModule;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.OnlineContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.activity.OnlineActivity;


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
@Component(modules = OnlineModule.class, dependencies = AppComponent.class)
public interface OnlineComponent {
    void inject(OnlineActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OnlineComponent.Builder view(OnlineContract.View view);

        OnlineComponent.Builder appComponent(AppComponent appComponent);

        OnlineComponent build();
    }
}