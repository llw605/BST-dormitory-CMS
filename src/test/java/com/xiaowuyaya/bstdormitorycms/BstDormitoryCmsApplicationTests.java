package com.xiaowuyaya.bstdormitorycms;

import com.xiaowuyaya.bstdormitorycms.entity.Building;
import com.xiaowuyaya.bstdormitorycms.mapper.BuildingMapper;
import com.xiaowuyaya.bstdormitorycms.mapper.DormitoryInfoMapper;
import com.xiaowuyaya.bstdormitorycms.service.DormitoryInfoService;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class BstDormitoryCmsApplicationTests {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void testPasswordEncoder() {
        String admin = passwordEncoder.encode("admin");
        System.out.println(admin);
    }

    @Autowired
    private DormitoryInfoService dormitoryInfoService;

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private DormitoryInfoMapper dormitoryInfoMapper;

    @Test
    void testSelect(){
        Building building = buildingMapper.selectById(1);
        System.out.println(building);
    }

    @Test
    void testSelectBy(){
        ResponseResult dormitoryInfoListPage = dormitoryInfoService.getDormitoryInfoListPage(1, 20);
        System.out.println(dormitoryInfoListPage);
    }


}
