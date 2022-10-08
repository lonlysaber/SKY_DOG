package com.example.skydog.utils;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class StringFormatter implements Formatter<String> {
    @Override
    public String parse(String text, Locale locale) throws ParseException {
        /**
         * 去掉首尾空格、中间空格
         * 如果参数为""或"  "，返回null
         */
        text = text.replaceAll("\\s*","");
        if(text != null && text .equals("")){
           return null;
        }
        return text;
    }
    @Override
    public String print(String object, Locale locale) {
        return object;
    }
}
