package com.xiaowuyaya.bstdormitorycms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaowuyaya.bstdormitorycms.entity.Building;
import com.xiaowuyaya.bstdormitorycms.entity.DormitoryInfo;
import com.xiaowuyaya.bstdormitorycms.mapper.BuildingMapper;
import com.xiaowuyaya.bstdormitorycms.mapper.DormitoryInfoMapper;
import com.xiaowuyaya.bstdormitorycms.service.DormitoryInfoService;
import com.xiaowuyaya.bstdormitorycms.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DormitoryInfoServiceImpl implements DormitoryInfoService {

    @Autowired
    private DormitoryInfoMapper dormitoryInfoMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    @Override
    public JsonResult getDormitoryInfoListPage(Integer pages, Integer limit) {

        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();

        Page<DormitoryInfo> page = new Page<>(pages,limit);
        dormitoryInfoMapper.selectPage(page,null);
        List<DormitoryInfo> records = page.getRecords();
        for (DormitoryInfo record : records) {
            Map<String, Object> data = new HashMap<String, Object>();

            Building building = buildingMapper.selectById(record.getBuildingId());
            data.put("id", record.getDormitoryId());
            data.put("buildingNo", building.getBuildingName());
            data.put("floor", record.getFloor());
            data.put("roomNo", record.getRoomNo());
            data.put("fansNo",record.getFansNo());
            data.put("sex", record.getSex());
            data.put("toFansDiff", record.getToFansDiff());
            data.put("comingTime", record.getComingTime());
            data.put("visitors", record.getVisitors());
            data.put("remarks", record.getRemarks());

            resList.add(data);
        }

        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("total", dormitoryInfoMapper.selectCount(null));
        resMap.put("items", resList);

        return JsonResult.success(resMap);
    }

    @Override
    public JsonResult getDormitoryInfoByBuildingNoAndFloor(String buildingNo, Integer floorNo) {

        List<Object> list = new ArrayList<>();

        QueryWrapper<DormitoryInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("building_id",buildingNo);
        queryWrapper.eq("floor",floorNo + "层");

        List<DormitoryInfo> dormitoryInfos = dormitoryInfoMapper.selectList(queryWrapper);

        for (DormitoryInfo dormitoryInfo : dormitoryInfos) {
            Map<String, Object> data = new HashMap<String, Object>();

            Building building = buildingMapper.selectById(dormitoryInfo.getBuildingId());
            data.put("id", dormitoryInfo.getDormitoryId());
            data.put("buildingNo", building.getBuildingName());
            data.put("floor", dormitoryInfo.getFloor());
            data.put("fansNo",dormitoryInfo.getFansNo());
            data.put("roomNo", dormitoryInfo.getRoomNo());
            data.put("sex", dormitoryInfo.getSex());
            data.put("toFansDiff", dormitoryInfo.getToFansDiff());
            data.put("comingTime", dormitoryInfo.getComingTime());
            data.put("visitors", dormitoryInfo.getVisitors());
            data.put("remarks", dormitoryInfo.getRemarks());

            list.add(data);
        }

        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("total", list.size());
        resMap.put("items", list);

        return JsonResult.success(resMap);

    }

    @Override
    public JsonResult updateDormitoryInfo(DormitoryInfo dormitoryInfo) {
        int i = dormitoryInfoMapper.updateById(dormitoryInfo);
        if (i != 1){
            return JsonResult.failed(i);
        }
        return JsonResult.success(i);
    }

    @Override
    public JsonResult postDormitoryStatistics(String buildingName, String floor, Integer roomNo) {
//        QueryWrapper<Building> buildingQuery = new QueryWrapper<>();
//        buildingQuery.eq("building_name", buildingName);

        QueryWrapper<DormitoryInfo> dormitoryInfoQuery = new QueryWrapper<>();
        dormitoryInfoQuery.eq("building_id", buildingName)
                .eq("floor", floor)
                .eq("room_no", roomNo);

        DormitoryInfo dormitoryInfo = dormitoryInfoMapper.selectOne(dormitoryInfoQuery);

        if (dormitoryInfo.getDorStatistics() == null){
            dormitoryInfo.setDorStatistics(1);
        }else if (dormitoryInfo.getDorStatistics() == 0){
            dormitoryInfo.setDorStatistics(1);
        }else {
            dormitoryInfo.setDorStatistics(dormitoryInfo.getDorStatistics() + 1);
        }

        int i = dormitoryInfoMapper.updateById(dormitoryInfo);

        if (i != 1){
            return JsonResult.failed(i);
        }

        //TODO: 提交成功需要生成兑奖码返回
        return JsonResult.success(i);
    }

    @Override
    public JsonResult createDormitoryInfo(DormitoryInfo dormitoryInfo) {
        int insert = dormitoryInfoMapper.insert(dormitoryInfo);
        if (insert != 1){
            return JsonResult.failed(insert);
        }
        return JsonResult.success(insert);
    }


}
