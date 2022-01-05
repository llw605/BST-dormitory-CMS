package com.xiaowuyaya.bstdormitorycms.service;


import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;

public interface BuildingService {

    ResponseResult getBuildingsByUniversityId(Integer universityId);

    ResponseResult getBuildingsByUniversityIdAndName(Integer universityId, String name);
}
