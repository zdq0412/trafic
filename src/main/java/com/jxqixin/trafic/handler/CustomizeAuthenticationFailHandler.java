package com.jxqixin.trafic.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.jxqixin.trafic.constant.EnumAsJaveBeanConfig;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomizeAuthenticationFailHandler implements AuthenticationFailureHandler {
    @Autowired
    IUserService userService;
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //返回json数据
        JsonResult result = new JsonResult(Result.FAIL);
        //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        //塞到HttpServletResponse中返回给前台
        String retString = JSON.toJSONString(result, EnumAsJaveBeanConfig.getSerializeConfig());
        httpServletResponse.getWriter().write(retString);
    }
}