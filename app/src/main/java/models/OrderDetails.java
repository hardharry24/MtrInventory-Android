package models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetails {
    @SerializedName("orderId")
    int orderId;

    @SerializedName("orderMasterId")
    int orderMasterId;

    @SerializedName("orderMotorId")
    int orderMotorId;

    @SerializedName("orderQty")
    int orderQty;

    @SerializedName("orderUsername ")
    String orderUsername ;

    @SerializedName("message")
    List<OrderDetails> detailsList;

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

    public List<OrderDetails> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<OrderDetails> detailsList) {
        this.detailsList = detailsList;
    }
}
