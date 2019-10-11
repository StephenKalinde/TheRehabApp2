package com.example.therehabapp;

public class NoteBuilder {

    private String title;
    private String content;

    public NoteBuilder()
    { }

    public NoteBuilder(String title, String content)
    {
        this.title=title;
        this.content=content;
    }

    public String getTitle()
    {
        return title;
    }

    public String getContent()
    {
        return content;
    }

}
