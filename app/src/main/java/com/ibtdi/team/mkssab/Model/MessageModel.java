package com.ibtdi.team.mkssab.Model;

/**
 * Created by team on 16-May-17.
 */

public class MessageModel {
    private String Message;
    private String Id;

    public MessageModel(String message,String id) {
        Message = message;
        Id = id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
