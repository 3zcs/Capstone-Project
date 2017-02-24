
package com.me.azcs.reviewbooks.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListPrice_ implements Parcelable{

    @SerializedName("amountInMicros")
    @Expose
    private Double amountInMicros;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;

    protected ListPrice_(Parcel in) {
        currencyCode = in.readString();
    }

    public static final Creator<ListPrice_> CREATOR = new Creator<ListPrice_>() {
        @Override
        public ListPrice_ createFromParcel(Parcel in) {
            return new ListPrice_(in);
        }

        @Override
        public ListPrice_[] newArray(int size) {
            return new ListPrice_[size];
        }
    };

    public Double getAmountInMicros() {
        return amountInMicros;
    }

    public void setAmountInMicros(Double amountInMicros) {
        this.amountInMicros = amountInMicros;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(currencyCode);
    }
}
