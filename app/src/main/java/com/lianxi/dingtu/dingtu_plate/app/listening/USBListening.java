package com.lianxi.dingtu.dingtu_plate.app.listening;

import com.lianxi.dingtu.dingtu_plate.app.entity.CardInfoBean;

public interface USBListening {
    void findRFCardListening(String code,int number);

    void findQRListening(String QRcode);

}
