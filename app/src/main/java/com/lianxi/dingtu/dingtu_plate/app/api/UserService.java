package com.lianxi.dingtu.dingtu_plate.app.api;


import com.lianxi.dingtu.dingtu_plate.app.base.BaseResponse;
import com.lianxi.dingtu.dingtu_plate.app.entity.CardInfoTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.EMGoodsTypeTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.GetFacePayAuthInfoParam;
import com.lianxi.dingtu.dingtu_plate.app.entity.GetFacePayAuthInfoTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.KeySwitchTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.OfflineExpenseParam;
import com.lianxi.dingtu.dingtu_plate.app.entity.QRExpenseParam;
import com.lianxi.dingtu.dingtu_plate.app.entity.QRExpenseTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.QRReadTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.SimpleExpenseParam;
import com.lianxi.dingtu.dingtu_plate.app.entity.SimpleExpenseTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.UserInfoTo;
import com.lianxi.dingtu.dingtu_plate.app.entity.WxExpenseParam;
import com.lianxi.dingtu.dingtu_plate.app.entity.WxExpenseTo;
import com.lianxi.dingtu.dingtu_plate.app.sql.Sql_EMGoodsPayDetail;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {
    @GET("Api/Token/GetToken")
    Call<BaseResponse<UserInfoTo>> synclogin(@Query("account") String account, @Query("password") String password, @Query("oldAccessToken") String oldAccessToken);

    @GET("Api/Token/GetToken")
    Observable<BaseResponse<UserInfoTo>> login(@Query("account") String account, @Query("password") String password, @Query("oldAccessToken") String oldAccessToken);

    @GET("Api/Config/GetCardPassword")
    Observable<BaseResponse<String>> getCardPassword();

    @GET("Api/EMGoodsType/GetList")
    Observable<BaseResponse<List<EMGoodsTypeTo>>> getEMGoodsType(@Query("state") String state);

    @GET("Api/EMGoods/GetPage")
    Observable<BaseResponse<EMGoodsTo>> getEMGoodsDetail(@Query("pageIndex") Integer pageIndex, @Query("pageSize") Integer pageSize, @Query("goodsType") String goodsType);

    @GET("Api/EMGoods/GetList")
    Observable<BaseResponse<List<EMGoodsTo.RowsBean.GoodsBean>>> getEMGoods(@Query("state") int state);

    @GET("Api/EMGoods/Get")
    Observable<BaseResponse<EMGoodsTo.RowsBean.GoodsBean>> getEMGoodsByNum(@Query("goodsNo") int goodsNo);

    @GET("Api/User/GetByNumber")
    Observable<BaseResponse<CardInfoTo>> getByNumber(@Query("number") int number);

    @GET("Api/Device/Get") Observable<BaseResponse<KeySwitchTo>> getEMDevice(@Query("id") int id);

    @POST("Api/Expense/SimpleExpense") Observable<BaseResponse<SimpleExpenseTo>> createSimpleExpense(@Body SimpleExpenseParam param);

    @GET("Api/User/QRCode/QRRead") Observable<BaseResponse<QRReadTo>> getQRRead(@Query("qrCodeContent") String qrCodeContent, @Query("deviceID") int deviceID);

    @POST("Api/Expense/QRExpense") Observable<BaseResponse<QRExpenseTo>> addQRExpense(@Body QRExpenseParam param);

    @POST("Api/Expense/WeChatFacePay") Observable<BaseResponse<WxExpenseTo>> addWxFacePayExpense(@Body WxExpenseParam param);

    @POST("Api/Expense/GetFacePayAuthInfo") Observable<BaseResponse<GetFacePayAuthInfoTo>> getFacePayAuthInfo(@Body GetFacePayAuthInfoParam param);

    @GET("Api/Config/GetConfig") Observable<BaseResponse<String>> getConfig(@Query("key") String key);

    @POST("Api/Expense/OfflineExpense") Observable<BaseResponse> isOfflineExpenseSuccess (@Body OfflineExpenseParam param);
}
