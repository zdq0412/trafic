package com.jxqixin.trafic.handler;
import com.alibaba.fastjson.JSON;
import com.jxqixin.trafic.constant.EnumAsJaveBeanConfig;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
       //删除Redis库中的token信息
       String token =  httpServletRequest.getHeader("token");
       if(token!=null){
           String username = (String)redisUtil.get(token);
           redisUtil.delete(token);
           if(!StringUtils.isEmpty(username)) {
               redisUtil.delete(username);
           }
       }
        JsonResult result = new JsonResult(Result.SUCCESS);
        //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        //塞到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(JSON.toJSONString(result, EnumAsJaveBeanConfig.getSerializeConfig()));
    }
}