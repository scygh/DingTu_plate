package com.lianxi.dingtu.dingtu_plate.app.entity;

import java.io.Serializable;

public class GetFacePayAuthInfoTo implements Serializable {
    /**
     * {
     *   "Content": {
     *     "AuthInfo": "string",
     *     "ExpiresIn": 0
     *   },
     *   "Result": true,
     *   "Message": "string",
     *   "StatusCode": 200
     * }
     */

    private String AuthInfo;
    private int ExpiresIn;

    public String getAuthInfo() {
        return AuthInfo;
    }

    public void setAuthInfo(String authInfo) {
        AuthInfo = authInfo;
    }

    public int getExpiresIn() {
        return ExpiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        ExpiresIn = expiresIn;
    }
}
