/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lianxi.dingtu.dingtu_plate.app.Utils.wx;


/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>http://dev.machine_api.dt128.com
 * http://machine_api.dt128.com
 * Created by JessYan on 08/05/2016 11:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface WxChatFacePay {
    public static final String RETURN_CODE = "return_code";
    public static final String FACE_CODE = "face_code";
    public static final String OPENID = "openid";
    public static final String SUB_OPENID = "sub_openid";
    public static final String RETURN_MSG = "return_msg";
    public static final String PARAMS_FACE_AUTHTYPE = "face_authtype";
    public static final String PARAMS_APPID = "appid";
    public static final String PARAMS_MCH_ID = "mch_id";
    public static final String PARAMS_MCH_NAME = "mch_name";
    public static final String PARAMS_STORE_ID = "store_id";
    public static final String PARAMS_AUTHINFO = "authinfo";
    public static final String PARAMS_OUT_TRADE_NO = "out_trade_no";
    public static final String PARAMS_TOTAL_FEE = "total_fee";
    public static final String PARAMS_TELEPHONE = "telephone";

    public static final String PARAMS_REPORT_ITEM = "item";
    public static final String PARAMS_REPORT_ITEMVALUE = "item_value";

    public static final String PARAMS_REPORT_SUB_MCH_ID = "sub_mch_id";
    public static final String PARAMS_REPORT_OUT_TRADE_NO = "out_trade_no";
    public static final String PARAMS_BANNER_STATE = "banner_state";
    public static final String ASK_RET_PAGE = "ask_ret_page";
    public static final String ASK_FACE_PERMIT = "ask_face_permit";


    public static final String PARAMS_FACE_AUTHTYPE_CONTENT = "FACEPAY";
    public static final String PARAMS_APPID_CONTENT = "wx25dac803b044c52c";
    public static final String PARAMS_MCH_ID_CONTENT = "1493126702";

    public static final int ASK_RET_PAGE_CONTENT = 0;
    public static final int ASK_FACE_PERMIT_CONTENT = 0;
    public static final String PARAMS_REPORT_SUT_MCH_ID_KEY = "ServiceProviderWeChatPayMerchantId";


}
