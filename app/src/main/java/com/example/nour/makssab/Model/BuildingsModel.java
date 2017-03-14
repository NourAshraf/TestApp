package com.example.nour.makssab.Model;

/**
 * Created by Kareem on 3/14/2017.
 */

public class BuildingsModel {
    private String Number;
    private String Name;
    private String City;
    private int Image;

    public BuildingsModel(String number, String name, String city, int image) {
        Number = number;
        Name = name;
        City = city;
        Image = image;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
