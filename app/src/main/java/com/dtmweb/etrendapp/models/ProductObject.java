package com.dtmweb.etrendapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mdmunirhossain on 3/13/18.
 */

public class ProductObject implements Parcelable {
    private String id = null;
    private String image_url = null;
    private String title = null;
    private String short_description = null;
    private String is_favourite = null;
    private String lowest_price = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShort_description() {
        return short_description;
    }

    public void setShort_description(String short_description) {
        this.short_description = short_description;
    }

    public String getIs_favourite() {
        return is_favourite;
    }

    public void setIs_favourite(String is_favourite) {
        this.is_favourite = is_favourite;
    }

    public String getLowest_price() {
        return lowest_price;
    }

    public void setLowest_price(String lowest_price) {
        this.lowest_price = lowest_price;
    }

    protected ProductObject(Parcel in) {
    }

    public ProductObject() {
    }

    public static final Creator<ProductObject> CREATOR = new Creator<ProductObject>() {
        @Override
        public ProductObject createFromParcel(Parcel in) {
            return new ProductObject(in);
        }

        @Override
        public ProductObject[] newArray(int size) {
            return new ProductObject[size];
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
