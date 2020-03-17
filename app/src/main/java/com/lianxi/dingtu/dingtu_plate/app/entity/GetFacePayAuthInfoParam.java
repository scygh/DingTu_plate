package com.lianxi.dingtu.dingtu_plate.app.entity;

import java.io.Serializable;

/**
 *  /Api/Expense/GetFacePayAuthInfo
 * 获取微信人脸支付授权信息所需要的数据
 */
public class GetFacePayAuthInfoParam implements Serializable {
/**
 * {
 *   "DeviceId": 0,
 *   "StoreId": "string",
 *   "StoreName": "string",
 *   "Attach": "string",
 *   "RawData": "string",
 *   "AppId": "string",
 *   "MchId": "string",
 *   "SubAppId": "string",
 *   "SubMchId": "string"
 * }
 */

    private int DeviceId;
    private String StoreId;
    private String StoreName;
    private String Attach;
    private String RawData;
    private String AppId;
    private String MchId;
    private String SubAppId;
    private String SubMchId;

    public int getDeviceId() {
        return DeviceId;
    }

    public void setDeviceID(int deviceID) {
        DeviceId = deviceID;
    }

    public String getStoreId() {
        return StoreId;
    }

    public void setStoreId(String storeId) {
        StoreId = storeId;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getAttach() {
        return Attach;
    }

    public void setAttach(String attach) {
        Attach = attach;
    }

    public String getRawData() {
        return RawData;
    }

    public void setRawData(String rawData) {
        RawData = rawData;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getMchId() {
        return MchId;
    }

    public void setMchId(String mchId) {
        MchId = mchId;
    }

    public String getSubAppId() {
        return SubAppId;
    }

    public void setSubAppId(String subAppId) {
        SubAppId = subAppId;
    }

    public String getSubMchId() {
        return SubMchId;
    }

    public void setSubMchId(String subMchId) {
        SubMchId = subMchId;
    }
}
