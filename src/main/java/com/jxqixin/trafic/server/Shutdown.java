package com.jxqixin.trafic.server;
import com.jxqixin.trafic.util.RedisUtil;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * 服务器关闭时执行的动作
 */
public class Shutdown implements DisposableBean {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void destroy() throws Exception {
        redisUtil.clearCache();
    }
}
