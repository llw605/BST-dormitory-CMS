package com.xiaowuyaya.bstdormitorycms;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaowuyaya.bstdormitorycms.entity.Building;
import com.xiaowuyaya.bstdormitorycms.mapper.BuildingMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest
class BstDormitoryCmsApplicationTests {

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test(){
        System.out.println(passwordEncoder.encode("bst666"));


    }

}
