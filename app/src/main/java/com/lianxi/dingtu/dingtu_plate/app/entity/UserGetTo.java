package com.lianxi.dingtu.dingtu_plate.app.entity;

import java.util.List;

/**
 * description ：
 * author : scy
 * email : 1797484636@qq.com
 * date : 2020/4/27 17:26
 */
public class UserGetTo {


    /**
     * UserFace : null
     * User : {"Id":"1176e3eb-e8ae-4a3c-8bd5-27419bb1ccae","DepartmentId":"00000000-0000-0000-0000-000000000001","Name":"人脸用户1","EmpId":"10009","IdCard":"","Sex":0,"Age":0,"Address":"","Phone":"","CreateTime":"2020-03-06 10:40:36","State":1,"Password":"Wabg2EsJTng=","Photo":null,"PayKey":"","AuthType":null,"AuthUrl":null,"AuthResult":null}
     * Finance : [{"UserId":"1176e3eb-e8ae-4a3c-8bd5-27419bb1ccae","Kind":0,"Balance":0},{"UserId":"1176e3eb-e8ae-4a3c-8bd5-27419bb1ccae","Kind":1,"Balance":0},{"UserId":"1176e3eb-e8ae-4a3c-8bd5-27419bb1ccae","Kind":2,"Balance":0},{"UserId":"1176e3eb-e8ae-4a3c-8bd5-27419bb1ccae","Kind":3,"Balance":0},{"UserId":"1176e3eb-e8ae-4a3c-8bd5-27419bb1ccae","Kind":4,"Balance":0}]
     * Card : {"Id":"1176e3eb-e8ae-4a3c-8bd5-27419bb1ccae","UserId":"1176e3eb-e8ae-4a3c-8bd5-27419bb1ccae","Number":1,"SerialNo":"Test001","Type":1,"Level":0,"Cost":0,"Deadline":"2030-04-15 00:00:00","PayCount":0,"LastSubsidyDate":"2020-03-01 00:00:00","CreateTime":"2020-04-15 00:00:00","IsGot":false,"State":1,"LastPayDateTime":"2020-04-15 00:00:00"}
     */

    private Object UserFace;
    private UserBean User;
    private CardBean Card;
    private List<FinanceBean> Finances;

    public Object getUserFace() {
        return UserFace;
    }

    public void setUserFace(Object UserFace) {
        this.UserFace = UserFace;
    }

    public UserBean getUser() {
        return User;
    }

    public void setUser(UserBean User) {
        this.User = User;
    }

    public CardBean getCard() {
        return Card;
    }

    public void setCard(CardBean Card) {
        this.Card = Card;
    }

    public List<FinanceBean> getFinances() {
        return Finances;
    }

    public void setFinances(List<FinanceBean> finances) {
        Finances = finances;
    }

    public static class UserBean {
        /**
         * Id : 1176e3eb-e8ae-4a3c-8bd5-27419bb1ccae
         * DepartmentId : 00000000-0000-0000-0000-000000000001
         * Name : 人脸用户1
         * EmpId : 10009
         * IdCard :
         * Sex : 0
         * Age : 0
         * Address :
         * Phone :
         * CreateTime : 2020-03-06 10:40:36
         * State : 1
         * Password : Wabg2EsJTng=
         * Photo : null
         * PayKey :
         * AuthType : null
         * AuthUrl : null
         * AuthResult : null
         */

        private String Id;
        private String DepartmentId;
        private String Name;
        private String EmpId;
        private String IdCard;
        private int Sex;
        private int Age;
        private String Address;
        private String Phone;
        private String CreateTime;
        private int State;
        private String Password;
        private Object Photo;
        private String PayKey;
        private Object AuthType;
        private Object AuthUrl;
        private Object AuthResult;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getDepartmentId() {
            return DepartmentId;
        }

        public void setDepartmentId(String DepartmentId) {
            this.DepartmentId = DepartmentId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getEmpId() {
            return EmpId;
        }

        public void setEmpId(String EmpId) {
            this.EmpId = EmpId;
        }

        public String getIdCard() {
            return IdCard;
        }

        public void setIdCard(String IdCard) {
            this.IdCard = IdCard;
        }

        public int getSex() {
            return Sex;
        }

        public void setSex(int Sex) {
            this.Sex = Sex;
        }

        public int getAge() {
            return Age;
        }

        public void setAge(int Age) {
            this.Age = Age;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public Object getPhoto() {
            return Photo;
        }

        public void setPhoto(Object Photo) {
            this.Photo = Photo;
        }

        public String getPayKey() {
            return PayKey;
        }

        public void setPayKey(String PayKey) {
            this.PayKey = PayKey;
        }

        public Object getAuthType() {
            return AuthType;
        }

        public void setAuthType(Object AuthType) {
            this.AuthType = AuthType;
        }

        public Object getAuthUrl() {
            return AuthUrl;
        }

        public void setAuthUrl(Object AuthUrl) {
            this.AuthUrl = AuthUrl;
        }

        public Object getAuthResult() {
            return AuthResult;
        }

        public void setAuthResult(Object AuthResult) {
            this.AuthResult = AuthResult;
        }
    }

    public static class CardBean {
        /**
         * Id : 1176e3eb-e8ae-4a3c-8bd5-27419bb1ccae
         * UserId : 1176e3eb-e8ae-4a3c-8bd5-27419bb1ccae
         * Number : 1
         * SerialNo : Test001
         * Type : 1
         * Level : 0
         * Cost : 0.0
         * Deadline : 2030-04-15 00:00:00
         * PayCount : 0
         * LastSubsidyDate : 2020-03-01 00:00:00
         * CreateTime : 2020-04-15 00:00:00
         * IsGot : false
         * State : 1
         * LastPayDateTime : 2020-04-15 00:00:00
         */

        private String Id;
        private String UserId;
        private int Number;
        private String SerialNo;
        private int Type;
        private int Level;
        private double Cost;
        private String Deadline;
        private int PayCount;
        private String LastSubsidyDate;
        private String CreateTime;
        private boolean IsGot;
        private int State;
        private String LastPayDateTime;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }

        public int getNumber() {
            return Number;
        }

        public void setNumber(int Number) {
            this.Number = Number;
        }

        public String getSerialNo() {
            return SerialNo;
        }

        public void setSerialNo(String SerialNo) {
            this.SerialNo = SerialNo;
        }

        public int getType() {
            return Type;
        }

        public void setType(int Type) {
            this.Type = Type;
        }

        public int getLevel() {
            return Level;
        }

        public void setLevel(int Level) {
            this.Level = Level;
        }

        public double getCost() {
            return Cost;
        }

        public void setCost(double Cost) {
            this.Cost = Cost;
        }

        public String getDeadline() {
            return Deadline;
        }

        public void setDeadline(String Deadline) {
            this.Deadline = Deadline;
        }

        public int getPayCount() {
            return PayCount;
        }

        public void setPayCount(int PayCount) {
            this.PayCount = PayCount;
        }

        public String getLastSubsidyDate() {
            return LastSubsidyDate;
        }

        public void setLastSubsidyDate(String LastSubsidyDate) {
            this.LastSubsidyDate = LastSubsidyDate;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public boolean isIsGot() {
            return IsGot;
        }

        public void setIsGot(boolean IsGot) {
            this.IsGot = IsGot;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public String getLastPayDateTime() {
            return LastPayDateTime;
        }

        public void setLastPayDateTime(String LastPayDateTime) {
            this.LastPayDateTime = LastPayDateTime;
        }
    }

    public static class FinanceBean {
        /**
         * UserId : 1176e3eb-e8ae-4a3c-8bd5-27419bb1ccae
         * Kind : 0
         * Balance : 0.0
         */

        private String UserId;
        private int Kind;
        private double Balance;

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String UserId) {
            this.UserId = UserId;
        }

        public int getKind() {
            return Kind;
        }

        public void setKind(int Kind) {
            this.Kind = Kind;
        }

        public double getBalance() {
            return Balance;
        }

        public void setBalance(double Balance) {
            this.Balance = Balance;
        }
    }
}
