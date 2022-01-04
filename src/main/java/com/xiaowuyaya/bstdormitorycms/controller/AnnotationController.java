package com.xiaowuyaya.bstdormitorycms.controller;

import com.xiaowuyaya.bstdormitorycms.entity.Annotation;
import com.xiaowuyaya.bstdormitorycms.service.AnnotationService;
import com.xiaowuyaya.bstdormitorycms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * 公告控制器
 * @author xiaowuyaya
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/annotation")
public class AnnotationController {

    @Autowired
    private AnnotationService annotationService;

    @PostMapping("/addNewAnnotation")
    public JsonResult addNewAnnotation(@RequestParam("content")String content){
        return annotationService.addAnnotation(content);
    }

    @GetMapping("/getAnnotationList")
    public JsonResult getAnnotationList(){
        return annotationService.getAnnotationList();
    }

}
