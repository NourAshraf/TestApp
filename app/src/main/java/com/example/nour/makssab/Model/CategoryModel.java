package com.example.nour.makssab.Model;

/**
 * Created by Kareem on 3/8/2017.
 */

public class CategoryModel {
    private String Id;
    private String Name;
    private String CategoryId;

    public CategoryModel(String id, String name, String categoryId) {
        Id = id;
        Name = name;
        CategoryId = categoryId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }
}
