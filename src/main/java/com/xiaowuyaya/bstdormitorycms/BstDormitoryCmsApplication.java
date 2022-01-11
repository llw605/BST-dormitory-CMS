package com.xiaowuyaya.bstdormitorycms;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan("com.xiaowuyaya.bstdormitorycms.mapper")
public class BstDormitoryCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BstDormitoryCmsApplication.class, args);
    }

}
