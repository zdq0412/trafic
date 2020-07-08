package com.jxqixin.trafic.util;
import org.thymeleaf.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 身份证号码工具类
 */
public class IdCardUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    /**
     * 从身份证号中获取性别
     * @param idnum
     * @return
     */
    public static String getSex(String idnum){
        validateIdnum(idnum);
        if(idnum.trim().length()==18){
            if (Integer.parseInt(idnum.substring(16, 17)) % 2 == 0) {
                return "女";
            }else{
                return "男";
            }
        }else
            throw new RuntimeException("身份证号不合法!");
    }

    private static void validateIdnum(String idnum){
        if(StringUtils.isEmpty(idnum)){
            throw  new RuntimeException("身份证号不能为空!");
        }
        String regex = "\\d{17}[0-9xX]{1}";
        if(!idnum.matches(regex)){
            throw new RuntimeException("身份证号码不合法!");
        }
    }

    /**
     * 从身份证号中获取年龄
     * @param idnum
     * @return
     */
    public static int getAge(String idnum){
        validateIdnum(idnum);
        String birthday = idnum.substring(6,14);
        try {
            Date birthdayDate = dateFormat.parse(birthday);
            Date now = new Date();
            Calendar birthdayCal = Calendar.getInstance();
            birthdayCal.setTime(birthdayDate);

            Calendar nowCal = Calendar.getInstance();
            nowCal.setTime(now);

            if(birthdayCal.after(nowCal)){
                throw new RuntimeException("身份证号不合法!");
            }

            int year = nowCal.get(Calendar.YEAR) - birthdayCal.get(Calendar.YEAR);
            int month = nowCal.get(Calendar.MONTH)-birthdayCal.get(Calendar.MONTH);
            if(month>0){
                return year;
            }else{
                return year - 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(getAge("220722198304125616"));
        System.out.println(getSex("220722198304125616"));
    }
}
