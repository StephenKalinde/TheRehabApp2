package com.example.therehabapp;

public class NoteBuilder {

    private String title;
    private String content;
    private String date;

    public NoteBuilder()
    { }

    public NoteBuilder(String title, String content, String date)
    {
        this.title=title;
        this.content=content;
        this.date=date;
    }

    public NoteBuilder(String title, String content)
    {

        this.title= title;
        this.content= content;

    }

    public String getTitle()
    {
        return title;
    }

    public String getContent()
    {
        return content;
    }

    public String getDate(){return date;}

}
