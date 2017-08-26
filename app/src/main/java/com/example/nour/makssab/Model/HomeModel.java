package com.example.nour.makssab.Model;

/**
 * Created by nour on 08-Mar-17.
 */

public class HomeModel {
    private String Title;
    private int id;
    private int Image;

    public HomeModel(String title, int id, int image) {
        Title = title;
        this.id = id;
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
