package com.xiaowuyaya.bstdormitorycms.controller;

import com.xiaowuyaya.bstdormitorycms.entity.StaticForm;
import com.xiaowuyaya.bstdormitorycms.service.StaticFormService;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/form")
public class StaticFormController {

    @Autowired
    private StaticFormService staticFormService;


    @GetMapping("/getFormList")
    public ResponseResult getFormList(){
        return staticFormService.getFormList();
    }

    @PostMapping("/addForm")
    public ResponseResult addForm(@RequestBody StaticForm staticForm){
        return staticFormService.addForm(staticForm);
    }

    @PostMapping("/updateForm")
    public ResponseResult updateForm(@RequestBody StaticForm staticForm){
        return staticFormService.updateForm(staticForm);
    }

    @PostMapping("/deleteForm")
    public ResponseResult deleteForm(@RequestBody StaticForm staticForm){
        return staticFormService.deleteForm(staticForm);
    }

    @GetMapping("/getFormById")
    public ResponseResult getFormById(Integer universityId){
        System.out.println(universityId);
        return staticFormService.getFormById(universityId);
    }
}
