package com.lianxi.dingtu.dingtu_plate.app.entity;


public class EMGoodsTypeTo {

    /**
     * Id : 1e024250-8d92-4449-8f3c-49ef0bf9bda4
     * Name : 荤菜类
     * ParentId : null
     * Description : 3
     * State : 1
     */

    private String Id;
    private String Name;
    private int Order;
    private Object ParentId;
    private String Description;
    private int State;


    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getOrder() {
        return Order;
    }

    public void setOrder(int order) {
        Order = order;
    }

    public Object getParentId() {
        return ParentId;
    }

    public void setParentId(Object ParentId) {
        this.ParentId = ParentId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }
}
