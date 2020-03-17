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

    private int ID;
    private String PlaceID;
    private int Pattern;
    private double AutoAmount;
    private boolean KeyEnabled;
    private boolean MealEnabled;
    private boolean DepositEnabled;
    private boolean RefundEnabled;
    private boolean CorrectionEnabled;
    private String AllowType;
    private String AllowMeal;
    private String LinkIpAddress;
    private String LinkPort;
    private String GoodsRange;
    private int State;
    private boolean DiscountEnabled;
    private String FirmwareVersion;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPlaceID() {
        return PlaceID;
    }

    public void setPlaceID(String PlaceID) {
        this.PlaceID = PlaceID;
    }

    public int getPattern() {
        return Pattern;
    }

    public void setPattern(int Pattern) {
        this.Pattern = Pattern;
    }

    public double getAutoAmount() {
        return AutoAmount;
    }

    public void setAutoAmount(double AutoAmount) {
        this.AutoAmount = AutoAmount;
    }

    public boolean isKeyEnabled() {
        return KeyEnabled;
    }

    public void setKeyEnabled(boolean KeyEnabled) {
        this.KeyEnabled = KeyEnabled;
    }

    public boolean isMealEnabled() {
        return MealEnabled;
    }

    public void setMealEnabled(boolean MealEnabled) {
        this.MealEnabled = MealEnabled;
    }

    public boolean isDepositEnabled() {
        return DepositEnabled;
    }

    public void setDepositEnabled(boolean DepositEnabled) {
        this.DepositEnabled = DepositEnabled;
    }

    public boolean isRefundEnabled() {
        return RefundEnabled;
    }

    public void setRefundEnabled(boolean RefundEnabled) {
        this.RefundEnabled = RefundEnabled;
    }

    public boolean isCorrectionEnabled() {
        return CorrectionEnabled;
    }

    public void setCorrectionEnabled(boolean CorrectionEnabled) {
        this.CorrectionEnabled = CorrectionEnabled;
    }

    public String getAllowType() {
        return AllowType;
    }

    public void setAllowType(String AllowType) {
        this.AllowType = AllowType;
    }

    public String getAllowMeal() {
        return AllowMeal;
    }

    public void setAllowMeal(String AllowMeal) {
        this.AllowMeal = AllowMeal;
    }

    public String getLinkIpAddress() {
        return LinkIpAddress;
    }

    public void setLinkIpAddress(String LinkIpAddress) {
        this.LinkIpAddress = LinkIpAddress;
    }

    public String getLinkPort() {
        return LinkPort;
    }

    public void setLinkPort(String LinkPort) {
        this.LinkPort = LinkPort;
    }

    public String getGoodsRange() {
        return GoodsRange;
    }

    public void setGoodsRange(String GoodsRange) {
        this.GoodsRange = GoodsRange;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }

    public boolean isDiscountEnabled() {
        return DiscountEnabled;
    }

    public void setDiscountEnabled(boolean DiscountEnabled) {
        this.DiscountEnabled = DiscountEnabled;
    }

    public String getFirmwareVersion() {
        return FirmwareVersion;
    }

    public void setFirmwareVersion(String FirmwareVersion) {
        this.FirmwareVersion = FirmwareVersion;
    }
}
