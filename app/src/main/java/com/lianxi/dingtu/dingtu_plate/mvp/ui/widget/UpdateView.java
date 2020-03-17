package com.lianxi.dingtu.dingtu_plate.mvp.ui.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.dingtu_plate.app.api.UpdateCenterService;
import com.lianxi.dingtu.dingtu_plate.app.entity.UpdateInfo;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import retrofit2.Call;
import timber.log.Timber;


@SuppressLint("HandlerLeak")
public class UpdateView {

    private static final String TAG = "UpdateView";

    private Handler handler = new Handler();
    public ProgressDialog pBar; // 更新进度条
    public Context mContext;
    private String downloadUrl = "";

    private String newVerCode = ""; // 新版本号
    private String newVerName = "";// 新版本名称
    private boolean bShow = true;
    private UpdateInfo updateInfo;

    public UpdateView(Context context) {
        this.mContext = context;
        bShow = true;
    }

    public UpdateView(Context context, boolean isshow) {
        this.mContext = context;
        this.bShow = isshow;
    }


    /**
     * 开始更新
     */
    public void Start() {
        if (getServerVerCode()) {
            String vercode = UpdateConfig.getVerName(mContext);
            Log.e("updateInfo", "vercode"+vercode+"newVerCode"+newVerCode);

            int i =compareVersion(newVerCode,vercode);
            if (i==1) {
                Message newMsg = new Message();
                newMsg.arg1 = 1;
                dialogHhandler.sendMessage(newMsg);
                Log.e("updateInfo", "Start: "+newMsg);
            } else {
                if (bShow) //判断是否显示不更新对话框
                {
                    Message noMsg = new Message();
                    noMsg.arg1 = 2;
                    dialogHhandler.sendMessage(noMsg);
                }

            }
        }
    }

    /**
     * 0代表相等，1代表version1大于version2，-1代表version1小于version2
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        Log.d("HomePageActivity", "version1Array=="+version1Array.length);
        Log.d("HomePageActivity", "version2Array=="+version2Array.length);
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        Log.d("HomePageActivity", "verTag2=2222="+version1Array[index]);
        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    Handler dialogHhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub

            switch (msg.arg1) {
                case 1:
                    doNewVersionUpdate();
                    break;
                case 2:
                    notNewVersionShow();
                    break;
            }
            super.handleMessage(msg);
        }

    };

    /**
     * 获取服务器版本号
     *
     * @return
     */
    private boolean getServerVerCode() {
        try {
//            String verjson = getContent("http://machine_api.dt128.com/Api/VersionRepository/Highest?operatingSystem=android&type=em&extensionName=.apk");

            Call<UpdateInfo> call = ArmsUtils.obtainAppComponentFromContext(mContext).repositoryManager().obtainRetrofitService(UpdateCenterService.class).syncUpdate("Android_GoodsEM");

            UpdateInfo.Content updateInfo = call.execute().body().getContent();

            if (updateInfo != null) {

                try {
                    newVerCode = updateInfo.getVersion();
                    newVerName = updateInfo.getResourceName();
                    downloadUrl = updateInfo.getResourceUri();
                } catch (Exception e) {
                    newVerCode = "";
                    newVerName = "";
                    return false;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 不包含新版本
     */
    private void notNewVersionShow() {
        int verCode = UpdateConfig.getVerCode(mContext);
        String verName = UpdateConfig.getVerName(mContext);
        StringBuffer sb = new StringBuffer();
        sb.append("当前版本号:");
        sb.append(verName);
//        sb.append(" Code:");
//        sb.append(verCode);
        sb.append("\n已是最新版,无需更新!");
        Dialog dialog = new AlertDialog.Builder(mContext).setTitle("软件更新").setMessage(sb.toString())// 设置内容
                .setPositiveButton("确定",// 设置确定按钮
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }

                        }).create();// 创建
        // 显示对话框
        dialog.show();
    }

    /**
     * 更新APK文件
     */
    void updateApk() {
        File file = new File(
                Environment.getExternalStorageDirectory()
                , "update.apk");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(mContext, "com.linxi.dingtu.fangz.fileprovider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }


//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
//                "update.apk")), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    /**
     * 下载新版本的程序
     */
    private void doNewVersionUpdate() {
        int verCode = UpdateConfig.getVerCode(mContext);
        String verName = UpdateConfig.getVerName(mContext);
        StringBuffer sb = new StringBuffer();
        sb.append("当前版本号:Ver");
        sb.append(verName);
        //sb.append(" Code:");
        //sb.append(verCode);
        sb.append("\n发现新版本:");
        sb.append(newVerName);
        //sb.append(" Code:");
        sb.append("\n新版变化：");
        sb.append("\n修复部分已知问题，提升使用体验");
        sb.append("\n是否更新?");
        Dialog dialog = new AlertDialog.Builder(mContext).setTitle("软件更新").setMessage(sb.toString())
                // 设置内容
                .setPositiveButton("更新",// 设置确定按钮
                        (dialog12, which) -> {

                            String newPath = downloadUrl;

                            pBar = new ProgressDialog(mContext);
                            Timber.wtf("下载路径：" + newPath);
                            OkGo.<File>get(newPath)
                                    .tag(mContext)
                                    .cacheMode(CacheMode.NO_CACHE)
                                    .execute(new FileCallback("/sdcard/", "update.apk") {
                                        @Override
                                        public void onSuccess(Response<File> response) {
                                            pBar.dismiss();
                                            System.out.println("成功了");
                                            updateApk();
                                        }

                                        @Override
                                        public void downloadProgress(Progress progress) {
                                            System.out.println("下载中");
                                            pBar.setTitle("正在下载软件");
                                            pBar.setMessage("请稍候...");
                                            pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                            pBar.setMax((int) progress.totalSize / 1024 / 1024);
                                            pBar.setProgress((int) progress.currentSize / 1024 / 1024);
                                            pBar.setProgressNumberFormat("%1d Mb /%2d Mb");
                                            pBar.show();
                                        }

                                        @Override
                                        public void onError(Response<File> response) {
                                            Toast.makeText(mContext, "更新失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }).setNegativeButton("暂不更新", (dialog1, whichButton) -> {
                    // 点击"取消"按钮之后退出程序
                    System.exit(0);
                }).create();// 创建
        // 显示对话框
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }


    /**
     * 获取网址内容
     *
     * @param url
     * @return
     * @throws
     */
    @SuppressWarnings("deprecation")
    public static String getContent(String url) throws Exception {
        StringBuilder sb = new StringBuilder();

        HttpClient client = new DefaultHttpClient();
        HttpParams httpParams = client.getParams();
        //设置网络超时参数
        HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
        HttpConnectionParams.setSoTimeout(httpParams, 5000);
        HttpResponse response = client.execute(new HttpGet(url));
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"), 8192);

            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            reader.close();
        }
        return sb.toString();
    }

}
