package com.linlai.bunnyscholar.utils;

public class CommonUtil {
    public static String getRandom6() {
        return (int)((Math.random()*9+1)*100000) + "";
    }
}
