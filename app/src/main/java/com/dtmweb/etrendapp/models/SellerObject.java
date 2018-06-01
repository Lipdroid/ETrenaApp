package com.dtmweb.etrendapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mdmunirhossain on 6/1/18.
 */

public class SellerObject implements Parcelable {
    private String userId = null;
    private String email = null;
    private String username = null;
    private String pro_img = null;
    private String store_name = null;
    private String bank_name = null;
    private String bank_acc_name = null;
    private String bank_acc_number = null;
    private String country = null;
    private String city = null;
    private String address =null;
    private String store_owner = null;
    private String contact_no = null;
    private String instagram = null;
    private String user_type = null;

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

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_acc_name() {
        return bank_acc_name;
    }

    public void setBank_acc_name(String bank_acc_name) {
        this.bank_acc_name = bank_acc_name;
    }

    public String getBank_acc_number() {
        return bank_acc_number;
    }

    public void setBank_acc_number(String bank_acc_number) {
        this.bank_acc_number = bank_acc_number;
    }

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

    public String getStore_owner() {
        return store_owner;
    }

    public void setStore_owner(String store_owner) {
        this.store_owner = store_owner;
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

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public SellerObject(Parcel in) {
    }

    public SellerObject() {
    }

    public static final Creator<SellerObject> CREATOR = new Creator<SellerObject>() {
        @Override
        public SellerObject createFromParcel(Parcel in) {
            return new SellerObject(in);
        }

        @Override
        public SellerObject[] newArray(int size) {
            return new SellerObject[size];
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
