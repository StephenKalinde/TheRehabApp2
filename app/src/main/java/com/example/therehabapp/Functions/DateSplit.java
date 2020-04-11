/**
 * Author Tarisai Stephen Kalinde (www.stephdev.co.za)
 *
 * Date loose value holder**/

package com.example.therehabapp.Functions;

public class DateSplit {

    public int Day;
    public int Month;
    public int Year;
    public int Week;

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

    public String toString()
    {
        return ""+Day+"/"+Month+"/"+Year;
    }

    public void SetWeek(int week){

        this.Week = week;

    }

}
