package com.example.nour.makssab.Model;

import java.util.ArrayList;

/**
 * Created by Kareem on 3/27/2017.
 */

public class StoresDetails {
    private int id;
    private String name;
    private String photo;
    private String title;
    private String description;
    private String phone;
    private String longitude;
    private String latitude;
    private String city_name;
    private String city_id;
    private String userId;
    private String userName;
    private ArrayList<String> Images;
    private String created_at;
    private int commentsSize;
    private String Views;
    private String advtitle;

    public StoresDetails(int id, String name, String photo, String title, String description, String phone, String longitude, String latitude, String city_name, String city_id, String userId, String userName, ArrayList<String> images, String created_at, int commentsSize, String views, String advtitle) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.title = title;
        this.description = description;
        this.phone = phone;
        this.longitude = longitude;
        this.latitude = latitude;
        this.city_name = city_name;
        this.city_id = city_id;
        this.userId = userId;
        this.userName = userName;
        Images = images;
        this.created_at = created_at;
        this.commentsSize = commentsSize;
        Views = views;
        this.advtitle = advtitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<String> getImages() {
        return Images;
    }

    public void setImages(ArrayList<String> images) {
        Images = images;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getCommentsSize() {
        return commentsSize;
    }

    public void setCommentsSize(int commentsSize) {
        this.commentsSize = commentsSize;
    }

    public String getViews() {
        return Views;
    }

    public void setViews(String views) {
        Views = views;
    }

    public String getAdvtitle() {
        return advtitle;
    }

    public void setAdvtitle(String advtitle) {
        this.advtitle = advtitle;
    }
}
