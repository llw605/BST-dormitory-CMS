package com.xiaowuyaya.bstdormitorycms.controller;

import com.xiaowuyaya.bstdormitorycms.entity.DormitoryInfo;
import com.xiaowuyaya.bstdormitorycms.service.DormitoryInfoService;
import com.xiaowuyaya.bstdormitorycms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public JsonResult getDormitoryInfoListPage(Integer page, Integer limit){
        return dormitoryInfoService.getDormitoryInfoListPage(page, limit);
    }

    @GetMapping("/getDormitoryInfoByBuildingNoAndFloor")
    public JsonResult getDormitoryInfoByBuildingNoAndFloor(String buildingNo, Integer floor){
        return dormitoryInfoService.getDormitoryInfoByBuildingNoAndFloor(buildingNo, floor);
    }

    @PostMapping("/updateDormitoryInfo")
    public JsonResult updateDormitoryInfo(DormitoryInfo dormitoryInfo){
        return dormitoryInfoService.updateDormitoryInfo(dormitoryInfo);
    }

    @PostMapping("/postDormitoryStatistics")
    public JsonResult postDormitoryStatistics( String buildingName, String floor, Integer roomNo){
        return dormitoryInfoService.postDormitoryStatistics( buildingName, floor, roomNo);
    }


    @PostMapping("createDormitoryInfo")
    public JsonResult createDormitoryInfo(DormitoryInfo dormitoryInfo){
        return dormitoryInfoService.createDormitoryInfo(dormitoryInfo);
    }

}
