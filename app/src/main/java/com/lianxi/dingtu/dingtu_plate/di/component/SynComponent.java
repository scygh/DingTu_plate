package com.lianxi.dingtu.dingtu_plate.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lianxi.dingtu.dingtu_plate.di.module.SynModule;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.SynContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.activity.SynActivity;


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
@Component(modules = SynModule.class, dependencies = AppComponent.class)
public interface SynComponent {
    void inject(SynActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        SynComponent.Builder view(SynContract.View view);

        SynComponent.Builder appComponent(AppComponent appComponent);

        SynComponent build();
    }
}