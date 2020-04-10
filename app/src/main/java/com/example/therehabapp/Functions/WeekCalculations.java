package com.example.therehabapp.Functions;

public class WeekCalculations {

    /**
     * Author Tarisai Stephen Kalinde (www.stephdev.co.za)**/

    private String Date;
    private int Day;
    private int Month;
    private int Year;

    public WeekCalculations(){}

    public WeekCalculations(String date){

        this.Date = date;
        Split();

    }

    public void Split()
    {

        String[] dateArray = Date.split("-");
        this.Year = Integer.parseInt(dateArray[0]);
        this.Month = Integer.parseInt(dateArray[1]);
        this.Day = Integer.parseInt(dateArray[2]);

    }

    public DateSplit GetSplitDate(int day, int month, int year)
    {

        DateSplit dateSplit = new DateSplit(day,month,year);
        return dateSplit;

    }

    public DateSplit GetSplitDate()
    {

        DateSplit dateSplit = new DateSplit(Day,Month,Year);

        return dateSplit;
    }

}
