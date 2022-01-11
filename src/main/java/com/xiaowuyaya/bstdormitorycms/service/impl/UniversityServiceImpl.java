package com.xiaowuyaya.bstdormitorycms.service.impl;

import com.xiaowuyaya.bstdormitorycms.entity.University;
import com.xiaowuyaya.bstdormitorycms.mapper.UniversityMapper;
import com.xiaowuyaya.bstdormitorycms.service.UniversityService;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    private UniversityMapper universityMapper;

    @Override
    public ResponseResult getUniversityList() {
        List<University> universities = universityMapper.selectList(null);

        return ResponseResult.success(universities);
    }
}
