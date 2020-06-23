package com.jxqixin.trafic.constant;
import com.alibaba.fastjson.serializer.SerializeConfig;
/**
 * json化时将枚举作为对象json化配置
 */
public class EnumAsJaveBeanConfig {

   private static final SerializeConfig config = new SerializeConfig();
   static{
       config.configEnumAsJavaBean(Result.class);
   }

   public static SerializeConfig getSerializeConfig(){
       return config;
   }
}
