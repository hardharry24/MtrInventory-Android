package models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Payment {
    @SerializedName("pId")
    int pId;
    @SerializedName("pOrderMsterId")
    int pOrderMsterId;
    @SerializedName("pAmount")
    Double pAmount;
    @SerializedName("pTotal")
    Double pTotal;
    @SerializedName("pChange")
    Double pChange;
    @SerializedName("pUsername")
    String pUsername;
    @SerializedName("pDateTime")
    String pDateTime;

    @SerializedName("message")
    List<Payment> paymentList;


    public int getpId() {
        return pId;
    }

    public int getpOrderMsterId() {
        return pOrderMsterId;
    }

    public Double getpAmount() {
        return pAmount;
    }

    public Double getpTotal() {
        return pTotal;
    }

    public Double getpChange() {
        return pChange;
    }

    public String getpUsername() {
        return pUsername;
    }

    public String getpDateTime() {
        return pDateTime;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }
}
