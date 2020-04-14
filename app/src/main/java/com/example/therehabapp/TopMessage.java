package com.example.therehabapp;

public class TopMessage {

    public String Message;
    public String Date;
    public String Time;
    public String UID;
    public String InboxID;

    public TopMessage(){}

    public TopMessage(String message, String date, String time, String uid, String inboxId)
    {

        Message = message;
        Date = date;
        Time = time;
        UID = uid;
        InboxID = inboxId;

    }

}
