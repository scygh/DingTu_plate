package com.lianxi.dingtu.dingtu_plate.app.entity;

import java.util.List;

public class OfflineExpenseParam {
    public List<OfflineExpenseBean> ListOfflineExpense;

    public List<OfflineExpenseBean> getListOfflineExpense() {
        return ListOfflineExpense;
    }

    public void setListOfflineExpense(List<OfflineExpenseBean> listOfflineExpense) {
        ListOfflineExpense = listOfflineExpense;
    }

    public static class OfflineExpenseBean {
        /**
         * "ListOfflineExpense": [
         *     {
         *       "DeviceId": 0,
         *       "Amount": 0,
         *       "TradeDateTime": "2019-11-29T03:09:25.221Z",
         *       "OfflinePayCount": 0,
         *       "Number": 0
         *     }
         */
        private int Number;
        private int Pattern;
        private int DeviceID;
        private double Amount;
        private String TradeDateTime;
        private int OfflinePayCount;

        public int getNumber() {
            return Number;
        }

        public void setNumber(int number) {
            Number = number;
        }

        public int getPattern() {
            return Pattern;
        }

        public void setPattern(int pattern) {
            Pattern = pattern;
        }

        public int getDeviceID() {
            return DeviceID;
        }

        public void setDeviceID(int deviceID) {
            DeviceID = deviceID;
        }

        public double getAmount() {
            return Amount;
        }

        public void setAmount(double amount) {
            Amount = amount;
        }

        public String getTradeDateTime() {
            return TradeDateTime;
        }

        public void setTradeDateTime(String tradeDateTime) {
            TradeDateTime = tradeDateTime;
        }

        public int getOfflinePayCount() {
            return OfflinePayCount;
        }

        public void setOfflinePayCount(int offlinePayCount) {
            OfflinePayCount = offlinePayCount;
        }
    }
}
