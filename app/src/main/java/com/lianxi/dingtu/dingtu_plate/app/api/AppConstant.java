package com.lianxi.dingtu.dingtu_plate.app.api;


public interface AppConstant {
    interface Api {
        String TOKEN = "token";
        String COMPANYCODE = "companycode";
        String PASSWORD = "password";
        String ACCOUNT = "account";
    }

    interface KeyValue{
        String KEY_USER_INFO = "userInfo";
        String KEY_IS_LOGIN_INFO = "info";
    }

    interface Card{
        String KEY = "key";
    }

    interface Print{
        String isPrint = "print_state";
        String SERIALPORTBAUDRATE = "serialportbaudrate";
        String SERIALPORTPATH = "serialportpath";
    }

    interface Receipt {
        String MAC_NUMBER = "mac_number";
        String NAME = "receipt_name";
        String ADDRESS = "receipt_address";
        String PHONE = "receipt_phone";
        String isPrint = "print_state";
        String PAYSTATE ="pay_state";
        String PLATE_INDATE = "plate_indate";
        String PLATE_READ_PATTERN = "plate_read_patter";
    }

    interface SerialPort{
        String MACHINE_PORT = "/dev/ttyS4";
        int MACHINE_BAUDRTE = 115200;
    }
    interface WxFacePay{
        String PARAMS_REPORT_SUT_MCH_ID = "sub_mch_id";
        String AUTHINFO = "authinfo";

    }
}
