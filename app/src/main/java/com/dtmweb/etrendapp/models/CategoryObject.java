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
    private String attribute_id = null;
    private String attribute_name = null;

    public String getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(String attribute_id) {
        this.attribute_id = attribute_id;
    }

    public String getAttribute_name() {
        return attribute_name;
    }

    public void setAttribute_name(String attribute_name) {
        this.attribute_name = attribute_name;
    }

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
        readFromParcel(in);
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
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(icon);
        dest.writeString(attribute_id);
        dest.writeString(attribute_name);
    }

    public void readFromParcel(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.icon = in.readString();
        this.attribute_id = in.readString();
        this.attribute_name = in.readString();
    }
}