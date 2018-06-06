package com.dtmweb.etrendapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mdmunirhossain on 6/6/18.
 */

public class BannerObject implements Parcelable {
    private String id = null;
    private String image = null;
    private String title = null;
    private String text = null;
    private String shop = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    protected BannerObject(Parcel in) {
    }

    public BannerObject() {
    }

    public static final Creator<BannerObject> CREATOR = new Creator<BannerObject>() {
        @Override
        public BannerObject createFromParcel(Parcel in) {
            return new BannerObject(in);
        }

        @Override
        public BannerObject[] newArray(int size) {
            return new BannerObject[size];
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
