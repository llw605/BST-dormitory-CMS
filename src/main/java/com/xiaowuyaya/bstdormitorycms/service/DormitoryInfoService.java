package com.xiaowuyaya.bstdormitorycms.service;

import com.xiaowuyaya.bstdormitorycms.entity.DormitoryInfo;
import com.xiaowuyaya.bstdormitorycms.util.JsonResult;


public interface DormitoryInfoService {

    /**
     * 分页查询宿舍信息
     * @param pages
     * @param limit
     * @return
     */
    JsonResult getDormitoryInfoListPage(Integer pages, Integer limit);

    JsonResult getDormitoryInfoByBuildingNoAndFloor(String buildingNo, Integer floorNo);

    JsonResult updateDormitoryInfo(DormitoryInfo dormitoryInfo);

    JsonResult postDormitoryStatistics(String buildingName, String floor, Integer roomNo);

    JsonResult createDormitoryInfo(DormitoryInfo dormitoryInfo);

}
