package com.lianxi.dingtu.dingtu_plate.app.http;

import android.content.Context;

import com.jess.arms.utils.ArmsUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.SpUtils;
import com.lianxi.dingtu.dingtu_plate.app.Utils.UserInfoHelper;
import com.lianxi.dingtu.dingtu_plate.app.api.AppConstant;
import com.lianxi.dingtu.dingtu_plate.app.api.UserService;
import com.lianxi.dingtu.dingtu_plate.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_plate.app.entity.UserInfoTo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;

/**
 * 全局自动刷新Token的拦截器
 * <p>
 */
public class TokenInterceptor implements Interceptor {
    public Context context;

    public TokenInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        //  LogUtil.print("response.code=" + response.code());

        if (isTokenExpired(response)) {//根据和服务端的约定判断token过期
            // LogUtil.print("静默自动刷新Token,然后重新请求数据");
            //同步请求方式，获取最新的Token
            String newSession = getNewToken();
            //使用新的Token，创建新的请求
            Request newRequest = chain.request()
                    .newBuilder()
                    .header("AccessToken", newSession)
                    .build();
            //重新请求
            return chain.proceed(newRequest);
        }
        return response;
    }

    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response) {
        if (response.code() == 401) {
            return true;
        }
        return false;
    }

    private String getNewToken() throws IOException {
        UserInfoHelper mUserInfoHelper = UserInfoHelper.getInstance(context);
        UserInfoTo mUserInfoTo = mUserInfoHelper.getUserInfoTo();
        String account = mUserInfoTo.getAccount();
        String password = (String) SpUtils.get(context, AppConstant.Api.PASSWORD,"");
        String oldAccessToken = mUserInfoTo.getAccessToken();
        Call<BaseResponse<UserInfoTo>> call = ArmsUtils.obtainAppComponentFromContext(context).repositoryManager().obtainRetrofitService(UserService.class).synclogin(account, password, oldAccessToken);
        UserInfoTo userInfoTo = call.execute().body().getContent();
        mUserInfoHelper.updateUser(userInfoTo);
        SpUtils.put(context, AppConstant.Api.TOKEN,userInfoTo.getAccessToken());
        return mUserInfoHelper.getAccessToken();
    }

}

