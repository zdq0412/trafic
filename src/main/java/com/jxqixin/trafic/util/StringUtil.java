package com.jxqixin.trafic.util;

import org.thymeleaf.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy");
    /**
     * 处理页面传递到后台的ID数组
     * @param idStr
     * @return
     */
    public static String[] handleIds(String idStr){
       if(idStr==null || "".equals(idStr.trim())){
           return null;
       }
       idStr = idStr.replace("[","");
       idStr = idStr.replace("]","");
       idStr = idStr.replace("\"","");
        return idStr.split(",");
    }

    /**
     * 根据企业简称生成新的发文字号
     * @param num 企业简称
     * @param maxNum 当前记录中文字编号的最大值
     * @return
     */
    public static  String generateNewNum(String num,String maxNum) {
        Date now = new Date();
        String currentYear = format.format(now);
        if(StringUtils.isEmpty(maxNum)){
            if(StringUtils.isEmpty(num)){
                return currentYear + "0001";
            }else{
                return num + currentYear + "0001";
            }
        }else{
            //截取后倒数第八位到倒数第四位
            String year = maxNum.substring(maxNum.length()-8,maxNum.length()-4);
            if(currentYear.compareTo(year)==0){
                //截取后四位加一
                String last4 = maxNum.substring(maxNum.length()-4);
                int intNum = Integer.parseInt(last4) + 1;
                String strNum = String.valueOf(intNum);
                switch (strNum.length()){
                    case 1:{
                        last4 = "000" + strNum;
                        break;
                    }
                    case 2:{
                        last4 = "00" + strNum;
                        break;
                    }
                    case 3:{
                        last4 = "0" + strNum;
                        break;
                    }case 4:{
                        last4 = strNum;
                        break;
                    }
                }
                return num==null?currentYear+last4:num+currentYear+last4;
            }else{
                return num==null?currentYear+"0001":num+currentYear+"0001";
            }
        }
    }
}
