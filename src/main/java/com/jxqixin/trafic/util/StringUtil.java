package com.jxqixin.trafic.util;

import org.thymeleaf.util.StringUtils;

public class StringUtil {
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
}
