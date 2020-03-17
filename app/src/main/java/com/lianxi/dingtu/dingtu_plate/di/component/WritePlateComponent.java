package com.lianxi.dingtu.dingtu_plate.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.lianxi.dingtu.dingtu_plate.di.module.WritePlateModule;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.WritePlateContract;

import com.jess.arms.di.scope.ActivityScope;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.activity.WritePlateActivity;


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
@Component(modules = WritePlateModule.class, dependencies = AppComponent.class)
public interface WritePlateComponent {
    void inject(WritePlateActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        WritePlateComponent.Builder view(WritePlateContract.View view);

        WritePlateComponent.Builder appComponent(AppComponent appComponent);

        WritePlateComponent build();
    }
}