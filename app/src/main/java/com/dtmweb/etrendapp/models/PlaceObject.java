package com.dtmweb.etrendapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mdmunirhossain on 6/6/18.
 */

public class PlaceObject implements Parcelable {

    private String id = null;
    private String name = null;

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

    protected PlaceObject(Parcel in) {
        readFromParcel(in);
    }

    public PlaceObject() {
    }

    public static final Creator<PlaceObject> CREATOR = new Creator<PlaceObject>() {
        @Override
        public PlaceObject createFromParcel(Parcel in) {
            return new PlaceObject(in);
        }

        @Override
        public PlaceObject[] newArray(int size) {
            return new PlaceObject[size];
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
    }
    public void readFromParcel(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }
}
