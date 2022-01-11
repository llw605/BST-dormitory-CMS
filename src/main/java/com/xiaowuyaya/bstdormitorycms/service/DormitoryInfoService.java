package com.xiaowuyaya.bstdormitorycms.service;

import com.xiaowuyaya.bstdormitorycms.entity.DormitoryInfo;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;


public interface DormitoryInfoService {

    /**
     * 分页查询宿舍信息
     * @param pages
     * @param limit
     * @return
     */
    ResponseResult getDormitoryInfoListPage(Integer pages, Integer limit, Integer universityId);

    ResponseResult getDormitoryInfoByBuildingNoAndFloor(String buildingNo, Integer floorNo);

    ResponseResult getDormitoryStatisticsByBuildingNoAndFloor(String buildingNo, Integer FloorNo);

    ResponseResult updateDormitoryInfo(DormitoryInfo dormitoryInfo);

    ResponseResult postDormitoryStatistics(String buildingName, String floor, Integer roomNo);

    ResponseResult createDormitoryInfo(DormitoryInfo dormitoryInfo);

    ResponseResult getDormitoryStatistics(Integer pages, Integer limit,Integer universityId);

    ResponseResult getInputFormCount(Integer buidingFormNum, Integer floor);

}
