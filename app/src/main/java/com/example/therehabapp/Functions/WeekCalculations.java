package com.example.therehabapp.Functions;

public class WeekCalculations {

    /**
     * Author Tarisai Stephen Kalinde (www.stephdev.co.za)**/

    private String Date;
    private int Day;
    private int Month;
    private int Year;

    private DateSplit[] dateArray;

    private ScheduleLog log = null;

    /**
     * empty constructor*/
    public WeekCalculations()
    {

        dateArray = new DateSplit[12];

    }

    /**
     * constructor that takes in date string*/
    public WeekCalculations(String date){

        this.Date = date;
        Split();

        dateArray = new DateSplit[12];
        dateArray[0]= new DateSplit(Day,Month,Year);

        DateSplit sp = Calculate(Day,Month,Year,1);

        log = new ScheduleLog(dateArray[0],dateArray[1],dateArray[2],dateArray[3],dateArray[4],dateArray[5],dateArray[6],dateArray[7],dateArray[8],dateArray[9],dateArray[10],dateArray[11]);

    }

    /**
     * splits the date string to day, month
     * and year integers*/
    private void Split()
    {

        String[] dateArray = Date.split("-");
        this.Year = Integer.parseInt(dateArray[0]);
        this.Month = Integer.parseInt(dateArray[1]);
        this.Day = Integer.parseInt(dateArray[2]);

    }

    /** recursive function to calculate the subsequent weeks,
     wk1 wk2 wk3... wk12
     fills date array + returns null */
    private DateSplit Calculate(int day, int month, int year,int week)
    {
        //25, 1
        if (week>=12)
        {
            return null;
        }

        if (week < 1)
        {

            return null;

        }

        //define constraints
        int monthLong = 31;
        int monthStd = 30;
        int monthFebLong = 29;
        int monthFebShort = 28;

        //long months
        if(month== 1 || month == 3 || month==5 || month==7 || month==8 || month == 10 || month==12)
        {

            day = day + 7;  //32

            if( day > monthLong)  //true
            {

                int newDay = day-monthLong; // 32 -31 = 1
                day = newDay;      // 1
                month++;  // 04

                if(month > 12)  //false
                {

                    year++;
                    month = month - 12;

                }

            }

            //System.out.println(""+ day);

            week++;
            DateSplit dateSplit = new DateSplit(day,month,year);

            dateArray[week-1]= dateSplit;

            return Calculate(day,month,year,week);

        }

        //standard months
        if( month == 4 || month == 6 || month== 9 || month ==11 )
        {


            day = day + 7 ;

            if( day > monthStd)
            {

                day = day-monthStd;
                month++;

            }

            //System.out.println(""+ day);

            week++;
            DateSplit dateSplit = new DateSplit(day,month,year);

            dateArray[week-1]= dateSplit;

            return Calculate(day,month,year,week);

        }

        //February long
        if(month== 2 && year%4==0)
        {

            day = day + 7;

            if( day > monthFebLong)
            {

                day = day-monthFebLong;
                month++;

            }

            //System.out.println(""+ day);

            week++;
            DateSplit dateSplit = new DateSplit(day,month,year);

            dateArray[week-1]= dateSplit;

            return Calculate(day,month,year,week);

        }

        //Feb short
        else
        {

            day = day + 7;

            if( day > monthFebShort)
            {

                day = day - monthFebShort;
                month++;

            }

            //System.out.println(""+ day);

            week++;
            DateSplit dateSplit = new DateSplit(day,month,year);

            dateArray[week-1]= dateSplit;

            return Calculate(day,month,year,week);

        }

    }

    /**
     * returns DateSplit object*/
    private DateSplit GetSplitDate()
    {

        DateSplit dateSplit = new DateSplit(Day,Month,Year);

        return dateSplit;
    }

    /**
     * returns DateSplit object (public access)*/
    public DateSplit GetSplitDate(int day, int month, int year)
    {

        DateSplit dateSplit = new DateSplit(day,month,year);
        return dateSplit;

    }

    /**
     * returns ScheduleLog Object (public access)*/
    public ScheduleLog GetScheduleLog()
    {
        return log;
    }

}
