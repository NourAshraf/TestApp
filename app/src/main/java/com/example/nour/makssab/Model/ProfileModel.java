package com.example.nour.makssab.Model;

/**
 * Created by Kareem on 4/2/2017.
 */

public class ProfileModel {
    private String id;
    private String username;
    private String email;
    private String phone;
    private String created_at;
    private String last_login;
    private String city_id;
    private String name;

    public ProfileModel(String id, String username, String email, String phone, String created_at, String last_login, String city_id, String name) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.created_at = created_at;
        this.last_login = last_login;
        this.city_id = city_id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
