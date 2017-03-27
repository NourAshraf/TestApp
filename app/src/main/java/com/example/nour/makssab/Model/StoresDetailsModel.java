package com.example.nour.makssab.Model;

import java.util.ArrayList;

/**
 * Created by Kareem on 3/27/2017.
 */

public class StoresDetailsModel {
    private String id;
    private String userId;
    private String name;
    private ArrayList<String> Images;
    private String description;
    private String phone;
    private String created_at;
    private String userName;
    private String city_id;
    private String city_name;
    private String category_id;
    private String title;
    private String Views;


    public StoresDetailsModel(String id, String userId, String name, ArrayList<String> images, String description, String phone, String created_at, String userName, String city_id, String city_name, String category_id, String title, String views) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        Images = images;
        this.description = description;
        this.phone = phone;
        this.created_at = created_at;
        this.userName = userName;
        this.city_id = city_id;
        this.city_name = city_name;
        this.category_id = category_id;
        this.title = title;
        Views = views;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getImages() {
        return Images;
    }

    public void setImages(ArrayList<String> images) {
        Images = images;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViews() {
        return Views;
    }

    public void setViews(String views) {
        Views = views;
    }


}
