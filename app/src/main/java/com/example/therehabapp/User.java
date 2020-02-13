package com.example.therehabapp;

public class User {

    public String Name;
    public String Surname;
    public String ID;
    public String DateOfBirth;
    public String AddressLine1;
    public String AddressLine2;
    public String City;
    public String PostalCode;
    public String HomePhone;
    public String CellPhone;
    public String EmailAddress;
    public String UID;

    public User(){}

    public User(String name, String surname , String id, String dateOfBirth, String addressLine1, String addressLine2, String city , String postalCode, String homePhone, String cellPhone, String emailAddress, String uid)
    {
        Name= name;
        Surname=surname;
        ID=id;
        DateOfBirth=dateOfBirth;
        AddressLine1= addressLine1;
        AddressLine2= addressLine2;
        City= city;
        PostalCode= postalCode;
        HomePhone= homePhone;
        CellPhone= cellPhone;
        EmailAddress= emailAddress;
        UID=uid;
    }
}
