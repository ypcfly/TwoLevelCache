package com.ypc.spring.customcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CustomCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomCacheApplication.class, args);
    }

}
