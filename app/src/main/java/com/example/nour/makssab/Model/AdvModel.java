package com.example.nour.makssab.Model;

/**
 * Created by nour on 13-Mar-17.
 */

public class AdvModel {
    private int Id;
    private int City_Id;
    private int Views;
    private int category_id;
    private String Title;
    private String Description;
    private String Phone;
    private String City_Name;

    public AdvModel(int id, int city_Id, int views, int category_id, String title, String description, String phone, String city_Name) {
        Id = id;
        City_Id = city_Id;
        Views = views;
        this.category_id = category_id;
        Title = title;
        Description = description;
        Phone = phone;
        City_Name = city_Name;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCity_Id() {
        return City_Id;
    }

    public void setCity_Id(int city_Id) {
        City_Id = city_Id;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int views) {
        Views = views;
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
}
