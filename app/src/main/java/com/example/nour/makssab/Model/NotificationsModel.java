package com.example.nour.makssab.Model;

/**
 * Created by Kareem on 3/7/2017.
 */

public class NotificationsModel {
    private String NotificationsName;
    private String NotificationsDetails;
    private String ImageNotifications;

    public NotificationsModel(String notificationsName, String notificationsDetails, String imageNotifications) {
        NotificationsName = notificationsName;
        NotificationsDetails = notificationsDetails;
        ImageNotifications = imageNotifications;
    }

    public String getNotificationsName() {
        return NotificationsName;
    }

    public void setNotificationsName(String notificationsName) {
        NotificationsName = notificationsName;
    }

    public String getNotificationsDetails() {
        return NotificationsDetails;
    }

    public void setNotificationsDetails(String notificationsDetails) {
        NotificationsDetails = notificationsDetails;
    }

    public String getImageNotifications() {
        return ImageNotifications;
    }

    public void setImageNotifications(String imageNotifications) {
        ImageNotifications = imageNotifications;
    }
}
