package com.xiaowuyaya.bstdormitorycms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaowuyaya.bstdormitorycms.entity.Building;
import com.xiaowuyaya.bstdormitorycms.service.BuildingService;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Building (控制器)
 * @author xiaowuyaya
 */
@RestController
@RequestMapping("/building")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @GetMapping("/getBuildingsByUniversityId")
    public ResponseResult getBuildingsByUniversityId(Integer universityId){
        return buildingService.getBuildingsByUniversityId(universityId);
    }

    @GetMapping("/getBuildingsByUniversityIdAndName")
    public ResponseResult getBuildingsByUniversityIdAndName(Integer universityId, String name){
        return buildingService.getBuildingsByUniversityIdAndName(universityId, name);
    }

    @GetMapping("/getBuildingList")
    public ResponseResult getBuildingList(Integer page, Integer limit, Integer universityId){
        return buildingService.getBuildingList(page,limit,universityId);
    }

    @GetMapping("/deleteAllBuildings")
    public ResponseResult deleteAllBuildings(Integer universityId){
        return buildingService.deleteAllBuildings(universityId);
    }

    @PostMapping("/saveBuildingList")
    public ResponseResult saveBuildingList(@RequestBody JSONObject excelTableData){
        return buildingService.saveBuildingList(excelTableData);
    }


    @PostMapping("/updateBuilding")
    public ResponseResult updateBuilding(@RequestBody Building building){
        return buildingService.updateBuilding(building);
    }



}
