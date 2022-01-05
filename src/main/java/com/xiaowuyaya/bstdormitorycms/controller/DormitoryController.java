package com.xiaowuyaya.bstdormitorycms.controller;

import com.xiaowuyaya.bstdormitorycms.entity.DormitoryInfo;
import com.xiaowuyaya.bstdormitorycms.service.DormitoryInfoService;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Dormitory (控制器)
 * @author xiaowuyaya
 */
@RestController
@CrossOrigin("*")
@RequestMapping(value = "/dormitory")
public class DormitoryController {

    @Autowired
    private DormitoryInfoService dormitoryInfoService;


    @GetMapping("/fetchList")
    public ResponseResult getDormitoryInfoListPage(Integer page, Integer limit){
        return dormitoryInfoService.getDormitoryInfoListPage(page, limit);
    }

    @GetMapping("/getDormitoryInfoByBuildingNoAndFloor")
    public ResponseResult getDormitoryInfoByBuildingNoAndFloor(String buildingNo, Integer floor){
        return dormitoryInfoService.getDormitoryInfoByBuildingNoAndFloor(buildingNo, floor);
    }

    @PostMapping("/updateDormitoryInfo")
    public ResponseResult updateDormitoryInfo(DormitoryInfo dormitoryInfo){
        return dormitoryInfoService.updateDormitoryInfo(dormitoryInfo);
    }

    @PostMapping("/postDormitoryStatistics")
    public ResponseResult postDormitoryStatistics( String buildingName, String floor, Integer roomNo){
        return dormitoryInfoService.postDormitoryStatistics( buildingName, floor, roomNo);
    }


    @PostMapping("createDormitoryInfo")
    public ResponseResult createDormitoryInfo(DormitoryInfo dormitoryInfo){
        return dormitoryInfoService.createDormitoryInfo(dormitoryInfo);
    }

    @GetMapping("/getDormitoryStatistics")
    public ResponseResult getDormitoryStatistics(Integer page, Integer limit){
        return dormitoryInfoService.getDormitoryStatistics(page,limit);
    }

    @GetMapping("/getDormitoryStatisticsByBuildingNoAndFloor")
    public ResponseResult getDormitoryStatisticsByBuildingNoAndFloor(String  buildingNo, Integer floor){
        return dormitoryInfoService.getDormitoryStatisticsByBuildingNoAndFloor(buildingNo, floor);
    }

}
