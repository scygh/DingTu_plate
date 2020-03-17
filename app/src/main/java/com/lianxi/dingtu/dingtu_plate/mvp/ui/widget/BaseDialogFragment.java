package com.lianxi.dingtu.dingtu_plate.mvp.ui.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lianxi.dingtu.dingtu_plate.R;

public abstract class BaseDialogFragment extends DialogFragment {
    View view;

    protected abstract int getResoureId();

    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getResoureId(), container);
        return view;
    }

    @NonNull @Override public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialogTransparent);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
        start();
        initListener();
    }

    protected void initListener() {

    }

    protected void initData() {

    }

    protected void initView() {

    }

    protected void start() {

    }

    @Override public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override public void onResume() {
        super.onResume();
    }
}
