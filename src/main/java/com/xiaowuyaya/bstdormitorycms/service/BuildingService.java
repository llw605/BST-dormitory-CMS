package com.xiaowuyaya.bstdormitorycms.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;

public interface BuildingService {

    ResponseResult getBuildingsByUniversityId(Integer universityId);

    ResponseResult getBuildingsByUniversityIdAndName(Integer universityId, String name);

    ResponseResult getBuildingList(Integer pages, Integer limit,Integer universityId);

    ResponseResult deleteAllBuildings(Integer universityId);

    ResponseResult saveBuildingList(JSONObject excelTableData);
}
