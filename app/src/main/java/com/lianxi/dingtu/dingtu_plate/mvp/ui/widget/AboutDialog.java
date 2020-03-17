package com.lianxi.dingtu.dingtu_plate.mvp.ui.widget;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.dingtu_plate.R;
import com.lianxi.dingtu.dingtu_plate.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.UserInfoHelper;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.activity.LoginActivity;

public class AboutDialog extends BaseDialogFragment {

    public static AboutDialog newInstance() {
        AboutDialog aboutDialog = new AboutDialog();
        return aboutDialog;
    }

    @Override protected int getResoureId() {
        return R.layout.dialog_about;
    }


    @Override protected void initListener() {
        super.initListener();
        view.findViewById(R.id.mBtnLoginOut).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                SpUtils.put(getActivity(), AppConstant.Api.PASSWORD, "");
                SpUtils.put(getActivity(), AppConstant.Api.TOKEN, "");
                SpUtils.put(getActivity(), AppConstant.Card.KEY, "");
                UserInfoHelper mUserHelper = UserInfoHelper.getInstance(getActivity());
                mUserHelper.updateLogin(false);
                mUserHelper.updateUser(null);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                ArmsUtils.obtainAppComponentFromContext(getActivity()).appManager().killAll(LoginActivity.class);
                getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
        view.findViewById(R.id.mIvClose).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                dismiss();
            }
        });
    }

    @NonNull @Override public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialogDimEnabledFalse);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override protected void initView() {
        super.initView();
    }

    @Override public void onResume() {
        super.onResume();
        Dialog dialog = this.getDialog();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = ArmsUtils.dip2px(this.getContext(), 280.0F);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }
}
