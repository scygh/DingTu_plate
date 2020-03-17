package com.lianxi.dingtu.dingtu_plate.app.task;


public enum TaskResult {
    OK, //数据正常
    FAILED,//失败
    CANCELLED, //取消
    AUTH_ERROR,//授权失败
    DATA_EMPTY,//数据空
    DATA_NULL,//数据NULL
    DATA_ERROR,//数据错误
    NET_ERRROR, //网络错误
    NOT_FOLLOWED_ERROR,
    IO_ERROR,
    ERROR_PWD,
}
