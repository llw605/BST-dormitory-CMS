package com.xiaowuyaya.bstdormitorycms.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaowuyaya.bstdormitorycms.entity.StaticForm;
import com.xiaowuyaya.bstdormitorycms.entity.University;
import com.xiaowuyaya.bstdormitorycms.mapper.StaticFormMapper;
import com.xiaowuyaya.bstdormitorycms.mapper.UniversityMapper;
import com.xiaowuyaya.bstdormitorycms.service.StaticFormService;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class StaticFormServiceImpl implements StaticFormService {

    @Autowired
    private StaticFormMapper staticFormMapper;

    @Autowired
    private UniversityMapper universityMapper;

    @Override
    public ResponseResult getFormList() {

        JSONArray jsonArray = new JSONArray();
        for (StaticForm staticForm : staticFormMapper.selectList(null)) {
            JSONObject json = new JSONObject();
            University university = universityMapper.selectOne(new QueryWrapper<University>().eq("university_id", staticForm.getUniversityId()));
            json.put("id",staticForm.getId());
            json.put("universityName",university.getUniversityName());
            json.put("universityId",staticForm.getUniversityId());
            json.put("title",staticForm.getTitle());
            json.put("createTime", staticForm.getCreateTime());
            json.put("status", staticForm.getStatus()==0?"正常":"停用");
            json.put("content", staticForm.getContent());
            jsonArray.add(json);
        }
        return ResponseResult.success(jsonArray);

    }

    @Override
    public ResponseResult addForm(StaticForm staticForm) {
        staticForm.setCreateTime(new Date(System.currentTimeMillis()));
        try{
            staticFormMapper.insert(staticForm);
            return ResponseResult.success("success");
        }catch (Exception e){
            log.error("插入错误",e);
            return ResponseResult.fail("fail");
        }
    }

    @Override
    public ResponseResult updateForm(StaticForm staticForm) {
        staticForm.setCreateTime(new Date(System.currentTimeMillis()));
        try{
            staticFormMapper.updateById(staticForm);
            return ResponseResult.success("success");
        }catch (Exception e){
            log.error("更新错误",e);
            return ResponseResult.fail("fail");
        }

    }

    @Override
    public ResponseResult deleteForm(StaticForm staticForm) {
        try{
            staticFormMapper.deleteById(staticForm);
            return ResponseResult.success("success");
        }catch (Exception e){
            log.error("删除错误",e);
            return ResponseResult.fail("fail");
        }
    }

    @Override
    public ResponseResult getFormById(Integer id) {
        StaticForm staticForm = staticFormMapper.selectOne(new QueryWrapper<StaticForm>().eq("university_id", id));
        return ResponseResult.success(staticForm);
    }

}
