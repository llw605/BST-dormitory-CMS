package com.xiaowuyaya.bstdormitorycms.service.impl;

import com.xiaowuyaya.bstdormitorycms.entity.Annotation;
import com.xiaowuyaya.bstdormitorycms.mapper.AnnotationMapper;
import com.xiaowuyaya.bstdormitorycms.service.AnnotationService;
import com.xiaowuyaya.bstdormitorycms.util.DateUtil;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.*;

/**
 * @author liaol
 */
@Service
public class AnnotationServiceImpl implements AnnotationService {

    @Autowired
    private AnnotationMapper annotationMapper;

    @SneakyThrows
    @Override
    public ResponseResult addAnnotation(String content) {

        Annotation annotation = new Annotation();

        annotation.setUploadTime(new java.sql.Date(System.currentTimeMillis()));
        annotation.setContent(content);

        int res = annotationMapper.insert(annotation);

        if (res != 1){
            return ResponseResult.fail("服务器异常");
        }
        return ResponseResult.success("success");
    }

    @Override
    public ResponseResult getAnnotationList() {
        List<Annotation> annotations = annotationMapper.selectList(null);
        return ResponseResult.success(annotations);
    }
}
