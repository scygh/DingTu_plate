package com.lianxi.dingtu.dingtu_plate.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lianxi.dingtu.dingtu_plate.di.module.OfflineModule;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.OfflineContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.activity.OfflineActivity;


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
@Component(modules = OfflineModule.class, dependencies = AppComponent.class)
public interface OfflineComponent {
    void inject(OfflineActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        OfflineComponent.Builder view(OfflineContract.View view);

        OfflineComponent.Builder appComponent(AppComponent appComponent);

        OfflineComponent build();
    }
}