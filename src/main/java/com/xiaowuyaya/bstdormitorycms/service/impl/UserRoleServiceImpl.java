package com.xiaowuyaya.bstdormitorycms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaowuyaya.bstdormitorycms.entity.User;
import com.xiaowuyaya.bstdormitorycms.entity.UserRole;
import com.xiaowuyaya.bstdormitorycms.mapper.UserMapper;
import com.xiaowuyaya.bstdormitorycms.mapper.UserRoleMapper;
import com.xiaowuyaya.bstdormitorycms.service.UserRoleService;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult adminGetUser(Integer pages, Integer limit) {

        List<Object> resList = new ArrayList<>();

        Page<User> page = new Page<User>(pages,limit);
        userMapper.selectPage(page, null);
        for (User record : page.getRecords()) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("userName",record.getUserName());
            jsonObject.put("nickName",record.getNickName());
            jsonObject.put("password","不可查看");
            String role = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("id", record.getUserRole())).getRole();
            jsonObject.put("role",role);
            jsonObject.put("status",record.getStatus());
            jsonObject.put("phonenumber", record.getPhonenumber());
            jsonObject.put("createTime",record.getCreateTime());
            resList.add(jsonObject);
        }

        JSONObject result = new JSONObject();
        result.put("items", resList);
        result.put("total", page.getRecords().size());


        return ResponseResult.success(result);
    }

    @Override
    public ResponseResult getRolesList() {
        List<UserRole> userRoles = userRoleMapper.selectList(null);
        return ResponseResult.success(userRoles);
    }

    @Override
    public ResponseResult addUser(User user) {
        user.setCreateTime(new Date(System.currentTimeMillis()));
        int insert = userMapper.insert(user);
        if (insert != 1){
            return ResponseResult.fail("添加失败");
        }
        return ResponseResult.success("插入成功");
    }

    @Override
    public ResponseResult updateUser(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", user.getUserName());
        User temp = userMapper.selectOne(queryWrapper);
        temp.setNickName(user.getNickName());
        temp.setPhonenumber(user.getPhonenumber());
        temp.setUserRole(user.getUserRole());
        temp.setStatus(user.getStatus());
        int i = userMapper.updateById(temp);

        if (i != 1){
            return ResponseResult.fail("更新失败");
        }
        return ResponseResult.success("更新成功");
    }

    @Override
    public ResponseResult resetPassword(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", user.getUserName());
        User temp = userMapper.selectOne(queryWrapper);
        temp.setPassword(user.getPassword());
        int i = userMapper.updateById(temp);
        if (i != 1){
            return ResponseResult.fail("更新失败");
        }
        return ResponseResult.success("更新成功");
    }
}
