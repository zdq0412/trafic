package com.jxqixin.trafic.interceptors;

import com.alibaba.fastjson.JSON;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Token信息拦截器
 */
@Component
public class AuthencationInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            response.getWriter().print("用户未登录，请登录后操作！");
            return false;
        }else{
             String username = (String)redisUtil.get(token);
             //登录超时，默认为30分钟
             if(StringUtils.isEmpty(username)){
                 response.getWriter().write(JSON.toJSONString(new JsonResult(false,"登录超时")));
                 return false;
             }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
