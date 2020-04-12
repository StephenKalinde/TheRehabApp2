/**
 * Author Tarisai Stephen Kalinde (www.stephdev.co.za)
 *
 * Schedule log for programme from the start date**/

package com.example.therehabapp.Functions;

public class ScheduleLog
{

    public DateSplit week1;
    public DateSplit week10;
    public DateSplit week11;
    public DateSplit week12;
    public DateSplit week2;
    public DateSplit week3;
    public DateSplit week4;
    public DateSplit week5;
    public DateSplit week6;
    public DateSplit week7;
    public DateSplit week8;
    public DateSplit week9;

    public ScheduleLog(){}

    public ScheduleLog(DateSplit wk1, DateSplit wk2, DateSplit wk3, DateSplit wk4, DateSplit wk5, DateSplit wk6,DateSplit wk7, DateSplit wk8, DateSplit wk9, DateSplit wk10, DateSplit wk11, DateSplit wk12 )
    {
        this.week1 =wk1;
        week1.Week =1;
        this.week2 =wk2;
        week2.Week =2;
        this.week3 =wk3;
        week3.Week =3;
        this.week4 = wk4;
        week4.Week =4;
        this.week5 = wk5;
        week5.Week =5;
        this.week6 = wk6;
        week6.Week =6;
        this.week7 = wk7;
        week7.Week =7;
        this.week8 = wk8;
        week8.Week =8;
        this.week9 = wk9;
        week9.Week =9;
        this.week10= wk10;
        week10.Week =10;
        this.week11= wk11;
        week11.Week =11;
        this.week12= wk12;
        week12.Week =12;
    }



}

