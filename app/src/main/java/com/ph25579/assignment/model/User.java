package com.ph25579.assignment.model;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("Id")
    int id;
    @SerializedName("Username")
    String username;
    @SerializedName("Password")
    String password;
    @SerializedName("Email")
    String email;
    @SerializedName("route")
    int route;

    public User(int id, String username, String password, String email, int route) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.route = route;
    }

    public User(String username, String password, String email, int route) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.route = route;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }
}
