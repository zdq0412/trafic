package com.jxqixin.trafic.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 计算两个日期之间的日期
     * @param from
     * @param to
     * @return
     */
    public static List<String> daysBetween(Date from,Date to){
        List<String> list = new ArrayList<>();
        if(from.after(to)){
            throw new RuntimeException("开始时间不能大于结束时间!");
        }
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(from);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(to);
        while(fromCalendar.before(toCalendar)){
            list.add(format.format(fromCalendar.getTime()));
            fromCalendar.add(Calendar.DATE,1);
        }
        return list;
    }
}
