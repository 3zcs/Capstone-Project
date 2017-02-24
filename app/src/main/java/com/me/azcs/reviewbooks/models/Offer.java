
package com.me.azcs.reviewbooks.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offer implements Parcelable{

    @SerializedName("finskyOfferType")
    @Expose
    private Integer finskyOfferType;
    @SerializedName("listPrice")
    @Expose
    private ListPrice_ listPrice;
    @SerializedName("retailPrice")
    @Expose
    private RetailPrice_ retailPrice;

    protected Offer(Parcel in) {
        retailPrice = in.readParcelable(RetailPrice_.class.getClassLoader());
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

    public Integer getFinskyOfferType() {
        return finskyOfferType;
    }

    public void setFinskyOfferType(Integer finskyOfferType) {
        this.finskyOfferType = finskyOfferType;
    }

    public ListPrice_ getListPrice() {
        return listPrice;
    }

    public void setListPrice(ListPrice_ listPrice) {
        this.listPrice = listPrice;
    }

    public RetailPrice_ getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(RetailPrice_ retailPrice) {
        this.retailPrice = retailPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(retailPrice, flags);
    }
}
