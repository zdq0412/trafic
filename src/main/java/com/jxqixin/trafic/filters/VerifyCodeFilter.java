package com.jxqixin.trafic.filters;
import com.alibaba.fastjson.JSON;
import com.jxqixin.trafic.constant.EnumAsJaveBeanConfig;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.model.JsonResult;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
@Component
public class VerifyCodeFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse res = (HttpServletResponse)servletResponse;
        String path = req.getServletPath();
        String method = req.getMethod();
        if("/login".equals(path) && "POST".equals(method)){
            String code = req.getParameter("verifyCode");
            String verifyCode = (String) req.getSession().getAttribute("code");
            if(!StringUtils.isEmpty(code) && verifyCode.toLowerCase().equals(code.toLowerCase())){
                filterChain.doFilter(req,res);
            }else{
                res.setContentType("application/json;charset=utf-8");
                Writer out = res.getWriter();
                out.write(JSON.toJSONString(new JsonResult(Result.VERIFYCODE_ERROR), EnumAsJaveBeanConfig.getSerializeConfig()));
                out.flush();
                out.close();
                return ;
            }
        }else{
            filterChain.doFilter(req,res);
        }
    }
}
