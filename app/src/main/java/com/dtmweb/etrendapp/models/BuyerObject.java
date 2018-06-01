package com.dtmweb.etrendapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mdmunirhossain on 6/1/18.
 */

public class BuyerObject implements Parcelable {


    protected BuyerObject(Parcel in) {
    }

    public static final Creator<BuyerObject> CREATOR = new Creator<BuyerObject>() {
        @Override
        public BuyerObject createFromParcel(Parcel in) {
            return new BuyerObject(in);
        }

        @Override
        public BuyerObject[] newArray(int size) {
            return new BuyerObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
