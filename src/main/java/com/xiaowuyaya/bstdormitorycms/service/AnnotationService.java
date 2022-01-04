package com.xiaowuyaya.bstdormitorycms.service;

import com.xiaowuyaya.bstdormitorycms.entity.Annotation;
import com.xiaowuyaya.bstdormitorycms.util.JsonResult;

import java.util.Date;

public interface AnnotationService {

    JsonResult addAnnotation(String content);

    JsonResult getAnnotationList();

}
