package models;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("success")
    int success;
    @SerializedName("message")
    String message;

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
