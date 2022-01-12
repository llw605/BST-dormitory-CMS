package com.xiaowuyaya.bstdormitorycms.service;

import com.xiaowuyaya.bstdormitorycms.entity.University;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import org.springframework.stereotype.Service;


public interface UniversityService {

     ResponseResult getUniversityList();

     ResponseResult addUniversity(University university);

     ResponseResult UpdateUniversityById(University university);

     ResponseResult deleteUniversity(Integer id);

}
