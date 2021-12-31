package com.xiaowuyaya.bstdormitorycms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaowuyaya.bstdormitorycms.mapper")
public class BstDormitoryCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BstDormitoryCmsApplication.class, args);
    }

}
