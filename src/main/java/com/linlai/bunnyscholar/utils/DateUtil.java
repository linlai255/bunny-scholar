package com.linlai.bunnyscholar.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    public static String getNowDate() {
        return new SimpleDateFormat(DATE_FORMAT).format(new Date());
    }
}
