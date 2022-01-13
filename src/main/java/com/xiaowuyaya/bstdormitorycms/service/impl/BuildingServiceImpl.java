package com.xiaowuyaya.bstdormitorycms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaowuyaya.bstdormitorycms.entity.Building;
import com.xiaowuyaya.bstdormitorycms.entity.DormitoryInfo;
import com.xiaowuyaya.bstdormitorycms.entity.University;
import com.xiaowuyaya.bstdormitorycms.mapper.BuildingMapper;
import com.xiaowuyaya.bstdormitorycms.mapper.UniversityMapper;
import com.xiaowuyaya.bstdormitorycms.service.BuildingService;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private UniversityMapper universityMapper;

    @Override
    public ResponseResult getBuildingsByUniversityId(Integer universityId) {
        QueryWrapper<Building> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("university_id",universityId);
        List<Building> buildings = buildingMapper.selectList(queryWrapper);
        return ResponseResult.success(buildings);
    }

    @Override
    public ResponseResult getBuildingsByUniversityIdAndName(Integer universityId, String name) {
        QueryWrapper<Building> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("university_id",universityId)
                .like("building_name",name);

        List<Building> buildings = buildingMapper.selectList(queryWrapper);
        return ResponseResult.success(buildings);
    }

    @Override
    public ResponseResult getBuildingList(Integer pages, Integer limit, Integer universityId) {

        QueryWrapper<Building> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("university_id",universityId);

        Page<Building> page = new Page<>(pages,limit);
        buildingMapper.selectPage(page, queryWrapper);

        JSONArray array = new JSONArray();

        for (Building record : page.getRecords()) {
            JSONObject json = new JSONObject();

            University university = universityMapper.selectOne(new QueryWrapper<University>().eq("university_id", record.getUniversityId()));
            json.put("buildingId",record.getBuildingId());
            json.put("universityName",university.getUniversityName());
            json.put("buildingName",record.getBuildingName());
            json.put("floor",record.getFloor());
            array.add(json);

        }

        JSONObject res = new JSONObject();
        res.put("total", buildingMapper.selectCount(queryWrapper));
        res.put("items", array);


        return ResponseResult.success(res);
    }

    @Override
    public ResponseResult deleteAllBuildings(Integer universityId) {
        try{
            buildingMapper.delete(new QueryWrapper<Building>().eq("university_id", universityId));
        }catch (Exception e){
            return ResponseResult.fail("fail");
        }
        return ResponseResult.success("success");
    }

    @Override
    public ResponseResult saveBuildingList(JSONObject excelTableData) {
        JSONArray arr = excelTableData.getJSONArray("excelTableData");

        try{
            arr.forEach( o ->{
                JSONObject json = (JSONObject) o;
                Integer universityId = (Integer) json.get("universityId");
                String buildingName = (String) json.get("buildingName");
                Integer floor = (Integer) json.get("floor");

                Building building = new Building();
                building.setUniversityId(universityId);
                building.setBuildingName(buildingName);
                building.setFloor(floor);

                buildingMapper.insert(building);
            });
        }catch (Exception e){
            return ResponseResult.fail("fail");
        }

        return ResponseResult.success("success");
    }

    @Override
    public ResponseResult updateBuilding(Building building) {
        try{
            buildingMapper.updateById(building);
            return ResponseResult.success("success");
        }catch (Exception e){
            log.error("fail",e);
            return ResponseResult.fail("fail");
        }

    }


}
