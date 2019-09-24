package models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("ID")
    int ID;
    @SerializedName("Lastname")
    String Lastname;
    @SerializedName("Firstname")
    String Firstname;
    @SerializedName("MI")
    String MI;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getMI() {
        return MI;
    }

    public void setMI(String MI) {
        this.MI = MI;
    }
}
