package com.lianxi.dingtu.dingtu_plate.app.entity;

import java.io.Serializable;
import java.util.List;

public class SimpleExpenseTo implements Serializable {

    private ExpenseDetailBean ExpenseDetail;
    private List<ListEMGoodsDetailBean> ListEMGoodsDetails;
    private ThirdPartyExpenseBean ThirdPartyExpense;

    public ExpenseDetailBean getExpenseDetail() {
        return ExpenseDetail;
    }

    public void setExpenseDetail(ExpenseDetailBean expenseDetail) {
        ExpenseDetail = expenseDetail;
    }

    public List<ListEMGoodsDetailBean> getListEMGoodsDetails() {
        return ListEMGoodsDetails;
    }

    public void setListEMGoodsDetails(List<ListEMGoodsDetailBean> listEMGoodsDetails) {
        ListEMGoodsDetails = listEMGoodsDetails;
    }

    public ThirdPartyExpenseBean getThirdPartyExpense() {
        return ThirdPartyExpense;
    }

    public void setThirdPartyExpense(ThirdPartyExpenseBean thirdPartyExpense) {
        ThirdPartyExpense = thirdPartyExpense;
    }

    public static class ExpenseDetailBean {
        /**
         *  "Id": "00000000-0000-0000-0000-000000000000",
         *       "UserId": "00000000-0000-0000-0000-000000000000",
         *       "Number": 0,
         *       "DeviceType": 1,
         *       "DeviceId": 0,
         *       "Pattern": 1,
         *       "DetailType": 0,
         *       "PayCount": 0,
         *       "Finance": 0,
         *       "OriginalAmount": 0,
         *       "Amount": 0,
         *       "Balance": 0,
         *       "IsDiscount": true,
         *       "DiscountRate": 0,
         *       "TradeDateTime": "2019-08-19T02:49:30.690Z",
         *       "CreateTime": "2019-08-19T02:49:30.690Z",
         *       "Description": "string",
         *       "OfflinePayCount": 0
         */

        private String Id;
        private String UserId;
        private int Number;
        private int DeviceType;
        private int DeviceID;
        private int Pattern;
        private int DetailType;
        private int PayCount;
        private int Finance;
        private double OriginalAmount;
        private double Amount;
        private double Balance;
        private boolean IsDiscount;
        private int DiscountRate;

        private String TradeDateTime;
        private String CreateTime;
        private String Description;
        private int OfflinePayCount;


        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String userId) {
            UserId = userId;
        }

        public int getNumber() {
            return Number;
        }

        public void setNumber(int number) {
            Number = number;
        }

        public int getDeviceType() {
            return DeviceType;
        }

        public void setDeviceType(int deviceType) {
            DeviceType = deviceType;
        }

        public int getDeviceID() {
            return DeviceID;
        }

        public void setDeviceID(int deviceID) {
            DeviceID = deviceID;
        }

        public int getPattern() {
            return Pattern;
        }

        public void setPattern(int pattern) {
            Pattern = pattern;
        }

        public int getDetailType() {
            return DetailType;
        }

        public void setDetailType(int detailType) {
            DetailType = detailType;
        }

        public int getPayCount() {
            return PayCount;
        }

        public void setPayCount(int payCount) {
            PayCount = payCount;
        }

        public int getFinance() {
            return Finance;
        }

        public void setFinance(int finance) {
            Finance = finance;
        }

        public double getOriginalAmount() {
            return OriginalAmount;
        }

        public void setOriginalAmount(double originalAmount) {
            OriginalAmount = originalAmount;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double amount) {
            Amount = amount;
        }

        public double getBalance() {
            return Balance;
        }

        public void setBalance(double balance) {
            Balance = balance;
        }

        public boolean isDiscount() {
            return IsDiscount;
        }

        public void setDiscount(boolean discount) {
            IsDiscount = discount;
        }

        public int getDiscountRate() {
            return DiscountRate;
        }

        public void setDiscountRate(int discountRate) {
            DiscountRate = discountRate;
        }

        public String getTradeDateTime() {
            return TradeDateTime;
        }

        public void setTradeDateTime(String tradeDateTime) {
            TradeDateTime = tradeDateTime;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public int getOfflinePayCount() {
            return OfflinePayCount;
        }

        public void setOfflinePayCount(int offlinePayCount) {
            OfflinePayCount = offlinePayCount;
        }
    }

    public static class ListEMGoodsDetailBean {
        /**
         * "Id": "00000000-0000-0000-0000-000000000000",
         *         "Eid": "00000000-0000-0000-0000-000000000000",
         *         "GoodsNo": 0,
         *         "OrderNo": 0,
         *         "GoodsName": "string",
         *         "Price": 0,
         *         "Amount": 0,
         *         "Count": 0,
         *         "CreateTime": "2019-08-19T02:49:30.690Z"
         */

        private String Id;
        private String Eid;
        private int GoodsNo;
        private int OrderNo;
        private String GoodsName;
        private double Price;
        private double Amount;
        private int Count;
        private String CreateTime;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getEid() {
            return Eid;
        }

        public void setEid(String eid) {
            Eid = eid;
        }

        public int getGoodsNo() {
            return GoodsNo;
        }

        public void setGoodsNo(int goodsNo) {
            GoodsNo = goodsNo;
        }

        public int getOrderNo() {
            return OrderNo;
        }

        public void setOrderNo(int orderNo) {
            OrderNo = orderNo;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String goodsName) {
            GoodsName = goodsName;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double price) {
            Price = price;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double amount) {
            Amount = amount;
        }

        public int getCount() {
            return Count;
        }

        public void setCount(int count) {
            Count = count;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }
    }
    public static class ThirdPartyExpenseBean {
        /**
         *   "Id": "00000000-0000-0000-0000-000000000000",
         *       "DeviceType": 1,
         *       "DeviceId": 0,
         *       "Pattern": 1,
         *       "DetailType": 0,
         *       "OriginalAmount": 0,
         *       "Amount": 0,
         *       "IsDiscount": true,
         *       "DiscountRate": 0,
         *       "TradeDateTime": "2019-08-19T02:49:30.690Z",
         *       "CreateTime": "2019-08-19T02:49:30.690Z",
         *       "Description": "string",
         *       "ThirdPartyUserId": "string",
         *       "ThirdPartySourceId": "string",
         *       "OurSourceId": "string",
         *       "PayWay": 0,
         *       "Channel": 0,
         *       "State": 0
         */
        private String Id;
        private int DeviceType;
        private int DeviceId;
        private int Pattern;
        private int DetailType;
        private double OriginalAmount;
        private double Amount;
        private boolean IsDiscount;
        private String TradeDateTime;
        private String CreateTime;
        private String Description;
        private String ThirdPartyUserId;
        private String ThirdPartySourceId;
        private String OurSourceId;
        private int PayWay;
        private int Channel;
        private int State;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public int getDeviceType() {
            return DeviceType;
        }

        public void setDeviceType(int deviceType) {
            DeviceType = deviceType;
        }

        public int getDeviceId() {
            return DeviceId;
        }

        public void setDeviceId(int deviceId) {
            DeviceId = deviceId;
        }

        public int getPattern() {
            return Pattern;
        }

        public void setPattern(int pattern) {
            Pattern = pattern;
        }

        public int getDetailType() {
            return DetailType;
        }

        public void setDetailType(int detailType) {
            DetailType = detailType;
        }

        public double getOriginalAmount() {
            return OriginalAmount;
        }

        public void setOriginalAmount(double originalAmount) {
            OriginalAmount = originalAmount;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double amount) {
            Amount = amount;
        }

        public boolean isDiscount() {
            return IsDiscount;
        }

        public void setDiscount(boolean discount) {
            IsDiscount = discount;
        }

        public String getTradeDateTime() {
            return TradeDateTime;
        }

        public void setTradeDateTime(String tradeDateTime) {
            TradeDateTime = tradeDateTime;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String createTime) {
            CreateTime = createTime;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getThirdPartyUserId() {
            return ThirdPartyUserId;
        }

        public void setThirdPartyUserId(String thirdPartyUserId) {
            ThirdPartyUserId = thirdPartyUserId;
        }

        public String getThirdPartySourceId() {
            return ThirdPartySourceId;
        }

        public void setThirdPartySourceId(String thirdPartySourceId) {
            ThirdPartySourceId = thirdPartySourceId;
        }

        public String getOurSourceId() {
            return OurSourceId;
        }

        public void setOurSourceId(String ourSourceId) {
            OurSourceId = ourSourceId;
        }

        public int getPayWay() {
            return PayWay;
        }

        public void setPayWay(int payWay) {
            PayWay = payWay;
        }

        public int getChannel() {
            return Channel;
        }

        public void setChannel(int channel) {
            Channel = channel;
        }

        public int getState() {
            return State;
        }

        public void setState(int state) {
            State = state;
        }
    }
}
