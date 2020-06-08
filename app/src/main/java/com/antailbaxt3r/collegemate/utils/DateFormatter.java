package com.antailbaxt3r.collegemate.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    private Date date;
    public DateFormatter(String timestamp) throws ParseException {
        this.date = new SimpleDateFormat("yyyy-MM-dd").parse(timestamp);
    }
    public String getDateFormat1(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, hh aa");
        return dateFormat.format(this.date);
    }
}
