package models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Remaining {
    @SerializedName("id")
    int id;
    @SerializedName("orderMasterId")
    int orderMasterId;
    @SerializedName("mtrQty")
    int mtrQty;
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

    @SerializedName("message")
    List<Remaining> remainingList;

    public int getId() {
        return id;
    }

    public int getOrderMasterId() {
        return orderMasterId;
    }

    public int getMtrQty() {
        return mtrQty;
    }

    public int getOrderMotorId() {
        return orderMotorId;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public String getOrderUsername() {
        return orderUsername;
    }

    public String getMtrBrand() {
        return mtrBrand;
    }

    public String getMtrModel() {
        return mtrModel;
    }

    public String getMtrPrice() {
        return mtrPrice;
    }

    public String getMtrImg() {
        return mtrImg;
    }

    public List<Remaining> getRemainingList() {
        return remainingList;
    }
}
