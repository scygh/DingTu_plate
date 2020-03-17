package com.lianxi.dingtu.dingtu_plate.mvp.ui.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.dingtu_plate.R;
import com.lianxi.dingtu.dingtu_plate.app.Utils.AudioUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.DataCleanManager;
import com.lianxi.dingtu.dingtu_plate.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_plate.app.task.MainTask;
import com.lianxi.dingtu.dingtu_plate.app.task.TaskParams;
import com.lianxi.dingtu.dingtu_plate.di.component.DaggerSettingsComponent;
import com.lianxi.dingtu.dingtu_plate.mvp.contract.SettingsContract;
import com.lianxi.dingtu.dingtu_plate.mvp.presenter.SettingsPresenter;
import com.lianxi.dingtu.dingtu_plate.mvp.ui.widget.AboutDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 11/08/2019 16:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SettingsActivity extends BaseActivity<SettingsPresenter> implements SettingsContract.View {

    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mac_num)
    TextView macNum;
    @BindView(R.id.switch_print)
    Switch switchPrint;
    @BindView(R.id.plate_indate)
    TextView plateIndate;
    @BindView(R.id.spinner_pattern)
    Spinner spinnerPattern;
    @BindView(R.id.tv_head)
    TextView tvHead;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    SeekBar sbVolume;
    @BindView(R.id.cache)
    TextView cache;
    TextView version;
    TextView exit;
    @BindView(R.id.auto_pay)
    Switch autoPay;
    private int maxVolume, currentVolume;
    private int pic_max_size = 1000;
    private int which_one = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSettingsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_settings; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        sbVolume = findViewById(R.id.sounds_bar);
        version = findViewById(R.id.version);
        exit = findViewById(R.id.exit);
        version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskParams params = new TaskParams();
                MainTask.UpdateTask dbTask = new MainTask.UpdateTask(SettingsActivity.this);
                dbTask.execute(params);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutDialog.newInstance().show(getSupportFragmentManager(), "unlogin");
            }
        });
        spinnerPattern.setAdapter(ArrayAdapter.createFromResource(this, R.array.read_plate_pattern, R.layout.spinner_item));
        spinnerPattern.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpUtils.put(SettingsActivity.this, AppConstant.Receipt.PLATE_READ_PATTERN, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        boolean isPrint = (boolean) SpUtils.get(this, AppConstant.Receipt.isPrint, false);
        switchPrint.setChecked(isPrint);
        switchPrint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SpUtils.put(SettingsActivity.this, AppConstant.Print.isPrint, b);
            }
        });

        boolean isAutopay = (boolean) SpUtils.get(this, AppConstant.Receipt.PAYSTATE, false);
        autoPay.setChecked(isAutopay);
        autoPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SpUtils.put(SettingsActivity.this, AppConstant.Receipt.PAYSTATE, b);
            }
        });
        String no = (String) SpUtils.get(this, AppConstant.Receipt.MAC_NUMBER, "");
        if (!TextUtils.isEmpty(no)) {
            macNum.setText(no);
        }
        String pt = (String) SpUtils.get(this, AppConstant.Receipt.PLATE_INDATE, "");
        if (!TextUtils.isEmpty(pt)) {
            plateIndate.setText(pt);
        }
        int prp = (int) SpUtils.get(this, AppConstant.Receipt.PLATE_READ_PATTERN, 0);
        if (prp == 0) {
            spinnerPattern.setSelection(0);
        } else {
            spinnerPattern.setSelection(1);
        }

        String name = (String) SpUtils.get(this, AppConstant.Receipt.NAME, "");
        if (!TextUtils.isEmpty(name)) {
            tvHead.setText(name);
        }
        String address = (String) SpUtils.get(this, AppConstant.Receipt.ADDRESS, "");
        if (!TextUtils.isEmpty(address)) {
            tvAddress.setText(address);
        }
        String phone = (String) SpUtils.get(this, AppConstant.Receipt.PHONE, "");
        if (!TextUtils.isEmpty(phone)) {
            tvPhone.setText(phone);
        }

        //初始化音频管理器
        AudioManager mAudioManager = (AudioManager) this.getSystemService(AUDIO_SERVICE);

        //获取系统最大音量
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        // 获取设备当前音量
        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        Log.d("scy", "initData: " + maxVolume);
        // 设置seekbar的最大值
        sbVolume.setMax(maxVolume);

        // 显示音量
        sbVolume.setProgress(currentVolume);

        sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);  //获取当前值
                sbVolume.setProgress(currentVolume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            cache.setText("清除缓存（" + DataCleanManager.getTotalCacheSize(this) + "）");
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    @OnClick({R.id.mac_num, R.id.plate_indate, R.id.tv_head, R.id.tv_address, R.id.tv_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mac_num:
                new MaterialDialog.Builder(this)
                        .title("修改机器号")
                        .inputType(InputType.TYPE_CLASS_PHONE)
                        .positiveText("确定")
                        .positiveColorRes(R.color.colorPrimaryText)
                        .negativeText("取消")
                        .negativeColorRes(R.color.colorPrimaryText)
                        .input(
                                "",
                                macNum.getText().toString().trim(),
                                false,
                                (dialog, input) -> {
                                    macNum.setText(input.toString());
                                    if (TextUtils.isEmpty(input.toString())) {
                                        Toasty.warning(this, "必须填写机器号", Toast.LENGTH_SHORT, true).show();
                                        return;
                                    }
                                    SpUtils.put(this, AppConstant.Receipt.MAC_NUMBER, input.toString());
                                })
                        .show();
                break;
            case R.id.plate_indate:
                new MaterialDialog.Builder(SettingsActivity.this)
                        .title("设置餐盘有效期")
                        .inputType(InputType.TYPE_CLASS_NUMBER)
                        .positiveText("确定")
                        .positiveColorRes(R.color.colorPrimaryText)
                        .negativeText("取消")
                        .negativeColorRes(R.color.colorPrimaryText)
                        .input(
                                "数字需小于8760",
                                plateIndate.getText().toString().trim(),
                                true,
                                (dialog, input) -> {
                                    if (TextUtils.isEmpty(input.toString())) {
                                        plateIndate.setText("0");
                                        SpUtils.put(SettingsActivity.this, AppConstant.Receipt.PLATE_INDATE, "0");
                                    } else {
                                        if (Integer.parseInt(input.toString()) <= 8760) {
                                            plateIndate.setText(input.toString());
                                            SpUtils.put(SettingsActivity.this, AppConstant.Receipt.PLATE_INDATE, input.toString());
                                        } else {
                                            Toast.makeText(SettingsActivity.this, "设置失败：数字需小于8760", Toast.LENGTH_SHORT).show();
                                            AudioUtils.getInstance().speakText("设置失败");
                                        }
                                    }
                                })
                        .show();
                break;
            case R.id.tv_head:
                new MaterialDialog.Builder(this)
                        .title("修改抬头名称")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .positiveText("确定")
                        .positiveColorRes(R.color.colorPrimaryText)
                        .negativeText("取消")
                        .negativeColorRes(R.color.colorPrimaryText)
                        .input(
                                "",
                                tvHead.getText().toString().trim(),
                                false,
                                (dialog, input) -> {
                                    tvHead.setText(input.toString());
                                    SpUtils.put(this, AppConstant.Receipt.NAME, input.toString());
                                })

                        .show();
                break;
            case R.id.tv_address:
                new MaterialDialog.Builder(this)
                        .title("修改地址名称")
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .positiveText("确定")
                        .positiveColorRes(R.color.colorPrimaryText)
                        .negativeText("取消")
                        .negativeColorRes(R.color.colorPrimaryText)
                        .input(
                                "",
                                tvAddress.getText().toString().trim(),
                                false,
                                (dialog, input) -> {
                                    tvAddress.setText(input.toString());
                                    SpUtils.put(this, AppConstant.Receipt.ADDRESS, input.toString());
                                })

                        .show();
                break;
            case R.id.tv_phone:
                new MaterialDialog.Builder(this)
                        .title("修改联系电话")
                        .inputType(InputType.TYPE_CLASS_PHONE)
                        .positiveText("确定")
                        .positiveColorRes(R.color.colorPrimaryText)
                        .negativeText("取消")
                        .negativeColorRes(R.color.colorPrimaryText)
                        .input(
                                "",
                                tvPhone.getText().toString().trim(),
                                false,
                                (dialog, input) -> {
                                    tvPhone.setText(input.toString());
                                    SpUtils.put(this, AppConstant.Receipt.PHONE, input.toString());
                                })
                        .show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
