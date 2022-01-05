package com.xiaowuyaya.bstdormitorycms;

import com.xiaowuyaya.bstdormitorycms.entity.Building;
import com.xiaowuyaya.bstdormitorycms.mapper.BuildingMapper;
import com.xiaowuyaya.bstdormitorycms.mapper.DormitoryInfoMapper;
import com.xiaowuyaya.bstdormitorycms.service.DormitoryInfoService;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BstDormitoryCmsApplicationTests {

    @Test
    void contextLoads() {
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
