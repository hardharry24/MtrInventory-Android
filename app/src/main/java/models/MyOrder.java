package models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrder {
    @SerializedName("orderMasterId")
    int orderMasterId;
    @SerializedName("ordertbMasterId")
    int ordertbMasterId;
    @SerializedName("orderMstrUsername")
    String orderMstrUsername;
    @SerializedName("orderMstrDateTime")
    String orderMstrDateTime;

    @SerializedName("message")
    List<MyOrder> myOrders;

    public int getOrderMasterId() {
        return orderMasterId;
    }

    public int getOrdertbMasterId() {
        return ordertbMasterId;
    }

    public String getOrderMstrUsername() {
        return orderMstrUsername;
    }

    public String getOrderMstrDateTime() {
        return orderMstrDateTime;
    }

    public List<MyOrder> getMyOrders() {
        return myOrders;
    }
}
