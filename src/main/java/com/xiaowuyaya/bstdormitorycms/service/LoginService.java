package com.xiaowuyaya.bstdormitorycms.service;

import com.xiaowuyaya.bstdormitorycms.entity.User;
import com.xiaowuyaya.bstdormitorycms.util.ResponseResult;

public interface LoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
