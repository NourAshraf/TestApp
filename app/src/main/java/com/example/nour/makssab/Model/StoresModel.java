package com.example.nour.makssab.Model;

/**
 * Created by Kareem on 3/14/2017.
 */

public class StoresModel {
    private String id;
    private String name;
    private String photo;
    private String description;
    private String phone;
    private String longitude;
    private String latitude;
    private String ads_count;
    private String city_name;
    private String city_id;
    private String User_id;
    private String User_Name;

    public StoresModel(String id, String name, String photo, String description, String phone, String longitude, String latitude, String ads_count, String city_name, String city_id,String user_id,String user_name) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.description = description;
        this.phone = phone;
        this.longitude = longitude;
        this.latitude = latitude;
        this.ads_count = ads_count;
        this.city_name = city_name;
        this.city_id = city_id;
        User_Name=user_name;
        User_id=user_id;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAds_count() {
        return ads_count;
    }

    public void setAds_count(String ads_count) {
        this.ads_count = ads_count;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }
}
