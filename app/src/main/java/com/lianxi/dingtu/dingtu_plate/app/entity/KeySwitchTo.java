package com.lianxi.dingtu.dingtu_plate.app.entity;

public class KeySwitchTo {

    /**
     * ID : 1
     * PlaceID : 00000000-0000-0000-0000-000000000001
     * Pattern : 3
     * AutoAmount : 0.01
     * KeyEnabled : false
     * MealEnabled : false
     * DepositEnabled : true
     * RefundEnabled : true
     * CorrectionEnabled : true
     * AllowType : 1,2,3,4
     * AllowMeal :
     * LinkIpAddress : 172.16.204.235
     * LinkPort : 1400
     * GoodsRange : 1,100
     * State : 1
     * DiscountEnabled : true
     * FirmwareVersion : SCM_EM_1000-181212
     */

    private int Id;
    private String Name;
    private String PlaceId;
    private int Pattern;
    private double AutoAmount;
    private boolean KeyEnabled;
    private boolean MealEnabled;
    private boolean DepositEnabled;
    private boolean RefundEnabled;
    private boolean CorrectionEnabled;
    private boolean DiscountEnabled;
    private int[] AllowType;
    private String AllowTypeAsString;
    private int[] AllowMeal;
    private String AllowMealAsString;
    private String LinkIpAddress;
    private int LinkPort;
    private String GoodsRange;
    private String FirmwareVersion;
    private int DeviceVersion;
    private int State;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPlaceId() {
        return PlaceId;
    }

    public void setPlaceId(String placeId) {
        PlaceId = placeId;
    }

    public int getPattern() {
        return Pattern;
    }

    public void setPattern(int pattern) {
        Pattern = pattern;
    }

    public double getAutoAmount() {
        return AutoAmount;
    }

    public void setAutoAmount(double autoAmount) {
        AutoAmount = autoAmount;
    }

    public boolean isKeyEnabled() {
        return KeyEnabled;
    }

    public void setKeyEnabled(boolean keyEnabled) {
        KeyEnabled = keyEnabled;
    }

    public boolean isMealEnabled() {
        return MealEnabled;
    }

    public void setMealEnabled(boolean mealEnabled) {
        MealEnabled = mealEnabled;
    }

    public boolean isDepositEnabled() {
        return DepositEnabled;
    }

    public void setDepositEnabled(boolean depositEnabled) {
        DepositEnabled = depositEnabled;
    }

    public boolean isRefundEnabled() {
        return RefundEnabled;
    }

    public void setRefundEnabled(boolean refundEnabled) {
        RefundEnabled = refundEnabled;
    }

    public boolean isCorrectionEnabled() {
        return CorrectionEnabled;
    }

    public void setCorrectionEnabled(boolean correctionEnabled) {
        CorrectionEnabled = correctionEnabled;
    }

    public boolean isDiscountEnabled() {
        return DiscountEnabled;
    }

    public void setDiscountEnabled(boolean discountEnabled) {
        DiscountEnabled = discountEnabled;
    }

    public int[] getAllowType() {
        return AllowType;
    }

    public void setAllowType(int[] allowType) {
        AllowType = allowType;
    }

    public String getAllowTypeAsString() {
        return AllowTypeAsString;
    }

    public void setAllowTypeAsString(String allowTypeAsString) {
        AllowTypeAsString = allowTypeAsString;
    }

    public int[] getAllowMeal() {
        return AllowMeal;
    }

    public void setAllowMeal(int[] allowMeal) {
        AllowMeal = allowMeal;
    }

    public String getAllowMealAsString() {
        return AllowMealAsString;
    }

    public void setAllowMealAsString(String allowMealAsString) {
        AllowMealAsString = allowMealAsString;
    }

    public String getLinkIpAddress() {
        return LinkIpAddress;
    }

    public void setLinkIpAddress(String linkIpAddress) {
        LinkIpAddress = linkIpAddress;
    }

    public int getLinkPort() {
        return LinkPort;
    }

    public void setLinkPort(int linkPort) {
        LinkPort = linkPort;
    }

    public String getGoodsRange() {
        return GoodsRange;
    }

    public void setGoodsRange(String goodsRange) {
        GoodsRange = goodsRange;
    }

    public String getFirmwareVersion() {
        return FirmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        FirmwareVersion = firmwareVersion;
    }

    public int getDeviceVersion() {
        return DeviceVersion;
    }

    public void setDeviceVersion(int deviceVersion) {
        DeviceVersion = deviceVersion;
    }

    public int getState() {
        return State;
    }

    public void setState(int state) {
        State = state;
    }
}
