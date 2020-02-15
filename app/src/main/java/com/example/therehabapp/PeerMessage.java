package com.example.therehabapp;

import com.example.therehabapp.Messages;
import com.example.therehabapp.Peer;

public class PeerMessage {

    public String PhotoURL;
    public String PeerName;
    public String DateTime;
    public String Message;
    public String MessagePreview;

    public PeerMessage() { }

    public PeerMessage(String PhotoURL , String PeerName ,String DateTime, String Message)
    {
        this.PhotoURL= PhotoURL;
        this.PeerName= PeerName;
        this.DateTime= DateTime;
        this.Message= Message;

        SetPreviewMessage();
    }

    private void SetPreviewMessage()
    {

        MessagePreview= Message.substring(0,11);

    }

}
