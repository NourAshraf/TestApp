package com.example.nour.makssab.Model;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kareem on 3/6/2017.
 */

public class MembersFavoritesModel {
    private String Title;
    private String Name;
    private String Place;
    private String CommentNumber;
    private String SeenNumber;
    private String Dakeka;
    private String Monzoo;
    private String TimeNumber;
    private int Adv;
    private String Person;
    private String Flag;
    private String Time;
    private String Comment;
    private String Eye;

    public MembersFavoritesModel(String title, String name, String place, String commentNumber, String seenNumber, String dakeka, String monzoo, String timeNumber, int adv, String person, String flag, String time, String comment, String eye) {
        Title = title;
        Name = name;
        Place = place;
        CommentNumber = commentNumber;
        SeenNumber = seenNumber;
        Dakeka = dakeka;
        Monzoo = monzoo;
        TimeNumber = timeNumber;
        Adv = adv;
        Person = person;
        Flag = flag;
        Time = time;
        Comment = comment;
        Eye = eye;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getCommentNumber() {
        return CommentNumber;
    }

    public void setCommentNumber(String commentNumber) {
        CommentNumber = commentNumber;
    }

    public String getSeenNumber() {
        return SeenNumber;
    }

    public void setSeenNumber(String seenNumber) {
        SeenNumber = seenNumber;
    }

    public String getDakeka() {
        return Dakeka;
    }

    public void setDakeka(String dakeka) {
        Dakeka = dakeka;
    }

    public String getMonzoo() {
        return Monzoo;
    }

    public void setMonzoo(String monzoo) {
        Monzoo = monzoo;
    }

    public String getTimeNumber() {
        return TimeNumber;
    }

    public void setTimeNumber(String timeNumber) {
        TimeNumber = timeNumber;
    }

    public int getAdv() {
        return Adv;
    }

    public void setAdv(int adv) {
        Adv = adv;
    }

    public String getPerson() {
        return Person;
    }

    public void setPerson(String person) {
        Person = person;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getEye() {
        return Eye;
    }

    public void setEye(String eye) {
        Eye = eye;
    }
}
