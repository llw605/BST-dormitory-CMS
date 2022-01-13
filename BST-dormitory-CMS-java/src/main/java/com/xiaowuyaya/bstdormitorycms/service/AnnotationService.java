package com.xiaowuyaya.bstdormitorycms.service;

import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;

public interface AnnotationService {

    ResponseResult addAnnotation(String content);

    ResponseResult getAnnotationList();

}
