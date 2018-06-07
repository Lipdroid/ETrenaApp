package com.dtmweb.etrendapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mdmunirhossain on 6/7/18.
 */

public class CategoryObject implements Parcelable {
    private String id = null;
    private String name = null;
    private String icon = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    protected CategoryObject(Parcel in) {
    }

    public CategoryObject() {
    }

    public static final Creator<CategoryObject> CREATOR = new Creator<CategoryObject>() {
        @Override
        public CategoryObject createFromParcel(Parcel in) {
            return new CategoryObject(in);
        }

        @Override
        public CategoryObject[] newArray(int size) {
            return new CategoryObject[size];
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