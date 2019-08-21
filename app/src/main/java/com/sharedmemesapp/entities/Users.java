package com.sharedmemesapp.entities;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Users {
    @SerializedName("users")
    HashMap<String,User> usuarios;

    public Users() {
    }

    public Users(HashMap<String, User> usuarios) {
        this.usuarios = usuarios;
    }

    public HashMap<String, User> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(HashMap<String, User> usuarios) {
        this.usuarios = usuarios;
    }
}
