package com.example.nour.makssab.Model;

/**
 * Created by Kareem on 3/7/2017.
 */

public class NotificationsModel {
    private String Title;
    private String UserName;
    private String Type;

    public NotificationsModel(String notificationsName, String notificationsDetails, String imageNotifications) {
        Title = notificationsName;
        UserName = notificationsDetails;
        Type = imageNotifications;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
