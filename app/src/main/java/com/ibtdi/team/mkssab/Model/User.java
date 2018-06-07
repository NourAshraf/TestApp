package com.ibtdi.team.mkssab.Model;

import com.stfalcon.chatkit.commons.models.IUser;

/**
 * Created by team on 11-Mar-17.
 */

public class User implements IUser {

    private final String mAvatar;
    private final String mName;
    private final String mId;

    public User(String id, String name, String Avatar) {
        mId=id;
        mName=name;
        mAvatar=Avatar;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getAvatar() {
        return mAvatar;
    }
}
