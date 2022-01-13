package com.xiaowuyaya.bstdormitorycms.service;

import com.xiaowuyaya.bstdormitorycms.entity.StaticForm;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;

public interface StaticFormService {

    ResponseResult getFormList();

    ResponseResult addForm(StaticForm staticForm);

    ResponseResult updateForm(StaticForm staticForm);

    ResponseResult deleteForm(StaticForm staticForm);

    ResponseResult getFormById(Integer id);
}
