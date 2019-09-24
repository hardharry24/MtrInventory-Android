package models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Motorcycle {
    @SerializedName("mtrId")
    int mtrId;
    @SerializedName("mtrBrand")
    String mtrBrand;
    @SerializedName("mtrModel")
    String mtrModel;
    @SerializedName("mtrQty")
    int mtrQty;
    @SerializedName("mtrPrice")
    String mtrPrice;
    @SerializedName("mtrDateTime")
    String mtrDateTime;
    @SerializedName("mtrImg")
    String mtrImg;

    public int getMtrId() {
        return mtrId;
    }

    public void setMtrId(int mtrId) {
        this.mtrId = mtrId;
    }

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

    public int getMtrQty() {
        return mtrQty;
    }

    public void setMtrQty(int mtrQty) {
        this.mtrQty = mtrQty;
    }

    public String getMtrDateTime() {
        return mtrDateTime;
    }

    public void setMtrDateTime(String mtrDateTime) {
        this.mtrDateTime = mtrDateTime;
    }

    public String getMtrImg() {
        return mtrImg;
    }

    public void setMtrImg(String mtrImg) {
        this.mtrImg = mtrImg;
    }

    public String getMtrPrice() {
        return mtrPrice;
    }

    public void setMtrPrice(String mtrPrice) {
        this.mtrPrice = mtrPrice;
    }

    @SerializedName("message")
    public List<Motorcycle> motorcycles;

    public List<Motorcycle> getMotorcycles() {
        return motorcycles;
    }

    public void setMotorcycles(List<Motorcycle> motorcycles) {
        this.motorcycles = motorcycles;
    }
}
