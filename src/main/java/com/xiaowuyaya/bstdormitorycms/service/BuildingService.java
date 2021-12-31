package com.xiaowuyaya.bstdormitorycms.service;

import com.xiaowuyaya.bstdormitorycms.util.JsonResult;
import org.springframework.stereotype.Service;


public interface BuildingService {

    JsonResult getBuildingsByUniversityId(Integer universityId);

}
