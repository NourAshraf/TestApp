package com.example.nour.makssab.Model;

import java.util.ArrayList;

/**
 * Created by nour on 13-Mar-17.
 */

public class AdvModel {
    private String Id;
    private String City_Id;
    private String Views;
    private String category_id;
    private String Title;
    private String Description;
    private String Phone;
    private String City_Name;
    private String UserId;
    private String UserName;
    private ArrayList<String> Images;

    public AdvModel(String id, String city_Id, String views, String category_id, String title, String description, String phone, String city_Name, String userId, String userName, ArrayList<String> images) {
        Id = id;
        City_Id = city_Id;
        Views = views;
        this.category_id = category_id;
        Title = title;
        Description = description;
        Phone = phone;
        City_Name = city_Name;
        UserId = userId;
        UserName = userName;
        Images = images;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCity_Id() {
        return City_Id;
    }

    public void setCity_Id(String city_Id) {
        City_Id = city_Id;
    }

    public String getViews() {
        return Views;
    }

    public void setViews(String views) {
        Views = views;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCity_Name() {
        return City_Name;
    }

    public void setCity_Name(String city_Name) {
        City_Name = city_Name;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public ArrayList<String> getImages() {
        return Images;
    }

    public void setImages(ArrayList<String> images) {
        Images = images;
    }
}
