package com.xiaowuyaya.bstdormitorycms.controller;

import com.xiaowuyaya.bstdormitorycms.entity.University;
import com.xiaowuyaya.bstdormitorycms.service.UniversityService;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * University (控制器)
 * @author xiaowuyaya
 */
@RestController
@RequestMapping("/university")
public class UniversityController {

    @Autowired
    private UniversityService universityService;


    @GetMapping("/getUniversityList")
    public ResponseResult getUniversityList(){
        return universityService.getUniversityList();
    }

    @PostMapping("/addUniversity")
    public ResponseResult addUniversity(University university){
        return universityService.addUniversity(university);
    }

    @PostMapping("/UpdateUniversityById")
    public ResponseResult UpdateUniversityById(University university){
        return universityService.UpdateUniversityById(university);
    }

    @GetMapping("/deleteUniversity")
    public ResponseResult deleteUniversity(Integer universityId){
        return universityService.deleteUniversity(universityId);
    }

    @GetMapping("/getUniversityById")
    public ResponseResult getUniversityById(Integer universityId){
        return universityService.getUniversityById(universityId);
    }



}
