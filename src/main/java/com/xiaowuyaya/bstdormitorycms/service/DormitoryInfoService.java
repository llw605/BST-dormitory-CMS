package com.xiaowuyaya.bstdormitorycms.service;

import com.xiaowuyaya.bstdormitorycms.util.JsonResult;
import org.springframework.stereotype.Service;


public interface DormitoryInfoService {

    /**
     * 分页查询宿舍信息
     * @param pages
     * @param limit
     * @return
     */
    JsonResult getDormitoryInfoListPage(Integer pages, Integer limit);

}
