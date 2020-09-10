package com.jxqixin.trafic.preload;

import com.jxqixin.trafic.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 预加载数据
 */
@Component
public class Preload implements CommandLineRunner {
    @Autowired
    private ICategoryService categoryService;
    @Override
    public void run(String... args) throws Exception {
        categoryService.findAll("区域");
    }
}
