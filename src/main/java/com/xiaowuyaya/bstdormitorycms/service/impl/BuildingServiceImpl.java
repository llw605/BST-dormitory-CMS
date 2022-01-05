package com.xiaowuyaya.bstdormitorycms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaowuyaya.bstdormitorycms.entity.Building;
import com.xiaowuyaya.bstdormitorycms.mapper.BuildingMapper;
import com.xiaowuyaya.bstdormitorycms.service.BuildingService;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingMapper buildingMapper;

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
}
