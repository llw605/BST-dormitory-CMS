package com.xiaowuyaya.bstdormitorycms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaowuyaya.bstdormitorycms.entity.Building;
import com.xiaowuyaya.bstdormitorycms.entity.DormitoryInfo;
import com.xiaowuyaya.bstdormitorycms.mapper.BuildingMapper;
import com.xiaowuyaya.bstdormitorycms.mapper.DormitoryInfoMapper;
import com.xiaowuyaya.bstdormitorycms.mapper.UniversityMapper;
import com.xiaowuyaya.bstdormitorycms.service.DormitoryInfoService;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DormitoryInfoServiceImpl implements DormitoryInfoService {

    @Autowired
    private DormitoryInfoMapper dormitoryInfoMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private UniversityMapper universityMapper;

    @Override
    public ResponseResult getDormitoryInfoListPage(Integer pages, Integer limit, Integer universityId) {

        List<Integer> buildIds = new ArrayList<>();
        for (Building university : buildingMapper.selectList(new QueryWrapper<Building>().eq("university_id", universityId))) {
            buildIds.add(university.getBuildingId());
        }

        if (buildIds.size() == 0){
            return ResponseResult.success("无数据",buildIds);
        }


        List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
        Page<DormitoryInfo> page = new Page<>(pages,limit);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.in("building_id", buildIds);

        dormitoryInfoMapper.selectPage(page,queryWrapper);
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

        return ResponseResult.success(resMap);
    }

    @Override
    public ResponseResult getDormitoryInfoByBuildingNoAndFloor(String buildingNo, Integer floorNo) {

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

        return ResponseResult.success(resMap);

    }

    @Override
    public ResponseResult getDormitoryStatisticsByBuildingNoAndFloor(String buildingNo, Integer FloorNo) {

        List<Object> list = new ArrayList<>();

        QueryWrapper<DormitoryInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("building_id",buildingNo)
                .eq("floor",FloorNo + "层")
                .isNotNull("dor_statistics")
                .gt("dor_statistics",0);

        List<DormitoryInfo> dormitoryInfos = dormitoryInfoMapper.selectList(queryWrapper);
        for (DormitoryInfo dormitoryInfo : dormitoryInfos) {
            Map<String, Object> data = new HashMap<>();
            Building building = buildingMapper.selectById(dormitoryInfo.getBuildingId());
            data.put("university",universityMapper.selectById(building.getUniversityId()).getUniversityName());
            data.put("buildingName",building.getBuildingName());
            data.put("floor", dormitoryInfo.getFloor());
            data.put("roomNo", dormitoryInfo.getRoomNo());
            data.put("counts", dormitoryInfo.getDorStatistics());

            list.add(data);
        }

        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("total", dormitoryInfoMapper.selectCount(queryWrapper));
        resMap.put("items", list);

        return ResponseResult.success(resMap);
    }

    @Override
    public ResponseResult updateDormitoryInfo(DormitoryInfo dormitoryInfo) {
        int i = dormitoryInfoMapper.updateById(dormitoryInfo);
        if (i != 1){
            return ResponseResult.fail(String.valueOf(i));
        }
        return ResponseResult.success(String.valueOf(i));
    }

    @Override
    public ResponseResult postDormitoryStatistics(String buildingName, String floor, Integer roomNo) {
//        QueryWrapper<Building> buildingQuery = new QueryWrapper<>();
//        buildingQuery.eq("building_name", buildingName);

        QueryWrapper<DormitoryInfo> dormitoryInfoQuery = new QueryWrapper<>();
        dormitoryInfoQuery.eq("building_id", buildingName)
                .like("floor", floor)
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
            return ResponseResult.fail(String.valueOf(i));
        }

        //TODO: 提交成功需要生成兑奖码返回
        return ResponseResult.success(UUID.randomUUID());
    }

    @Override
    public ResponseResult createDormitoryInfo(DormitoryInfo dormitoryInfo) {
        int insert = dormitoryInfoMapper.insert(dormitoryInfo);
        if (insert != 1){
            return ResponseResult.fail(String.valueOf(insert));
        }
        return ResponseResult.success(insert);
    }

    @Override
    public ResponseResult getDormitoryStatistics(Integer pages, Integer limit, Integer universityId) {

        List<Integer> buildIds = new ArrayList<>();
        for (Building university : buildingMapper.selectList(new QueryWrapper<Building>().eq("university_id", universityId))) {
            buildIds.add(university.getBuildingId());
        }

        if (buildIds.size() == 0){
            return ResponseResult.success("无数据",buildIds);
        }

        Page<DormitoryInfo> page = new Page<>(pages,limit);
        QueryWrapper<DormitoryInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("dor_statistics")
                .gt("dor_statistics",0)
                .in("building_id", buildIds);

        dormitoryInfoMapper.selectPage(page,queryWrapper);

        List<DormitoryInfo> records = page.getRecords();

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (DormitoryInfo record : records) {
            Map<String, Object> data = new HashMap<>();
            Building building = buildingMapper.selectById(record.getBuildingId());
            data.put("university",universityMapper.selectById(building.getUniversityId()).getUniversityName());
            data.put("buildingName",building.getBuildingName());
            data.put("floor", record.getFloor());
            data.put("roomNo", record.getRoomNo());
            data.put("counts", record.getDorStatistics());
            list.add(data);
        }

        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("total", dormitoryInfoMapper.selectCount(queryWrapper));
        resMap.put("items", list);

        return ResponseResult.success(resMap);
    }

    @Override
    public ResponseResult getInputFormCount(Integer buidingFormNum, Integer floor) {

        QueryWrapper<DormitoryInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("dor_statistics")
                .gt("dor_statistics",0);
        if (floor == null){
            queryWrapper.eq("building_id", buidingFormNum);
        }else {
            queryWrapper.eq("building_id", buidingFormNum)
                    .like("floor", floor);
        }
        List<DormitoryInfo> dormitoryInfos = dormitoryInfoMapper.selectList(queryWrapper);
        int counts = 0;
        for (DormitoryInfo dormitoryInfo : dormitoryInfos) {
            counts += dormitoryInfo.getDorStatistics();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count", counts);
        return ResponseResult.success(jsonObject);
    }


}
