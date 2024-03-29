package com.sharedmemesapp.entities;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("name")
    String name;
    @SerializedName("address")
    String address;

    public User() {
    }

    public User(String name, String address) {
        this.address = address;
        this.name = name;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
