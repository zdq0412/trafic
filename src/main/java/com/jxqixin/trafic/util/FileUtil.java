package com.jxqixin.trafic.util;

import java.io.File;

/**
 * 文件操作工具类
 */
public class FileUtil {
    /**
     * 根据文件路径删除
     * @param path 文件所在绝对路径
     */
    public static void deleteFile(String path){
        File file  = new File(path);
        if(file.exists()){
            file.delete();
        }
    }
}
