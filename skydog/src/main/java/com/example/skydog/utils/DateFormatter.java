package com.example.skydog.utils;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter implements Formatter<Date> {
    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        String regDate = "^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}$";
        String regDateTime = "^[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}\\s[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}$";
        SimpleDateFormat sdf;
        /*
         * 匹配日期格式
         */
        if (text.trim().matches(regDate)){
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }else if(text.trim().matches(regDateTime)){
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }else {
            return null;
        }
        Date date = sdf.parse(text);
        return date;
    }
    @Override
    public String print(Date object, Locale locale) {
        return null;
    }
}






