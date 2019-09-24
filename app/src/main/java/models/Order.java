package models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {
    @SerializedName("orderId")
    int orderId;
    @SerializedName("orderMasterId")
    int orderMasterId;
    @SerializedName("orderMotorId")
    int orderMotorId;
    @SerializedName("orderQty")
    int orderQty;
    @SerializedName("orderUsername")
    String orderUsername;
    @SerializedName("mtrBrand")
    String mtrBrand;
    @SerializedName("mtrModel")
    String mtrModel;
    @SerializedName("mtrPrice")
    String mtrPrice;
    @SerializedName("mtrImg")
    String mtrImg;

    public String getMtrBrand() {
        return mtrBrand;
    }

    public void setMtrBrand(String mtrBrand) {
        this.mtrBrand = mtrBrand;
    }

    public String getMtrModel() {
        return mtrModel;
    }

    public void setMtrModel(String mtrModel) {
        this.mtrModel = mtrModel;
    }

    public String getMtrPrice() {
        return mtrPrice;
    }

    public void setMtrPrice(String mtrPrice) {
        this.mtrPrice = mtrPrice;
    }

    public String getMtrImg() {
        return mtrImg;
    }

    public void setMtrImg(String mtrImg) {
        this.mtrImg = mtrImg;
    }

    @SerializedName("message")
    public List<Order> orderList;

    public List<Order> getOrderList() {
        return orderList;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderMasterId() {
        return orderMasterId;
    }

    public void setOrderMasterId(int orderMasterId) {
        this.orderMasterId = orderMasterId;
    }

    public int getOrderMotorId() {
        return orderMotorId;
    }

    public void setOrderMotorId(int orderMotorId) {
        this.orderMotorId = orderMotorId;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public String getOrderUsername() {
        return orderUsername;
    }

    public void setOrderUsername(String orderUsername) {
        this.orderUsername = orderUsername;
    }
}
