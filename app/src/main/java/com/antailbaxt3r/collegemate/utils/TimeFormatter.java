package com.antailbaxt3r.collegemate.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatter {

    private Date date;
    public TimeFormatter(String time) throws ParseException {
        //In 24 hour format
        this.date = new SimpleDateFormat("hh-mm").parse(time);
    }

    public TimeFormatter(long timestamp){
        this.date = new Date(timestamp);
    }

    public String getDatabaseTimeFormat(){
        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("hh-mm");
        return simpleDateFormat.format(date);
    }

    public String getTimeFormat1(){
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
        return sdf.format(date);
    }

}
