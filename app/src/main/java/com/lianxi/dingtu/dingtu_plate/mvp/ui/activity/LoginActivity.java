package com.lianxi.dingtu.dingtu_plate.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.lianxi.dingtu.dingtu_plate.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.UserInfoHelper;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_plate.app.entity.UserInfoTo;
import com.lianxi.dingtu.dingtu_plate.di.component.DaggerLoginComponent;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.LoginContract;
import com.lianxi.dingtu.dingtu_plate.mvp.presenter.LoginPresenter;

import com.lianxi.dingtu.dingtu_plate.R;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.widget.LoadDialog;


import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>登录界面
 * Created by MVPArmsTemplate on 10/30/2019 15:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    LoadDialog loadDialog;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.iv_clean_phone)
    ImageView ivCleanPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.clean_password)
    ImageView cleanPassword;
    @BindView(R.id.iv_show_pwd)
    ImageView ivShowPwd;
    @BindView(R.id.cbIsRememberPass)
    CheckBox cbIsRememberPass;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private boolean flag = false;
    private SharedPreferences sharedPreferences;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        loadDialog = LoadDialog.getInstance();
        loadDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.load_dialog);
        sharedPreferences = getSharedPreferences("remenberpass", Context.MODE_PRIVATE);
        boolean isRemenber = sharedPreferences.getBoolean("remenberpass", false);
        if (isRemenber) {
            String name = sharedPreferences.getString("name", "");
            String pass = sharedPreferences.getString("pass", "");
            etUsername.setText(name);
            etUsername.requestFocus();
            etPassword.setText(pass);
            cbIsRememberPass.setChecked(true);
        }
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && ivCleanPhone.getVisibility() == View.GONE) {
                    ivCleanPhone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    ivCleanPhone.setVisibility(View.GONE);
                }
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && cleanPassword.getVisibility() == View.GONE) {
                    cleanPassword.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    cleanPassword.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty()) return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    s.delete(temp.length() - 1, temp.length());
                    etPassword.setSelection(s.length());
                }
            }
        });
    }

    @Override
    public void showLoading() {
        loadDialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void hideLoading() {
        loadDialog.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @OnClick({R.id.et_username, R.id.iv_clean_phone, R.id.et_password, R.id.clean_password, R.id.iv_show_pwd, R.id.cbIsRememberPass, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_username:

                break;
            case R.id.et_password:

                break;
            case R.id.iv_clean_phone:
                etUsername.setText("");
                break;
            case R.id.clean_password:
                etPassword.setText("");
                break;
            case R.id.iv_show_pwd:
                if (flag == true) {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    ivShowPwd.setImageResource(R.mipmap.pass_gone);
                    flag = false;
                } else {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    ivShowPwd.setImageResource(R.mipmap.pass_visuable);
                    flag = true;
                }
                String pwd = etPassword.getText().toString();
                if (!TextUtils.isEmpty(pwd)) etPassword.setSelection(pwd.length());
                break;
            case R.id.cbIsRememberPass:

                break;
            case R.id.btn_login:
                if (checking())
                    return;
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                mPresenter.login(username, password);
                //mPresenter.login("正式环境测试", "123456");
                break;
        }
    }

    @Override
    public void onLogin(UserInfoTo userInfoTo) {
        UserInfoHelper mUserHelper = UserInfoHelper.getInstance(getApplication());
        SpUtils.put(this, AppConstant.Api.PASSWORD, etPassword.getText().toString().trim());
        mUserHelper.updateUser(userInfoTo);
        mUserHelper.updateLogin(true);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (cbIsRememberPass.isChecked()) {
            editor.putBoolean("remenberpass", true);
            editor.putString("name", etUsername.getText().toString().trim());
            editor.putString("pass", etPassword.getText().toString().trim());
        } else {
            editor.clear();
        }
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }



    private boolean checking() {
        if (TextUtils.isEmpty(etUsername.getText())) {
            Toast.makeText(this, "似乎您忘记输入账号", Toast.LENGTH_LONG).show();
            return true;
        } else if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            Toast.makeText(this, "似乎您忘记输入密码", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
}