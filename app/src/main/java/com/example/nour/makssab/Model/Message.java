package com.example.nour.makssab.Model;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

/**
 * Created by nour on 11-Mar-17.
 */

public class Message implements IMessage {

    private final Date mCreatedAt;
    private final String mId;
    private final String mText;
    private final String mUserAvatar;
    private final String mUsername;

    public Message(String id, String Text, String UserName, String UserAvatar, Date CreatedAt) {
        mId=id;
        mText=Text;
        mUsername=UserName;
        mUserAvatar=UserAvatar;
        mCreatedAt=CreatedAt;
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public String getText() {
        return mText;
    }

    @Override
    public IUser getUser() {
        User user = new User(mId, mUsername, mUserAvatar);
        return user;
    }

    @Override
    public Date getCreatedAt() {
        return mCreatedAt;
    }
}
