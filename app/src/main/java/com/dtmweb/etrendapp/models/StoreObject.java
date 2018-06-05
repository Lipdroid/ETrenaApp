package com.dtmweb.etrendapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mdmunirhossain on 6/5/18.
 */

public class StoreObject implements Parcelable {

    private String store_name = null;
    private String bank_name = null;
    private String bank_acc_name = null;
    private String bank_acc_number = null;
    private String country = null;
    private String city = null;
    private String address =null;
    private String cover_photo = null;
    private Boolean is_Active = false;
    private Boolean is_subscribed = false;

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

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }

    public Boolean getIs_Active() {
        return is_Active;
    }

    public void setIs_Active(Boolean is_Active) {
        this.is_Active = is_Active;
    }

    public Boolean getIs_subscribed() {
        return is_subscribed;
    }

    public void setIs_subscribed(Boolean is_subscribed) {
        this.is_subscribed = is_subscribed;
    }

    public StoreObject(Parcel in) {
    }

    public StoreObject() {
    }

    public static final Creator<StoreObject> CREATOR = new Creator<StoreObject>() {
        @Override
        public StoreObject createFromParcel(Parcel in) {
            return new StoreObject(in);
        }

        @Override
        public StoreObject[] newArray(int size) {
            return new StoreObject[size];
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
