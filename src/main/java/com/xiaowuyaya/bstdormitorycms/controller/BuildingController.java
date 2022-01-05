package com.xiaowuyaya.bstdormitorycms.controller;

import com.xiaowuyaya.bstdormitorycms.service.BuildingService;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Building (控制器)
 * @author xiaowuyaya
 */
@RestController
@CrossOrigin("*")
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


}
