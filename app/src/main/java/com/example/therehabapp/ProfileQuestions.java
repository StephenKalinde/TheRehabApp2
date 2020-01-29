package com.example.therehabapp;

public class ProfileQuestions {

    public boolean HaveYouEverBeenHospitalized;
    public String PhysicianName;
    public String PhysicianNumber;
    public boolean AnyAddictions;
    public String Substances;
    public boolean DoYouSmoke;
    public boolean AnyCriminalRecord;

    public ProfileQuestions(boolean haveYouEverBeenHospitalized, String physicianName, String physicianNumber, boolean anyAddictions, String substances, boolean doYouSmoke, boolean anyCriminalRecord)
    {

        HaveYouEverBeenHospitalized= haveYouEverBeenHospitalized;
        PhysicianName= physicianName;
        PhysicianNumber= physicianNumber;
        AnyAddictions=anyAddictions;
        Substances= substances;
        DoYouSmoke= doYouSmoke;
        AnyCriminalRecord= anyCriminalRecord;

    }
}
