package com.example.therehabapp.Functions;

public class DateSplit {

    public int Day;
    public int Month;
    public int Year;

    public DateSplit(){}

    public DateSplit(String day, String month, String year)
    {

        this.Day= Integer.parseInt(day);
        this.Month= Integer.parseInt(month);
        this.Year= Integer.parseInt(year);

    }

    public DateSplit(int day, int month, int year)
    {

        this.Day = day;
        this.Month =month;
        this.Year =year;

    }

}
