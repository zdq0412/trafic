package com.jxqixin.trafic;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(basePackages = "com.jxqixin.trafic.mapper")
@EnableCaching
public class TraficServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TraficServerApplication.class, args);
    }
}
