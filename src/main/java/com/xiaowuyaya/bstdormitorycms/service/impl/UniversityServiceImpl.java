package com.xiaowuyaya.bstdormitorycms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaowuyaya.bstdormitorycms.entity.University;
import com.xiaowuyaya.bstdormitorycms.entity.User;
import com.xiaowuyaya.bstdormitorycms.entity.UserRole;
import com.xiaowuyaya.bstdormitorycms.mapper.UniversityMapper;
import com.xiaowuyaya.bstdormitorycms.mapper.UserMapper;
import com.xiaowuyaya.bstdormitorycms.mapper.UserRoleMapper;
import com.xiaowuyaya.bstdormitorycms.service.UniversityService;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    private UniversityMapper universityMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult getUniversityList() {
        List<University> universities = universityMapper.selectList(null);

        return ResponseResult.success(universities);
    }

    @Override
    public ResponseResult getUniversityById(Integer id) {
        University university = universityMapper.selectById(id);
        return ResponseResult.success(university);
    }

    @Override
    public ResponseResult addUniversity(University university) {
        int insert = universityMapper.insert(university);
        if (insert != 1){
            return ResponseResult.fail("插入失败");
        }
        return ResponseResult.success("success");
    }

    @Override
    public ResponseResult UpdateUniversityById(University university) {
        University temp = universityMapper.selectById(university.getUniversityId());
        temp.setUniversityName(university.getUniversityName());
        temp.setRole(university.getRole());
        int i = universityMapper.updateById(temp);
        if (i != 1){
            return ResponseResult.fail("插入失败");
        }
        return ResponseResult.success("success");
    }

    @Override
    public ResponseResult deleteUniversity(Integer id) {
        University university = universityMapper.selectById(id);
        String role = university.getRole();
        UserRole roleId = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("role", role));
        try{
            userMapper.delete(new QueryWrapper<User>().eq("user_role",roleId.getId()));
            userRoleMapper.deleteById(roleId.getId());
            universityMapper.deleteById(university);
        }catch (Exception e){
            log.error("删除出现问题",e);
            return ResponseResult.fail("fail");
        }
        return ResponseResult.success("success");

    }
}
