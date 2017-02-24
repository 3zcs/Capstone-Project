
package com.me.azcs.reviewbooks.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReadingModes implements Parcelable{

    @SerializedName("text")
    @Expose
    private Boolean text;
    @SerializedName("image")
    @Expose
    private Boolean image;

    protected ReadingModes(Parcel in) {
    }

    public static final Creator<ReadingModes> CREATOR = new Creator<ReadingModes>() {
        @Override
        public ReadingModes createFromParcel(Parcel in) {
            return new ReadingModes(in);
        }

        @Override
        public ReadingModes[] newArray(int size) {
            return new ReadingModes[size];
        }
    };

    public Boolean getText() {
        return text;
    }

    public void setText(Boolean text) {
        this.text = text;
    }

    public Boolean getImage() {
        return image;
    }

    public void setImage(Boolean image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
