package com.xiaowuyaya.bstdormitorycms.service.impl;

import com.xiaowuyaya.bstdormitorycms.entity.Annotation;
import com.xiaowuyaya.bstdormitorycms.mapper.AnnotationMapper;
import com.xiaowuyaya.bstdormitorycms.service.AnnotationService;
import com.xiaowuyaya.bstdormitorycms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author liaol
 */
@Service
public class AnnotationServiceImpl implements AnnotationService {

    @Autowired
    private AnnotationMapper annotationMapper;

    @Override
    public JsonResult addAnnotation( String content) {

        Date dateTime = new Date();
        Annotation annotation = new Annotation();
        annotation.setUploadTime(dateTime);
        annotation.setContent(content);

        int res = annotationMapper.insert(annotation);
        if (res != 1){
            return JsonResult.failed("服务器异常");
        }
        return JsonResult.success("插入成功");
    }

    @Override
    public JsonResult getAnnotationList() {
        List<Annotation> annotations = annotationMapper.selectList(null);
        return JsonResult.success(annotations);
    }
}
