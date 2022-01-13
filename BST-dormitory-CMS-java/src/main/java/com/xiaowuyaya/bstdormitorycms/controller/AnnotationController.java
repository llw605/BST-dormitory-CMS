package com.xiaowuyaya.bstdormitorycms.controller;

import com.xiaowuyaya.bstdormitorycms.service.AnnotationService;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

/**
 * 公告控制器
 * @author xiaowuyaya
 */
@RestController
@RequestMapping("/annotation")
public class AnnotationController {

    @Autowired
    private AnnotationService annotationService;

    @PostMapping("/addNewAnnotation")
    public ResponseResult addNewAnnotation(HttpServletRequest request, @RequestParam("content")String content){
        return annotationService.addAnnotation(content);
    }

    @GetMapping("/getAnnotationList")
    public ResponseResult getAnnotationList(){
        return annotationService.getAnnotationList();
    }

}
