package com.example.therehabapp.ValueObjects;

public class MessagingReturnObject {

    private boolean FolderExists;
    private String InboxID;

    public MessagingReturnObject(boolean FolderExists, String InboxID)
    {

        this.FolderExists = FolderExists;
        this.InboxID = InboxID;

    }

    public String getInboxID()
    {

        String returnID = InboxID;
        return  returnID;

    }

    public boolean getFolderExist()
    {

        boolean returnFolderExist = FolderExists;
        return returnFolderExist;

    }
}
