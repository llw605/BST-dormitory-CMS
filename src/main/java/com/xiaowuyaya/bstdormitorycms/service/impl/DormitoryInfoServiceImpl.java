package com.xiaowuyaya.bstdormitorycms.service.impl;

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

}
