package com.example.therehabapp.ValueObjects;

public class Thought {

    public String ThoughtMessage;
    public String TimeStamp;
    public String DateStamp;
    public String Topic;
    public String Name;

    public Thought(){}

    public Thought(String ThoughtMessage, String TimeStamp, String DateStamp,String Topic,String Name)
    {

        this.ThoughtMessage=ThoughtMessage;
        this.TimeStamp = TimeStamp;
        this.DateStamp = DateStamp;
        this.Topic = Topic;
        this.Name = Name;

    }
}
