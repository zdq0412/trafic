package com.jxqixin.trafic.handler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.jxqixin.trafic.constant.EnumAsJaveBeanConfig;
import com.jxqixin.trafic.constant.RedisConstant;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.model.AreaManager;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IAreaManagerService;
import com.jxqixin.trafic.service.IUserService;
import com.jxqixin.trafic.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private IUserService userService;
    @Autowired
    private IAreaManagerService areaManagerService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException,
            ServletException {
        String username = authentication.getName();
        String token = redisUtil.generateToken();
        User user = userService.queryUserByUsername(username);
        String loginType = "";
        if(user==null){
            loginType = "AreaManager";
            redisUtil.set(username,loginType);
        }else{
            loginType = "User";
            redisUtil.set(username,loginType);
        }
        redisUtil.setExpire(token + "-" + loginType,username, RedisConstant.EXPIRE_MINUTES);
        //返回json数据
        JsonResult result = new JsonResult(Result.SUCCESS,token + "-" + loginType);
        //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("text/json;charset=utf-8");
        String origin = httpServletRequest.getHeader("Origin");
        if(origin == null) {
            origin = httpServletRequest.getHeader("Referer");
        }
        httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
        //塞到HttpServletResponse中返回给前台
        String retString = JSON.toJSONString(result, EnumAsJaveBeanConfig.getSerializeConfig());
        httpServletResponse.getWriter().write(retString);
    }
}