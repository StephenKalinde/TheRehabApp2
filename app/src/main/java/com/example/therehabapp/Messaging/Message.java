package com.example.therehabapp.Messaging;

public class Message {

    public String Message;
    public String Date;
    public String Time;
    public String UID;
    public String SenderName;
    public String DestinationUID;
    public String InboxId;

    public Message(){}

    public Message(String message, String date, String time, String uid, String destinationUID, String senderName,String inboxId)
    {

        Message = message;
        Date = date;
        Time = time;
        UID = uid;
        DestinationUID = destinationUID;
        SenderName =senderName;
        InboxId = inboxId;

    }

}
