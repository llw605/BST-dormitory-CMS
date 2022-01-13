package com.xiaowuyaya.bstdormitorycms.service;

import com.xiaowuyaya.bstdormitorycms.entity.User;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;

public interface UserRoleService {

    ResponseResult adminGetUser(Integer pages, Integer limit);

    ResponseResult getRolesList();

    ResponseResult addUser(User user);

    ResponseResult updateUser(User user);

    ResponseResult resetPassword(User user);


}
