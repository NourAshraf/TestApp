package com.ibtdi.team.mkssab.Model;

/**
 * Created by Kareem on 3/6/2017.
 */

public class MembersModel {
    private String Name;
    private String Follow;
    private String ImageProfile;

    public MembersModel(String name, String follow, String imageProfile) {
        Name = name;
        Follow = follow;
        ImageProfile = imageProfile;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFollow() {
        return Follow;
    }

    public void setFollow(String follow) {
        Follow = follow;
    }

    public String getImageProfile() {
        return ImageProfile;
    }

    public void setImageProfile(String imageProfile) {
        ImageProfile = imageProfile;
    }
}
