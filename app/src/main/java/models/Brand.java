package models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Brand {
    @SerializedName("brandId")
    int brandId;
    @SerializedName("brandName")
    String brandName;
    @SerializedName("brandImg")
    String brandImg;

    public String getBrandImg() {
        return brandImg;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @SerializedName("message")
    public List<Brand> brands;

    public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }
}
