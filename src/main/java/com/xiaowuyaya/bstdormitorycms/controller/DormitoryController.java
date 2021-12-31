package com.xiaowuyaya.bstdormitorycms.controller;

import com.xiaowuyaya.bstdormitorycms.service.DormitoryInfoService;
import com.xiaowuyaya.bstdormitorycms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public JsonResult getDormitoryInfoListPage(Integer page, Integer limit){
        return dormitoryInfoService.getDormitoryInfoListPage(page, limit);
    }

}
