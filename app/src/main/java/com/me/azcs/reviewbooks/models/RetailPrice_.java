
package com.me.azcs.reviewbooks.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RetailPrice_ implements Parcelable{

    @SerializedName("amountInMicros")
    @Expose
    private Double amountInMicros;
    @SerializedName("currencyCode")
    @Expose
    private String currencyCode;

    protected RetailPrice_(Parcel in) {
        currencyCode = in.readString();
    }

    public static final Creator<RetailPrice_> CREATOR = new Creator<RetailPrice_>() {
        @Override
        public RetailPrice_ createFromParcel(Parcel in) {
            return new RetailPrice_(in);
        }

        @Override
        public RetailPrice_[] newArray(int size) {
            return new RetailPrice_[size];
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
