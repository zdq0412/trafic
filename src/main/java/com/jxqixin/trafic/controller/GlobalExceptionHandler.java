package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.model.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //捕获全局异常,处理所有不可知的异常
    @ExceptionHandler(value = Exception.class)
    Object handleException(Exception e, HttpServletRequest request){
        LOG.error("url {}, msg {}",request.getRequestURL(), e.getMessage());
        Result result = Result.FAIL;
        result.setMessage("系统异常，请联系管理员!");
        return new JsonResult(result);
    }
}
