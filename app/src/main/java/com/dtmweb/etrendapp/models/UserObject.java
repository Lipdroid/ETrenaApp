package com.dtmweb.etrendapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.dtmweb.etrendapp.constants.Constants;

/**
 * Created by mdmunirhossain on 6/5/18.
 */

public class UserObject implements Parcelable {

    private String userId = null;
    private String email = null;
    private String username = null;
    private String pro_img = null;
    private String contact_no = null;
    private String instagram = null;
    private Boolean is_subscribed = false;
    private String user_type = null;
    private StoreObject storeObject = null;
    private String country = null;
    private String city = null;
    private String address =null;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public StoreObject getStoreObject() {
        return storeObject;
    }

    public void setStoreObject(StoreObject storeObject) {
        this.storeObject = storeObject;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(Boolean is_buyer ,Boolean is_seller) {
        if(is_buyer){
            this.user_type = Constants.CATEGORY_BUYER;
        }else if(is_seller){
            this.user_type = Constants.CATEGORY_SELLER;
        }else {
            this.user_type = Constants.CATEGORY_NON_LOGGED;
        }

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPro_img() {
        return pro_img;
    }

    public void setPro_img(String pro_img) {
        this.pro_img = pro_img;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public Boolean getIs_subscribed() {
        return is_subscribed;
    }

    public void setIs_subscribed(Boolean is_subscribed) {
        this.is_subscribed = is_subscribed;
    }

    public UserObject(Parcel in) {
    }

    public UserObject() {
    }

    public static final Creator<UserObject> CREATOR = new Creator<UserObject>() {
        @Override
        public UserObject createFromParcel(Parcel in) {
            return new UserObject(in);
        }

        @Override
        public UserObject[] newArray(int size) {
            return new UserObject[size];
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
