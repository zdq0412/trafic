package com.jxqixin.trafic.util;

import com.jxqixin.trafic.constant.EmpArchiveType;
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
                return currentYear + "01";
            }else{
                return num + currentYear + "01";
            }
        }else{
            //截取后倒数第六位到倒数第二位
            String year = maxNum.substring(maxNum.length()-6,maxNum.length()-2);
            if(currentYear.compareTo(year)==0){
                //截取后两位加一
                String last4 = maxNum.substring(maxNum.length()-2);
                int intNum = Integer.parseInt(last4) + 1;
                String strNum = String.valueOf(intNum);
                switch (strNum.length()){
                    case 1:{
                        last4 = "0" + strNum;
                        break;
                    }
                    case 2:{
                        last4 = strNum;
                        break;
                    }
                }
                return num==null?currentYear+last4:num+currentYear+last4;
            }else{
                return num==null?currentYear+"01":num+currentYear+"01";
            }
        }
    }
    /**
     * 人员档案,档案码操作
     * @param empArchiveType 人员档案类别
     * @param archiveCode 档案码
     * @param count 增加或减少的数量
     * @return 处理后的档案码
     */
    public static String handleEmpArchiveCode(EmpArchiveType empArchiveType,String archiveCode, int count){
        switch (empArchiveType){
            case RESUME:{
                return handle(archiveCode,0,count);
            }
            case CONTRACT:{
                return handle(archiveCode,1,count);
            }
            case QUALIFICATIONDOCUMENT:{
                return handle(archiveCode,2,count);
            }
            case JOBHISTORY:{
                return handle(archiveCode,3,count);
            }
            case INDUCTIONTRAINING:{
                return handle(archiveCode,4,count);
            }
            case SAFETYRESPONSIBILITYAGREEMENT:{
                return handle(archiveCode,5,count);
            }
            case TRAININGEXAMINE:{
                return handle(archiveCode,6,count);
            }
            case OTHERDOCUMENT:{
                return handle(archiveCode,7,count);
            }
        }
        return "";
    }
    /**
     * 处理人员档案档案码
     * @param archiveCode 档案码
     * @param index 索引，0:简历,1:劳动合同,2:资质文件,3:从业经历,4:入职培训,5:安全责任书,6:培训考核情况,7:其他文件
     * @param count 增加或减少的数量
     * @return 处理后的档案码
     */
    private static String handle(String archiveCode,int index,int count){
        String initCode = archiveCode;
        if(StringUtils.isEmpty(initCode)){
            initCode = "0.0.0.0.0.0.0.0";
        }
        String[] initCodeArr = initCode.split("\\.");
        initCodeArr[index]=count+"";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < initCodeArr.length; i++) {
            builder.append(initCodeArr[i]);
            if(i<initCodeArr.length-1){
                builder.append(".");
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(handle("",6,2));
    }
}
