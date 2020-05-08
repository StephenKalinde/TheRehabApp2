package com.example.therehabapp.Messaging;

public class Message {

    public String Message;
    public String Date;
    public String Time;
    public String UID;
    public String DestinationUID;

    public Message(){}

    public Message(String message, String date, String time, String uid, String destinationUID)
    {

        Message = message;
        Date = date;
        Time = time;
        UID = uid;
        DestinationUID = destinationUID;

    }

}
